【详解】Java多线程中的观察者模式（分别对应thread1, thread2）

分类专栏： Java并发 文章标签： java 多线程 设计模式


观察者模式介绍
定义一个观察者，观察一个主题subject

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


定义一个被观察的主题
当状态发生变化是，通知全部的观察者

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


定义两个观察者，当状态发生转变时，分别将状态输出为二进制和八进制

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



测试

public class ObserverClient {
    public static void main(String[] args) {

        final Subject subject = new Subject();
        new BinaryObserver(subject);
        new OctalObserver(subject);
        System.out.println("==================");
        subject.setState(10);
        System.out.println("==================");
        subject.setState(10);

        System.out.println("==================");
        subject.setState(15);
    }
}






结果

==================
Binary String:1010
Octal String:12
==================
==================
Binary String:1111
Octal String:17

使用观察者模式监视多线程的生命周期
需求：

实现当线程状态发生更改时，观察者可以观察到，并作出一些处理
分析：

使用观察者模式重点是定义被观察的主题和观察者的响应
被观察的主题，在多线程中，可以通过实现Runnable接口，在创建线程的时候传递给线程。
在主题中需要定义一个集合，用于存储观察者，当状态发生更改时，通知观察者。
观察者则需要在创建线程的时候告诉线程，需要通知自己
观察者需要定义自己被通知后的处理
/**
 * 观察者接口
 */
public interface LifeCycleListener {
    
    void onEvent(ObservableRunnable.RunnableEvent event);
}



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

    /**
     * 定义一个状态事件，包含线程的状态、发生更改的线程以及错误造成的原因
     */
    public static class RunnableEvent {
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
}



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
    public void onEvent(ObservableRunnable.RunnableEvent event) {
        synchronized (LOCK) {//输出哪个线程状态发生了更改
            System.out.println("The runnable [" + event.getThread().getName() + "] data changed and state is [" + event.getState() + "]");
            if (event.getCause() != null) {//如果有异常
                System.out.println("The runnable [" + event.getThread().getName() + "] process failed.");
                event.getCause().printStackTrace();
            }
        }
    }
}


public class ThreadLifeCycleClient {
    public static void main(String[] args) {
        new ThreadLifeCycleObserver().concurrentQuery(Arrays.asList("1", "2"));
    }
}

结果：

The runnable [2] data changed and state is [RUNNING]
The runnable [1] data changed and state is [RUNNING]
query for the id 1
query for the id 2
The runnable [2] data changed and state is [DONE]
The runnable [1] data changed and state is [DONE]






