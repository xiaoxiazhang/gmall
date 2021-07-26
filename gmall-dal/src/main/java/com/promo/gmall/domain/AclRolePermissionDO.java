package com.promo.gmall.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AclRolePermissionDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 关联auth_role的主键
     */
    private Long authRoleId;

    /**
     * 关联auth_permission的主键
     */
    private Long authPermissionId;

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