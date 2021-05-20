package com.zj.modules.controller;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zj.modules.service.TestThread;
import com.zj.modules.service.TestThreadPool;

/**
 * 线程多线程的实现(线程池等。。。)
 *
 * @author zj
 * 
 * 2018年12月5日
 */
@RestController
@RequestMapping("/test")
public class TestThreadController {
	
	//再换一种
//	new Thread(new Runnable() {
//	    public void run() {
//	    }
//	}).start();

	@RequestMapping("/moreThread")
	public static Object moreThread() {
		TestThread r = new TestThread();
		for (int i = 0; i < 10; i ++) {
			Thread t = new Thread(r);
			t.start();
		}
		
		return "";
		
	}
	
	public static Object moreThreadParam() {
		
		for (int i = 0; i < 10; i ++) {
			TestThread r = new TestThread(i);
			Thread t = new Thread(r);
			t.start();
		}
		
		return "";
		
	}
	
	/**
	 * 线程池
	 * ArrayBlockingQueue;
		LinkedBlockingQueue;
		SynchronousQueue;
		ArrayBlockingQueue和PriorityBlockingQueue使用较少，一般使用LinkedBlockingQueue和Synchronous。线程池的排队策略与BlockingQueue有关。
		
		　如果调用了shutdown()方法，则线程池处于SHUTDOWN状态，此时线程池不能够接受新的任务，它会等待所有任务执行完毕；
		　如果调用了shutdownNow()方法，则线程池处于STOP状态，此时线程池不能接受新的任务，并且会去尝试终止正在执行的任务；
		　当线程池处于SHUTDOWN或STOP状态，并且所有工作线程已经销毁，任务缓存队列已经清空或执行结束后，线程池被设置为TERMINATED状态
		
		尽量调大maximumPoolSize，例如设置为Integer.MAX_VALUE
		 使用其他排队策略，例如LinkedBlockingQueue  public ExecutorService customerExecutorService = new ThreadPoolExecutor(3, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		
	 * zj
	 * 2018年12月6日
	 * @throws InterruptedException 
	 */
	public static Object moreThreadPool() throws InterruptedException {
//		ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 10, 0, TimeUnit.SECONDS, 
//				new LinkedBlockingQueue<>(5));
		ThreadPoolExecutor tpe = new ThreadPoolExecutor(20, 30, 200, TimeUnit.SECONDS, 
				new ArrayBlockingQueue<>(20));
		for (int i = 0; i < 50; i ++) {
			TestThreadPool r = new TestThreadPool(i);
			tpe.execute(r);
			System.out.println("线程池中线程数目："+tpe.getPoolSize()+"，队列中等待执行的任务数目："+
					tpe.getQueue().size()+"，已执行玩别的任务数目："+tpe.getCompletedTaskCount());
		}
		tpe.shutdown();
		int poolSize = tpe.getPoolSize();
		while (poolSize != 0) {
			Thread.sleep(5*1000);
			poolSize = tpe.getPoolSize();
			System.out.println("aa---" + poolSize);
		}
		System.out.println("所有的都已经执行完毕！");
		return "";
		
	}
	
	//同一实例(Runnable实例)的多个线程。
	public static void main(String[] args) throws InterruptedException {
//		moreThread();
//		moreThreadParam();
		moreThreadPool();
//		System.exit(0);
		
		
		
		
		AtomicInteger a = new AtomicInteger(1);
        System.out.println(a.incrementAndGet());
        System.out.println(a.incrementAndGet());
        System.out.println(a.incrementAndGet());
        System.out.println(a.incrementAndGet());
        System.out.println(1);
		
		
		
		
	}
	
}
