package com.dh.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * threadlocal的三个重要方法
 *
 * ThreadLocal三个重要方法
 * 1、set()，如果没有set操作的ThreadLocal，容易引起脏读的问题
 * 2、get()，始终没有get操作的ThreadLocal是没有意义的
 * 3、remove()，如果没有remove操作，容易引起内存泄露
 */
public class DirtyDataInThreadLocal {

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    static AtomicInteger counter = new AtomicInteger(0);


    private static class MyThread extends Thread {

        @Override
        public void run() {
            if (counter.getAndIncrement() % 3 == 0) {
                threadLocal.set(this.getName() + " .data");
            }
            System.out.println(this.getName() + "线程是" + threadLocal.get());
            counter.getAndIncrement();
            threadLocal.remove();
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            pool.execute(new MyThread());
        }
    }
}
