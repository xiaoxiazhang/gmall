package com.promo.gmall.model.base;

import com.promo.gmall.domain.MetaDataDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class MetaDataBO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 元数据类型: 1-材质 2-主题 3-印刷方式
     */
    private Integer type;

    /**
     * 是否逻辑删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * DO对象转业务对象BO
     */
    public static MetaDataBO from(MetaDataDO metaDataDO) {
        if (metaDataDO == null) {
            return null;
        }

        MetaDataBO metaDataBO = new MetaDataBO();
        BeanUtils.copyProperties(metaDataDO, metaDataBO);
        return metaDataBO;
    }


    /**
     * 业务对象转DO
     */
    public MetaDataDO convert2DO() {
        MetaDataDO metaDataDO = new MetaDataDO();
        BeanUtils.copyProperties(this, metaDataDO);
        return metaDataDO;
    }


}
