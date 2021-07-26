package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HomeResourceDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类目配置
     */
    private String categoryConfig;

    /**
     * banner配置
     */
    private String bannerConfig;

    /**
     * topSellling配置
     */
    private String topSaleConfig;

    /**
     * 新品配置
     */
    private String newItemConfig;

    /**
     * 猜你喜欢配置
     */
    private String youMayLikeConfig;

    /**
     * 是否逻辑删除，0-未删除，1-已删除
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