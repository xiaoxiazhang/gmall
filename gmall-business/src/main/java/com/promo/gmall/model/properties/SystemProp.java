package com.promo.gmall.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "system.prop")
public class SystemProp {

    /**
     * 环境变量值
     */
    private String env;


    /**
     * 缓存前缀
     */
    private String cachePrefix;


}
