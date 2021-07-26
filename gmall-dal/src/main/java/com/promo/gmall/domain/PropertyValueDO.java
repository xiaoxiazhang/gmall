package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PropertyValueDO implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 属性值名称
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 属性值大写
     */
    private String nameUpper;

    private static final long serialVersionUID = 1L;
}