package com.promo.gmall.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author wuji(zhangxiaoxia @ corp.netease.com)
 */
public class Main6 {

    public static void main(String[] args) {

        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),
                10000, 0.01);
        bloomFilter.put("1234");

        System.out.println(bloomFilter.mightContain("1234"));
        System.out.println(bloomFilter.mightContain("123"));

    }
}
