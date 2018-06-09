package com.dh.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Recipes_Lock {

    static String zookeeperConnectionString = "127.0.0.1:2181";

    static String lock_path = "/curator_recipels_lock_path";
    static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    static CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

    public static void main(String[] args) {
        client.start();

        final InterProcessMutex lock = new InterProcessMutex(client, lock_path);
        final CountDownLatch down = new CountDownLatch(1);
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(0, 30).forEach(i -> {
            new Thread(() -> {
                try {
                    if (lock.acquire(3, TimeUnit.SECONDS)) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss|SSS");
                            String orderNo = sdf.format(new Date());
                            System.out.println("生成的订单号是:" + orderNo);
                        } finally {
                            lock.release();
                        }
                    }
                } catch (Exception e) {
                }
            }).start();
        });
        down.countDown();
        long end = System.currentTimeMillis();
        System.out.println("time is:" + (end - start));
    }
}
