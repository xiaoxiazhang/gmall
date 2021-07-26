package com.promo.gmall.model.acl;

import com.promo.gmall.domain.AclUserDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class AclUserBO implements Serializable {

    private static final long serialVersionUID = 8738903000480871716L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户名(唯一)
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 加密盐值
     */
    private String salt;


    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;


    /**
     * 用户权限列表
     */
    private List<AclPermissionBO> permissionBOList = Collections.emptyList();


    public static AclUserBO from(AclUserDO aclUserDO) {
        AclUserBO aclUserBO = new AclUserBO();

        BeanUtils.copyProperties(aclUserDO, aclUserBO);
        return aclUserBO;
    }


}
