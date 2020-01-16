package com.zj.modules.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.zj.modules.util.BaseUtil;
@RestController
@RequestMapping("/file")
public class FileUtilController {

	@RequestMapping("/download")
	//@ResponseBody
	public String download(HttpServletResponse response, HttpServletRequest request, String downLoadPath, String realFileName){
		response.setContentType("text/html;charset=UTF-8");  
        try {  
        	//此处为需要下载的网络加密文件地址如：http://files.21cnjy.com/group1/M00/00/05/wKgDD1sLb0OARgVEAAAcH0MXCyM80.xlsx?st=Ar-3OP0SMWkHkUgnTpAa1g&e=1527482047
//        	String realFileName = fileName + "." + fileType;
            request.setCharacterEncoding("UTF-8");  
            BufferedInputStream bis = null;  
            BufferedOutputStream bos = null;  
           
            response.setContentType("application/octet-stream");  
            response.reset();//清除response中的缓存  
            //根据网络文件地址创建URL  
            URL url = new URL(downLoadPath);  
            //获取此路径的连接  
            URLConnection conn = url.openConnection();  
  
            Long fileLength = conn.getContentLengthLong();//获取文件大小  
            //设置reponse响应头，真实文件名重命名，就是在这里设置，设置编码  
            response.setHeader("Content-disposition",  
            "attachment; filename=" + new String(realFileName.getBytes("utf-8"), "ISO8859-1"));  
            response.setHeader("Content-Length", String.valueOf(fileLength));  
  
            bis = new BufferedInputStream(conn.getInputStream());//构造读取流  
            bos = new BufferedOutputStream(response.getOutputStream());//构造输出流  
            byte[] buff = new byte[1024];  
            int bytesRead;  
            //每次读取缓存大小的流，写到输出流  
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            	bos.write(buff, 0, bytesRead);  
            }  
            response.flushBuffer();//将所有的读取的流返回给客户端  
            bis.close();  
            bos.close();  
  
        } catch (Exception e) {  
            return e + e.getMessage();  
        }  
        return null;  
		
	}
	

	/**
	 * 下载网络图片至本地
	 * @author zj
	 * @param urlStrings 统一以逗号分割
	 * @param filenames  统一以逗号分割
	 * @param filePath 指生成文件的保存的位置 此处 尽量以 文件夹名称 +公司id来进行区分 如：files/234252
	 * @throws Exception
	 * @Return  返回 文件存储的 位置path
	 * 创建时间：2019年3月22日 下午2:07:47
	 */
	public static String downloadNetFileToLocal(HttpServletRequest request, String urlStrings, String fileNameStrings, String filePath) {
		StringBuilder filePathForLocal = new StringBuilder();
		//默认保存地址
		String savePath = BaseUtil.getDemoPath(request, filePath);
	    // 构造URL
		URL url = null;
		// 输入流
	    InputStream is = null;
	    //输出流
	    OutputStream os = null;
	    try {
			String[] urlStrs =  BaseUtil.splitForComma(urlStrings);
			String[] fileNames =  BaseUtil.splitForComma(fileNameStrings);
			if (BaseUtil.isEmptyStrings(urlStrs) || BaseUtil.isEmptyStrings(fileNames) || urlStrs.length != fileNames.length) {
				return "";
			}
			
			for (int i = 0; i < urlStrs.length; i ++) {
				String urlStr = urlStrs[i];
				String fileName = fileNames[i];
				url = new URL(urlStr);
			    // 打开连接
			    URLConnection con = url.openConnection();
			    //设置请求超时为5s
			    con.setConnectTimeout(5 * 1000);
			    // 输入流
			    is = con.getInputStream();

			    // 1K的数据缓冲
			    byte[] bs = new byte[1024];
			    // 读取到的数据长度
			    int len;
			    // 输出的文件流
			    File sf=new File(savePath);
			    if(!sf.exists()){
			        sf.mkdirs();
			    }
			    String filePathForLocalStr = savePath + "/" + fileName;
			    System.out.println("filePathForLocalStr = " + filePathForLocalStr);
			    
			    if (i == 0) {
			    	filePathForLocal.append(filePathForLocalStr);
				} else {
					filePathForLocal.append("," + filePathForLocalStr);
				}
			    
			    os = new FileOutputStream(filePathForLocalStr);
			    // 开始读取
			    while ((len = is.read(bs)) != -1) {
			        os.write(bs, 0, len);
			    }
			    // 完毕，关闭所有链接
			    os.close();
			    is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage() + e);
			return "";
		}
	    System.out.println("filePathForLocal.toString()=" + filePathForLocal.toString());
	    return filePathForLocal.toString();
	}
	
	/**
	 * 下载文件（模板）
	 * zj
	 * 2018年10月15日
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
		try {
			response.setContentType("text/html;charset=UTF-8");  
			InputStream is = new FileInputStream(new File(filePath));
			// 设置response参数，可以打开下载页面
			fileName = BaseUtil.getStringByBrowserType(request, fileName);
			
			response.reset();
//			response.setContentType("application/octet-stream");  
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
			// 获取文件输出IO流  
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
				// 放到缓冲流里面 
			    bis = new BufferedInputStream(is);
			    // 读取目标文件，通过response将目标文件写到客户端
			    bos = new BufferedOutputStream(out);
			    byte[] buff = new byte[2048];
			    // 写文件
			    int bytesRead;
			    // Simple read/write loop.
			    // 开始向网络传输文件流  
			    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			    	bos.write(buff, 0, bytesRead);
			    }
			} catch (final IOException e) {
			    throw e;
			} finally {
//				bos.flush();// 这里一定要调用flush()方法  
			    if (bis != null)
				bis.close();
			    if (bos != null)
				bos.close();
			    File tFile = new File(filePath);
			    tFile.deleteOnExit();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage() + e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage() + e);
		}
	}
	
	/**
	 * 批量压缩并下载
	 * filePaths 文件聚堆位置 如：C:/Users/Administrator/Desktop/PropertiesUtil.java, C:/Users/Administrator/Desktop/PropertiesUtil.java
	 * fileNames 文件名称 如： PropertiesUtil.java, PropertiesUtil.java
	 * fileZipName 压缩文件名称 ： aaa.zip
	 * fileZipDir 压缩文件预放位置 尽量以zip + 公司id形式来进行区分如： zip/21313
	 * zj
	 * 2018年12月8日
	 */
	public static void batchZipDownload(HttpServletRequest request, HttpServletResponse response, String filePaths,
			String fileNames, String fileZipName, String fileZipDir) {
        byte[] buffer = new byte[1024];  
        try {
        	fileZipDir = BaseUtil.getDemoPath(request, fileZipDir);
        	fileZipName.replaceAll("/", "_");
        	fileZipDir += "/" + fileZipName;
        	System.out.println("zip fileZipDir =" + fileZipDir);
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fileZipDir));  
            String[] files=filePaths.split(",",-1);
            String[] names=fileNames.split(",",-1);
            // 下载的文件集合
            for (int i = 0; i < files.length; i++) {  
                FileInputStream fis = new FileInputStream(files[i]);  
                out.putNextEntry(new ZipEntry(names[i])); 
                 //设置压缩文件内的字符编码，不然会变成乱码  
                out.setEncoding("GBK");  
                int len;  
                // 读入需要下载的文件的内容，打包到zip文件  
                while ((len = fis.read(buffer)) > 0) {  
                    out.write(buffer, 0, len);  
                }  
                out.closeEntry();  
                fis.close();  
            }
             out.close();  
             downloadFile(request, response, fileZipDir, fileZipName);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage() + e);
        }
    }
	
	/**
	 * 将json数组字符串转换成 String以逗号分割形式的String
	 * @author zj
	 * @param fileUrls
	 * @return
	 * 创建时间：2019年3月22日 下午2:38:17
	 */
	public static Map<String, String> getJsonArrFileNameAndUrlToString (String  fileUrls, String urlKey, String fileNmeKey) {
		Map<String, String> returnMap = new HashMap<String, String>(); 
		JSONArray fileurlArr = com.alibaba.fastjson.JSONObject.parseArray(fileUrls);
		StringBuilder sbUrl = new StringBuilder();
		StringBuilder sbFileName = new StringBuilder();
		for (int i = 0; i < fileurlArr.size(); i ++) {
			com.alibaba.fastjson.JSONObject fileObj = com.alibaba.fastjson.JSONObject.parseObject(fileurlArr.getString(i));
			String prefix = BaseUtil.getGivenRandomNum("F", 7);
			String realFileName =  fileObj.getString(fileNmeKey);
			String downLoadPath =  fileObj.getString(urlKey);
			String[] realFileNames = realFileName.split("\\.");
			realFileName = realFileNames[0] + prefix + "." + realFileNames[1];
			
			if (i == 0) {
				sbUrl.append(downLoadPath);
				sbFileName.append(realFileName);
			} else {
				sbUrl.append("," + downLoadPath);
				sbFileName.append("," + realFileName);
			}
		}
		returnMap.put("urls", sbUrl.toString());
		returnMap.put("fileNames", sbFileName.toString());
		return returnMap;
	}
	
	
	//静态方法：三个参数：文件的二进制，文件路径，文件名
	//通过该方法将在指定目录下添加指定文件	
	public static void uploadToLocal(byte[] file,String filePath,String fileName) throws IOException {
		//目标目录		
//		Runtime.getRuntime().exec("chmod 777 -R " + filePath);//设置文件权限
		File targetfile = new File(filePath);
		System.out.println("filePath = " + filePath + ", fileName");
		System.out.println("targetfile.exists() = " + targetfile.exists());
		if(!targetfile.exists()) {
			targetfile.setWritable(true, false);
			targetfile.mkdirs();
			System.out.println("--------------已经创建文件夹");
		}
		
		//二进制流写入		
		FileOutputStream out = new FileOutputStream(filePath+fileName);
	    out.write(file);
	    out.flush();
	    out.close();
	}
	
}
