package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

/**
 * 69. Sqrt(x)
 * <p>
 * https://leetcode.com/problems/sqrtx/
 */
public class _69_SqrtX {

    /**
     * 二分法求平方根
     * <p>
     * 由于写代码的不够严谨，submit遇到两次错误
     * 1、mid <= x / mid 第一次写成了 mid*mid<=x，如果mid很大的话就溢出了
     * 2、没有判断x的输入情况，如果为0的话，0作为除数报错了
     * <p>
     * 经验：应该重复考虑到边界情况
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        if (x <= 1) {
            return x;
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
        return res;
    }


    @Test
    public void testMySqrt() {
        int x = 5;
        Assert.assertEquals(4, mySqrt(x));
    }
}
