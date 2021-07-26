package com.promo.gmall.enums;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public enum IsDeletedEnum {

    /**
     * 删除状态为0
     */
    NO(0, "否"),


    /**
     * 删除状态为1
     */
    YES(1, "是");

    private Integer code;

    private String msg;


    IsDeletedEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
