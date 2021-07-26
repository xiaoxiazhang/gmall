package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PropertyNameDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 属性项名称
     */
    private String name;

    /**
     * 是否是关键属性，1是，0否
     */
    private Integer isKeyProperty;

    /**
     * 是否是销售属性，1是，0否
     */
    private Integer isSellProperty;

    /**
     * 是否是商品属性，1是，0否
     */
    private Integer isGoodsProperty;

    /**
     * 1单选(不可自定义)，2单选(允许自定义)，3多选(不可自定义)，4多选(允许自定义)，5文本输入框
     */
    private Integer inputType;

    /**
     * 1可修改，2不可修改
     */
    private Integer modifyType;

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