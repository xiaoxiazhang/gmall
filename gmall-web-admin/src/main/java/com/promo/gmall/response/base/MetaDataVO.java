package com.promo.gmall.response.base;

import com.promo.gmall.model.base.MetaDataBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
@ApiModel
public class MetaDataVO implements Serializable {

    private static final long serialVersionUID = -4520913629254460255L;


    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("名称")
    private String name;


    @ApiModelProperty("描述信息")
    private String description;


    @ApiModelProperty("元数据类型: 1-材质 2-主题 3-印刷方式")
    private Integer type;


    @ApiModelProperty("创建时间")
    private Date createTime;


    @ApiModelProperty("更新时间")
    private Date updateTime;


    public static MetaDataVO from(MetaDataBO metaDataBO) {
        MetaDataVO metaDataVO = new MetaDataVO();

        BeanUtils.copyProperties(metaDataBO, metaDataVO);
        return metaDataVO;
    }

}
