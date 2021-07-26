package com.promo.gmall.utils;

import java.util.PriorityQueue;

/**
 * @author wuji(zhangxiaoxia @ corp.netease.com)
 */
public class Main {


    public static void main(String[] args) {
        int[] ints = {1, 3, 5, 4, 2, 8, 9};
        int kth = findKth(ints, 7, 3);

        System.out.println(kth);
    }


    public static int findKth(int[] a, int n, int K) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            if (queue.size() < K) {
                queue.add(a[i]);
            } else if (a[i] > queue.peek()) {
                queue.remove();
                queue.add(a[i]);
            }
        }

        return queue.peek();
    }
}
