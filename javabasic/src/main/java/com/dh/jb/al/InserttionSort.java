package com.dh.jb.al;

import org.junit.Test;

public class InserttionSort {
    public void inserttionSort(int[] a) {
        for (int j = 1; j < a.length; j++) {
            int key = a[j];
            int i = j - 1;
            while (i >= 0 && a[i] > key) {
                a[i + 1] = a[i];
                i = i - 1;
            }
            a[i + 1] = key;
        }
    }

    @Test
    public void testInserttionSort() {
        int[] a = {5, 7, 9, 11, 8, 2, 3, 9, 7, 16};
        inserttionSort(a);
        Print.print(a);
    }
}
