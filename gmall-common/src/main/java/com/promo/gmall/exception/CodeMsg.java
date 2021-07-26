package com.promo.gmall.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class CodeMsg implements Serializable {


    private static final long serialVersionUID = -47983502855470530L;


    /**
     * 错误异常码
     */
    private Long code;


    /**
     * 错误信息
     */
    private String message;


}
