package com.jinyafu.util;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 反射处理对象参数操作工具类
 * @Description: 类功能介绍
 * @Copyright 深圳金雅福控股集团有限公司
 * @author zhouzj
 * @Date 2022-4-2815:24:17
 * @Version: 1.0
 */
public class ReflectParamsUtils {
	private static Logger logger = LoggerFactory.getLogger(ReflectParamsUtils.class);
 
	/**
	 * 判断对象中是否包含该字段 用于排序字段是否正确判断
	 * 
	 * @param object
	 * @param fieldName 字段名
	 * @return
	 */
	public static boolean checkClassIsHaveFeild(@SuppressWarnings("rawtypes") Class clazz, String fieldName) {
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				try {
					if (field.getName().equals(fieldName)) {
						return true;
					}
				} catch (Exception e) {
					logger.error("判断对象中是否存在该字段异常->ParamsUtils.checkObjectFieldIsHave:class->" + clazz.getName()
							+ "|fieldName->" + fieldName, e);
				}
			}
		}
		return false;
	}
 
	/**
	 * 判断对象中字段是否全为空值(除可为空字段外) 若对象除可为空字段外,其余字段全为空,返回true 用于更新数据库对象之前判空
	 * 
	 * @param object
	 * @param fieldNames 可为空字段数组
	 * @return
	 */
	public static boolean checkObjectFieldIsAllNull(Object object, String[] fieldNames) {
		Class<? extends Object> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				field.setAccessible(true);
				try {
					if (!checkFiledIsNull(field, object) && !checkFieldNamesIsContainField(field, fieldNames)) {
						return false;
					}
				} catch (Exception e) {
					logger.error("判断对象字段是否为空异常->ParamsUtils.checkObjectFiledIsAllNull:" + JSON.toJSONString(object), e);
				}
			}
		}
		return true;
	}
 
	/**
	 * 判断对象中字段是否有空值(除可为空字段外) 如果有空值 返回map对象中 isNull为true fieldName为该空值字段名
	 * 此方法只会返回第一个空值字段 不会返回全部空值字段 用于插入数据库对象之前判空
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Object> checkObjectFieldIsNull(Object object, String[] fieldNames) {
		Map<String, Object> obMap = new HashMap<>();
		obMap.put("isNull", false);
		Class<? extends Object> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				field.setAccessible(true);
				try {
					if (checkFiledIsNull(field, object) && !checkFieldNamesIsContainField(field, fieldNames)) {
						obMap.put("isNull", true);
						obMap.put("fieldName", field.getName());
						return obMap;
					}
				} catch (Exception e) {
					logger.error("判断对象字段是否为空异常->ParamsUtils.checkObjectFiledIsNull:" + JSON.toJSONString(object), e);
				}
			}
		}
		return obMap;
	}
 
	/**
	 * 检测对象字段是否为空
	 * 
	 * @param field
	 * @param object
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static boolean checkFiledIsNull(Field field, Object object)
			throws IllegalArgumentException, IllegalAccessException {
		if (field.getType() == String.class && field.get(object) != null) {
			return StringUtils.isEmpty(field.get(object).toString());
		} else {
			return field.get(object) == null;
		}
	}
 
	/**
	 * 判断数组中是否包含该字段名
	 * 
	 * @param field
	 * @param fieldNames
	 * @return
	 */
	private static boolean checkFieldNamesIsContainField(Field field, String[] fieldNames) {
		if (fieldNames != null && fieldNames.length > 0 && field != null) {
			for (String string : fieldNames) {
				if (string.equals(field.getName())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 设置对象的属性值
	 * @Description: 
	 * @Copyright 深圳金雅福控股集团有限公司
	 * @author zhouzj
	 * @Date 2022-4-2815:29:43
	 * @Version: 1.0
	 * @param obj
	 * @param declaredField
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public static boolean setFieldValue(Object target, String fieldName, Object fieldValue) {
//        Class<? extends TopcsWebPlugInUnitResDTO> class2 = aa.getClass();
        
        try {
            Class<?> classO = target.getClass();
            //判断字段是否存在
            boolean isExist = checkClassIsHaveFeild(classO, fieldName);
            if (!isExist) {
                return false;
            }
            Field declaredField = classO.getDeclaredField(fieldName);
            declaredField.setAccessible(true);
//            String string = declaredField.getGenericType().toString();
//            Object object = declaredField.get(aa);
//            class1.newInstance();
            declaredField.set(target, fieldValue);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
//        System.out.println("aa"  + "--bb" + aa.getPlugInName() + aa.getCustomerServiceName());
	    return false;
	}
	
	
	
    public static void main(String[] args) throws Exception {
        
//        TopcsWebPlugInUnitResDTO obj = new TopcsWebPlugInUnitResDTO();
        Object obj = null;
//        obj.setPlugInName("asdad");
        String fieldName = "windowName";//需要修改的字段属性
        Object fieldValue = "这是修改后的值啊";// 需要修改的值
        ReflectParamsUtils.setFieldValue(obj, fieldName, fieldValue);
        
        System.out.println("aa--bb" + obj);
    }
	
}