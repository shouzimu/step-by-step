package com.dh.jb.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileOutputStream {

    public static void main(String[] args) {
        try {
            System.out.println("开始执行");
            FileInputStream in = new FileInputStream(
                    "/Users/lihong/developer/code/java/java-demo/javabasic/src/main/java/com/dh/jb/io/TestFileInputStream.java");
            FileOutputStream out = new FileOutputStream("/Users/lihong/Downloads/2.java");
            int b = 0;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
