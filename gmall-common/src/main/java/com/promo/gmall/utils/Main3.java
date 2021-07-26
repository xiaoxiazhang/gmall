package com.promo.gmall.utils;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author wuji(zhangxiaoxia @ corp.netease.com)
 */
public class Main3 {


    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3);

        list.add(1, 5);
        System.out.println(list);
    }
}
