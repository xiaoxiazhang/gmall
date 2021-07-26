package com.promo.gmall.utils;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author wuji(zhangxiaoxia @ corp.netease.com)
 */
public class Main2 {

    public static ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {

        ArrayList<Integer> result = new ArrayList<>(k);
        if (k > input.length || k == 0) {
            return result;
        }


        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);

        for (int i = 0; i < k; i++) {
            queue.offer(input[i]);
        }

        for (int j = k; j < input.length; j++) {
            if (queue.peek() > input[j]) {
                queue.poll();
                queue.offer(input[j]);
            }
        }


        for (int i = 0; i < k; i++) {
            result.add(queue.poll());
        }

        return result;

    }

    public static void main(String[] args) {
        int[] ints = {1, 3, 2, 5, 8, 9};
        ArrayList<Integer> integers = GetLeastNumbers_Solution(ints, 3);

    }
}
