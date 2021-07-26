package com.promo.gmall.request.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@ApiModel
@Data
public class MetaDataUpdateRequest implements Serializable {

    private static final long serialVersionUID = 5820869915967394866L;


    @NotNull(message = "id不能为空")
    @ApiModelProperty("id")
    private Long id;


    @NotEmpty
    @Length(min = 1, max = 128, message = "名称长度必须是1-128个字符")
    @ApiModelProperty("名称")
    private String name;


    @NotEmpty
    @Length(min = 1, max = 128, message = "名称长度必须是1-128个字符")
    @ApiModelProperty("描述信息")
    private String description;


    @NotEmpty
    @Range(min = 1, max = 3, message = "元数据类型不存在【1-3】")
    @ApiModelProperty("元数据类型: 1-材质 2-主题 3-印刷方式")
    private Integer type;


}
