package com.zj.modules.util.disignPattern.observer.thread;

import java.util.List;

//import com.zj.modules.util.disignPattern.observer.thread.ObservableRunnable.RunnableEvent;
import com.zj.modules.util.disignPattern.observer.thread.ObservableRunnable.RunnableState;

/**
 * 观察者
 * 创建多个线程
 * 当线程发生变换时，将事件传递给观察者，执行onEvent方法
 */
public class ThreadLifeCycleObserver implements LifeCycleListener {

    private final Object LOCK = new Object();

    /**
     * 启动多个线程
     * @param ids
     */
    public void concurrentQuery(List<String> ids) {
        if (ids == null || ids.isEmpty())
            return;

        ids.stream().forEach(id -> new Thread(new ObservableRunnable(this) {
            @Override
            public void run() {
                try {
                    notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                    System.out.println("query for the id " + id);
                    Thread.sleep(1000L);
                    // int i = 1/0;
                    notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                } catch (Exception e) {
                    notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                }
            }
        }, id).start());
    }

    @Override
    public void onEvent(RunnableEvent event) {
//	public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {//输出哪个线程状态发生了更改
            System.out.println("The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {//如果有异常
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}
