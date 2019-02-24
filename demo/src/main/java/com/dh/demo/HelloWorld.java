package com.dh.demo;

public class HelloWorld {

    public static void main(String[] args) {
        System.out.println("Hello World");
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        while (threadGroup != null) {
            if (null != threadGroup.getParent()) {
                threadGroup = threadGroup.getParent();
            } else {
                break;
            }
        }
        Thread t = new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.setName("test Thread");
        t.start();
        t.interrupt();
        System.out.println(threadGroup.activeCount());
        System.out.println(threadGroup.getName());
        System.out.println();
        Thread[] threadList = new Thread[threadGroup.activeCount()];
        threadGroup.enumerate(threadList,true);
        for (Thread thread : threadList) {
            System.out.println(thread.getName()+" \t "+thread.getState());
        }

    }
}
