package com.dh.jo;

import org.junit.Test;

/**
 * 输入数字n，打印从1到n位十进制最大数字之间的所有数。比如输入3则打印1、2、3...998、999
 *
 * 算法思路来自于剑指offer
 * 首先的想法，算出最大的数，然后循环从1开始打印，但是这样存在一个问题，如果输入一个很大的n，则循环会溢出
 * 所以转换一下使用数组的方式来处理，这是一个大数的问题。
 */
public class PrintNumber {
    void printNum(int n) {
        if (n == 0) {
            return;
        }
        int[] arry = new int[n];
        while (!increment(arry)) {
            print(arry);
        }

    }

    //数组第0位相加如果发生了进位则说明已经达到最大数了
    // 例如 999 + 1 第一位进位，说明结束了，返回false
    boolean increment(int[] arry) {
        boolean end = false; //检验是否结束
        int sum;
        int carry = 1; //进位
        for (int i = arry.length - 1; i >= 0; i--) {
            sum = arry[i] + carry;
            if (sum >= 10) {
                carry = 1;
                if (i == 0) {
                    end = true;
                }
                sum = sum - 10;
            } else {
                carry = 0;
            }
            arry[i] = sum;
            if (carry == 0) {
                break;
            }
        }
        return end;
    }


    //打印的问题，n位数组，不足的位数是前面补0，所以前面补0的不应该打印
    void print(int[] arry) {
        //前面为补零，不输出
        if (arry.length == 0) {
            return;
        }
        boolean beginZero = arry[0] == 0;
        for (int i : arry) {
            if (beginZero && i != 0) {
                beginZero = false;
            }
            if (!beginZero) {
                System.out.print(i);
            }
        }
        System.out.println();
    }

    @Test
    public void testpPrintNum() {
        int num = 0;
        printNum(num);
    }
}
