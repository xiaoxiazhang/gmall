package com.promo.gmall.response.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@ApiModel
public class AclUserVO implements Serializable {

    private static final long serialVersionUID = 3592971550020638562L;


    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("用户名")
    private String username;


    @ApiModelProperty("用户邮箱")
    private String email;


    @ApiModelProperty("创建时间")
    private Date gmtCreate;


}
