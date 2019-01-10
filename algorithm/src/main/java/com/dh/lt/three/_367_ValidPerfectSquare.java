package com.dh.lt.three;


import org.junit.Assert;
import org.junit.Test;

/**
 * 367. Valid Perfect Square
 * <p>
 * https://leetcode.com/problems/valid-perfect-square/
 */
public class _367_ValidPerfectSquare {
    public boolean isPerfectSquare(int x) {
        if (x <= 1) {
            return true;
        }
        int mid, res = 0, l = 0, r = x;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (mid <= x / mid) {
                l = mid + 1;
                res = mid;
            } else {
                r = mid - 1;
            }
        }
        return (x / (float)res) == res;
    }

    @Test
    public void testIsPerfectSquare() {
        int x = 5;
        Assert.assertEquals(false, isPerfectSquare(x));
    }
}
