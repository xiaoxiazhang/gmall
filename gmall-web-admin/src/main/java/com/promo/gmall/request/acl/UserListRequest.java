package com.promo.gmall.request.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@ApiModel
public class UserListRequest implements Serializable {

    private static final long serialVersionUID = -7946724830313173183L;

    @ApiModelProperty("用户名")
    private String username;


    @ApiModelProperty("用户邮箱")
    private String email;


}
