package com.promo.gmall.constants;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public abstract class ErrorCodes {


    /**
     * 参数异常码
     */
    public static final Long PARAM_ERROR_CODE = 100_000_000L;


    /**
     * 状态异常码
     */
    public static final Long STATE_ERROR_CODE = 100_000_001L;


    /**
     * 业务异常码
     */
    public static final Long MALL_BIZ_ERROR_CODE = 100_000_002L;


    /**
     * 未登录异常码
     */
    public static final Long UN_LOGIN_ERROR_CODE = 100_000_003L;


    /**
     * 用户不存在异常码
     */
    public static final Long NO_EXISTS_USER_ERROR_CODE = 100_000_004L;


    /**
     * 未授权异常吗
     */
    public static final Long UN_AUTH_ERROR_CODE = 100_000_005L;


    /**
     * 系统异常码
     */
    public static final Long SYSTEM_ERROR_CODE = 100_000_999L;


}
