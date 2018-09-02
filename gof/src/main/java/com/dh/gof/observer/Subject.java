package com.dh.gof.observer;

import java.util.Vector;

/**
 * 被观察者
 */
public class Subject {
    private Vector<Observer> observerVector = new Vector<>();

    public void addObserver(Observer o) {
        observerVector.add(o);
    }

    public void delObserver(Observer o) {
        observerVector.remove(o);
    }

    public void nofifyObservers(Object o) {
        for (Observer observer : observerVector) {
            observer.update(o);
        }
    }

    public void doSomething(){
        nofifyObservers("开始吃饭");
    }

}
