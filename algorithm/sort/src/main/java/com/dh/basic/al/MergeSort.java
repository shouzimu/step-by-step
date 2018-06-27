package com.dh.basic.al;

import org.junit.Test;

public class MergeSort {

    public void mergeSort(int[] a, int p, int q, int r) {
        int n1 = q - p;
        int n2 = r - q;
        // let L[1..n1+n] and R[1..n2+1] be new arrays
        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];
        int i;
        int j;
        for (i = 0; i < n1; i++) {
            L[i] = a[p + i];
        }

        for (j = 0; j < n2; j++) {
            R[j] = a[q + j];
        }
        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE;

        i = 0;
        j = 0;

        for (int k = p; k < r; k++) {
            if (L[i] <= R[j]) {
                a[k] = L[i];
                i++;
            } else {
                a[k] = R[j];
                j++;
            }
        }
    }

    @Test
    public void testMergeSort() {
        int[] a = {5, 8, 9, 11, 1, 2, 3, 7, 12, 16};
        mergeSort(a, 0, 4, 9);
        Print.print(a);
    }
}

