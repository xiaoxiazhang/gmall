package com.promo.gmall.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里.
 * @since 1.0.0
 */
@Slf4j
@Component
public class RedisDistributedLock {

    /**
     * 释放分布式锁脚本
     */
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";


    private ThreadLocal<String> lockFlag = new ThreadLocal<>();

    /**
     * 释放锁正确返回值
     */
    private static final Long RELEASE_SUCCESS = 1L;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * 获取分布式锁
     *
     * @param lockKey 锁
     * @param expire  锁重试过期时间
     * @return 是否成功获取锁
     */
    public boolean tryLock(String lockKey, long expire) {
        // 获取并设置线程请求唯一ID
        String uuid = UUID.randomUUID().toString();
        lockFlag.set(uuid);

        Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, expire, TimeUnit.SECONDS);
        return result != null && result;
    }


    /**
     * 性能较低，线程每次都要等待固定时间，才能获取锁。
     *
     * @param lockKey     锁
     * @param expire      过期时间
     * @param retryTimes  重试次数
     * @param sleepMillis 线程随眠时间
     * @return 是否成功获取锁
     */
    public void lock(String lockKey, long expire, int retryTimes, long sleepMillis) {

        // 如果获取锁失败，按照传入的重试次数进行重试
        while ((!tryLock(lockKey, expire)) && retryTimes-- > 0) {
            try {
                // 线程睡眠，防止空转导致cpu过高
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                // 使用异常中断程序
                throw new IllegalStateException("InterruptedException error", e);

            } catch (Throwable throwable) {
                log.error("Retry lock error, count==> {}", retryTimes, throwable);
            }

        }

        if (retryTimes <= 0) {
            throw new IllegalStateException("获取锁失败.");
        }

    }


    /**
     * 释放分布式锁
     *
     * @param lockKey 锁
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptSource(new StaticScriptSource(RELEASE_LOCK_SCRIPT));
        script.setResultType(Long.class);

        String requestId = lockFlag.get();
        Object result = redisTemplate.execute(script, Collections.singletonList(lockKey), requestId);

        return RELEASE_SUCCESS.equals(result);
    }

}


