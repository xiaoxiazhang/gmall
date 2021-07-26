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
public class RegionDataGroup implements Serializable {

    private static final long serialVersionUID = -7809263421628524298L;

    /**
     * 查询地区数据集合
     */
    private List<RegionData> regionDatas;


}
