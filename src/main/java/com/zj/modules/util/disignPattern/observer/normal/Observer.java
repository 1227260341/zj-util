package com.zj.modules.util.disignPattern.observer.normal;
/**
 * 一个观察者的抽象类
 * 具体当状态发生变化进行的操作交给子类实现
 */
public abstract class Observer {

    protected Subject subject;

    public Observer(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
    }

    public abstract void update();

}
