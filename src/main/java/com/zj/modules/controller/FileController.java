package com.zj.modules.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class FileController {

	/**
	 * 字节读取流
	 * zj
	 * 2018年12月5日
	 * @throws FileNotFoundException 
	 */
	public static void byteIn() throws Exception {
		File file = new File("D:/write.txt");
		FileInputStream fis = new FileInputStream(file);
		int length = 0;
		byte[] b = new byte[1024];
		while ((length = fis.read(b)) != -1) {
			String s = new String(b, 0, length);
			System.out.println("----1024--------------------------------s" + s);
		}
		fis.close();
	}
	
	/**
	 * 字节写入流
	 * zj
	 * 2018年12月5日
	 */
	public static void byteOut() throws Exception {
		File file = new File("D:/write - 副本.txt");
		String content = "这是zj要写入的数据";
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(content.getBytes());
		fos.close();
		System.out.println("写入成功！");
	}
	
	/**
	 * 字节流追加写入内容
	 * zj
	 * 2018年12月5日
	 */
	public static void byteOutGoOn() throws Exception {
		File file = new File("D:/write - 副本.txt");
		//如果不存在则创建新的
		if (!file.exists()) {
			file.mkdirs();
		}
		String content = "这是zj要写入的数据\r\n";
		FileOutputStream fos = new FileOutputStream(file, true);
		fos.write(content.getBytes());
		fos.close();
		System.out.println("写入成功！");
	}
	
	/**
	 * 字符读取流
	 * zj
	 * 2018年12月5日
	 */
	public static void characterIn() throws Exception {
		File file = new File("D:/write.txt");
		FileReader fr = new FileReader(file);
		int length = 0;
		char[] c = new char[1024];
		while ((length = fr.read(c)) != -1) {
			String s = new String(c, 0, length);
			System.out.println("----1024--------------------------------s" + s);
		}
		fr.close();
	}
	
	public static void characterIn2() throws Exception {
		File file = new File("D:/write.txt");
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		int length = 0;
		char[] c = new char[1024];
		while ((length = isr.read(c)) != -1) {
			String s = new String(c, 0, length);
			System.out.println("----1024--------------------------------s" + s);
		}
		fis.close();
		isr.close();
	}
	
	/**
	 * 字符流一行一行读取
	 * zj
	 * 2018年12月5日
	 */
	public static void characterInLine() throws Exception {
		File file = new File("D:/write.txt");
		FileReader fr = new FileReader(file);
		LineNumberReader lnr = new LineNumberReader(fr);
		String text = null;
		while ((text = lnr.readLine()) != null) {
			System.out.println("----text --- 一行一行读到的-------------------------------" + text);
		}
		fr.close();
		lnr.close();
	}
	
	/**
	 * 字符写入流
	 * zj
	 * 2018年12月5日
	 */
	public static void characterOut() throws Exception {
		String content = "这是zj需要写入的内容啊发发安抚f";
		File file = new File("D:/write - 副本.txt");
		//如果不存在则创建新的
		if (!file.exists()) {
			file.mkdirs();
		}
		FileWriter fw = new FileWriter(file);
		fw.write(content);
		fw.close();
		System.out.println("成功写入数据！");
	}
	
	public static void main(String[] args) throws Exception {
		//byteIn();
//		byteOut();
//		characterIn();
//		characterIn2();
//		characterInLine();
//		characterOut();
		byteOutGoOn();
	}
	
	
	
}
