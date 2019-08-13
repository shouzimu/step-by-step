package com.dh.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 要求:子线程运行执行 10 次后，主线程再运行 5 次。这样交替执行三遍
 */
public class Main3Sub10 {

    private boolean subExe = true;

    public static void main(String[] args) {
        testByCondition();
    }

    public static void testByNotify() {
        final Main3Sub10 m = new Main3Sub10();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                m.subMethod();
            }
        }).start();

        for (int i = 0; i < 3; i++) {
            m.mainMethod();
        }
    }

    public synchronized void mainMethod() {
        while (subExe) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()
                    + " : main thread running loop count -- " + i);

        }
        subExe = true;
        notifyAll();
    }

    public synchronized void subMethod() {
        while (!subExe) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 10; i++) {
            System.err.println(Thread.currentThread().getName()
                    + " : sub thread running loop count -- " + i);
        }
        subExe = false;
        notifyAll();
    }


    private Lock lock = new ReentrantLock();
    private Condition mainCondition = lock.newCondition();
    private Condition subCondition = lock.newCondition();
    private CountDownLatch c = new CountDownLatch(1);

    public static void testByCondition() {
        final Main3Sub10 m = new Main3Sub10();
        new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                m.subMethodCondition();
            }
        }).start();

        for (int i = 0; i < 3; i++) {
            m.mainMethodCondition();
        }
    }

    public void mainMethodCondition() {
        lock.lock();
        try {
            c.await();
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()
                        + " : main thread running loop count -- " + i);

            }
            subCondition.signal();
            mainCondition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void subMethodCondition() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.err.println(Thread.currentThread().getName()
                        + " : sub thread running loop count -- " + i);
            }
            c.countDown();
            try {
                mainCondition.signal();
                subCondition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
