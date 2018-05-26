package com.dh;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import org.junit.Test;

public class TestHashMap {

    @Test
    public void testHash() {
        HashMap<Integer, Integer> mp = new HashMap();
        int length = 1<<25;
        IntStream.range(0, length).forEach(i -> {
            mp.put(i, i);
        });
        System.out.println("exe end");
    }


    @Test
    public void testConcurrentHash() {
        ConcurrentHashMap<Integer, Integer>  mp= new ConcurrentHashMap();
        IntStream.range(0, 2).forEach(i -> {
            new Thread(() -> {
                IntStream.range(0, 2).forEach(j -> {
                    mp.put(j, j);
                });
            }).start();

        });
    }
}
