package com.promo.gmall.utils.taobao.response;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */

import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
public class CodeInfo implements Serializable {

    private static final long serialVersionUID = -5831423881772159208L;

    /**
     * 编码
     */
    private String postCode;
}
