package com.promo.gmall.config;

import com.promo.gmall.redis.listener.RedisCacheChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import static com.promo.gmall.constants.MallCommonConstants.REDIS_CACHE_REMOVAL_TOPIC;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Configuration
public class RedisListenerConfig {

    @Autowired
    private RedisCacheChangeListener redisCacheChangeListener;


//    @Bean
//    public MessageListenerAdapter messageListenerAdapter(){
//        return new MessageListenerAdapter(new RedisChannelListener());
//    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(redisCacheChangeListener, new PatternTopic(REDIS_CACHE_REMOVAL_TOPIC));
        return container;
    }

}
