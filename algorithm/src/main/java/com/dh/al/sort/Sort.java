package com.dh.al.sort;

import org.junit.Test;

public class Sort {

    public void insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int v = a[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (a[j] > v) {
                    a[j + 1] = a[j];
                } else {
                    break;
                }
            }
            a[j + 1] = v;
        }
    }

    public void quickSort(int[] a, int l, int r) {
        if (l >= r) {
            return;
        }
        int part = partition(a, l, r);
        quickSort(a, l, part - 1);
        quickSort(a, part + 1, r);
    }

    public int partition(int[] a, int l, int r) {
        int v = a[r];
        for (int j = l; j < r; j++) {
            if (a[j] < v) {
                int t = a[j];
                a[j] = a[l];
                a[l] = t;
                l++;
            }
        }
        a[r] = a[l];
        a[l] = v;
        return l;
    }


    public void bucketSortBak(int[] a) {
        int max = 0;
        for (int i : a) {
            max = Math.max(max, i);
        }

        int[] bucket = new int[max + 1];
        for (int i : a) {
            bucket[i] = bucket[i] + 1;
        }

        int i = max;
        int j = a.length - 1;
        while (j >= 0) {
            if (bucket[i] == 0) {
                i--;
            } else {
                a[j] = i;
                j--;
                bucket[i] = bucket[i] - 1;
            }
        }
    }

    public void bucketSort(int[] a) {
        int n = a.length;
        int max = 0;
        for (int i : a) {
            max = Math.max(max, i);
        }

        int[] bucket = new int[max + 1];
        for (int i : a) {
            bucket[i] = bucket[i] + 1;
        }

        for (int i = 1; i <= max; i++) {
            bucket[i] = bucket[i] + bucket[i - 1];
        }

        int[] r = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            //找出a中下标为n的数应该是多少
            int index = bucket[a[i]] - 1;
            r[index] = a[i];
            bucket[a[i]] = bucket[a[i]] - 1;
        }
        System.arraycopy(r, 0, a, 0, n);
    }


    @Test
    public void testSort() {
        int[] a = new int[]{5, 4, 3, 2, 1, 9};
        insertSort(a);
        Print.print(a);
        a = new int[]{5, 4, 3, 2, 1, 9};
        quickSort(a, 0, 5);
        Print.print(a);

    }
}
