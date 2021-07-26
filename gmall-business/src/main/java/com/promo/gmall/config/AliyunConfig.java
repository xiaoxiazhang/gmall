package com.promo.gmall.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.promo.gmall.model.properties.AliyunOssProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Configuration
public class AliyunConfig {

    @Bean(destroyMethod = "shutdown")
    public OSSClient ossClient(AliyunOssProperties aliyunOssProperties) {
        return (OSSClient) new OSSClientBuilder().build(
                aliyunOssProperties.getEndpoint(),
                aliyunOssProperties.getAccessKeyId(),
                aliyunOssProperties.getAccessKeySecret());
    }


}
