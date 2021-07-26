package com.promo.gmall.utils.taobao.request;

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
public class StateCodeQueryRequest implements Serializable {

    private static final long serialVersionUID = -9016767714455448344L;


    private String language = "en";

    private Integer maxRegion = 100;

    private Integer maxPc = 100;

    private boolean returnPostcode = true;

    private String iso = "US";

    /**
     * 按照名称查询
     */
    private String searchName;


}
