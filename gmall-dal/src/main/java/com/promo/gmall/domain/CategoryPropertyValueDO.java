package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryPropertyValueDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 类目属性项id
     */
    private Integer categoryPropertyNameId;

    /**
     * 属性对id
     */
    private Integer propertyPairId;

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