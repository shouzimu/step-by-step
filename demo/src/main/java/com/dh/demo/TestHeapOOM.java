package com.dh.demo;

/**
 * -Xmx10M -Xms5M +XX:HeapDumpOnOutOfMemoryError
 */
public class TestHeapOOM {

    public static void main(String[] args) {
        int k100 = 1024 * 1024 * 100;

        int a[] = new int[k100];
        for (int i = 0; i < k100; i++) {
            a[i] = i;
        }
    }
}
