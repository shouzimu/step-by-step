package com.dh.jb.concurrent;

import java.util.stream.IntStream;

public class FindMaxCpuThread {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                for (int i = 0; i < 10; i++) {
                    int a = 1000 * 1000;
                    a = a * 199;
                }
            }
        }).start();

        IntStream.rangeClosed(0,5).forEach((i)->{
            new Thread(() -> {
                try {
                    Thread.sleep(1000*600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
