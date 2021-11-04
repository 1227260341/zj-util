package com.zj.modules.util.disignPattern.observer.thread;
/**
 * 观察者接口
 */
public interface LifeCycleListener {
    
//    void onEvent(ObservableRunnable.RunnableEvent event);
	
	void onEvent(RunnableEvent event);
}
