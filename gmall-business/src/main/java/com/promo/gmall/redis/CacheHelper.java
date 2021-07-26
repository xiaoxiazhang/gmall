package com.promo.gmall.redis;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.promo.gmall.constants.MallCommonConstants.REDIS_CACHE_REMOVAL_TOPIC;

/**
 * @author wuji
 * @Motto 他强由他强，清风拂山冈；他横任他横，明月照大江。
 * @since 1.0.3.6
 */
@Component
public class CacheHelper {

    /**
     * 缓存日志对象
     */
    private Logger log = LoggerFactory.getLogger("CacheLogger");


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 设置本地缓存
     */
    private Cache<String, Object> localCache = CacheBuilder.newBuilder()
            .maximumSize(2 * 1024L)
            .softValues()
            .expireAfterWrite(2 * 3600, TimeUnit.SECONDS)
            .recordStats()
            .removalListener(removalNotification -> {
                log.info("localCache onRemoval. key: {}, cause: {}", removalNotification.getKey(), removalNotification.getCause());
            })
            .build();


    // =================================key操作=================================

    /**
     * 判断redis key是否存在
     */
    public boolean exists(String key) {
        Boolean isExists = redisTemplate.hasKey(key);
        return isExists != null && isExists;
    }


    /**
     * 设置redis key过期时间
     */
    public void expired(String key, int expireTime) {
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);

    }

    /**
     * 通过keys删除对应缓存
     */
    public void delete(String... keys) {
        redisTemplate.delete(Arrays.asList(keys));
        log.info("delete cache, keys: {}", Arrays.toString(keys));
    }


    //=================================incr计数操作=================================

    /**
     * @param key   键
     * @param delta 步长
     * @return 返回键自增后的值
     */
    public long incrBy(String key, long delta) {
        Long increment = redisTemplate.opsForValue().increment(key, delta);
        Assert.notNull(increment, "increment must not be null");
        return increment;
    }


    //================================value缓存操作=================================


    /**
     * 设置redis缓存，不带过期时间
     */
    public <T> void set(String key, T value) {
        if (Objects.isNull(value)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
        log.info("put redis cache, key: {}", key);
    }


    /**
     * 设置redis缓存，加上过期时间
     */
    public <T> void set(String key, T value, long expireTime) {
        if (Objects.isNull(value)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
        log.info("put redis cache, key: {}, expire_time: {}s", key, expireTime);
    }


    /**
     * 通过key获取redis缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (null == obj) {
            return null;
        }
        return (T) obj;
    }


    /**
     * @param key        键
     * @param supplier   供给型函数
     * @param expireTime 过期时间（s）
     * @return 返回缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T getOrCreate(String key, Supplier<T> supplier, long expireTime) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (null != obj) {
            return (T) obj;
        }
        T result = supplier.get();

        // 不缓存null
        if (null != result) {
            set(key, result, expireTime);
        }
        return result;
    }


    /**
     * 批量设置缓存，使用pipeline提升效率
     */
    public <T> void multiSet(Map<String, ? extends T> map, long expireTime) {
        Assert.isTrue(expireTime > 0, "ExpireTime must large than zero.");

        if (CollectionUtils.isEmpty(map)) {
            return;
        }

        redisTemplate.executePipelined((RedisCallback<T>) connection -> {
            map.forEach((key, value) -> {
                @SuppressWarnings("rawtypes")
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                byte[] rawKey = keySerializer.serialize(key);

                @SuppressWarnings("rawtypes")
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                byte[] rawValue = valueSerializer.serialize(value);

                long randomExpireTime = expireTime + ThreadLocalRandom.current().nextLong(10);
                connection.setEx(rawKey, randomExpireTime, rawValue);
                log.info("put redis cache, key: {}, expire_time: {}s", key, expireTime);
            });

            return null;
        });
    }


    /**
     * 批量通过keys获取缓存中的值，如果key对应的缓存不存在，集合对应位置的值为null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> multiGet(List<String> keys) {
        Assert.notEmpty(keys, "keys must not be empty");

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        List<Object> objs = redisTemplate.opsForValue().multiGet(keys);
        List<T> result = Lists.newArrayListWithCapacity(objs.size());
        objs.forEach(obj -> {
            result.add((T) obj);
        });

        return result;
    }


    /**
     * 批量通过keys获取缓存中的值，并封装成对应map
     */
    public <T> Map<String, T> getCacheMapByKeys(List<String> keys) {
        // 获取缓存中对象
        List<T> cacheDataList = multiGet(keys);
        Map<String, T> result = new HashMap<>(cacheDataList.size());
        for (int i = 0; i < keys.size(); i++) {
            result.put(keys.get(i), cacheDataList.get(i));
        }
        return result;
    }


    /**
     * 通过批量id查询缓存数据，未命中缓存通过函数表达式获取数据并加入缓存
     */
    public <T> Map<Long, T> batchGetWithCache(List<Long> ids,
                                              String cacheKeyPrefix,
                                              long expireTime,
                                              Function<List<Long>, Map<Long, T>> getDataFunction) {
        if (CollectionUtils.isEmpty(ids)) {
            return Maps.newHashMap();
        }

        // id去重
        ids = ids.stream().distinct().collect(Collectors.toList());

        Map<Long, T> result = new HashMap<>(ids.size());

        List<String> keys = buildKeys(cacheKeyPrefix, ids);
        Map<String, T> cachedDataMap = getCacheMapByKeys(keys);


        // 获取未命中缓存ID集合
        List<Long> notExistIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cachedDataMap)) {
            List<Long> finalNotExistIds = notExistIds;
            cachedDataMap.forEach((key, value) -> {
                Long id = removeCacheKey(key, cacheKeyPrefix);
                if (value != null) {
                    result.put(id, value);
                } else {
                    finalNotExistIds.add(id);
                }
            });
        } else {
            notExistIds = ids;
        }

        if (notExistIds.isEmpty()) {
            return result;
        }

        // 查询未命中缓存数据
        Map<Long, T> unCachedDataMap = getDataFunction.apply(notExistIds);
        if (CollectionUtils.isEmpty(unCachedDataMap)) {
            return result;
        }

        // 将未命中的数据加入缓存
        Map<String, T> keyListMap = new HashMap<>(unCachedDataMap.size());
        unCachedDataMap.forEach((id, data) -> {
            result.put(id, data);
            keyListMap.put(cacheKeyPrefix + id, data);
        });
        multiSet(keyListMap, expireTime);

        return result;
    }


    /**
     * 通过【 缓存前缀 + id 】构造对应缓存key
     */
    private List<String> buildKeys(String cacheKeyPrefix, List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }

        return ids.stream().map(e -> cacheKeyPrefix + e).collect(Collectors.toList());
    }


    /**
     * 字符串截取掉缓存前缀
     */
    private Long removeCacheKey(String cacheKey, String prefix) {
        return Long.valueOf(cacheKey.substring(prefix.length()));
    }


    //=================================list队列操作=================================

    /**
     * @param key   键
     * @param value 值
     * @return 执行 LPUSH命令后，返回列表的长度。
     */
    public long leftPush(String key, Object value) {
        Long length = redisTemplate.opsForList().leftPush(key, value);
        Assert.notNull(length, "length must not be null");
        return length;
    }


    /**
     * @param key   键
     * @param value 值
     * @return 执行 RPUSH命令后，返回列表的长度。
     */
    public long rightPush(String key, Object value) {
        Long length = redisTemplate.opsForList().rightPush(key, value);
        Assert.notNull(length, "length must not be null");
        return length;
    }

    /**
     * @param key 键
     * @return 列表key的头元素。
     */
    @SuppressWarnings("unchecked")
    public <T> T leftPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @param key 键
     * @return 列表key的头元素。
     */
    @SuppressWarnings("unchecked")
    public <T> T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }


    /**
     * @param key   键
     * @param start 0 表示列表的第一个元素, -1 表示列表的最后一个元素, -2 表示列表的倒数第二个元素
     * @param end   结束索引
     * @return 一个列表，包含指定区间内的元素, 如果没有元素或者key不存在返回空列表
     * 如果key不是list类型,会抛出CacheException
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> lRange(String key, final long start, final long end) {
        List<Object> rangeList = redisTemplate.opsForList().range(key, start, end);
        if (rangeList == null) {
            return Lists.newArrayList();
        }
        List<T> result = Lists.newArrayList();
        rangeList.forEach(obj -> result.add((T) obj));
        return result;
    }

    /**
     * 获取list类型数据的长度，如果不存在指定的key，则返回0
     *
     * @param key 键
     * @return 如果key不是list类型, 会抛出CacheException
     */
    public long lLen(String key) {
        Long size = redisTemplate.opsForList().size(key);
        Assert.notNull(size, "size must not be null");
        return size;
    }


    // =================================二级缓存操作=================================

    /**
     * 同步设置一二级缓存
     *
     * @param expireTime 缓存过期时间,单位秒
     */
    public <T> void putTwoLevelCache(String key, T value, long expireTime) {
        localCache.put(key, value);
        set(key, value, expireTime);
    }


    /**
     * @param key        键
     * @param supplier   供给型函数
     * @param expireTime 过期时间（s）
     * @return 返回缓存
     */
    @SuppressWarnings("unchecked")
    public <T> T getOrCreateWithTwoLevelCache(String key, Supplier<T> supplier, long expireTime) {

        // 本地一级缓存中获取
        T localCacheData = (T) localCache.getIfPresent(key);
        if (localCacheData != null) {
            return localCacheData;
        }

        // redis二级缓存中获取
        Object obj = get(key);
        if (null != obj) {
            localCache.put(key, obj);
            return (T) obj;
        }

        // 调用supplier#get获取数据值，并缓存在一二级缓存中【不缓存null】
        T result = supplier.get();
        if (null != result) {
            set(key, result, expireTime);
            localCache.put(key, result);
        }

        return result;
    }


    /**
     * 从一二级缓存中获取数据，数据不存在使用函数表达式加载数据并添加到本地缓存和Redis缓存中。
     */
    public <T> Map<Long, T> batchGetWithTwoLevelCache(List<Long> ids,
                                                      String cacheKeyPrefix,
                                                      long expireTime,
                                                      Function<List<Long>, Map<Long, T>> getDataFunction) {
        if (CollectionUtils.isEmpty(ids)) {
            return Maps.newHashMap();
        }

        // id去重
        ids = ids.stream().distinct().collect(Collectors.toList());

        Map<Long, T> result = new HashMap<>(ids.size());

        List<String> keys = buildKeys(cacheKeyPrefix, ids);

        // 1. 从本地缓存中获取
        Map<String, Object> allPresent = localCache.getAllPresent(keys);
        allPresent.forEach((key, value) -> {
            result.put(removeCacheKey(key, cacheKeyPrefix), (T) value);
        });

        // 本地缓存中不存在的keys
        List<String> localMissedKeys = keys.stream().filter(e -> !allPresent.containsKey(e)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(localMissedKeys)) {
            return result;
        }

        // 2. 从redis中获取缓存数据
        Map<String, T> cachedDataMap = getCacheMapByKeys(localMissedKeys);

        // 获取未命中缓存ID集合
        List<Long> redisCacneMissedIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(cachedDataMap)) {
            List<Long> finalNotExistIds = redisCacneMissedIds;
            cachedDataMap.forEach((key, value) -> {
                Long id = removeCacheKey(key, cacheKeyPrefix);
                if (value != null) {
                    // 设置本地缓存
                    localCache.put(key, value);
                    result.put(id, value);
                } else {
                    finalNotExistIds.add(id);
                }
            });
        } else {
            redisCacneMissedIds = ids;
        }

        if (redisCacneMissedIds.isEmpty()) {
            return result;
        }

        // 3. 查询未命中缓存数据
        Map<Long, T> unCachedDataMap = getDataFunction.apply(redisCacneMissedIds);
        if (CollectionUtils.isEmpty(unCachedDataMap)) {
            return result;
        }

        // 从函数表达式中获取未命中的数据 并加入缓存
        Map<String, T> keyListMap = new HashMap<>(unCachedDataMap.size());
        unCachedDataMap.forEach((id, data) -> {
            result.put(id, data);
            keyListMap.put(cacheKeyPrefix + id, data);
        });


        multiSet(keyListMap, expireTime);
        keyListMap.forEach((key, value) -> {
            localCache.put(key, value);
        });

        return result;
    }


    /**
     * 清除redis, 缓存并通知删除本地缓存keys
     */
    public void deleteAndPublish(String... keys) {
        delete(keys);

        String keysStr = String.join(",", keys);
        redisTemplate.convertAndSend(REDIS_CACHE_REMOVAL_TOPIC, keysStr);
    }


    /**
     * 清除本地缓存keys
     */
    public void clearLocalCache(String... keys) {
        localCache.invalidateAll(Arrays.asList(keys));
    }


    /**
     * 获取本地缓存统计信息
     */
    public CacheStats getLocalCacheStats() {
        return localCache.stats();
    }


}
