package com.dh.lt.zero;

import org.junit.Test;

/**
 * 66. Plus One
 * <p>
 * https://leetcode.com/problems/plus-one/
 */
public class _66_PlusOne {

    public int[] plusOne(int[] digits) {
        if (null == digits || digits.length == 0) {
            return new int[]{1};
        }
        int carry = 1;
        int sum;
        for (int i = digits.length - 1; i >= 0; i--) {
            sum = digits[i] + carry;
            if (sum < 10) {
                carry = 0;
                digits[i] = sum;
                break;
            } else {
                sum = sum - 10;
                digits[i] = sum;
            }
        }
        //数组长度需要+1
        if (carry == 1) {
            int[] newArray = new int[digits.length + 1];
            /**
             * 第一次的写法，将老数组复制到新数组，然后第一位设置为1，实际上这是没必要的，数组初始化时所有的值为0
             * 并且如果发生数组需要扩容的情况 数的第一位必然是1，后面的位数全部是0，所以可以使用第二种方式
             */

            /* 方式1，这个复制实际全部是0，可以省略
             * System.arraycopy(digits, 0, newArray, 1, digits.length);
             */
            newArray[0] = carry;
            digits = newArray;
        }
        return digits;
    }

    @Test
    public void testPlusOne() {
        int[] digits = new int[]{1, 9};
        digits = plusOne(digits);
        for (int digit : digits) {
            System.out.println(digit);
        }
    }
}
