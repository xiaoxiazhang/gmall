package com.promo.gmall.utils.taobao.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
@Data
public class DivisionGroup implements Serializable {

    private static final long serialVersionUID = 3962923746561454519L;

    /**
     * 组合标签
     */
    private String label;

    /**
     *
     */
    private List<StateItem> items;
}
