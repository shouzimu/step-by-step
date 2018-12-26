package com.dh.al.sort;

import org.junit.Test;

public class BinarySearch {

    public int binarySearch(int[] arry, int target) {
        int left = 0;
        int right = arry.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arry[mid] == target) {
                return mid;
            } else if (arry[mid] < target) {
                left = mid + 1;
            } else if (arry[mid] > target) {
                right = mid - 1;
            }
        }
        return -1;
    }


    @Test
    public void testBinarySearch() {
        int[] arry = {1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 34, 67, 546, 3245, 5678, 6780, 23411};
        int index = binarySearch(arry, 1);
        System.out.println(index);
        System.out.println(arry[index]);
    }
}
