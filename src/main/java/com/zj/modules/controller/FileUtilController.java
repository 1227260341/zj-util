package com.zj.modules.controller;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;
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
	
}
