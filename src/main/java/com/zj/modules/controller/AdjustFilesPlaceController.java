package com.zj.modules.controller;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;



@RequestMapping("/adjustFilesPlace")
@Controller
public class AdjustFilesPlaceController {

	private String reg = "s[0-9]+__.*$";
	List<Map> needMoveFilesList = new ArrayList<>();
	List<Map> needMoveFilesListForDir = new ArrayList<>();
	
	/**
	 * 调整文件位置
	 * zj
	 * 2018年7月19日
	 */
	@RequestMapping("/execute")
	@ResponseBody
	public Object getSchoolInfo(HttpServletRequest request) {
		try {
			
	        return "";
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	
	public void froDir(File dir) {
	    //获得所有文件（这里面包括了文件夹和文件）
	    File[] files = dir.listFiles();
	    
	    //判断是否是多个文件夹
	    int filesLength = files.length;
//	    System.out.println("-----length=" + filesLength);
	    
	    for (File file : files) {
	    	
	        //增强for循环来打印文件路径
	        if (file.isDirectory()) {
	        	String fileName = file.getName();
	        	boolean isSFile = fileName.matches(reg);
//	            System.out.println(isSFile);
	        	if (isSFile) {//说名是s层文件夹
//	        		startFind(file);
	        		startFind2(file);
	        	} else {
	        		froDir(file);
	        	}
	            
	        } else {
	            //打印文件路径
	            String s = file.getAbsolutePath().toLowerCase();
//	            System.out.println("---------------文件路径：" + s);
	        }
	    }
	}
	
	public File startFind2(File dir) {
		File[] files = dir.listFiles();
		List<File> list = new ArrayList<File>();
		for(File file : files) {
			if(file.isDirectory()) {
				File newFile = null;
				if((newFile = startFind2(file)) != null) {
					list.add(newFile);
				}else if(file.exists()){
					list.add(file);
				}
			}else {
				if(file.getName().equals("Thumbs.db")) {
					file.delete();
				}else {
					list.add(file);
				}
			}
		}
		if(list.size() == 1) {
			File mvFile = list.get(0);
			String suffix = getSuffix(mvFile);
			File newFile = new File(dir.getParentFile(), dir.getName()+suffix+".tmp");
			if(mvFile.isDirectory()) {
				try {
					FileUtils.moveDirectory(mvFile, newFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				mvFile.renameTo(newFile);
			}
			File lastFile = new File(dir.getParentFile(), dir.getName()+suffix);
			dir.delete();
			if(newFile.isDirectory()) {
				try {
					FileUtils.moveDirectory(newFile, lastFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				newFile.renameTo(lastFile);
			}
			return lastFile;
		}else if(list.size() == 0) {
			dir.delete();
			return null;
		}
		return null;
	}
	
	
	/**
	 * 开始查找文件存放位置等是否正确
	 * zj
	 * 2018年7月19日
	 */
	public void startFind(File dir) {
		
		//获得所有文件（这里面包括了文件夹和文件）
	    File[] files = dir.listFiles();
	    if (files == null) {
	    	System.out.println("-----breakbreakbreakbreakbreak------------break-------------------------------");
	    	return ;
	    }
	    
	    //判断是否是多个文件夹
	    int filesLength = files.length;
	    System.out.println("-sssssssssssss----length=" + filesLength);
	    boolean isExistFiles = false;//存在文件
	    boolean isDirectory = false;//存在文件夹
	    for (File file : files) {
	    	if (file.isFile()) {//说名是文件
	    		isExistFiles = true;
	    	}
	    	if (file.isDirectory()) {
	    		isDirectory = true;
	    	}
	    }
	    
	    int fileLength = files.length;
	    String nowFileName = dir.getName();
	    
	    if (isDirectory && !isExistFiles) {//说名只存在文件夹 则继续循环
	    	if (fileLength > 1) {//说名存在两个文件夹或以上，则不需要操作
	    		return;
	    	}
	    	for (File file : files) {
	    		startFind(file);
	    	}
	    }
	    
	    Map fileMap = new HashMap<>();
	    File sFile = geiNeedDirectory(dir);//获取到需要的文件夹 即s层下级文件夹目录
    	File sParentFile = sFile.getParentFile();// s 层父级文件夹 - 即s层
    	
	    //进行判断，处理逻辑
	    if (isExistFiles && !isDirectory) {//说名只存在文件 
	    	
	    	if (fileLength == 1) {//说名是单个文件
	    		File needMoveFile = files[0];//需要移动的文件
	    		fileMap.put("oldFile", needMoveFile);
	    		String suffix = getSuffix(needMoveFile);
	    		File newFile = new File(sParentFile.getPath() + "/" + sFile.getName() + suffix);
	    		fileMap.put("newFile", newFile);
	    		needMoveFilesList.add(fileMap);
	    	} else {// 说名是 多个文件
	    		//多个文件首先判断是不是 在s层下面，是的话则不懂，不是的话 则 移动（连文件夹一起移动）
	    		boolean isSFile = nowFileName.matches(reg);
	    		if (isSFile) {//说名当前是s层文件夹 则不需要进行移动操作
	    			return;
	    		}
	    		
	    		//添加进需要操作的列表里面
	    		fileMap.put("oldFile", dir);
	    		fileMap.put("newFile", sParentFile);//放到s层目录下
	    		needMoveFilesListForDir.add(fileMap);
	    	}
	    	
	    }
	    
	    if (isExistFiles && isDirectory) {//说名文件 跟文件夹同时存在
	    	//多个文件首先判断是不是 在s层下面，是的话则不懂，不是的话 则 移动（连文件夹一起移动）
    		boolean isSFile = nowFileName.matches(reg);
    		if (isSFile) {//说名当前是s层文件夹 则不需要进行移动操作
    			return;
    		}
    		
    		//添加进需要操作的列表里面
    		fileMap.put("oldFile", dir);
    		fileMap.put("newFile", sParentFile);//放到s层目录下
    		needMoveFilesListForDir.add(fileMap);
	    }
		
	}
	
	/**
	 * 获取到需要的文件夹 s层下级文件夹目录
	 * zj
	 * 2018年7月19日
	 */
	public File geiNeedDirectory(File dir) {
		
		String fileName = dir.getName();
		boolean isSFile = fileName.matches(reg);
        System.out.println("-geiNeedDirectory---isSFile:" + isSFile);
        if (!isSFile) {
        	dir = geiNeedDirectory(dir.getParentFile());
        }
        
        return dir;
		
	}
	
	/**
	 * 获取后缀名
	 * zj
	 * 2018年7月19日
	 */
	public String getSuffix(File file) {
		String fileName = file.getName();
		if (fileName.indexOf(".") < 1) {//说名不存在后缀
			return "";
		}
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //System.out.println("需要移动的文件后缀名：" + suffix);
        return suffix;
	}
	
	
	public static void main(String[] args) {
		
//		File rootFile = new File("D://copyTest");
//		File rootFile = new File("Z:/平安-17417-沪教版（五四学制）已解压/普通");
		
//		File[] files = rootFile.listFiles();
		
		AdjustFilesPlaceController afpc = new AdjustFilesPlaceController();
		
//		String reg = "s[0-9]+_.*$";
//        System.out.println("s445ee_gdfgfgdffgn.aaaByPage".matches(reg));
		
//		afpc.froDir(rootFile);
		
		String rootFilePaths[] = {"Z:/平安_bsdb", "Z:/平安_unzip",
				"Z:/平安-10436-华东师大版已解压", "Z:/平安-17417-沪教版（五四学制）已解压", "Z:/平安-26365-北京课改版已解压",
				"Z:/平安-27217-已解压"};
		for (String rootFilePath : rootFilePaths) {
			System.out.println(rootFilePath + "-------------start");
			File rootFile = new File(rootFilePath);
			afpc.froDir(rootFile);
			System.out.println(rootFilePath + "-------------end");
		}
		
		
		
		List<Map> list = afpc.needMoveFilesList;
		for (int i = 0; i < list.size(); i ++) {
			Map map = list.get(i);
			File oldFile = (File) map.get("oldFile");
			File newFile = (File) map.get("newFile");
			String oldFilePath = oldFile.getPath();
			String newFilePath = newFile.getPath();
			
			System.out.println(",--oldFilePath-" + oldFilePath);
			System.out.println(",--newFilePath-" + newFilePath);
			System.out.println("----------------------华丽的分隔线--------------------------");
			
			boolean moveSuccess = oldFile.renameTo(newFile);//修改文件名并移动
			
			//TODO删除多余的文件夹
			
			
		}
		
		System.out.println("----------------------以下是以文件夹形式移动的--------------------------");
		List<Map> listDir = afpc.needMoveFilesListForDir;
		for (int i = 0; i < listDir.size(); i ++) {
			Map map = listDir.get(i);
			File oldFile = (File) map.get("oldFile");
			File newFile = (File) map.get("newFile");
			String oldFilePath = oldFile.getPath();
			String newFilePath = newFile.getPath();
			
			System.out.println(",--oldFilePath-" + oldFilePath);
			System.out.println(",--newFilePath-" + newFilePath);
			System.out.println("----------------------华丽的分隔线--------------------------");
			
			try {
				FileUtils.moveDirectory(oldFile, newFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		//移动文件
//		File fold = new File("D:/copyTest/平安_bsdb/精品/初中数学/北师大版/八年级上册/第二章 实数/1 认识无理数/s3__2.1.2认识无理数学案（学生版+教师版）/1 认识无理数aaaa.doc");//指定文件路径
//	    String strNewPath = "D:/copyTest/平安_bsdb/精品/初中数学/北师大版/八年级上册/第二章 实数/1 认识无理数/";//新的文件夹路径
//	    File file = new File(strNewPath + "aaaa.doc");//xx.xx表示文件名及后缀
//	    fold.renameTo(file);//修改文件名并移动
		
//		File fold = new File("D:/copyTest/平安_bsdb/精品/初中数学/北师大版/八年级上册/第二章 实数/1 认识无理数/s3__2.1.2认识无理数学案（学生版+教师版）");//指定文件路径
//		String strNewPath = "D:/copyTest/平安_bsdb/精品/初中数学/北师大版/八年级上册/第二章 实数";//新的文件夹路径
//		File file = new File(strNewPath);//
//		boolean moveSuccess = fold.renameTo(file);//修改文件名并移动
		
		
		System.out.println("----end-----------");
		
	}

}
