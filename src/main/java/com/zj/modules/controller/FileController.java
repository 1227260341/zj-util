package com.zj.modules.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 简单字节或字符流实现
 *
 * @author zj
 * 
 * 2018年12月5日
 */
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
	
	/**
	 * 将字节流转换成字符流
	 * zj
	 * 2018年12月13日
	 */
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
	
	/**
	 * 分页读取文件内容
	 * zj
	 * 2019年2月13日
	 */
	public static void readFileByPage(int pageIndex) {
		//TODO 此处可以自定义获取指定的页码数据
		ArrayList<Long> pageEnd = new ArrayList<Long>();//定义动态数组为Long型
		String line = null;  
		long pos=0; //定义指针
		int page = 0;  //页数
		int lineCount = 0; //页数计数器
		try {
			File file=new File("D:/write.txt");//构建指定文件，命令行参数方式输入文本名字
			RandomAccessFile raf = new RandomAccessFile(file,"r");//只读文件内容
			pageEnd.add(page, pos);//将每页指针存入动态数组  
			raf.seek(pos);//指针跳转到指定pos位置
			while(true){
				//按五行为一页输出
				for(int i=0;i<5;i++)
				{
					line= raf.readLine();//按行读文件
					lineCount++;//累计行数
					line =new String(line.getBytes("8859_1"), "utf8");//编码转化
					System.out.println(line);//输出文本内容
				}
				pos=raf.getFilePointer();//获取此时页末指针
				System.out.println("\n");
				page=lineCount/5; //每5行为一页（取整）
				System.out.println("                                  page"+page);//输出页码
				//System.out.println(lineCount);	
				//由于动态数组下标从0开始，在动态数组中第page页的开头对应于下标page-1；		
				pageEnd.add(page-1,pos);			
				System.out.println("页末指针动态数组："+pageEnd+"\n");//输出数组
			}
		}		
		catch(Exception e){
			//e.printStackTrace();
		}
		//本例缺陷：最后一页手动标码
		page=page+1;
		System.out.println("                                  page"+page);
	}
	
	/**
	 * 自己方式注意乱码问题， 此处暂时写的不完善， 可通过字节 单个 单个判断，比如字节末尾 存在 <0，说名有中文，对比前后 <0的数量， 是单数 说名少了（就是一个中文只读取到了一半）
	 * zj
	 * 2019年2月14日
	 */
	public static void readFileByPageForByte(int page) {
		int posLength = 1024;//指定读取的字节数量
		long pos=page*posLength; //定义指针
		System.out.println("--------pos " + pos);
		try {
			File file=new File("D:/write.txt");//构建指定文件，命令行参数方式输入文本名字
			RandomAccessFile raf = new RandomAccessFile(file,"r");//只读文件内容
			raf.seek(pos);//指针跳转到指定pos位置
			
			int length = 0;
			byte[] c = new byte[1024];
			byte[] chinese = new byte[2];
			int count = 0;
			while ((length = raf.read(c)) != -1) {
				if (count >= posLength) {
					break ;
				}
				count += 1024;
				String b  = "";
				String s = new String(c, 0, length);
//				if (c[1023] < 0) {//说名中文只读取到了一半，则舍弃
//					
//				}
				byte[] b2 = s.getBytes("iso8859_1");
				// 以 iso8859_1 编码时出现乱码时 byte 值为 63
				if (b2[b2.length - 1] == 63) {//说名末尾存在 中文（由于中文是两个自己，但只读取了 一个字节）
//					raf.seek(pos);//指针跳转到指定pos位置
//					byte[] b3 = new byte[1023];
//					raf.read(b3);
//					b = new String(b3, 0, 1023);
					b = getEndString(pos, raf, length);
				} else if(c[0] < 0) {//说名起始位置存在 中文  待完善 （暂时此处无效）
					b = getStartString(pos, raf, length);
				} else {
					b = new String(c, 0, length);
				}
				
				System.out.println(b);
			}
			
		}		
		catch(Exception e){
			//e.printStackTrace();
		}
		//本例缺陷：最后一页手动标码
		page=page+1;
		System.out.println("                                  page"+page);
	}
	
	public static String getStartString(long pos, RandomAccessFile raf, int length) throws IOException {
		pos --;
		length ++;
		raf.seek(pos);//指针跳转到指定pos位置
		byte[] b3 = new byte[length];
		raf.read(b3);
		if (b3[0] < 0 && length < 1026) {
			return getStartString(pos, raf, length);
		}
		String s = new String(b3, 0, length);
//		byte[] b2 = s.getBytes("iso8859_1");
//		if (b2[0] == 63) {
//			return getStartString(pos, raf);
//		}
		return s;
	}
	
	public static String getEndString(long pos, RandomAccessFile raf, int length) throws IOException {
		length --;
		raf.seek(pos);//指针跳转到指定pos位置
		byte[] b3 = new byte[length];
		raf.read(b3);
		String s = new String(b3, 0, length);
		byte[] b2 = s.getBytes("iso8859_1");
		if (b2[length - 1] == 63) {
			return getEndString(pos, raf, length);
		}
		return s;
	}
	
	public static void main(String[] args) throws Exception {
		//byteIn();
//		byteOut();
//		characterIn();
//		characterIn2();
//		characterInLine();
//		characterOut();
//		byteOutGoOn();
//		readFileByPageForByte(0);
//		readFileByPageForByte(1);
		readFileByPageForByte(2);
//		readFileByPageForByte(3);
	}
	
	
	
}
