package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CategoryPropertyNameDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 类目id
     */
    private Integer categoryId;

    /**
     * 属性项id
     */
    private Integer propertyNameId;

    /**
     * 是否必填, 0否，1是
     */
    private Integer isRequired;

    /**
     * 1单选框(选项不可自定义)，2单选框(属性允许自定义)，3多选框(选项不可自定义)，4多选框(选项允许自定义)，5文本输入框
     */
    private Integer inputType;

    /**
     * 属性类型，1关键属性，2销售属性，3商品属性
     */
    private Integer propertyType;

    /**
     * 排序（倒序）
     */
    private Integer sortOrder;

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