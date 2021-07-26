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
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {


    /**
     * 服务地址
     */
    private String endpoint;


    /**
     * accessKeyId
     */
    private String accessKeyId;


    /**
     * accessKeySecret
     */
    private String accessKeySecret;


    /**
     * 资源名称
     */
    private String bucketName;


}
