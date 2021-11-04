package com.zj.modules.util.disignPattern.observer.thread;
/**
* 被观察的主题
* 实现Runnable 接口可以放入到线程中，线程执行的过程中可以传递事件给观察者
*/
public abstract class ObservableRunnable implements Runnable {

    final protected LifeCycleListener listener;

    public ObservableRunnable(final LifeCycleListener listener) {
        this.listener = listener;
    }

    /**
     * 通知更改
     * @param event
     */
    protected void notifyChange(final RunnableEvent event) {
        listener.onEvent(event);//触发事件
    }

    /**
     * 线程的三种状态
     */
    public enum RunnableState {
        RUNNING, ERROR, DONE
    }

//    /**
//     * 定义一个状态事件，包含线程的状态、发生更改的线程以及错误造成的原因
//     */
//    public static class RunnableEvent {
//        private final RunnableState state;
//        private final Thread thread;
//        private final Throwable cause;
//
//        public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
//            this.state = state;
//            this.thread = thread;
//            this.cause = cause;
//        }
//
//        public RunnableState getState() {
//            return state;
//        }
//
//        public Thread getThread() {
//            return thread;
//        }
//
//        public Throwable getCause() {
//            return cause;
//        }
//    }
}
