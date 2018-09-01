package com.dh.io.basic;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.function.Supplier;

public class MappedIO {

    private static int numOfInts = 400000;
    private static int numOfUbuffInts = 200000;
    private static final String file = "/Users/lihong/Downloads/data.tmp";

    public void methodExeTime(Supplier<String> s) {
        long start = System.currentTimeMillis();
        String method = s.get();
        long end = System.currentTimeMillis();
        System.out.println(method + " time " + (end - start) + " ms");
    }

    public String testStreamWrite() {
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(file))));
            for (int i = 0; i < numOfInts; i++) {
                dos.write(i);
            }
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "StreamWrite";
    }

    public String testMappedWrite() {
        try {
            FileChannel fc = new RandomAccessFile(file, "rw").getChannel();
            IntBuffer ib = fc.map(MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
            for (int i = 0; i < numOfInts; i++) {
                ib.put(i);
            }
            fc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "MappedWrite";
    }

    public String testStreamRead() {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream((new File(file)))));
            for (int i = 0; i < numOfInts; i++) {
                dis.readInt();
            }
            dis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "StreamRead";
    }


    public String testMappedRead() {
        try {
            FileChannel fc = new FileInputStream(file).getChannel();
            IntBuffer ib = fc.map(MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();

            while (ib.hasRemaining()) {
                ib.get();
            }
            fc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "MappedRead";
    }

    @Test
    public void timeTest() {
        MappedIO io = new MappedIO();
        methodExeTime(io::testStreamWrite);
        methodExeTime(io::testStreamRead);
        methodExeTime(io::testMappedWrite);
        methodExeTime(io::testMappedRead);
    }
}
