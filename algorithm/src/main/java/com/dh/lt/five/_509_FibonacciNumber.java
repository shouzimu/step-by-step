package com.dh.lt.five;

import org.junit.Assert;
import org.junit.Test;

/**
 * 509. Fibonacci Number
 * <p>
 * https://leetcode.com/problems/fibonacci-number/
 */
public class _509_FibonacciNumber {

    public int fib(int N) {
        if (N == 0) return 0;
        if (N < 3) {
            return 1;
        }
        int[] fib = new int[N];
        fib[0] = 1;
        fib[1] = 1;
        for (int i = 2; i < N; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }
        return fib[N - 1];
    }

    @Test
    public void testFib() {
        Assert.assertEquals(1, fib(1));
        Assert.assertEquals(1, fib(2));
        Assert.assertEquals(2, fib(3));
        Assert.assertEquals(3, fib(4));
        Assert.assertEquals(5, fib(5));
        Assert.assertEquals(8, fib(6));
    }

}
