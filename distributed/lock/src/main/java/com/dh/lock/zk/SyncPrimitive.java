package com.dh.lock.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SyncPrimitive implements Watcher {

    private static ZooKeeper zk = null;

    static Integer mutex;

    String root;


    public SyncPrimitive(String address) {
        if (zk == null) {
            try {
                System.out.println("start zk");
                zk = new ZooKeeper(address, 2181, this);
                mutex = new Integer(-1);
                System.out.println("start zk success");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    public static void main(String[] args) {
        barrierTest();
    }


    public static void queueTest() {
        Queue q = new Queue("localhost", "/lh");
        int max = 10;
        IntStream.rangeClosed(1, max).forEach(i -> {
            // q.produce(i);
        });

        IntStream.rangeClosed(1, max).forEach(i -> {
            int r = q.consume();
            System.out.println("consume " + r);
        });
    }

    int consume() {
        int retvalue;
        Stat stat = null;
        try {
            while (true) {
                synchronized (mutex) {
                    List<String> list = zk.getChildren(root, true);
                    if (list.size() == 0) {
                        System.out.println("goint to wait");
                        mutex.wait();
                    } else {
                        int min = list.stream().mapToInt(s -> Integer.valueOf(s.substring(7))).min().getAsInt();
                        System.out.println("Temporary value: " + root + "/element000000000" + min);
                        byte[] b = zk.getData(root + "/element000000000" + min,
                                false, stat);
                        zk.delete(root + "/element000000000" + min, 0);
                        ByteBuffer buffer = ByteBuffer.wrap(b);
                        retvalue = buffer.getInt();
                        return retvalue;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    boolean produce(int i) {
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putInt(i);
        byte[] value = b.array();
        try {
            zk.create(root + "/element", value, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException();
        }
        return true;
    }

    static class Queue extends SyncPrimitive {
        Queue(String address, String name) {
            super(address);
            this.root = name;
            if (zk != null) {
                try {
                    Stat s = zk.exists(root, false);
                    if (s == null) {
                        zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                } catch (KeeperException e) {
                    System.out.println("Keeper exception when instantiating queue: " + e.toString());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted exception");
                }
            }
        }
    }

    static class Barrier extends SyncPrimitive {
        int size;
        String name;

        public Barrier(String address, int size, String root) {
            super(address);
            this.root = root;
            this.size = size;
            if (null != zk) {
                try {
                    Stat s = zk.exists(root, false);
                    if (s == null) {
                        zk.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    }
                } catch (KeeperException e) {
                    System.out.println("Keeper exception when instantiating queue: " + e.toString());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted exception");
                }
            }
            // My node name
            try {
                name = new String(InetAddress.getLocalHost().getCanonicalHostName().toString());
            } catch (UnknownHostException e) {
                System.out.println(e.toString());
            }
        }

        boolean enter() throws KeeperException, InterruptedException {
            zk.create(root+"/"+name ,new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            while (true){
                synchronized (mutex){
                    List<String> list = zk.getChildren(root,true);
                    if(list.size() < size){
                        mutex.wait();
                    }else {
                        return true;
                    }
                }
            }
        }

        boolean leave() throws KeeperException, InterruptedException {
            zk.delete(root + "/" + name,0);
            while (true){
                synchronized (mutex){
                    List<String> list = zk.getChildren(root,true);
                    if(list.size() > 0){
                        mutex.wait();
                    }else {
                        return true;
                    }
                }
            }

        }
    }


    public static void barrierTest(){
        Barrier b = new Barrier("localhost", 5, "/lh");
        try{
            boolean flag = b.enter();
            System.out.println("enter barrier");
            if (!flag) {
                System.out.println("Error when entering the barrier");
            }
        }catch (Exception e){

        }

        Random rand = new Random();
        int r = rand.nextInt(100);
        // Loop for rand iterations
        for (int i = 0; i < r; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        try{
            b.leave();
        } catch (KeeperException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Left barrier");
    }


}
