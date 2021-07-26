package com.promo.gmall.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wuji(zhangxiaoxia @ corp.netease.com)
 */
public class Main4 {

    public static void main(String[] args) {
        ShuntInfoAbtContent object = JSON.parseObject("{\"strategyType\":9,\"content\": {\"group\" : \"A1\"}}\t\n",
                new TypeToken<ShuntInfoAbtContent<CartFreeTryShuntResDTO>>() {
                }.getType());
        System.out.println(object);
    }
}

@Data
class ShuntInfoAbtContent<T> {

    private Integer strategyType;

    private Integer grantType;

    private List<String> grantIds;

    private T content;

}


@Data
class CartFreeTryShuntResDTO implements Serializable {

    public static final String TEST_GROUP = "B1";

    private static final long serialVersionUID = -3037629690227216241L;


    private String group;


    /**
     * @return 是否在测试组
     */
    public boolean inTestGroup() {
        return TEST_GROUP.equals(group);
    }


}
