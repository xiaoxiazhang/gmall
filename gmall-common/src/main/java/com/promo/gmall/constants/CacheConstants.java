package com.promo.gmall.constants;

/**
 * @author wuji
 * @Motto 他强由他强，清风拂山冈；他横任他横，明月照大江。
 * @since 1.0.0
 */
public abstract class CacheConstants {

    private CacheConstants() {
    }

    //=================================================================================
    // 缓存时间常量
    //=================================================================================

    // 1分钟
    public static final int ONE_MINITES = 60;

    // 2分钟
    public static final int TWO_MINITES = 2 * 60;

    // 5分钟
    public static final int FIVE_MINITES = 5 * 60;

    // 30分钟
    public static final int HALF_HOURS = 30 * 60;

    // 1小时
    public static final int ONE_HOURS = 60 * 60;

    // 2小时
    public static final int TWO_HOURS = 2 * 60 * 60;

    // 10小时
    public static final int TEN_HOURS = 10 * 60 * 60;

    // 1天
    public static final int ONE_DAYS = 24 * 60 * 60;

    // 3天
    public static final int THREE_DAYS = 3 * 24 * 60 * 60;


    //=================================================================================
    // common
    //=================================================================================

    /**
     * token缓存模板
     */
    public static final String SECURITY_TOKEN_KEY = "TOKEN_%s";


    //=================================================================================
    // 元数据
    //=================================================================================

    public static final String META_TYPE_KEY = "M_T_%s";


}
