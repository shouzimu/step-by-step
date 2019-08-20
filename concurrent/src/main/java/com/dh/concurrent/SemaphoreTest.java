package com.dh.concurrent;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 100).forEach(i -> {
                    pool.execute(() -> {
                        try {
                            semaphore.acquire();
                            Thread.sleep(2000);
                            System.out.println(System.currentTimeMillis() / 1000 + "  pass : " + i);
                            semaphore.release();
                        } catch (Exception e) {

                        }
                    });

                }
        );
    }
}
