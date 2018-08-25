package com.dh.basic.al.trie;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WordCount {
    private String word;
    private int count;

    public WordCount(String word) {
        this.word = word;
        this.count = 0;
    }

    public WordCount() {
    }

    public void sort(List<String> strs) {
        Trie trie = new Trie();
        List<WordCount> wcs = new ArrayList<>();
        for (String str : strs) {
            trie.insertNode(str);
        }
    }
}
