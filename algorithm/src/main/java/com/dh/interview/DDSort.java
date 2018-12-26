package com.dh.interview;

import org.junit.Test;

/**
 * 给定下面的数组，将其排序
 * 算法很简单 O(n^2)的冒泡，O(nlogn)的快排都可以实现
 * 但是面试官要求用O(n)的复杂度实现，给了很多提示依然没有实现
 * 实际上还是对排序算法不了解，桶排序就是一个O(n)复杂度的算法
 * 特此记录一下
 */
public class DDSort {

    //数组分成三个桶，
    void sort(int[] arry) {
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i : arry) {
            if (i < min) {
                min = i;
            }
            if (i > max) {
                max = i;
            }
        }
        int[] bucket = new int[max - min + 1];
        for (int i : arry) {

        }

    }

    @Test
    public void testSort() {
        int[] arry = {1, 2, 3, 1, 2, 3};
        sort(arry);
    }
}
