package com.self.common.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author GTsung
 * @date 2021/9/22
 */
public class FindMath {

//    [
//            [1,   4,  7, 11, 15],
//            [2,   5,  8, 12, 19],
//            [3,   6,  9, 16, 22],
//            [10, 13, 14, 17, 24],
//            [18, 21, 23, 26, 30]
//            ]

    public static void main(String[] args) {
        int[][] a = new int[][]{
                {1, 4, 7, 11, 15},
                {2, 5, 8, 12, 19},
                {3, 6, 9, 16, 22},
                {10, 13, 14, 17, 24},
                {18, 21, 23, 26, 30}
        };
        System.out.println(a.length); // row
        System.out.println(a[0].length); // column
        System.out.println(searchNum(a, 29));

        topK(new int[]{5,12,23,6,1,3,78,32,11,33}, 6);

    }

    private static void topK(int[] a, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2-o1;
            }
        });
        for (int i = 0; i< a.length; i++) {
            if (queue.size() < k) {
                queue.offer(a[i]);
            } else {
                if (a[i] < queue.peek()) {
                    queue.poll();
                    queue.offer(a[i]);
                }
            }
        }
        List<Integer> aa = new ArrayList<>();
        for (int i =0; i<k; i++) {
            aa.add(queue.poll());
        }
        for(Integer s :aa) {
            System.out.println(s);
        }
    }

    private static boolean searchNum(int[][] a, int search) {
        int i = 0, j = a[0].length - 1;

        while (i <= a.length-1 && j >= 0) {
            if (a[i][j] > search) {
                j--;
            } else if (a[i][j] < search) {
                i++;
            } else {
                return true;
            }
        }
        return false;
    }
}
