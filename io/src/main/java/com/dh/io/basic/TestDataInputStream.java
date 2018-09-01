package com.dh.io.basic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TestDataInputStream {

    public static void main(String[] args) {
        String fileName = "/Users/lihong/developer/code/java/java-demo/javabasic/src/main/java/com/dh/basic/io/TestFileInputStream.java";
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(fileName));
            int c;
            byte[] bytes = new byte[2];
            while ((c = in.read(bytes)) != -1) {
                System.out.print(new String(bytes,"utf-8"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
