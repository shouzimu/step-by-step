package com.dh.serialize.tk;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class IOtest1 {

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/lihong/Downloads/1.txt"));

        LinkedList<String> linkedList = new LinkedList();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            linkedList.addFirst(s);
        }
        linkedList.forEach(s2 -> System.out.println(s2));
    }
}
