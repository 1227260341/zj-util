package com.zj.modules.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zj.modules.service.TestThread;

@RestController
@RequestMapping("/test")
public class TestThreadController {

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
	
	//同一实例(Runnable实例)的多个线程。
	public static void main(String[] args) {
//		moreThread();
		moreThreadParam();
	}
	
}
