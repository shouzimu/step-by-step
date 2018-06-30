package com.dh.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印数字
 * 实际上就是线程交替运行
 */
public class AlternatePrint {
    private ReentrantLock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();


    public static void main(String[] args) {
        AlternatePrint alternatePrint = new AlternatePrint();
        alternatePrint.alternatePrint();
    }

    public void alternatePrint() {
        //两个线程交替执行

        new Thread(() -> {
            int i = 0;
            while (i < 100) {
                try {
                    lock.lock();
                    System.out.println("TA\t" + i);
                    i += 2;
                    c2.signal();
                    c1.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        }).start();

        new Thread(() -> {
            int i = 1;
            while (i < 100) {
                try {
                    lock.lock();
                    System.out.println("TB\t" + i);
                    i += 2;
                    c1.signal();
                    c2.await();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

    }
}
