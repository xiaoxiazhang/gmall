package com.promo.gmall.redis.listener;

import com.promo.gmall.redis.CacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Slf4j
@Component
public class RedisCacheChangeListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CacheHelper cacheHelper;


    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);

        RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
        String body = (String) valueSerializer.deserialize(message.getBody());
        log.info("Redis cache change listener. channel: {}, body: {} ", channel, body);

        // 清除本地一级缓存
        String[] keys = body.split(",");
        cacheHelper.clearLocalCache(keys);
    }

}
