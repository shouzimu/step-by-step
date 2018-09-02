package com.dh.gof.observer;

public class Client {
    public static void main(String[] args) {
        Subject subject = new Subject();
        ConcreateObserver observer = new ConcreateObserver();
        subject.addObserver(observer);
        subject.doSomething();
    }
}
