package com.dh.interview;

import org.junit.Test;

/**
 * 面试官给了一个字符串，反转字符串
 * 我写了一个简单的toCharArray，很显然是达不到要求的，他要求用递归的方式实现
 * 在面试过程中给了很多提示依然没写出来，挂掉后回家又思考了一会儿写出来了，
 * 特此记录一下
 *
 * 思路如下：
 *
 */
public class DDReverse {

    String reserve(String str) {
        if (null == str) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }
        return reserve(str.substring(1)) + str.charAt(0);
    }

    @Test
    public void testReserve() {
        String str = "111222333";
        System.out.println(reserve(str));
    }
}
