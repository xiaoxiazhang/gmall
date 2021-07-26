package com.promo.gmall.model.acl;

import lombok.Data;

import java.util.Date;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class AclPermissionBO {

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


}
