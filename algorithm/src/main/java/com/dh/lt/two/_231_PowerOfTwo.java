package com.dh.lt.two;

import org.junit.Assert;
import org.junit.Test;

/**
 * 231. Power of Two
 * <p>
 * https://leetcode.com/problems/power-of-two/
 *
 * 判断是否是2的幂 可以转换为 判断数的二进制位是否仅有1个1
 *
 * 考虑到n&n-1是去掉数的最后一位1，那么可以通过判断该结果是否和0相等判断是否为2的次方
 *
 */
public class _231_PowerOfTwo {
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    @Test
    public void testIsPowerOfTwo() {
        Assert.assertEquals(true, isPowerOfTwo(1));
        Assert.assertEquals(true, isPowerOfTwo(16));
        Assert.assertEquals(false, isPowerOfTwo(218));
        Assert.assertEquals(false, isPowerOfTwo(-1));
    }
}
