package com.dh.lt.zero;

import org.junit.Assert;
import org.junit.Test;

/**
 * 67. Add Binary
 * <p>
 * https://leetcode.com/problems/add-binary/
 */
public class _67_AddBinary {
    public String addBinary(String a, String b) {
        char[] arra = a.toCharArray();
        char[] arrb = b.toCharArray();

        StringBuilder res = new StringBuilder();

        int i = arra.length - 1;
        int j = arrb.length - 1;
        int carr = 0;
        while (i >= 0 || j >= 0) {
            int a1 = 0;
            int b1 = 0;
            if (i >= 0) {
                a1 = arra[i] - '0';
                i--;
            }
            if (j >= 0) {
                b1 = arrb[j] - '0';
                j--;
            }

            int sum = a1 + b1 + carr;
            if (sum >= 2) {
                carr = 1;
                res.append(sum % 2);
            } else {
                carr = 0;
                res.append(sum);
            }
        }
        if (carr == 1) {
            res.append(1);
        }
        return res.reverse().toString();

    }

    @Test
    public void testAddBinary() {
        String a = "11";
        String b = "1";
        String res = addBinary(a, b);
        Assert.assertEquals("100", res);
    }
}
