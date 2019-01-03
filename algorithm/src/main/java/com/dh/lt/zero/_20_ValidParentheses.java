package com.dh.lt.zero;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * https://leetcode.com/problems/valid-parentheses/
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']',
 * determine if the input string is valid.
 * <p>
 * 这个方法一共写了两版，第一版是不知道还有取栈顶元素的方法，直接pop出来比较
 * 后面发现peek方法是返回栈顶的值并且不会从栈中将它移除，所以就直接使用peek比较
 * <p>
 * 第三版是在极客时间上看到的，感觉很取巧，反复替换直到没有替换成功，这时候如果剩余字符串长度>0就说明不匹配
 */
public class _20_ValidParentheses {
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (stack.isEmpty()) {
                stack.push(c);
            } else {
                Character top = stack.peek();
                if (null != top && top == map.get(c)) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }

        }
        return stack.isEmpty();
    }


    public boolean isValidV1(String s) {
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            Character top = null;
            if (!stack.empty()) {
                top = stack.pop();
            }
            //栈里有元素，判断是否为c的匹配括号，如果匹配，则不入栈
            if (null != top) {
                Character before = map.get(c);
                if (!top.equals(before)) {
                    stack.push(top);
                    stack.push(c);
                }
            } else {
                stack.push(c);
            }


        }
        return stack.isEmpty();
    }

    public boolean isValidV2(String s) {
        int length = 0;
        while (s.length() != length) {
            length = s.length();
            s = s.replaceAll("\\(\\)", "").replaceAll("\\{\\}", "").replaceAll("\\[\\]", "");
        }
        return length == 0;
    }

    @Test
    public void testIsValid() {
        String s = "{}{}{}[][][()()([()][])]{}";
        System.out.println(isValid(s));
        System.out.println(isValidV1(s));
        System.out.println(isValidV2(s));
    }
}
