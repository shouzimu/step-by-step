package com.dh.jb.io;

import java.io.FileInputStream;
import java.io.IOException;

public class TestFileInputStream {

    public static void main(String[] args) {
        if (null == args || args.length == 0) {
            System.out.println("请输入参数");
            System.exit(-1);
        }
        String filename = args[0];
        try {
            FileInputStream in = new FileInputStream(filename);
            int b = 0;
            long num = 0;
            while ((b = in.read()) != -1) {
                System.out.print((char) b);
                num++;
            }
            in.close();
            System.out.printf("一共读取了%d个字节", num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
