package com.dh.lt.one;

import org.junit.Test;

/**
 * 191. Number of 1 Bits
 * <p>
 * https://leetcode.com/problems/number-of-1-bits/
 */
public class _191_Numberof1Bits {

    public int hammingWeight(int n) {
        int i = 0;
        while (n != 0) {
            n = n & (n - 1);
            i++;
        }
        return i;
    }

    @Test
    public void testHammingWeight() {
        int n = 199;
        System.out.println(hammingWeight(n));
    }
}
