package com.dh.demo;

public class StringTest {

    public static void main(String[] args) {
        String st1 = "a";
        String st2 = "b";
        String st3 = "ab";
        String st4 = new String("ab");
        String st5 = st1+st2;
        System.out.println(st3 == st4);
        System.out.println(st3 == st5);
    }
}
