package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemImageDO implements Serializable {
    /**
     * 单表自增主键，没有业务含义
     */
    private Long id;

    /**
     * 商品id
     */
    private Long itemId;

    /**
     * 图片类型 0-商品主图，1-商品白底图
     */
    private Integer type;

    /**
     * 图片url
     */
    private String url;

    /**
     * 图片宽度
     */
    private Integer width;

    /**
     * 图片高度
     */
    private Integer height;

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

    /**
     * 是否逻辑删除，0-未删除，1-已删除
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}