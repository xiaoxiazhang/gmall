package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FrontCategoryDO implements Serializable {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 前台类目名称
     */
    private String name;

    /**
     * 类目关键词【最多可以添加10个】
     */
    private String keyword;

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