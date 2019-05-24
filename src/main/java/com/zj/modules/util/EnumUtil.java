package com.zj.modules.util;

import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举工具类
 * @author zj
 *
 * 创建时间：2019年3月6日 上午11:03:22
 */
public class EnumUtil {
	
	
	/**
	 * 枚举转map结合value作为map的key,description作为map的value
	 * @param enumT
	 * @param method
	 * @return enum mapcolloction
	 */
	public static <T> Map<Object, String> EnumToMap(Class<T> enumT,
			String... methodNames) {
		Map<Object, String> enummap = new HashMap<Object, String>();
		if (!enumT.isEnum()) {
			return enummap;
		}
		T[] enums = enumT.getEnumConstants();
		if (enums == null || enums.length <= 0) {
			return enummap;
		}
		int count = methodNames.length;
//		String valueMathod = "getValue"; //默认接口value方法
//		String desMathod = "getDescription";//默认接口description方法
		String valueMathod = "getCode"; //默认接口value方法
		String desMathod = "getText";//默认接口description方法
		
		if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
			valueMathod = methodNames[0];
		}
		if (count == 2 && !"".equals(methodNames[1])) {
			desMathod = methodNames[1];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T tobj = enums[i];
			try {
				Object resultValue = getMethodValue(valueMathod, tobj); //获取value值
				if ("".equals(resultValue)) {
					continue;
				}
				Object resultDes = getMethodValue(desMathod, tobj); //获取description描述值
				if ("".equals(resultDes)) { //如果描述不存在获取属性值
					resultDes = tobj;
				}
				enummap.put(resultValue, resultDes + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return enummap;
	}
	
	/**
	 * 枚举转map结合value作为map的key,description作为map的value
	 * @param enumT
	 * @param method
	 * @return enum mapcolloction
	 */
	public static <T> List<String> EnumToList(Class<T> enumT,
			String... methodNames) {
		List<String> enumList = new ArrayList<>();
		if (!enumT.isEnum()) {
			return enumList;
		}
		T[] enums = enumT.getEnumConstants();
		if (enums == null || enums.length <= 0) {
			return enumList;
		}
		int count = methodNames.length;
//		String valueMathod = "getValue"; //默认接口value方法
//		String desMathod = "getDescription";//默认接口description方法
		String valueMathod = "getCode"; //默认接口value方法
		String desMathod = "getText";//默认接口description方法
		
		if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
			valueMathod = methodNames[0];
		}
		if (count == 2 && !"".equals(methodNames[1])) {
			desMathod = methodNames[1];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T tobj = enums[i];
			try {
				Object resultDes = getMethodValue(desMathod, tobj); //获取description描述值
				if ("".equals(resultDes)) { //如果描述不存在获取属性值
					resultDes = tobj;
				}
				enumList.add(resultDes + "");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return enumList;
	}
	
	/**
	 * 枚举转map结合value作为map的key,description作为map的value
	 * @param enumT
	 * @param method
	 * @return enum mapcolloction
	 */
	public static <T> List<Map> EnumToListMap(Class<T> enumT,
			String... methodNames) {
		List<Map> enumList = new ArrayList<>();
		if (!enumT.isEnum()) {
			return enumList;
		}
		T[] enums = enumT.getEnumConstants();
		if (enums == null || enums.length <= 0) {
			return enumList;
		}
		int count = methodNames.length;
//		String valueMathod = "getValue"; //默认接口value方法
//		String desMathod = "getDescription";//默认接口description方法
		String valueMathod = "getCode"; //默认接口value方法
		String desMathod = "getText";//默认接口description方法
		
		if (count >= 1 && !"".equals(methodNames[0])) { //扩展方法
			valueMathod = methodNames[0];
		}
		if (count == 2 && !"".equals(methodNames[1])) {
			desMathod = methodNames[1];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T tobj = enums[i];
			try {
				Object resultValue = getMethodValue(valueMathod, tobj); //获取value值
				if ("".equals(resultValue)) {
					continue;
				}
				Object resultDes = getMethodValue(desMathod, tobj); //获取description描述值
				if ("".equals(resultDes)) { //如果描述不存在获取属性值
					resultDes = tobj;
				}
				Map enumMap = new HashMap<>();
				enumMap.put("id", resultValue);
				enumMap.put("name", resultDes);
				enumList.add(enumMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return enumList;
	}
	
	/**
	 * 通过value值获取对应的描述信息
	 * @param value
	 * @param enumT
	 * @param method
	 * @return enum description
	 */
	public static <T> Object getEnumDescriotionByValue(Object value,
			Class<T> enumT, String... methodNames) {
		if (!enumT.isEnum()) { //不是枚举则返回""
			return "";
		}
		T[] enums = enumT.getEnumConstants();  //获取枚举的所有枚举属性，似乎这几句也没啥用，一般既然用枚举，就一定会添加枚举属性
		if (enums == null || enums.length <= 0) {
			return "";
		}
		int count = methodNames.length;
		String valueMathod = "getValue";    //默认获取枚举value方法，与接口方法一致
		String desMathod = "getDescription"; //默认获取枚举description方法
		if (count >= 1 && !"".equals(methodNames[0])) {
			valueMathod = methodNames[0];
		}
		if (count == 2 && !"".equals(methodNames[1])) {
			desMathod = methodNames[1];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T t = enums[i];
			try {
				Object resultValue = getMethodValue(valueMathod, t);//获取枚举对象value
				if (resultValue.toString().equals(value + "")) {
					Object resultDes = getMethodValue(desMathod, t); //存在则返回对应描述
					return resultDes;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 通过枚举value或者自定义值及方法获取枚举属性值
	 * @param value
	 * @param enumT
	 * @param method
	 * @return enum key
	 */
	public static <T> String getEnumKeyByValue(Object value, Class<T> enumT,
			String... methodNames) {
		if (!enumT.isEnum()) {
			return "";
		}
		T[] enums = enumT.getEnumConstants();
		if (enums == null || enums.length <= 0) {
			return "";
		}
		int count = methodNames.length;
		String valueMathod = "getValue"; //默认方法
		if (count >= 1 && !"".equals(methodNames[0])) { //独立方法
			valueMathod = methodNames[0];
		}
		for (int i = 0, len = enums.length; i < len; i++) {
			T tobj = enums[i];
			try {
				Object resultValue = getMethodValue(valueMathod, tobj);
				if (resultValue != null
						&& resultValue.toString().equals(value + "")) { //存在则返回对应值
					return tobj + "";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	/**
	 * 根据反射，通过方法名称获取方法值，忽略大小写的
	 * @param methodName
	 * @param obj
	 * @param args
	 * @return return value
	 */
	private static <T> Object getMethodValue(String methodName, T obj,
			Object... args) {
		Object resut = "";
		// boolean isHas = false;
		try {
			/********************************* start *****************************************/
			Method[] methods = obj.getClass().getMethods(); //获取方法数组，这里只要共有的方法
			if (methods.length <= 0) {
				return resut;
			}
			// String methodstr=Arrays.toString(obj.getClass().getMethods());
			// if(methodstr.indexOf(methodName)<0){ //只能粗略判断如果同时存在 getValue和getValues可能判断错误
			// return resut;
			// }
			// List<Method> methodNamelist=Arrays.asList(methods); //这样似乎还要循环取出名称
			Method method = null;
			for (int i = 0, len = methods.length; i < len; i++) {
//				Class<?>[] params =  methods[i].getParameterTypes();
				int paramCount = methods[i].getParameterCount();//此处是只需要没有参数的构造方法
				if (methods[i].getName().equalsIgnoreCase(methodName) && paramCount == 0) { //忽略大小写取方法
					// isHas = true;
					methodName = methods[i].getName(); //如果存在，则取出正确的方法名称
					method = methods[i];
					break;
				}
			}
			// if(!isHas){
			// return resut;
			// }
			/*************************** end ***********************************************/
			// Method method = obj.getClass().getDeclaredMethod(methodName); ///确定方法
			if (method == null) {
				return resut;
			}
			resut = method.invoke(obj, args); //方法执行
			if (resut == null) {
				resut = "";
			}
			return resut; //返回结果
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resut;
	}
	
	public static void main(String[] args) {
//		 Class<TestEnum> clasz=TestEnum.class;
//	     Map<Object, String>  map=EnumUtil.EnumToMap(clasz);
//	     System.out.println("获取枚举的map集合----------："+map);
//	     String des=(String) EnumUtil.getEnumDescriotionByValue(2, clasz);
//	     System.out.println("获取值为2的描述------------："+des);
//	     
//	     Object valueget=EnumUtil.getEnumDescriotionByValue("描述CCC", clasz,"getDescription","getValue");
//	     System.out.println("获取  描述CCC 的value值-----："+valueget);
//	     
//	     String field=(String) EnumUtil.getEnumKeyByValue(4, clasz);
//	     System.out.println("获取值为4的属性字段--------："+field);
//	     
//	     String fielda=(String) EnumUtil.getEnumKeyByValue("描述AAA", clasz,"getDescription");
//	     System.out.println("获取  描述AAA 的属性字段----："+fielda);
//	     
//	     TestEnum str=  Enum.valueOf(TestEnum.class,"AAA");	    
//	     System.out.println("根据字段名称取值---------："+ str.getValue());
//		 System.out.println("获取枚举所有字段---------："+ Arrays.toString(TestEnum.values()));
	}
	
}
