package com.zj.modules.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 一些简单的工具类
 * @author zj
 *
 * 创建时间：2019年3月22日 下午1:26:13
 */
//@Slf4j
public class BaseUtil {
	
	//专门用于分隔
	static Pattern separator = Pattern.compile(",");

	
	public static String getDemoPath(HttpServletRequest request, String folderName) {
		String tFilePath = System.getProperty("user.dir");
		System.out.println("user.dir path=" + tFilePath);
		if (isEmptyString(tFilePath) || "/".equals(tFilePath)) {
//			HttpServletRequest request = getRequestAttributes().getRequest();
			String realPath = request.getSession().getServletContext().getRealPath("/");
			tFilePath = realPath;
			System.out.println("getRealPath path=" + tFilePath);
		}
		tFilePath = tFilePath.replaceAll("\\\\", "/");
		tFilePath = tFilePath + "/temp/" + folderName;
		System.out.println("储存文件的地址tFilePath=" + tFilePath);
		File file = new File(tFilePath);
		if (!file.exists()) {//不存在文件夹 则进行创建
			file.mkdirs();
		}
		
		return tFilePath; 
	}
	
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
		InputStream in = new BaseUtil().getClass().getResourceAsStream("/" + name + ".properties");
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
			        System.out.println(e.getMessage() + e);
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
	 * 获取 RequestAttributes
	 * zj
	 * 2018年8月31日
	 */
//	public static ServletRequestAttributes getRequestAttributes() {
//		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//		return servletRequestAttributes;
//	}
	
	/**
	 * 根据浏览器不同获取不同编码格式的String
	 * zj
	 * 2018年10月15日
	 */
	public static  String getStringByBrowserType(HttpServletRequest request, String fileName) {
		try {
			final String userAgent = request.getHeader("USER-AGENT");
			if (userAgent != null && userAgent.indexOf("Firefox") >= 0 || userAgent.indexOf("Chrome") >= 0 
					 || userAgent.indexOf("Safari") >= 0) {
				fileName= new String((fileName).getBytes(), "ISO8859-1");
			} else {
				fileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
			}
		} catch (UnsupportedEncodingException e) {
		    System.out.println(e.getMessage() + e);
		}
		
		return fileName;
	}
	
	/**
	 *  返回特定的随机数编号(日期方式)
	 * @author zj
	 * @param prefix 前缀
	 * @param rondomNum 随机位数 1000 指 随机四位
	 * @return
	 * 创建时间：2019年4月4日 上午11:19:08
	 */
	public static String getGivenRandomNum(String prefix, int rondomNum) {
		LocalDateTime now = LocalDateTime.now();
		int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        int last = (int)((Math.random()*9+1)*rondomNum);//简单的获取后随机数
        String num = prefix + year + (month < 10? "0" + month: month) + (day < 10? "0" + day: day) + last;
		return num;
	}
	
	/**
	 * 返回特定的随机数编号（时间戳方式）
	 * @author zj
	 * @param prefix
	 * @param rondomNum
	 * @return
	 * 创建时间：2019年7月8日 下午2:04:01
	 */
	public static String getMilliRandomNum(String prefix, int rondomNum) {
		LocalDateTime now = LocalDateTime.now();
		Long milli = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        int last = (int)((Math.random()*9+1)*rondomNum);//简单的获取后随机数
        String num = prefix + milli + last;
		return num;
	}
	
	/**
	 * json 对象转换成map
	 * @author zj
	 * @param jsonString
	 * @return
	 * 创建时间：2019年4月11日 下午2:43:28
	 */
	public static Map<String, Object> jsonStringToMap(String jsonString) {
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> result = new HashMap<String, Object>();
        try {
            result = mapper.readValue(jsonString,
                    new TypeReference<HashMap<String, Object>>() {
                    });
            result = mapStringValTrim(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        return result;
	}
	
	
	private static Map<String, Object> mapStringValTrim(Map<String, Object> map){
	    if(map == null){
	        return null;
	    }
	    Map<String, Object> result = new HashMap<String, Object>();
	    for(Map.Entry<String, Object> mapEntry : map.entrySet()){
	        String key = mapEntry.getKey();
            Object values = mapEntry.getValue();
            if(values == null){
                continue;
            }
            if (values instanceof String) {
                values = values.toString().trim();
            }
            if (values instanceof Map) {
                Map<String, Object> valuesMap = (Map)values;
                values = mapStringValTrim(valuesMap);
            }
            if(values  instanceof Collection){
                Collection valueCollection = (Collection) values;
                values = listObjValStringTrim(valueCollection);
            }
            result.put(key, values);
	    }
	    return result;
	}
	
	
	private static List<Object>  listObjValStringTrim(Collection valueCollection){
	    List valueList = new ArrayList<>(valueCollection.size());
        for (Object valueItem : valueCollection) {
            Object valueListItem = valueItem;
            if (valueItem instanceof String) {
                valueListItem = valueItem.toString().trim();
            }
            if (valueItem instanceof Map) {
                Map<String, Object> valuesMap = (Map)valueItem;
                valueListItem = mapStringValTrim(valuesMap);
            }
            if(valueItem  instanceof Collection){
                Collection valueItemCollection = (Collection) valueItem;
                valueListItem = listObjValStringTrim(valueItemCollection);
            }
            valueList.add(valueListItem);
        }
        return valueList;
        
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
            System.out.println(e.getMessage() + e);
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
            System.out.println(e.getMessage() + e);
        } 
        return obj;
    }
	
    /**
     * 获取post请求里面的请求参数
     * warning 此处如果 方法上有使用 @resquestBody 则此处使用无效
     * 一个流不能读两次异常，这种异常一般出现在框架或者拦截器中读取了request中的流的数据，我们在业务代码中再次读取（如@requestBody），由于流中的数据已经没了，所以第二次读取的时候就会抛出异常。
     * @author zj
     * @param request
     * @return
     * 创建时间：2019年6月11日 下午5:56:56
     */
    public static Map getPostBodyParams(HttpServletRequest request) {
    	
    	Map<String,Object> params = new HashMap<String, Object>();
		BufferedReader br = null;
		try {
			br = request.getReader();
			String str, wholeStr = "";
			while((str = br.readLine()) != null){
				wholeStr += str;
			}
			if(!StringUtils.isEmpty(wholeStr)){
				params = JSON.parseObject(wholeStr,Map.class);
			}
			br.close();
		} catch (IOException e1) {
			System.out.println("needAddExpires="+e1);
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	return params;
    }
    
	
}
