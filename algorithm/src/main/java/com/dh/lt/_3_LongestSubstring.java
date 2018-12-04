package com.dh.lt;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class _3_LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        char[] arrys = s.toCharArray();
        if (arrys.length == 0) {
            return 0;
        }
        int length = arrys.length;
        int max = 1;
        for (int i = 0; i < length; i++) {
            int tempMax = 1;
            Map<Character, Character> charMap = new HashMap<>();
            for (int j = i + 1; j < length; j++) {
                if (charMap.put(arrys[j], arrys[j]) == null) {
                    if (arrys[i] != arrys[j]) {
                        tempMax++;
                    }
                } else {
                    break;
                }
                if (tempMax > max) {
                    max = tempMax;
                }
            }
        }
        return max;
    }

    public int lengthOfLongestSubstringV2(String s) {
        char[] arrys = s.toCharArray();
        if (arrys.length == 0) {
            return 0;
        }
        int length = arrys.length;
        int max = 0;
        Map<Character, Integer> charMap = new HashMap<>();

        for (int i = 0, j = 0; i < length; i++) {
            if (charMap.get(arrys[i]) != null) {
                j = Math.max(charMap.get(arrys[i]), j);
            }
            max = Math.max(max, i - j + 1);
            charMap.put(arrys[i], i + 1);
        }
        return max;
    }

    @Test
    public void testLengthOfLongestSubstring() {
        String input = "auaubbb";
        System.out.println(lengthOfLongestSubstring(input));
        System.out.println(lengthOfLongestSubstringV2(input));
    }

}
