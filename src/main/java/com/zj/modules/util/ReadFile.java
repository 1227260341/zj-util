package com.zj.modules.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 替换文件夹下 所有文件得具体内容值替换
 * @author zj
 *
 */
public class ReadFile {
	
	/**
	 * 主方法测试
	 * @version 2023-8-3014:55:27
	 * @author zhouzj
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:/filedata/初始部署文档/sql初始化脚本/"; //给我你要读取的文件夹路径
		File outPath = new File("D:/filedata/贵州银行/"); //随便给一个输出文件夹的路径(不存在也可以)
		readFolder(filePath,outPath);
	}
	
	/**
	 * 读取文件夹
	 * @version 2023-8-3014:55:40
	 * @author zhouzj
	 * @param filePath
	 * @param outPath
	 */
	public static void readFolder(String filePath,File outPath){
		try {
			//读取指定文件夹下的所有文件
			File file = new File(filePath);
			if (!file.isDirectory()) {
				System.out.println("---------- 该文件不是一个目录文件 ----------");
			} else if (file.isDirectory()) {
				System.out.println("---------- 很好，这是一个目录文件夹 ----------");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filePath + "\\" + filelist[i]);
					//String path = readfile.getPath();//文件路径
					String absolutepath = readfile.getAbsolutePath();//文件的绝对路径
					String filename = readfile.getName();//读到的文件名
					readFile(absolutepath,filename,i,outPath);//调用 readFile 方法读取文件夹下所有文件
				}
				System.out.println("---------- 所有文件操作完毕 ----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取文件夹下的文件
	 * @version 2023-8-3014:55:48
	 * @author zhouzj
	 * @param absolutepath
	 * @param filename
	 * @param index
	 * @param outPath
	 */
	public static void readFile(String absolutepath,String filename,int index,File outPath){
		try{
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutepath)));//数据流读取文件
			StringBuffer strBuffer = new StringBuffer();
			String empty = "贵州银行";
			String tihuan = "韶关银行";
			for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
//				if(temp.indexOf("/*") != -1 && temp.indexOf("*/") != -1){ //判断当前行是否存在想要替换掉的字符 -1表示存在
				if(temp.indexOf(tihuan) != -1){ //判断当前行是否存在想要替换掉的字符 -1表示存在
//					tihuan = temp.substring(0, 9);//这里截取多长自己改
					temp = temp.replaceAll(tihuan, empty);//替换为你想要的东东
				}
				strBuffer.append(temp);
				strBuffer.append(System.getProperty("line.separator"));//行与行之间的分割
			}
			bufReader.close();
			if(outPath.exists() == false){ //检查输出文件夹是否存在，若不存在先创建
				outPath.mkdirs();
				System.out.println("已成功创建输出文件夹：" + outPath);
			}
			PrintWriter printWriter = new PrintWriter(outPath+"\\"+filename);//替换后输出的文件位置（切记这里的E:/ttt 在你的本地必须有这个文件夹）
			printWriter.write(strBuffer.toString().toCharArray());
			printWriter.flush();
			printWriter.close();
			System.out.println("第 " + (index+1) +" 个文件   "+ absolutepath +"  已成功输出到    " +outPath+"\\"+filename);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}