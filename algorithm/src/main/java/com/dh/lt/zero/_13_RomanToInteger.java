package com.dh.lt.zero;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _13_RomanToInteger {
    public int romanToInt(String s) {
        Map<Character, Integer> roman = new HashMap<>();
        roman.put('I', 1);
        roman.put('V', 5);
        roman.put('X', 10);
        roman.put('L', 50);
        roman.put('C', 100);
        roman.put('D', 500);
        roman.put('M', 1000);

        char[] arrys = s.toCharArray();

        int len = arrys.length;
        int res = 0;
        for (int i = len - 1; i >= 0; i--) {
            if (i == len - 1) {
                res += roman.get(arrys[i]);
            } else {
                if (i + 1 < len && roman.get(arrys[i]) < roman.get(arrys[i + 1])) {
                    res = res - roman.get(arrys[i]);
                } else {
                    res = res + roman.get(arrys[i]);
                }
            }
        }
        return res;
    }


    @Test
    public void testRomanToInt() {
        String input = "III";
        System.out.println(romanToInt(input));
    }
}
