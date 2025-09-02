package com.zj.modules.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
//import org.apache.http.Consts;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	  JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection httpUrlConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			URL url = new URL(requestUrl);
		    httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			 //HttpURLConnection设置网络超时
            httpUrlConn.setConnectTimeout(4500);  
            httpUrlConn.setReadTimeout(4500);
            
//			httpUrlConn.setRequestProperty("content-type", "text/html");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
			// jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}finally{
			try {
			httpUrlConn.disconnect();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	  JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpUploadRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		HttpURLConnection httpUrlConn = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			URL url = new URL(requestUrl);
		    httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			
			 //HttpURLConnection设置网络超时
            httpUrlConn.setConnectTimeout(4500);  
            httpUrlConn.setReadTimeout(4500);
            
			httpUrlConn.setRequestProperty("content-type", "multipart/form-data");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
			// jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}finally{
			try {
			httpUrlConn.disconnect();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	
//	public static <T> String requestHttpForInnerService(T data, 
//            String serviceUrl) {
//		return requestHttpForInnerService(null, data, serviceUrl, null, null);
//	}
	
	 /**
     * 调用其他http请求
     * @Description: 可加上 try catch block...
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-7-718:22:11
     * @Version: 1.0
     * @param <T>
     * @param type
     * @param data  躯体的请求数据
     * @param serviceUrl  请求的 内部服务的http地址
     * @param headerName
     * @param headerValue
     * @return
     */
//    @SuppressWarnings({ "rawtypes"})
//	public static <T> String requestHttpForInnerService(MediaType type, T data, 
//            String serviceUrl, String headerName, String headerValue) {
//		
//		ResponseEntity<BaseResponseDTO> responseEntity = postForInnerService(type, data, serviceUrl, headerName, headerValue);
//		if (responseEntity.getStatusCode().value() == 200) {
//			if ("200".equals(responseEntity.getBody().getCode())
//					|| "0".equals(responseEntity.getBody().getCode())) {
//
//			} else {
//                log.info("接口：{}, 返回数据错误{}", serviceUrl, JSON.toJSONString(responseEntity.getBody()));
//                throw new MessageException(responseEntity.getBody().getCode(),
//                		responseEntity.getBody().getMessage());
//			}
//		} else {
//			log.info("接口：{},请求报错{}，", serviceUrl, responseEntity.getBody());
//			throw new MessageException(ResponseStatusEnum.ERROR_500.getCode(),
//					ResponseStatusEnum.ERROR_500.getMessage());
//		}
//
//		
//		Object dataObject = responseEntity.getBody().getData();
//		String dataJson = null;
//		if (dataObject instanceof String) {
//			dataJson = (String) responseEntity.getBody().getData();
//		} else {
//			dataJson = JSONObject.toJSONString(responseEntity.getBody().getData());
//		}
//		
//        return dataJson;
//    }
//    
//    @SuppressWarnings("rawtypes")
//	public static <T> ResponseEntity<BaseResponseDTO> postForInnerService(T data, 
//            String serviceUrl) {
//    	return postForInnerService(null, data, serviceUrl, null, null);
//    }
//    
//    /**
//     *  调用其他http请求（返回完整信息）
//     * @version 2022-9-3015:28:57
//     * @author zhouzj
//     * @param <T>
//     * @param type
//     * @param data
//     * @param serviceUrl
//     * @param headerName
//     * @param headerValue
//     * @return
//     */
//    @SuppressWarnings({ "rawtypes"})
//	public static <T> ResponseEntity<BaseResponseDTO> postForInnerService(MediaType type, T data, 
//            String serviceUrl, String headerName, String headerValue) {
//    	ResponseEntity<BaseResponseDTO> responseEntity = new ResponseEntity<BaseResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			HttpHeaders httpHeaders = new HttpHeaders();
//			if (StringUtils.isNotBlank(headerName) && StringUtils.isNotBlank(headerValue)) {
//				httpHeaders.set(headerName, headerValue);
//			}
//			if (type == null) {
//				type = MediaType.APPLICATION_JSON;
////				restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//			}
//			httpHeaders.setContentType(type);
//			HttpEntity httpEntity = new HttpEntity<>(data, httpHeaders);
//			responseEntity = restTemplate.postForEntity(serviceUrl, httpEntity,
//					BaseResponseDTO.class);
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//			log.info("接口：{},请求报错{}，", e.getMessage(), serviceUrl);
//		}
//        return responseEntity;
//    }
//    
//    
//
//    public static <T> String requestHttpForPostService(T data,
//            String serviceUrl) {
//    	return requestHttpForPostService(null, data, serviceUrl, null, null);
//    }
//    
//    /**
//     * post方式请求http服务
//     * @version 2022-9-2215:09:29
//     * @author zhouzj
//     * @param <T>
//     * @param type
//     * @param data  躯体的请求数据
//     * @param serviceUrl  请求的 内部服务的http地址
//     * @return
//     */
//    @SuppressWarnings({ "rawtypes"})
//	public static <T> String requestHttpForPostService(MediaType type, T data,
//            String serviceUrl, String headerName, String headerValue) {
//        String result = "";
//		try {
//			RestTemplate restTemplate = new RestTemplate();
//			HttpHeaders httpHeaders = new HttpHeaders();
//	        if (StringUtils.isNotBlank(headerName) && StringUtils.isNotBlank(headerValue)) {
//	            httpHeaders.set(headerName, headerValue);
//	        }
//			if (type == null) {
//			    type = MediaType.APPLICATION_JSON_UTF8;
//			    restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
//			}
//
//			httpHeaders.setContentType(type);
//			HttpEntity httpEntity = new HttpEntity<>(data, httpHeaders);
//			ResponseEntity<String> responseEntity = restTemplate.postForEntity(
//			         serviceUrl, httpEntity, String.class);
//			if (responseEntity.getStatusCode().value() == 200) {
//			    
//			}
//			else {
//			    log.info("接口：{},请求报错{}，", serviceUrl, responseEntity.getBody());
//			    throw new MessageException(ResponseStatusEnum.ERROR_500.getCode(),
//			            ResponseStatusEnum.ERROR_500.getMessage());
//			}
//			result = responseEntity.getBody();
//		} catch (Exception e) {
//			log.info("接口：{},请求报错{}，", e.getMessage(), serviceUrl);
//		}
//        return result;
//    }
//    
//    /**
//     * 上传多文件
//     * @version 2024-5-149:57:28
//     * @author zhouzj
//     * @param url   请求地址
//     * @param headers header 信息
//     * @param body 需要传输的内容
//     * @param files  MultipartFile 文件集合
//     * @param fileParName 文件接受名
//     * @return
//     */
//    public static String sendFilePost(String url, Map<String, String> headers, String body, MultipartFile[] files, String fileParName) {
//        //客户端
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        //请求
//        HttpPost httppost = new HttpPost(url);
//        
//        //文件请求
//        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//        //必须设置头
//        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
//        builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
//        
//        //单个文件
//        String fileName = null;
//        MultipartFile multipartFile = null;
//        for (int i = 0; i < files.length; i++) {
//            //第一个参数为 相当于 Form表单提交的file框的name值 第二个参数就是我们要发送的InputStream对象了
//            //第三个参数是文件名
//            //3)
//            multipartFile = files[i];
//            //文件名
//            fileName = multipartFile.getOriginalFilename();
//            InputStream inputStream = null;
//            try {
//                //得到流
//                inputStream = multipartFile.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //添加到 请求体中。注意这个参数名：fileParName，应该为file
//            builder.addBinaryBody(fileParName,inputStream, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
//        }
//        //4)构建请求参数 普通表单项
//		/*	StringBody stringBody = new StringBody("12", ContentType.MULTIPART_FORM_DATA);
//			builder.addPart("id", stringBody);*/
//        //决中文乱码
//        ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, Consts.UTF_8);
//        //添加请求的其他参数信息
//        builder.addTextBody("body", body, contentType);
//        //构建出 entity
//        org.apache.http.HttpEntity entity = builder.build();
//        //header头
//        if (null!=headers&&headers.size()>0){
//            for (Map.Entry<String, String> entry : headers.entrySet()) {
//                httppost.addHeader(entry.getKey(),entry.getValue());
//            }
//        }
//        //设置上实体
//        httppost.setEntity(entity);
//        CloseableHttpResponse response = null;
//        try {
//            //进行请求
//            response = httpclient.execute(httppost);
//        } catch (IOException e) {
//            log.error("请求出错:" + url, e);
//            return null;
//        }
//        String result = null;
//        try {
//            if(response!=null){
//            	org.apache.http.HttpEntity httpEntity = response.getEntity();
//                //判断http的状态
//                if (httpEntity != null && response.getStatusLine().getStatusCode()== HttpURLConnection.HTTP_OK) {// 判断请求状态
//                    //转成String返回
//                    result = EntityUtils.toString(httpEntity);
//                }
//            }
//        } catch (Exception e) {
//            log.error("请求出错:" + url, e);
//        } finally {
//            try {
//                if(response!=null){
//                    response.close();
//                }
//            } catch (IOException e) {
//                log.error("请求出错:" + url, e);
//            }
//        }
//        log.info("请求的URL:" + url + ", 返回结果:" + result);
//        return result;
//    }
    
//    public static void main(String[] args) {
//    	String requestOtherHttpService = HttpUtil.requestHttpForPostService("", "http://www.baidu.com");
//    	String requestOtherHttpService1 = HttpUtil.requestHttpForPostService("", "http://10.88.130.65:8809/open/api/gold/price");
////    	Kuaidi100StateVo aa = HttpUtil.requestHttpForInnerService(null, null, "3245325", Kuaidi100StateVo.class);
//    	
//    	System.out.println(requestOtherHttpService);
//	}
	
}
