package com.promo.gmall.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OverseaAddressDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父资源ID
     */
    private Long pResourceId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 邮政编码
     */
    private String postCode;

    /**
     * 等级: 1, 2, 3
     */
    private Integer level;

    /**
     * 0:未删除 1:删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    private static final long serialVersionUID = 1L;
}