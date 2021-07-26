package com.promo.gmall.utils.taobao.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
@Data
public class StateInfo implements Serializable {

    private static final long serialVersionUID = -426405731651946066L;

    /**
     * id
     */
    private String id;


    /**
     *
     */
    private String cnName;


    /**
     * 名称
     */
    private String name;


}
