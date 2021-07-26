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
public class RegionData implements Serializable {

    private static final long serialVersionUID = -8181397882408354194L;


    /**
     * id
     */
    private String id;


    /**
     * 名称
     */
    private String name;


    /**
     * 父节点信息
     */
    private List<StateInfo> parent;


    /**
     * 编码集合
     */
    private List<CodeInfo> pcList;


    /**
     * 删除状态
     */
    private String delFlag;


}
