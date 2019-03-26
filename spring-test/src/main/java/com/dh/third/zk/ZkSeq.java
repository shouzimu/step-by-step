package com.dh.third.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class ZkSeq {

    static String zookeeperConnectionString = "shouzimu.com:2181";

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

}
