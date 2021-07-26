package com.promo.gmall.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AclRoleDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 角色标识
     */
    private String role;

    /**
     * 描述
     */
    private String description;

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