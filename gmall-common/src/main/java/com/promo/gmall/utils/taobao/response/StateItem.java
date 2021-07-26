package com.promo.gmall.utils.taobao.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class StateItem implements Serializable {

    private static final long serialVersionUID = 8073175210237950057L;

    /**
     * 城市ID
     */
    private String id;


    /**
     * 城市名称
     */
    private String name;


}
