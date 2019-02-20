package com.dh.juc;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.*;

public class ScheduledThreadPoolTest {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    CountDownLatch countDownLatch = new CountDownLatch(14);

    @Test
    public void testSchedule() {
        int[] interval = new int[]{5, 6, 7, 8};
        for (int i : interval) {
            Task t = new Task();
            try {
                ScheduledFuture<String> future = scheduledExecutorService.schedule(t, i, TimeUnit.SECONDS);
                String res = future.get();
                if (res.equals("ok")) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

    class Task implements Callable<String> {
        @Override
        public String call() {
            LocalDateTime time = LocalDateTime.now();
            System.out.println(time.getMinute() + ":" + time.getSecond() + "\thello world");
            countDownLatch.countDown();
            return "1";
        }

    }
}
