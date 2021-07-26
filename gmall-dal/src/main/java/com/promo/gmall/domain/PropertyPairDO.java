package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PropertyPairDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 属性项id
     */
    private Integer propertyNameId;

    /**
     * 属性值id
     */
    private Integer propertyValueId;

    /**
     * 排序
     */
    private Integer order;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}