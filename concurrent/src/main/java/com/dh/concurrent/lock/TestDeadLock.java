package com.dh.concurrent.lock;

public class TestDeadLock extends Thread {

    String first;
    String second;

    public TestDeadLock(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        TestDeadLock t1 = new TestDeadLock(lockA, lockB);
        t1.setName("t1");

        TestDeadLock t2 = new TestDeadLock(lockB, lockA);
        t2.setName("t2");
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " " + first);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (second) {
                System.out.println(this.getName() + " " + second);
            }

        }
    }
}
