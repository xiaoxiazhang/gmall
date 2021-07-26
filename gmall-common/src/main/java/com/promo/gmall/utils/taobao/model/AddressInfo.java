package com.promo.gmall.utils.taobao.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.x.x
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
public class AddressInfo implements Serializable {
    private static final long serialVersionUID = -3399441885607095800L;


    /**
     * 父资源ID
     */
    private Long parentResourceId;

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 城市名称
     */
    private String name;


    /**
     * 等级
     */
    private Integer level;


    /**
     * 编码信息
     */
    private String code;


    public static void main(String[] args) {

    }


}
