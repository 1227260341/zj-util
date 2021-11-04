package com.zj.modules.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;


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
     * 判断对象中是否存在未空的属性
     * @version 2021-7-2217:58:31
     * @author zhouzj
     * @param obj 需要验证的对象  ignoreAttribute需要忽略的属性名，多个逗号分隔
     * @return
     * @throws IllegalAccessException
     */
    public static boolean checkObjFieldIsNull(Object obj, String ignoreAttributes) {

        try {
			boolean flag = false;
			for(Field f : obj.getClass().getDeclaredFields()){
			    f.setAccessible(true);
//            log.info(f.getName());
			    String name = f.getName();
			    Object object = f.get(obj);
			    if (!StringUtils.isEmpty(ignoreAttributes) && ignoreAttributes.contains(name)) {
			    	continue ;
			    }
			    if(f.get(obj) == null){
			        flag = true;
			        return flag;
			    }
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
        return false;
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
     * 生成随机字数字
     * @version 2021-7-2711:14:26
     * @author zhouzj
     * @param position
     * @return
     */
    public static String getRandomNo(Integer position) {
    	if (position == null) {
    		position = 6;	
    	}
        String linkNo = "";
        // 用字符数组的方式随机
//        String model = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String model = "0123456789";
        char[] m = model.toCharArray();
        for (int j = 0; j < position; j++) {
            char c = m[(int) (Math.random() * 10)];
            // 保证六位随机数之间没有重复的
//            if (linkNo.contains(String.valueOf(c))) {
//                j--;
//                continue;
//            }
            linkNo = linkNo + c;
        }
        return linkNo;
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
    
    
    /**
     * 将file 文件转换成FileItem 文件 便于转换成  MultipartFile 文件，转换成MultipartFile 文件方式如下：
     * FileItem fileItem = createFileItem(file, fileName);
       MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
       
       需要的jar:
  	<dependency>
  		<groupId>commons-fileupload</groupId>
  		<artifactId>commons-fileupload</artifactId>
  		<version>1.4</version>
  	</dependency>
     * @version 2021-1-2810:43:18
     * @author zhouzj
     * @param file
     * @param fileName
     * @return
     */
    public static FileItem createFileItem(File file, String fileName)
    {
        String filePath = file.getPath();
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "file";//这个必须与 MultipartFile 传输的 是一致的 如feign 调用   @RequestPart("file") MultipartFile file
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "multipart/form-data", true,
                fileName);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try
        {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return item;
    }
    
    /**
	 * easypoi 导出excel
	 * zj
	 * 2018年8月22日
	 * @throws Exception 
	 */
	public static void easypoiDownloadExcel(List<ExcelExportEntity> colList, List<Map<String, Object>> dataList, 
			String excelName, String schoolId, HttpServletResponse response) {
		
		try {
			ExportParams exportParams = new ExportParams(excelName, "数据");
			exportParams.setStyle(ExcelExportStylerDefaultImpl.class);//设置表头样式
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, colList,
					dataList);
			if (PropertiesUtil.isEmptyString(schoolId)) {
				schoolId = "暂无";
			}
			String fileName = excelName+".xls";
//	    String tFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//	    tFilePath = tFilePath.substring(0, tFilePath.indexOf("/WEB-INF/"));
			String tFilePath = getDemoPath(schoolId);
			tFilePath = tFilePath + "/" + fileName;
			System.out.println("存储路径-----------：" + tFilePath);
			
			FileOutputStream fos = new FileOutputStream(tFilePath);
			workbook.write(fos);
			fos.close();
			
			InputStream is = new FileInputStream(new File(tFilePath));
			// 设置response参数，可以打开下载页面
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
			ServletOutputStream out = response.getOutputStream();
			BufferedInputStream bis = null;
			BufferedOutputStream bos = null;
			try {
			    bis = new BufferedInputStream(is);
			    bos = new BufferedOutputStream(out);
			    byte[] buff = new byte[2048];
			    int bytesRead;
			    // Simple read/write loop.
			    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			    	bos.write(buff, 0, bytesRead);
			    }
			} catch (final IOException e) {
			    throw e;
			} finally {
			    if (bis != null)
				bis.close();
			    if (bos != null)
				bos.close();
			    File tFile = new File(tFilePath);
			    tFile.deleteOnExit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map easypoiDownloadExcel1(List<ExcelExportEntity> colList, List<Map<String, Object>> dataList, 
			String excelName, String schoolId, HttpServletResponse response) {
		
		try {
			Map resultMap = new HashMap<>();
			ExportParams exportParams = new ExportParams(excelName, "数据");
			exportParams.setStyle(ExcelExportStylerDefaultImpl.class);//设置表头样式
			Workbook workbook = ExcelExportUtil.exportExcel(exportParams, colList,
					dataList);
			if (PropertiesUtil.isEmptyString(schoolId)) {
				schoolId = "暂无";
			}
			String fileName = excelName+".xls";
//	    String tFilePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//	    tFilePath = tFilePath.substring(0, tFilePath.indexOf("/WEB-INF/"));
			String tFilePath = getDemoPath(schoolId);
			tFilePath = tFilePath + "/" + fileName;
			System.out.println("存储路径-----------：" + tFilePath);
			
			FileOutputStream fos = new FileOutputStream(tFilePath);
			workbook.write(fos);
			fos.close();
			
			resultMap.put("downloadResult", true);
			resultMap.put("fileName", fileName);
			resultMap.put("filePath", tFilePath);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * poi 导出excel
	 * zj
	 * 2018年8月24日
	 */
	public static void poiDownloadExcel(ArrayList<String> titleDefine, ArrayList<ArrayList<Object>> datas, 
			String excelName, String schoolId, List<CellRangeAddress> CRAs, HttpServletResponse response) {
		
		try {
			if (PropertiesUtil.isEmptyString(schoolId)) {
				schoolId = "暂无";
			}
			String fileName = excelName+".xls";
			//获取工程目录
			String tFilePath = getDemoPath(schoolId);
			tFilePath = tFilePath + "/" + fileName;
			System.out.println("存储路径-----------：" + tFilePath);
			
			if (PoiExportUtil.export(tFilePath, "Sheet1", titleDefine, datas, CRAs)) {
		    	
		    	//sheet.addMergedRegion(new CellRangeAddress(0,0,4,0));
		    	
				InputStream is = new FileInputStream(new File(tFilePath));
				// 设置response参数，可以打开下载页面
				response.reset();
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));
				ServletOutputStream out = response.getOutputStream();
				BufferedInputStream bis = null;
				BufferedOutputStream bos = null;
				try {
				    bis = new BufferedInputStream(is);
				    bos = new BufferedOutputStream(out);
				    byte[] buff = new byte[2048];
				    int bytesRead;
				    // Simple read/write loop.
				    while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				    }
				} catch (final IOException e) {
				    throw e;
				} finally {
				    if (bis != null)
					bis.close();
				    if (bos != null)
					bos.close();
				    File tFile = new File(tFilePath);
				    tFile.deleteOnExit();
				}
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载报表， 此处指返回 生成报表的结果（未进行下载功能）
	 * zj
	 * 2018年12月14日
	 */
	public static Map poiDownloadExcel1(ArrayList<String> titleDefine, ArrayList<ArrayList<Object>> datas, 
			String excelName, String schoolId, List<CellRangeAddress> CRAs, HttpServletResponse response) {
		Map resultMap = new HashMap<>();
		try {
			if (PropertiesUtil.isEmptyString(schoolId)) {
				schoolId = "暂无";
			}
			String fileName = excelName+".xls";
			//获取工程目录
			String tFilePath = getDemoPath(schoolId);
			tFilePath = tFilePath + "/" + fileName;
			System.out.println("存储路径-----------：" + tFilePath);
			Boolean downloadResult = PoiExportUtil.export(tFilePath, "Sheet1", titleDefine, datas, CRAs);
			resultMap.put("downloadResult", downloadResult);
			resultMap.put("fileName", fileName);
			resultMap.put("filePath", tFilePath);
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取工程excle储存地址
	 * zj
	 * 2018年8月31日
	 */
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
     * 下载质量分析表（实例）
     * zj
     * 2018年8月23日
     * @throws Exception 
     */
    public Map downloadQualityAnalysis(Map returnMap, HttpServletResponse response, String schoolId) throws Exception {
    	
    	if (returnMap.get("code") == null || (int)returnMap.get("code") != 0 || returnMap.get("data") == null) {
    		return null;
    	}
    	String titleName = (String) returnMap.get("titleName");
    	Map returnData = (Map) returnMap.get("data");
    	List<Map> gradeData = (List<Map>) returnData.get("gradeData");
    	List<Map> subjectCount = (List<Map>) returnData.get("subjectCount");
    	int gradeDataSize = gradeData.size();
    	
    	ArrayList<String> titleDefine = new ArrayList<String>();
 	    ArrayList<Object> rowList = new ArrayList<Object>();
 	    ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
 	    
 	    //一表头
 	    rowList.add(titleName);
 	    rowList.add("");
 	    rowList.add("");
 	    rowList.add("");
 	    rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	for (int i = 0; i < gradeDataSize; i ++) {
	 		rowList.add("");
	 		rowList.add("");
	 	}
	 	datas.add(rowList);
 	    
 	    //二表头
	 	rowList = new ArrayList<Object>();
 	    rowList.add("班级");
 	    rowList.add("人数");
 	    rowList.add("");
 	    rowList.add("分数");
 	    rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("标准差");
	 	for (int i = 0; i < gradeDataSize; i ++) {
	 		if (i == 0) {
	 			rowList.add("等级");
	 			rowList.add("");
	 			continue ;
	 		}
	 		rowList.add("");
	 		rowList.add("");
	 	}
	 	datas.add(rowList);
	 	
	 	//三表头
	 	rowList = new ArrayList<Object>();
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	rowList.add("");
	 	for (int i = 0; i < gradeDataSize; i ++) {
	 		Map gradeMap = gradeData.get(i);
	 		rowList.add(gradeMap.get("gradeName"));
	 		rowList.add("");
	 	}
	 	datas.add(rowList);
	 	
	 	//三表头
	 	rowList = new ArrayList<Object>();
	 	rowList.add("");
	 	rowList.add("实考");
	 	rowList.add("缺考");
	 	rowList.add("平均分");
	 	rowList.add("离均差");
	 	rowList.add("最高分");
	 	rowList.add("最低分");
	 	rowList.add("");
	 	for (int i = 0; i < gradeDataSize; i ++) {
	 		rowList.add("人数");
	 		rowList.add("比率");
	 	}
	 	datas.add(rowList);
	 	
	 	//数据绑定
	 	if (PropertiesUtil.verifyList(subjectCount)) {
	 		for (int i = 0; i < subjectCount.size(); i ++) {
	 			Map countMap = subjectCount.get(i);
	 			rowList = new ArrayList<Object>();
	 			rowList.add(countMap.get("className"));
	 			rowList.add(countMap.get("examNum"));
	 			rowList.add(countMap.get("missNum"));
	 			rowList.add(countMap.get("classAvg"));
	 			rowList.add(countMap.get("leaveAvgRate"));
	 			rowList.add(countMap.get("classMax"));
	 			rowList.add(countMap.get("classMin"));
	 			rowList.add(countMap.get("standdardDeviation"));
	 			for (int j = 0; j < gradeDataSize; j ++) {
	 		 		Map gradeMap = gradeData.get(j);
	 		 		Object gradeId = gradeMap.get("gradeId");
	 		 		rowList.add(countMap.get(gradeId + "_num"));
	 		 		rowList.add(countMap.get(gradeId + "_rate") + "%");
	 		 	}
	 			datas.add(rowList);
	 		}
	 	}
	 	
	 	
	    //设置合并行
	    List<CellRangeAddress> CRAs = new ArrayList<>();
	    CRAs.add(new CellRangeAddress(0, 0, 0, 8 + gradeDataSize*2 -1));
	    CRAs.add(new CellRangeAddress(1, 3, 0, 0));
	    CRAs.add(new CellRangeAddress(1, 2, 1, 2));
	    CRAs.add(new CellRangeAddress(1, 2, 3, 6));
	    CRAs.add(new CellRangeAddress(1, 3, 7, 7));
	    CRAs.add(new CellRangeAddress(1, 1, 8, 8 + gradeDataSize*2 -1));
	    for (int j = 0; j < gradeDataSize; j ++) {
	    	int lastColumn = 8 + j*2 + 1;
	    	CRAs.add(new CellRangeAddress(2, 2, 8 + j*2 , lastColumn));
	    }
	    /*CRAs.add(new CellRangeAddress(0, 2, 0, 0));
	    CRAs.add(new CellRangeAddress(0, 1, 1, 2));
	    CRAs.add(new CellRangeAddress(0, 1, 3, 6));
	    CRAs.add(new CellRangeAddress(0, 2, 7, 7));
	    CRAs.add(new CellRangeAddress(0, 0, 8, 8 + gradeDataSize*2 -1));
	    for (int j = 0; j < gradeDataSize; j ++) {
	    	int lastColumn = 8 + j*2 + 1;
	    	CRAs.add(new CellRangeAddress(1, 1, 8 + j*2 , lastColumn));
	    }*/
	    PoiExportUtil.columnWidth = 9*256;//设置生成的表格宽度
	    Map<Integer, Integer> columnWidthMap = new HashMap<>();
	    columnWidthMap.put(0, 15*256);
	    PoiExportUtil.columnWidthMap = columnWidthMap;
	    Map resultMap = this.poiDownloadExcel1(titleDefine, datas, titleName, schoolId, CRAs, response);
	    return resultMap;
    }
    
    
    /**
     * 单科名次分布表(实例)
     * zj
     * 2018年8月23日
     */
//    public Map downloadRankSpread2(Map returnMap, HttpServletResponse response, String schoolId) {
//    	
//    	if (returnMap.get("code") == null || (int)returnMap.get("code") != 0 || returnMap.get("data") == null) {
//    		return null;
//    	}
//    	
//    	String titleName = (String) returnMap.get("titleName");
//    	List<Map> dataMap = (List<Map>) returnMap.get("data");
//    	List<ReportSet> tableHead = (List<ReportSet>) returnMap.get("tableHead");
//    	
//    	List<ExcelExportEntity> colList = new ArrayList<ExcelExportEntity>();
//    	colList.add(new ExcelExportEntity("班级", "className"));
//    	
//    	ExcelExportEntity memberEEE = new ExcelExportEntity("人数", "member");
//    	List<ExcelExportEntity> memberEEEList = new ArrayList<ExcelExportEntity>();
//    	memberEEEList.add(new ExcelExportEntity("实考", "realExam"));
//    	memberEEEList.add(new ExcelExportEntity("缺考", "missExam"));
//    	memberEEE.setList(memberEEEList);
//    	colList.add(memberEEE);
//    	
//    	colList.add(new ExcelExportEntity("T值", "tValue"));
//    	
//    	for (ReportSet item : tableHead) {
//    		Integer id = item.getId();
//    		String name = item.getName();
//    		ExcelExportEntity itemEEE = new ExcelExportEntity(name, id + "_item");
//        	List<ExcelExportEntity> itemEEEList = new ArrayList<ExcelExportEntity>();
//        	itemEEEList.add(new ExcelExportEntity("人数", id + "_num"));
//        	itemEEEList.add(new ExcelExportEntity("比率", id + "_rate"));
//        	//goodEEEList.add(new ExcelExportEntity("排名", "beforeTenRank"));
//        	itemEEE.setList(itemEEEList);
//        	colList.add(itemEEE);
//    	}
//    	
//    	//渲染数据
//    	List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//    	for (int i = 0; i < dataMap.size(); i++) {
//    		Map subjectCountMap = (Map) dataMap.get(i);
//    		Object className = subjectCountMap.get("className");
//    		
//    		Map valMap = new HashMap();
//            valMap.put("className", className);
//            
//            List<Map> classMember = new ArrayList<Map>();
//            Map classMemberMap = new HashMap();
//            classMemberMap.put("realExam", subjectCountMap.get("examNum"));
//            classMemberMap.put("missExam", subjectCountMap.get("missNum"));
//            classMember.add(classMemberMap);
//            valMap.put("member", classMember);
//            
//            valMap.put("tValue", subjectCountMap.get("tValue"));
//            for (ReportSet item : tableHead) {
//            	Integer id = item.getId();
//            	List<Map> itemList = new ArrayList<Map>();
//                Map itemMap = new HashMap();
//                itemMap.put(id + "_num", subjectCountMap.get(id + "_num"));
//                itemMap.put(id + "_rate", subjectCountMap.get(id + "_rate"));
//                itemList.add(itemMap);
//                valMap.put(id + "_item", itemList);
//            }
//            dataList.add(valMap);
//    	}
//    	
//    	Map resultMap = BaseUtil.easypoiDownloadExcel1(colList, dataList, titleName, schoolId, response);
//    	return resultMap;
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
}
