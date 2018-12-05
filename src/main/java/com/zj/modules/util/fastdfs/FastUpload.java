package com.zj.modules.util.fastdfs;


import java.io.IOException;
import java.text.DecimalFormat;

import org.csource.common.NameValuePair;
import org.springframework.web.multipart.MultipartFile;

import com.zj.modules.util.LogUtils;
import com.zj.modules.util.PropertiesUtil;


/**
 * 统一上传
 *
 * @author zj
 * @return filePath
 * 2017年9月8日
 */
public class FastUpload {

	public static String upload(MultipartFile file) {

		String filePath = "";
		try {
			// 获取文件后缀名
			String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			FastDFSFile fastFile = new FastDFSFile(file.getBytes(), ext);
			NameValuePair[] meta_list = new NameValuePair[4];
			meta_list[0] = new NameValuePair("fileName", file.getOriginalFilename());
			meta_list[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
			meta_list[2] = new NameValuePair("fileExt", ext);
			meta_list[3] = new NameValuePair("fileAuthor", "zj");
			
			filePath = FileManager.upload(fastFile, meta_list);
			
//			//上传成功保存至 文件配置表
//			String id = UuidUtil.get32UUID();
//			String name = "";
//			String type = "";
//			String size = "0KB";
//			String fileName = file.getOriginalFilename();
//			if (fileName != null && !"".equals(fileName)) {
//				String values[] = fileName.split("\\.");
//				if (values != null && values.length > 0) {
//					name = values[0];
//					type = values[1];
//				}
//			}
//			size = getFileSize(file.getSize());
			
			String protocol = PropertiesUtil.getValue("protocol", null);
	        String addr = PropertiesUtil.getValue("tracker.ngnix.addr", null);
	        String separator = PropertiesUtil.getValue("separator", null);
	        filePath = protocol + addr + filePath;
			
			LogUtils.info(":::filePath:::::" + filePath);
		} catch (IOException e) {
			LogUtils.info("上传失败！");
			e.printStackTrace();
			return "";
		}
		
		return filePath;
	}
	
	public static String uploadFile(MultipartFile file,String fileName) {

		String filePath = "";
		try {
			// 获取文件后缀名
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			FastDFSFile fastFile = new FastDFSFile(file.getBytes(), ext);
			NameValuePair[] meta_list = new NameValuePair[4];
			meta_list[0] = new NameValuePair("fileName", file.getOriginalFilename());
			meta_list[1] = new NameValuePair("fileLength", String.valueOf(file.getSize()));
			meta_list[2] = new NameValuePair("fileExt", ext);
			meta_list[3] = new NameValuePair("fileAuthor", "zj");
			
			filePath = FileManager.upload(fastFile, meta_list);
			
			String protocol = PropertiesUtil.getValue("protocol", null);
	        String addr = PropertiesUtil.getValue("tracker.ngnix.addr", null);
	        String separator = PropertiesUtil.getValue("separator", null);
	        filePath = protocol + addr + filePath;
			LogUtils.info(":::filePath:::::" + filePath);
		} catch (IOException e) {
			LogUtils.info("上传失败！");
			e.printStackTrace();
			return "";
		}
		
		return filePath;
	}
	
	/**
	 * 
	 * zj
	 * 2017年9月26日
	 */
	public static String getFileSize(long fileSize){
		String size = "";
		try {
			DecimalFormat df = new DecimalFormat("#.00"); 
			if (fileSize < 1024) {
				size = df.format((double) fileSize) + "BT";
			} else if (fileSize < 1048576) {
				size = df.format((double) fileSize / 1024) + "KB";
			} else if (fileSize < 1073741824) {
				size = df.format((double) fileSize / 1048576) + "MB";
			} else {
				size = df.format((double) fileSize / 1073741824) +"GB";
			}
		} catch (Exception e) {
			e.printStackTrace();
			size = "0KB";
		}
		return size;
	}

}