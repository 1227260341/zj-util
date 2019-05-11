package com.zj.modules.util;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.modules.util.LogUtils;


/**
 * 获取 .properties配置  及判断null "" .... 
 *name 及 properties文件名
 * @author zj
 * 
 * 2017年9月25日
 */
public class PropertiesUtil {
	
	static String[] units = { "", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
			"十亿", "百亿", "千亿", "万亿" };
	static char[] numArray = { '零', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
	
	//专门用于分隔
	static Pattern separator = Pattern.compile(",");
	
	
	/**
	 * 获取配置文件的 value
	 * zj
	 * 2017年10月24日
	 */
	public static String getValue(String key, String name){
		if (name ==null || "".equals(name)) {
			name = "baseconfig";
		}
		Properties prop = new Properties();
		InputStream in = new PropertiesUtil().getClass().getResourceAsStream("/" + name + ".properties");
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
	
	/**
	 * 验证 string类型 
	 * zj
	 * 2017年10月24日
	 * 
	 */
	public static boolean verifyString(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证string 判断 ‘null’ 的字符串
	 * zj
	 * 2017年11月29日
	 */
	public static boolean verifyStringNull(String str) {
		if (str == null || "".equals(str.trim()) || "null".equalsIgnoreCase(str.trim())) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证list
	 * zj
	 * 2017年10月24日
	 */
	public static boolean verifyList(List list) {
		if (list == null || list.size() < 1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证对象
	 * zj
	 * 2017年10月24日
	 */
	public static boolean verifyObject(Object obj) {
		if (obj == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 验证 string类型
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyString(String str) {
		if (str != null) {
			str = replaceBlank(str);
		}
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证list
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyList(List list) {
		if (list == null || list.size() < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证对象
	 * zj
	 * 2017年10月24日
	 */
	public static boolean isEmptyObject(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmptyStrings(String[] str) {
		if (str == null || str.length < 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * 验证多参数，是否是 null 或者 "",如果是 则 返回 true
	 * zj
	 * 2017年11月17日
	 */
	public static boolean existEmptyParams(String... params) {
		if (params == null || params.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < params.length; i ++) {
			String param = params[i];
			if (isEmptyString(param)) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 验证多参数，是否是 null 或 'null'字符串 或者 "",如果是 则 返回 true
	 * zj
	 * 2018年1月31日
	 */
	public static boolean existEmptyOrNullParams(String... params) {
		if (params == null || params.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < params.length; i ++) {
			String param = params[i];
			if (!verifyStringNull(param)) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 验证Object多参数，是否是 null 或 'null'字符串 或者 "",如果是 则 返回 true
	 * zj
	 * 2018年5月30日
	 */
	public static boolean existEmptyOrNullObject(Object... objects) {
		if (objects == null || objects.length < 1) {
			return true;
		}
		
		boolean isEmpty = false;
		for (int i = 0; i < objects.length; i ++) {
			Object param = objects[i];
			if (param == null || "".equals(param) || "null".equalsIgnoreCase(param.toString())) {
				isEmpty = true;
				return isEmpty;
			}
		}
		return isEmpty;
	}
	
	/**
	 * 获取请求的ip
	 * zj
	 * 2018年1月17日
	 */
	public static String getIpAddr(HttpServletRequest request) {
	     String ipAddress = null;
	     //ipAddress = this.getRequest().getRemoteAddr();
	     ipAddress = request.getHeader("x-forwarded-for");
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	      ipAddress = request.getHeader("Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");
	     }
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
		     ipAddress = request.getRemoteAddr();
		     if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){
		       //根据网卡取本机配置的IP
		       InetAddress inet=null;
			    try {
			     inet = InetAddress.getLocalHost();
			    } catch (UnknownHostException e) {
			     e.printStackTrace();
			    }
			    ipAddress= inet.getHostAddress();
		     }
	     }

	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15
	         if(ipAddress.indexOf(",")>0){
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
	         }
	     }
	     return ipAddress; 
	}
	
	
	/**
	 * 保存cookies加密值 信息 zj
	 */
	public static boolean setCookiesEncryptValue(String cookieName, String cookieValue, HttpServletResponse response) {
		
		if (PropertiesUtil.existEmptyParams(cookieName, cookieValue)) {
			LogUtils.info("设置cookie接口传入的参数有误@!");
			return false;
		}
		String value = new String(Base64.encodeBase64(cookieValue.getBytes()));
		LogUtils.info("加密后的值：" + value);
		String deValue = new String(Base64.decodeBase64(value));
		LogUtils.info("解密后的值：" + deValue);
		Cookie cookieLoginToken = new Cookie(cookieName, value);
		cookieLoginToken.setMaxAge(3 * 3600 * 24);
		cookieLoginToken.setPath("/");
		//加入cookie中
		response.addCookie(cookieLoginToken);
		
		return true;
	}
	
	
	/**
	 * zj
	 * 获取cookie 中对应的解密值
	 */
	public static String getCookiesDecript(HttpServletRequest request, String cookieName) {
		if (PropertiesUtil.isEmptyString(cookieName)) {
			LogUtils.info("获取cookie方法请传入正确的参数");
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				String value = cookie.getValue();
				if (PropertiesUtil.isEmptyString(value)) {
					return null;
				}
				String loginToken = new String(Base64.decodeBase64(value));
				return loginToken;
			}
		}
		return null;
	}
	
	/**
	 * 保存 cookie 
	 * zj
	 * 2018年2月25日
	 */
	public static boolean setCookies(String cookieName, String cookieValue, int timeOut ,HttpServletResponse response) {
		
		if (PropertiesUtil.existEmptyParams(cookieName, cookieValue)) {
			LogUtils.info("设置cookie接口传入的参数有误@!");
			return false;
		}
		
		Cookie cookieLoginToken = new Cookie(cookieName, cookieValue);
		cookieLoginToken.setMaxAge(timeOut);//秒
		cookieLoginToken.setPath("/");
		//加入cookie中
		response.addCookie(cookieLoginToken);
		
		return true;
	}
	
	/**
	 * 获取对应cookie值 并刷新cookie值存在的时间
	 * cookieTime 时间  单位 s（秒）
	 * zj
	 * 2018年2月25日
	 */
	public static String getCookies(HttpServletRequest request, String cookieName, Integer cookieTime) {
		if (PropertiesUtil.isEmptyString(cookieName)) {
			LogUtils.info("获取cookie方法请传入正确的参数");
			return null;
		}
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				if (cookieTime != null) {// 则进行刷新cookie值时间
					cookie.setMaxAge(cookieTime);
				}
				String value = cookie.getValue();
				return value;
			}
		}
		return null;
	}
	
	/**
	 * 删除cookie 对应的值
	 * cookieName 需要删除的cookie对应name
	 * zj
	 * 2018年1月23日
	 */
	public static boolean delCookies(HttpServletRequest reuqest, HttpServletResponse response, String cookieName) {
		Cookie[] cookies = reuqest.getCookies();
		if (cookies == null) {
			LogUtils.info("清除cookie失败！");
			return false;
		}
		String cookieValue = null;
		for (Cookie cookie : cookies) {
			if (cookieName.equals(cookie.getName())) {
				cookieValue = cookie.getValue();
			}
		}
		
		if (PropertiesUtil.verifyString(cookieValue)) {//说名cookie值还存在，则进行清除操作
			Cookie cookieClean = new Cookie(cookieName, null);
			cookieClean.setMaxAge(0);
			cookieClean.setPath("/");
			response.addCookie(cookieClean);
		}
		
		return true;
	}
	
	/**
	 * 判断两个double是否相等
	 * zj
	 * 2018年4月27日
	 */
	public static boolean equalsDouble(double a, double b) {
		
		if ((a - b > -0.000001) && (a-b) < 0.00001 ) {
			return true;
		}
		return false;
	}

	/**
	 * 四舍五入保留两位小数方法
	 * zj
	 * 2018年8月23日
	 */
	public static double doubleKeepTwo(double doubleValue) {
		
		BigDecimal bd = new BigDecimal(doubleValue);
		doubleValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return doubleValue;
	}
	
	/**
	 * 修改map 获取需要的字段
	 * zj
	 * 2018年9月30日
	 */
	public static double includeMap(double doubleValue) {
		
		BigDecimal bd = new BigDecimal(doubleValue);
		doubleValue = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return doubleValue;
	}
	
	/**
	 * 将泛型转换成map
	 * zj
	 * 2018年10月8日
	 */
	public static <T> Map<String, Object> conversionToMap(T bean) throws Exception {		
		Map<String, Object> map = new HashMap<String, Object>();	
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();	
		PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean); 	
		for (PropertyDescriptor d : descriptors) {		
			String fieldName = d.getName();		
			Object value = propertyUtilsBean.getNestedProperty(bean, fieldName);		
			if (!"class".equals(fieldName))			
				map.put(fieldName, value);		
		}		
		return map;
	}
	
	/**
     * 实体对象转成Map
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * Map转成实体对象
     * @param map map实体对象包含属性
     * @param clazz 实体对象类型
     * @return
     */
    public static Object map2Object(Map<String, Object> map, Class<?> clazz) {
        if (map == null) {
            return null;
        }
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return obj;
    }

	/**
	 * 获取指定的字段数据返回
	 * zj
	 * 2018年10月8日
	 * 
	 */
	public static  Map getSpecifiedFields(Object obj, String... fields) {
		Map<String, Object> data = null;
		try {
			data = object2Map(obj);
			
			if (fields == null || fields.length < 1) {
				return null;
			}
			
			Iterator<Map.Entry<String, Object>> entrys = data.entrySet().iterator();
			while(entrys.hasNext()) {
				Map.Entry<String, Object> entry = entrys.next(); 
				boolean isNeedRemove = true;
				for (int i = 0; i < fields.length; i ++) {
					String param = fields[i];
					//System.out.println(param +"-------------------------------------------------------" + entry.getKey());
					if (param.equals(entry.getKey())) {
						isNeedRemove = false;
						//System.out.println("----------ddddddddddddddddd---------------------------------------------");
					}
				}
				if (isNeedRemove) {
					entrys.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 获取指定的字段数据返回List
	 * zj
	 * 2018年10月8日
	 * @param <E>
	 */
	public static  <E> List<Map<String, Object>> getSpecifiedFieldsList(List<E> objList, String... fields) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		try {
			for (Object obj : objList) {
				Map<String, Object> data = getSpecifiedFields(obj, fields);
				dataList.add(data);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/**
	 * 根据浏览器不同获取不同编码格式的String
	 * zj
	 * 2018年10月15日
	 */
	public static  String getStringByBrowserType(HttpServletRequest request, String fileName) {
		
//      if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
//        finalFileName = URLEncoder.encode(fileName,"UTF8");
//    }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
//        finalFileName = new String(fileName.getBytes(), "ISO8859-1");
//    }else{
//        finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
//    }
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 
					 || userAgent.indexOf("Safari") >= 0) {
				fileName= new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return fileName;
	}
	
	/**
	 * 下载文件（模板）
	 * zj
	 * 2018年10月15日
	 */
	public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String filePath, String fileName) {
		try {
			InputStream is = new FileInputStream(new File(filePath));
			// 设置response参数，可以打开下载页面
			fileName = PropertiesUtil.getStringByBrowserType(request, fileName);
			
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量压缩并下载
	 * zj
	 * 2018年12月8日
	 */
	public static void batchZipDownload(HttpServletRequest request, HttpServletResponse response, String filePaths,
			String fileNames, String fileZipName, String fileZipDir) {
        byte[] buffer = new byte[1024];  
        try {
        	fileZipDir = getDemoPath(fileZipDir);
        	fileZipName.replaceAll("/", "_");
        	fileZipDir += "/" + fileZipName;
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
        }
    }
	
	public static String getDemoPath(String folderName) {
		String tFilePath = System.getProperty("user.dir");
		LogUtils.info("user.dir path=" + tFilePath);
		if (PropertiesUtil.isEmptyString(tFilePath) || "/".equals(tFilePath)) {
			HttpServletRequest request = getRequestAttributes().getRequest();
			String realPath = request.getSession().getServletContext().getRealPath("/");
			tFilePath = realPath;
			LogUtils.info("getRealPath path=" + tFilePath);
		}
		tFilePath = tFilePath.replaceAll("\\\\", "/");
		tFilePath = tFilePath + "/temp/" + folderName;
		File file = new File(tFilePath);
		if (!file.exists()) {//不存在文件夹 则进行创建
			file.mkdirs();
		}
		
		return tFilePath; 
	}
	
	/**
	 * 获取 RequestAttributes
	 * zj
	 * 2018年8月31日
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes;
	}
	
	/**
	 * 将阿拉伯数字转换成中文大写数字
	 * zj
	 * 2018年11月7日
	 */
	public static String convertChineseNum(int num) {
		char[] val = String.valueOf(num).toCharArray();
		int len = val.length;
		System.out.println("----" + len);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			String m = val[i] + "";
			int n = Integer.valueOf(m);
			boolean isZero = n == 0;
			String unit = units[(len - 1) - i];
			if (isZero) {
				if ('0' == val[i - 1]) {
					//当前val[i]的下一个值val[i-1]为0则不输出零
					continue;
				} else {
					//只有当当前val[i]的下一个值val[i-1]不为0才输出零
					sb.append(numArray[n]);
				}
			} else {
				sb.append(numArray[n]);
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	
	public static String convertChineseNum2(String string) {
		String[] s1 = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] s2 = { "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千" };

        String result = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';
            if (n == 2) {//说名是两位
            	if (i == 0) {
            		if (num == 1) {
            			result += s2[0];
            		} else {
            			result += s1[num] + s2[0];
            		}
            	} else {
            		if (num == 0) {
            			continue ;
            		}
            		result += s1[num];
            	}
            	
            } else if (n == 1) {
            	result = s1[num];
            } else {
	            if (i != n - 1 && num != 0) {
	                result += s1[num] + s2[n - 2 - i];
	            } else {
	                result += s1[num];
	            }
            }

        }

        System.out.println(result);
		return result;
	}
	
	/**
	 * 去除空格、回车、换行符、制表符
	 * \n 回车(\u000a) 
	 * \t 水平制表符(\u0009) 
	 * \s 空格(\u0008) 
	 * \r 换行(\u000d)
	 * zj
	 * 2018年12月21日
	 */
	public static String replaceBlank(String str) {
		String result = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			result = m.replaceAll("");
		}
		return result;
	}
	
	/**
	 * split 以逗号分隔
	 * zj
	 * 2018年12月25日
	 */
	public static String[] splitForComma(String str) {
		String[] result = {};
		if (str!=null) {
			result = separator.split(str);
			
		}
		return result;
	}
	
	/**
	 * 将json字符串转换成map
	 * @author zj
	 * @param jsonString
	 * @return
	 * 创建时间：2019年4月11日 下午12:21:23
	 */
	public static Map<String, Object> jsonStringToMap(String jsonString) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = mapper.readValue(jsonString,
					new TypeReference<HashMap<String, Object>>() {
					});
			Set<String> set = map.keySet();
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String values = map.get(key).toString();
				values = values.trim();
				map.put(key, values);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
}