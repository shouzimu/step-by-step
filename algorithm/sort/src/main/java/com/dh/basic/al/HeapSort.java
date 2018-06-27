package com.dh.basic.al;

import org.junit.Test;

public class HeapSort {

    public void maxHeapIfy(int[] a, int i, int heapSize) {
        int l = 2 * i;
        int r = 2 * i + 1;
        int largest;
        if (l <= heapSize - 1 && a[l] > a[i]) {
            largest = l;
        } else {
            largest = i;
        }
        if (r <= heapSize - 1 && a[r] > a[largest]) {
            largest = r;
        }
        if (largest != i) {
            swap(a, i, largest);
            maxHeapIfy(a, largest, heapSize);
        }
    }

    public void swap(int[] a, int i, int j) {
        int swap = a[j];
        a[j] = a[i];
        a[i] = swap;
    }


    public void buildMaxHeap(int[] a) {
        int heapSize = a.length;
        for (int i = heapSize / 2; i >= 0; i--) {
            maxHeapIfy(a, i, heapSize);
        }
    }

    public void heapSort(int[] a) {
        int heapSize = a.length;
        buildMaxHeap(a);
        for (int i = heapSize - 1; i >= 0; i--) {
            swap(a, 0, i);
            maxHeapIfy(a, 0, i);
        }
    }

    @Test
    public void heapSort() {
        int[] a = {1, 4, 2, 3, 9, 7, 8, 10, 14, 16};
        heapSort(a);
        for (int i : a) {
            System.out.print(i + " ");
        }
    }
}
