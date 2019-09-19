package com.dh.juc.locks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.junit.Test;

public class FairAndUnFairTest {

    private ReentrantLock2 fairLock = new ReentrantLock2(true);
    private ReentrantLock2 unfairLock = new ReentrantLock2(false);

    private static final int count = 5;
    private static CountDownLatch latch = new CountDownLatch(count);

    @Test
    public void testFair() {
        testLock(fairLock);
    }

    @Test
    public void testUnfair() {
        testLock(unfairLock);
    }

    private void testLock(ReentrantLock2 lock) {
        for (int i = 0; i < count; i++) {
            Job t = new Job(lock);
            t.setName(i + "");
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Job extends Thread {

        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                lock.lock();
                try {
                    //连续两次打印当前的Thread和等待队列中的thread
                    Collection<Thread> threads = lock.getQueuedThreads();
                    String wait = threads.stream().map(t -> t.getName()).collect(Collectors.joining(","));
                    System.out.println("Lock by " + Thread.currentThread().getName() + ",Waiting by " + wait);
                } finally {
                    lock.unlock();
                }
            }
            latch.countDown();
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public List<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }
}
