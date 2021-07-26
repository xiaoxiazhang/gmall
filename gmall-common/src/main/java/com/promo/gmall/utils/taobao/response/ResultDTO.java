package com.promo.gmall.utils.taobao.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
@Data
public class ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 759669032496479677L;

    /**
     * api名称
     */
    private String api;


    /**
     * 返回数据
     */
    private T data;


    /**
     * 返回结果数组
     */
    private List<String> ret;


    /**
     * 版本信息
     */
    private String v;


}
