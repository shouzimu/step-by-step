package com.dh.juc.locks;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);

        for (int i = 0; i < 2; i++) {

            Thread t = new Thread(() -> {
                for (int j = 0; j < 1; j++) {
                    lock.lock();
                    try {
                        Long random = ThreadLocalRandom.current().nextLong(10000) + 10;
                        try {
                            Thread.sleep(random);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + " --" + j + "-- " + " -- 获得锁" + random);
                    } finally {
                        lock.unlock();
                    }
                }
            });
            t.setName("- " + i + " -");
            t.start();
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
