package com.zj.modules.util.disignPattern.observer.thread2;
public class TestThread extends Thread {
	private Watcher watcher;
	// 将被观察者对象传入线程，这里充分显示了面向对象开发的好处
	public TestThread (Watcher watcher) {
		this.watcher = watcher;
	}
	public void run() {
		Watched concreteWatched = new ConcreteWatched();
		concreteWatched.notifyWatchers(watcher, "我是:" + hashCode());
	}
 
}