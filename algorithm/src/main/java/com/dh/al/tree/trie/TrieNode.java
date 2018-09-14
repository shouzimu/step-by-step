package com.dh.al.tree.trie;

import lombok.Data;

import java.util.LinkedList;

@Data
public class TrieNode {
    char c;
    boolean isEnd;
    int count;
    LinkedList<TrieNode> childList;

    public TrieNode(char c) {
        this.c = c;
        isEnd = false;
        count = 0;
        childList = new LinkedList<>();
    }

    public TrieNode subNode(char c) {
        if (childList != null) {
            for (TrieNode trieNode : childList) {
                if (trieNode.c == c) {
                    return trieNode;
                }
            }
        }
        return null;
    }
}
