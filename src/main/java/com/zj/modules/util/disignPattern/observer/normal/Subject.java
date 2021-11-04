package com.zj.modules.util.disignPattern.observer.normal;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察的主题
 */
public class Subject {

    private List<Observer> observers = new ArrayList<>();//观察者集合

    private int state;//被观察的状态

    public int getState() {
        return this.state;
    }

    /**
     * 修改状态
     * @param state
     */
    public void setState(int state) {
        if (state == this.state) {
            return;
        }
        this.state = state;
        notifyAllObserver();
    }

    /**
     * 添加观察者
     * @param observer
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * 通知所有的观察者
     */
    private void notifyAllObserver() {
        observers.stream().forEach(Observer::update);
    }
}
