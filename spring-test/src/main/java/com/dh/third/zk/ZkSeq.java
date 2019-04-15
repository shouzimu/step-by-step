package com.dh.third.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class ZkSeq {

    static String zookeeperConnectionString = "127.0.0.1:2181";

    static String seq_path = "/seq_path";
    static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    static CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);

    static {
        client.start();
    }

    public static Long nextId() {
        try {
            String t = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(seq_path);
            return Long.parseLong(t.replace(seq_path, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String createPersistentSequential(String path) {
        try {
            String t = client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String createPersistent(String path) {
        try {
            String t = client.create().withMode(CreateMode.PERSISTENT).forPath(path);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deletePath(String path) {
        try {
            client.delete().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPath(String path) {
        try {
            Stat stat = new Stat();
            client.getData().storingStatIn(stat).forPath(path);
            System.out.println(stat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
