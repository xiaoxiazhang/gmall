package com.promo.gmall.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -2930323012203196941L;

    /**
     * 页码
     */
    private int page = 1;


    /**
     * 每页长度
     */
    private int size = 20;


}
