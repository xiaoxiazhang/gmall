package com.promo.gmall.constants;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class MallCommonConstants {


    /**
     * 系统用户session
     */
    public static final String SYSTEM_USER_SESSION = "USER";


    /**
     * cookie用户名
     */
    public static final String COOKIE_NAME = "cookie_username";


    /**
     * redis 缓存清理通知
     */
    public static final String REDIS_CACHE_REMOVAL_TOPIC = "cache.change";


    /**
     * token有效时间
     */
    public static final Integer TOKEN_VALID_TIME = 2 * 3600;


    /**
     * cookie有效时间
     */
    public static final Integer COOKIE_TIME = 24 * 3600;


}
