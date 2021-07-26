package com.promo.gmall.request.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@ApiModel
public class AdminLoginRequest implements Serializable {

    private static final long serialVersionUID = -5292600175403691303L;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户密码")
    private String password;


}
