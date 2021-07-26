package com.promo.gmall.redis;

import com.promo.gmall.model.properties.SystemProp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author wuji
 * @Motto 十年寒窗苦读，只为程序一生
 * @since 2018-11-24
 */
@Slf4j
@Component
public class StringSerializer implements RedisSerializer<String> {

    @Autowired
    private SystemProp systemProp;

    private final Charset charset;


    public StringSerializer() {
        this(StandardCharsets.UTF_8);
    }


    public StringSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public byte[] serialize(String key) {
        String redisCachePrefix = systemProp.getCachePrefix();
        return (key == null ? null : (redisCachePrefix + ":" + key).getBytes(charset));
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

}

