package com.promo.gmall.exception;

import com.promo.gmall.constants.ErrorCodes;

import static com.promo.gmall.constants.ErrorCodes.UN_AUTH_ERROR_CODE;
import static com.promo.gmall.constants.ErrorCodes.UN_LOGIN_ERROR_CODE;

/**
 * 业务异常枚举,统一管理业务异常
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public enum BizErrorCodeEnum {


    /**
     * 未登录异常
     */
    UN_LOGIN_ERROR(new CodeMsg(UN_LOGIN_ERROR_CODE, "系统未登录")),


    /**
     * 用户不存在
     */
    NO_EXISTS_USER_ERROR_CODE(new CodeMsg(ErrorCodes.NO_EXISTS_USER_ERROR_CODE, "用户不存在")),


    /**
     * 未授权异常
     */
    UN_AUTH_ERROR(new CodeMsg(UN_AUTH_ERROR_CODE, "未授权异常")),

    ;

    private CodeMsg codeMsg;


    BizErrorCodeEnum(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }


    public Long getCode() {
        return codeMsg.getCode();
    }

    public String getMessage() {
        return codeMsg.getMessage();
    }

}
