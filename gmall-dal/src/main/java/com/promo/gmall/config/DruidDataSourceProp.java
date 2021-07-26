package com.promo.gmall.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据源配置信息
 *
 * @author wuji
 */
@Data
@Component
@ConfigurationProperties(prefix = "datasource")
public class DruidDataSourceProp {

    /**
     * 驱动className
     */
    private String driverClassName;

    /**
     * 初始化创建的连接数
     */
    private Integer initSize;

    /**
     * 最大连接数量
     */
    private Integer maxActive;

    /**
     * 最小空闲连接
     */
    private Integer minIdle;


    /**
     * 数据库配置信息: url - 用户名 -密码
     */
    private String mallDbUrl;

    private String mallDbUsername;

    private String mallDbPassword;

}
