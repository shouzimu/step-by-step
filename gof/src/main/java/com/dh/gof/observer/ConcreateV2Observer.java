package com.dh.gof.observer;

public class ConcreateV2Observer implements Observer {
    @Override
    public void update(Object o) {
        System.out.println("B1-接收到信息，并开始处理");
    }
}
