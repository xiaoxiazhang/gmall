package com.promo.gmall.utils.taobao.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class StateQueryRequest implements Serializable {

    private static final long serialVersionUID = 4876236652827367714L;


    private String sn = "suibianchuan";

    /**
     * 父地区ID
     */
    private String pid;


}
