package com.dh;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestThreadException {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        new Thread(() -> {
            System.out.println(100 / 0);
            System.out.println(100 / 0);
            System.out.println(100 / 0);
            System.out.println(100 / 0);
        }).start();

        ExecutorService executor = Executors.newFixedThreadPool(100);
        Future<Integer> future1 = executor.submit(() -> 100 / 1);
        Future<Integer> future2 = executor.submit(() -> 100 / 2);
        Future<Integer> future3 = executor.submit(() -> 100 / 3);
        Future<Integer> future4 = executor.submit(() -> 100 / 0);
        int a = future1.get(1, TimeUnit.SECONDS);
        int b = future2.get(1, TimeUnit.SECONDS);
        int c = future3.get(1, TimeUnit.SECONDS);
        int d = future4.get(1, TimeUnit.SECONDS);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        executor.shutdown();
    }

}
