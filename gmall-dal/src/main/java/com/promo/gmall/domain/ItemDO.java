package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ItemDO implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 平台类别
     */
    private String category;

    /**
     * 概要
     */
    private String features;

    /**
     * 描述
     */
    private String description;

    /**
     * 主题
     */
    private String theme;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 叶子类目id
     */
    private Integer categoryId;

    /**
     * 类目id全路径，eg. /1/2/3/4
     */
    private String categoryPath;

    /**
     * 产品颜色
     */
    private String productColor;

    /**
     * 材质
     */
    private String material;

    /**
     * 产品尺寸
     */
    private String productSize;

    /**
     * 印刷方式
     */
    private String imprintingMethods;

    /**
     * 印刷位置
     */
    private String imprintLocation;

    /**
     * logo尺寸
     */
    private String imprintSize;

    /**
     * 装运尺寸长度
     */
    private Integer shippingDimensionsLength;

    /**
     * 装箱尺寸宽度
     */
    private Integer shippingDimensionsWidth;

    /**
     * 装箱尺寸高度
     */
    private Integer shippingDimensionsHeight;

    /**
     * 装箱重量 单位克
     */
    private Integer shippingWeight;

    /**
     * 装箱数量
     */
    private Integer packageCount;

    /**
     * 产品包装方式
     */
    private String insidePacking;

    /**
     * 订制费用
     */
    private Integer setupCharge;

    /**
     * 上下架状态，0-下架，1-上架
     */
    private Integer shelf;

    /**
     * 修改版本号，用于实现乐观锁
     */
    private Integer version;

    /**
     * 是否逻辑删除
     */
    private Integer isDeleted;

    /**
     * 商品扩展信息
     */
    private String extra;

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