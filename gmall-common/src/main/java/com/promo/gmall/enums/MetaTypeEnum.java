package com.promo.gmall.enums;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public enum MetaTypeEnum {

    /**
     * 材料
     */
    MATERIAL(1, "材料"),

    /**
     * 材料
     */
    IMPRINT_METHOD(2, "印刷方式"),

    /**
     * 主题
     */
    THEME(3, "主题");


    private Integer code;

    private String msg;


    MetaTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public static MetaTypeEnum getByCode(Integer code) {
        for (MetaTypeEnum value : MetaTypeEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return null;
    }

}
