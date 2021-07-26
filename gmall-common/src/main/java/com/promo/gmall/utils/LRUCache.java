package com.promo.gmall.utils;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author wuji
 * @Motto 我的貂蝉在哪里
 * @since 1.0.0
 */
public class LRUCache {


    /**
     * 缓存长度
     */
    private int capacity;

    /**
     * 优先级列表：
     */
    private LinkedList<Integer> list;


    /**
     * 缓存数据
     */
    private HashMap<Integer, Integer> cache;


    public LRUCache(int capacity, LinkedList<Integer> list, HashMap<Integer, Integer> cache) {
        this.capacity = capacity;
        this.list = list;
        this.cache = cache;
    }


    public Integer get(Integer key) {
        if (cache.containsKey(key)) {
            move2LastPoint(key);
            return cache.get(key);

        } else {
            return null;
        }
    }

    /**
     * 将缓存key, 移动到最后
     *
     * @param key 缓存可以
     */
    public void move2LastPoint(Integer key) {
        list.remove(key);
        list.add(key);
    }


    /**
     * 放入缓存
     */
    public void put(Integer key, Integer value) {
        cache.put(key, value);
        if (!list.contains(key)) {
            list.add(key);
        }

        if (cache.size() > capacity) {
            Integer oldKey = list.getFirst();
            list.removeFirst();
            cache.remove(oldKey);
        }
    }


    public static void main(String[] args) {


    }


}
