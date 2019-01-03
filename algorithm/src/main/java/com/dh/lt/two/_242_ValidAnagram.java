package com.dh.lt.two;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/valid-anagram/
 * <p>
 * Example 1:
 * <p>
 * Input: s = "anagram", t = "nagaram"
 * Output: true
 * Example 2:
 * <p>
 * Input: s = "rat", t = "car"
 * Output: false
 *
 * 方法一：对两个字符串排序然后使用equals判断
 *
 * 方法二：使用map分别记录每一个字符出现的次数，然后比较
 *
 * 错误思路：char相加判断结果是否详单，结果ac=bb了，在此也记录一下
 *
 */
public class _242_ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] s1 = s.toLowerCase().toCharArray();
        char[] t1 = t.toLowerCase().toCharArray();

        Map<Character, Integer> s1Map = new HashMap<>();
        Map<Character, Integer> t2Map = new HashMap<>();

        for (char c : s1) {
            Integer i = s1Map.get(c);
            if (null == i) {
                i = 0;
            }
            i++;
            s1Map.put(c, i);
        }

        for (char c : t1) {
            Integer i = t2Map.get(c);
            if (null == i) {
                i = 0;
            }
            i++;
            t2Map.put(c, i);
        }

        boolean res = true;
        for (Map.Entry<Character, Integer> entry : s1Map.entrySet()) {
            char k = entry.getKey();
            Integer v1 = entry.getValue();
            Integer v2 = t2Map.get(k);
            if (null == v2 || v2 - v1 != 0) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Test
    public void testIsAnagram() {
        String s = "aaa";
        String t = "bbb";
        System.out.println(isAnagram(s, t));
    }

}

