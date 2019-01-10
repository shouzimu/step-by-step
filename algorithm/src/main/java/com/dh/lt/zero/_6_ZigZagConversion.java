package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

/**
 * 6. ZigZag Conversion
 * <p>
 * https://leetcode.com/problems/zigzag-conversion/
 */
public class _6_ZigZagConversion {
    public String convert(String s, int numRows) {
        if (numRows == 1) {
            return s;
        }
        char[][] nums = new char[numRows][s.length()];

        //遍历字符，将字符添加到二维数组中
        char[] arry = s.toCharArray();
        boolean goDown = true;//标识当前向下走
        int y = 0;//垂直方向
        int x = 0;//水平方向
        for (int i = 0; i < arry.length; i++) {
            char c = arry[i];
            nums[y][x] = c;
            if (y < numRows - 1 && goDown) {
                //正常向下走
                y += 1;
            } else if (y == numRows - 1 && goDown) {
                //到底了，右移和上移
                y = y - 1;
                x = x + 1;
                goDown = false;
            } else if (y > 0 && !goDown) {
                //向上走
                x = x + 1;
                y = y - 1;

            }
            if (y == 0) {
                //到顶了
                goDown = true;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (char[] num : nums) {
            for (char c : num) {
                if (c != '\0') {
                    builder.append(c);
                }
            }
        }
        return builder.toString();
    }

    @Test
    public void testConvert() {
        String s = "PAYPALISHIRING";
        int numRows = 4;
        String res = convert(s, numRows);
        Assert.assertEquals("PINALSIGYAHRPI", res);
    }

}
