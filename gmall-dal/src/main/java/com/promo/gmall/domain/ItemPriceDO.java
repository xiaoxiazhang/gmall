package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemPriceDO implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 逻辑外键，商品ID
     */
    private Long itemId;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 层级对应价格
     */
    private Integer price;

    /**
     * 生产时间：单位天
     */
    private Integer productTime;

    /**
     * 是否逻辑删除
     */
    private Integer isDeleted;

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