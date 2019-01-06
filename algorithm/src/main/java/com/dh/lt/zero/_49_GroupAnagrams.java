package com.dh.lt.zero;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 49. Group Anagrams
 * https://leetcode.com/problems/group-anagrams/
 * <p>
 * Input: ["eat", "tea", "tan", "ate", "nat", "bat"],
 * Output:
 * [
 * ["ate","eat","tea"],
 * ["nat","tan"],
 * ["bat"]
 * ]
 */
public class _49_GroupAnagrams {

    /**
     * 这个题的难度定义为Medium，应该没这么高
     * 第242. Valid Anagram  https://leetcode.com/problems/valid-anagram/
     * 这题题目的解题思路就是将字符串sort后判断是否相等就可以完成
     * 所以我们继续利用这意思路，将其按照sort后的字符串分组，利用Java8的特性几行代码就可以解决问题
     * 当然如果要求按照出现的顺序来处理的话也可以简单的排序
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<String> list = Arrays.asList(strs);
        Map<String, List<String>> groups = list.stream().collect(Collectors.groupingBy(t -> {
            char[] arry = t.toCharArray();
            Arrays.sort(arry);
            return new String(arry);
        }));
        List<List<String>> res = new ArrayList<>();
        groups.forEach((k, v) -> {
            res.add(v);
        });
        //如果需要排序，则按照list的index判断一下
        res.sort(Comparator.comparing(t -> list.indexOf(t.get(0))));
        return res;
    }

    @Test
    public void testGroupAnagrams() {
        String[] arry = new String[]{"tec", "eat", "tea", "tan", "ate", "nat", "bat"};
        List<List<String>> res = groupAnagrams(arry);
        System.out.println(res.size());
    }

}
