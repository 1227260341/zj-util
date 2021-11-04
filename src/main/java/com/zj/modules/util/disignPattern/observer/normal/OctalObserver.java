package com.zj.modules.util.disignPattern.observer.normal;
public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        super(subject);
    }

    /**
     * 将状态变成八进制输出
     */
    @Override
    public void update() {//8进制
        System.out.println("Octal String:" + Integer.toOctalString(subject.getState()));
    }
}
