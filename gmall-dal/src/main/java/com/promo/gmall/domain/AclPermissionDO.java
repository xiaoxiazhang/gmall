package com.promo.gmall.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AclPermissionDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 父节点(0表示无父节点)
     */
    private Long parentId;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 权限url
     */
    private String permUrl;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 菜单排序
     */
    private Integer orderNum;

    /**
     * 菜单icon
     */
    private String menuIcon;

    /**
     * 描述
     */
    private String description;

    /**
     * 0:未启用 1:启用
     */
    private Integer isActive;

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