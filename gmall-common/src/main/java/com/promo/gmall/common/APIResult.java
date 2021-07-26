package com.promo.gmall.common;

import com.promo.gmall.exception.CodeMsg;
import com.promo.gmall.exception.MallBusinessException;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wuji
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
public class APIResult<T> implements Serializable {

    private static final long serialVersionUID = 1994804735489497631L;

    /**
     * 接口错误返回状态
     */
    private static final int FAIL_STATUS = 0;

    /**
     * 接口正确返回状态
     */
    private static final int SUCCESS_STATUS = 1;


    /**
     * 模型数据
     */
    private T data;

    /**
     * 接口返回状态 0 ==> 失败 ； 1==> 成功
     */
    private Integer status;


    /**
     * 业务编码
     */
    private Long code;

    /**
     * 接口提示信息
     */
    private String message;


    public static <T> APIResult<T> success() {
        return new APIResult<T>()
                .setCode(1L)
                .setMessage("请求成功")
                .setStatus(SUCCESS_STATUS)
                .setData(null);

    }


    public static <T> APIResult<T> success(T data) {
        return new APIResult<T>()
                .setCode(1L)
                .setMessage("请求成功")
                .setStatus(SUCCESS_STATUS)
                .setData(data);

    }


    public static <T> APIResult<T> fail(MallBusinessException exception) {
        CodeMsg codeMsg = exception.getCodeMsg();
        return new APIResult<T>()
                .setStatus(FAIL_STATUS)
                .setCode(codeMsg.getCode())
                .setMessage(codeMsg.getMessage());
    }


    public static <T> APIResult<T> fail(CodeMsg codeMsg) {
        return new APIResult<T>()
                .setStatus(FAIL_STATUS)
                .setCode(codeMsg.getCode())
                .setMessage(codeMsg.getMessage());
    }


}
