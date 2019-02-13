package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

/**
 * 70. Climbing Stairs
 * <p>
 * https://leetcode.com/problems/climbing-stairs/
 * <p>
 * Input: 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 */
public class _70_ClimbingStairs {
    public int climbStairs(int n) {
        if (n == 0) {
            return 0;
        }
        int[] f = new int[n];
        f[0] = 1;
        if (n >= 2) {
            f[1] = 2;

            for (int i = 2; i < n; i++) {
                f[i] = f[i - 1] + f[i - 2];
            }
        }
        return f[n - 1];
    }

    @Test
    public void testClimbStairs() {
        Assert.assertEquals(1, climbStairs(1));
        Assert.assertEquals(2, climbStairs(2));
    }
}
