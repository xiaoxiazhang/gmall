package com.promo.gmall.utils.taobao.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class DivisionGroupData implements Serializable {

    private static final long serialVersionUID = 5395665779424871849L;


    /**
     * 美国州组合数据
     */
    private List<DivisionGroup> divisionGroups;


}
