package com.promo.gmall.domain;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserDO implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 用户头像URL
     */
    private String image;

    /**
     * 账号密码
     */
    private String password;

    /**
     * 邮箱号
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态
     */
    private Integer status;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 注册版本
     */
    private String version;

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