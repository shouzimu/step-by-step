package com.dh.lt.two;

import org.junit.Assert;
import org.junit.Test;

/**
 * 208. Implement Trie (Prefix Tree)
 * https://leetcode.com/problems/implement-trie-prefix-tree/
 */
public class _208_ImplementTrie {

    /**
     * 此实现为第二版，从极客时间上学习直接利用数组O(1)访问的特性来实现
     */
    class Trie {

        TrieNode root;

        class TrieNode {
            TrieNode[] children;
            char c;
            boolean isEnd;

            public TrieNode(char c) {
                this.c = c;
                this.children = new TrieNode[26];
                isEnd = false;
            }
        }

        /**
         * Initialize your data structure here.
         */
        public Trie() {
            root = new TrieNode('\0');
        }

        /**
         * Inserts a word into the trie.
         */
        public void insert(String word) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                TrieNode[] children = current.children;
                TrieNode node;
                if (children[c - 'a'] == null) {
                    node = new TrieNode(c);
                    children[c - 'a'] = node;
                } else {
                    node = children[c - 'a'];
                }
                current = node;
            }
            current.isEnd = true;
        }

        /**
         * Returns if the word is in the trie.
         */
        public boolean search(String word) {
            TrieNode current = root;
            for (char c : word.toCharArray()) {
                TrieNode[] children = current.children;
                TrieNode node;
                if (children[c - 'a'] == null) {
                   return false;
                } else {
                    node = children[c - 'a'];
                }
                current = node;
            }
            return current.isEnd;
        }


        /**
         * Returns if there is any word in the trie that starts with the given prefix.
         */
        public boolean startsWith(String prefix) {
            TrieNode current = root;
            for (char c : prefix.toCharArray()) {
                TrieNode[] children = current.children;
                TrieNode node;
                if (children[c - 'a'] == null) {
                    return false;
                } else {
                    node = children[c - 'a'];
                }
                current = node;
            }
            return true;
        }
    }

    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("apple");
        Assert.assertEquals(true,trie.startsWith("apple"));
        Assert.assertEquals(false,trie.search("app"));
        Assert.assertEquals(true,trie.startsWith("app"));
        trie.insert("app");
        Assert.assertEquals(true,trie.search("app"));
    }


   /**
    *
    *
    * 实现的第一版，虽然leetcode一次通过，时间优于38的Java
    * 但还是考虑不到位，既然利用了数组，我却没有考虑到利用数组O(1)根据下标获取值的用法， 海循环遍历数组，判断是否相当
    * 实际上既然children初始化为26支持a-z的存法，那么直接a[c-'a']就可以准确的取到位置
    * 连辅助的length都可以省略了
    *
    *
    public void insert(String word) {
        Trie.TrieNode current = root;
        for (char c : word.toCharArray()) {
            Trie.TrieNode[] children = current.children;
            int i = 0;
            if (current.length > 0) {
                while (children[i] != null && children[i].c != c) {
                    i++;
                }
            }
            Trie.TrieNode node;
            //非空且没找到 or 为空
            if ((children.length > 0 && i == current.length) || current.length == 0) {
                node = new Trie.TrieNode(c);
                children[i] = node;
                current.length = current.length + 1;
            } else {
                node = children[i];
            }
            current = node;
        }
        current.isEnd = true;
    }

    public boolean search(String word) {
        Trie.TrieNode current = root;
        for (char c : word.toCharArray()) {
            Trie.TrieNode[] children = current.children;
            int i = 0;
            if (current.length > 0) {
                while (children[i] != null && children[i].c != c) {
                    i++;
                }
            }
            Trie.TrieNode node;
            //非空且没找到 or 为空
            if ((children.length > 0 && i == current.length) || current.length == 0) {
                return false;
            } else {
                node = children[i];
            }
            current = node;
        }
        return current.isEnd;
    }


    public boolean startsWith(String prefix) {
        Trie.TrieNode current = root;
        for (char c : prefix.toCharArray()) {
            Trie.TrieNode[] children = current.children;
            int i = 0;
            if (current.length > 0) {
                while (children[i] != null && children[i].c != c) {
                    i++;
                }
            }
            Trie.TrieNode node;
            //非空且没找到 or 为空
            if ((children.length > 0 && i == current.length) || current.length == 0) {
                return false;
            } else {
                node = children[i];
            }
            current = node;
        }
        return true;
    }

    **/
}
