package com.dh.juc;

import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Test;

public class LinkedBlockingQueueTest {

    @Test
    public void testPut() throws InterruptedException {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue(2);
        q.put(1);
        q.put(2);
        q.put(3);
    }


    @Test
    public void testOffer() throws InterruptedException {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue(2);
        q.offer(1);
        q.offer(2);
        q.offer(3);
    }
}
