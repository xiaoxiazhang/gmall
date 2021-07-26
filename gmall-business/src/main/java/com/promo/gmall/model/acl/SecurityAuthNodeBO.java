package com.promo.gmall.model.acl;

import lombok.Data;

/**
 * 认证节点信息
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class SecurityAuthNodeBO {

    /**
     * 权限URL
     */
    private String permUrl;


    /**
     * permission
     */
    private String permission;


    /**
     * 权限节点描述信息
     */
    private String description;


    /**
     * 是否是菜单
     */
    private boolean isMenuNode;


    /**
     * 创建菜单节点
     */
    public static SecurityAuthNodeBO createMenuNode(String permission, String description) {
        SecurityAuthNodeBO securityAuthNodeBO = new SecurityAuthNodeBO();
        securityAuthNodeBO.setDescription(description);
        securityAuthNodeBO.setPermission(permission);
        securityAuthNodeBO.setMenuNode(true);

        return securityAuthNodeBO;
    }


    /**
     * 创建权限叶子节点节点
     */
    public static SecurityAuthNodeBO createLeafNode(String permission, String permUrl, String description) {
        SecurityAuthNodeBO securityAuthNodeBO = new SecurityAuthNodeBO();
        securityAuthNodeBO.setDescription(description);
        securityAuthNodeBO.setPermission(permission);
        securityAuthNodeBO.setPermUrl(permUrl);
        securityAuthNodeBO.setMenuNode(false);

        return securityAuthNodeBO;
    }


}
