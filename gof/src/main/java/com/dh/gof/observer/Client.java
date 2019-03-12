package com.dh.gof.observer;

public class Client {
    public static void main(String[] args) {
        Subject subject = new Subject();
        ConcreateObserver observer = new ConcreateObserver();
        ConcreateV2Observer observer2 = new ConcreateV2Observer();
        subject.addObserver(observer);
        subject.addObserver(observer2);
        subject.doSomething();
    }
}
