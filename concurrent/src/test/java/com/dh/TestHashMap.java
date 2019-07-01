package com.dh;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class TestHashMap {

    public static void main(String[] args) {
        int length = 100000;
        final CountDownLatch c = new CountDownLatch(length);
        final HashMap<Long,EasyCoding> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    map.put(System.nanoTime(),new EasyCoding());
                    c.countDown();
                }
            };
            thread.start();
        }

        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map.size());
    }

    static class EasyCoding{

    }

}
