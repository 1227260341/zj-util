package com.zj.modules.util.disignPattern.observer.thread2;
public class Test
{
    public static void main(String[] args)
    {
        Watched girl = new ConcreteWatched();
        // 将观察者加入队列中
        Watcher watcher1 = new ConcreteWatcher();
        girl.addWatcher(watcher1);
        TestThread t = new TestThread(watcher1);
        t.start();
    }
}