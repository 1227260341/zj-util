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

import com.zj.modules.util.ParseText;


/**
 * 获取附件等的文本
 *
 * @author zj
 * 
 * 2018年12月5日
 */
@RequestMapping("/enclosure")
@Controller
public class GetTextController {

	
/*	@RequestMapping("/syncData/{schoolId}")
	public Object syncData(@PathVariable String schoolId) {
		try {
			syncDataService.syncData(schoolId);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return "";
	}*/

	
	
	/**
	 * 获取附件文本
	 * zj
	 * 2018年6月11日
	 */
	@RequestMapping("/getText")
	@ResponseBody
	public Object getSchoolInfo(HttpServletRequest request) {
		try {
			String fileUrl = request.getParameter("fileUrl");
			String suffix = request.getParameter("suffix");
			if (fileUrl == null || "".equals(fileUrl) || suffix == null || "".equals(suffix) ) {
				return -1;
			}
//			fileUrl = "http://localhost/file/download?fileId=9a51de72c5c24783bd60d30d627825eb";
//			suffix = "txt";
//			fileUrl = "http://localhost/devel/html/download?schoolId=429963&schoolYear=2017&term=2&classId=bfcf27f026c44768af423883bfc083a0&remark=1";
//			suffix = "xlsx";
			
			URL url = new URL(fileUrl);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        DataInputStream in = new DataInputStream(conn.getInputStream());  
	        ByteArrayOutputStream out=new ByteArrayOutputStream();  
	        byte[] buffer=new byte[1024*4];  
	        int n=0;  
	        while ( (n=in.read(buffer)) !=-1) {  
	            out.write(buffer,0,n);  
	        }  
	        
	        String text = ParseText.parse(out.toByteArray(), suffix);
	        
	        String aa = new String(text.getBytes(), "utf-8");
	        //return out.toByteArray();  
	        List textList = new ArrayList<>();
	        textList.add(aa);
	        return textList;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
