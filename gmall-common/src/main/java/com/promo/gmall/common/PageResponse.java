package com.promo.gmall.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class PageResponse<T> implements Serializable {
    private static final long serialVersionUID = 7966318496467142039L;


    /**
     * 总记录数
     */
    private Long count;


    /**
     * 分页对应数据项
     */
    private List<T> rows;

}
