package com.zj.modules.util.disignPattern.observer.thread;

import com.zj.modules.util.disignPattern.observer.thread.ObservableRunnable.RunnableState;

/**
 * 定义一个状态事件，包含线程的状态、发生更改的线程以及错误造成的原因
 */
public class RunnableEvent {
    private final RunnableState state;
    private final Thread thread;
    private final Throwable cause;

    public RunnableEvent(RunnableState state, Thread thread, Throwable cause) {
        this.state = state;
        this.thread = thread;
        this.cause = cause;
    }

    public RunnableState getState() {
        return state;
    }

    public Thread getThread() {
        return thread;
    }

    public Throwable getCause() {
        return cause;
    }
}