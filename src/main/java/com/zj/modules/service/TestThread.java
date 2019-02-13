package com.zj.modules.service;

import java.util.concurrent.atomic.AtomicInteger;

public class TestThread implements Runnable{

	private int n = 0;
	
	private int id;
	
	private static AtomicInteger count = new AtomicInteger(0);//线程安全
	
	public TestThread() {
		
	}
	
	public TestThread(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		
//		System.out.println("++n-" + ++n);
//		System.out.println("n++--" + n++);
		System.out.println("id-" + id);
		
	}

}
