package com.zj.modules.service;

public class TestThread implements Runnable{

	private int n = 0;
	
	private int id;
	
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
