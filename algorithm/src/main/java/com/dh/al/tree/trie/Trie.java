package com.dh.al.tree.trie;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode(' ');
    }

    public void insertNode(String world) {
        TrieNode current = root;
        for (char c : world.toCharArray()) {
            TrieNode node = current.subNode(c);
            if (node != null) {
                current = node;
            } else {
                TrieNode newNode = new TrieNode(c);
                current.childList.add(newNode);
                current = current.subNode(c);
            }
            current.count++;
        }
        current.isEnd = true;
    }


    public TrieNode search(String world) {
        TrieNode current = root;
        for (char c : world.toCharArray()) {
            if (current.subNode(c) == null) {
                return null;
            } else
                current = current.subNode(c);
        }

        return current;
    }

    @Test
    public void testInsertNode() {
        Trie trie = new Trie();
        List<String> strings = Arrays.asList("a,b,cc,b,b,cc,bb,dd,aa,c".split(","));
        for (String string : strings) {
            trie.insertNode(string);
        }
        for (String string : strings) {
            System.out.println(string + "--" + trie.search(string).count);
        }
    }
}
