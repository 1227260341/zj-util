package com.zj.modules.service;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPool implements Runnable{

	private int n = 0;
	
	private int id;//非线程安全，可能存在并发 问题
	
	private static AtomicInteger count = new AtomicInteger(0);//线程安全
	
	public TestThreadPool() {
		
	}
	
	public TestThreadPool(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		
		System.out.println("正在执行task "+id);
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+id+"执行完毕");
		
	}

}
