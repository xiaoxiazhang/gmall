package com.promo.gmall.utils;

import com.promo.gmall.constants.ErrorCodes;
import com.promo.gmall.exception.CodeMsg;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public abstract class CodeMsgHelper {


    public static CodeMsg buildParamError(String message) {
        return new CodeMsg(ErrorCodes.PARAM_ERROR_CODE, message);
    }


    public static CodeMsg buildBusinessError(String message) {
        return new CodeMsg(ErrorCodes.MALL_BIZ_ERROR_CODE, message);
    }


    public static CodeMsg buildServerError(String message) {
        return new CodeMsg(ErrorCodes.SYSTEM_ERROR_CODE, message);
    }


}
