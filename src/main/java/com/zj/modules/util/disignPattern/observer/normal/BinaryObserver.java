package com.zj.modules.util.disignPattern.observer.normal;
public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        super(subject);
    }

    /**
     * 将状态变成二进制输出
     */
    @Override
    public void update() {
        System.out.println("Binary String:" + Integer.toBinaryString(subject.getState()));
    }
}
