package com.zj.modules.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

/**
 * 脱敏工具类
 * @Description: 类功能介绍
 * @Copyright 深圳金雅福控股集团有限公司
 * @author zhouzj
 * @Date 2022-9-915:29:09
 * @Version: 1.0
 */
public class DesensitizationUtils {

    /**
     * 名字脱敏
     * @Description: 脱敏规则: 隐藏中中间部分，比如:李某人 置换为 李*人 , 李某置换为 *某，司徒司翘置换为 司**翘
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:31:59
     * @Version: 1.0
     * @param fullName
     * @return
     */
    public static String desensitizedName(String fullName){
        if (!Strings.isNullOrEmpty(fullName)) {
            int length = fullName.length();
            if(length == 2){
                return "*".concat(fullName.substring(1));
            }else if(length == 3){
                return StringUtils.left(fullName,1).concat("*").concat(StringUtils.right(fullName,1));
            }else if(length > 3){
                return StringUtils.left(fullName,1).concat(generateAsterisk(fullName.substring(1,length-1).length())).concat(StringUtils.right(fullName,1));
            }else {
                return fullName;
            }
        }
        return fullName;
    }


    /**
     * 手机号脱敏
     * @Description: 脱敏规则: 保留前三后四, 比如15566026528置换为155****6528
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:31:43
     * @Version: 1.0
     * @param phoneNumber
     * @return
     */
    public static String desensitizedPhoneNumber(String phoneNumber){
        if(StringUtils.isNotEmpty(phoneNumber)){
            int length = phoneNumber.length();
            if(length == 11){
                return phoneNumber.replaceAll("(\\w{3})\\w*(\\w{4})", "$1****$2");
            }else if(length > 2){
                return StringUtils.left(phoneNumber,1).concat(generateAsterisk(phoneNumber.substring(1,length-2).length())).concat(StringUtils.right(phoneNumber,1));
            }else {
                return phoneNumber;
            }
        }
        return phoneNumber;
    }


    /**
     * 身份证脱敏
     * @Description: 
     * 脱敏规则: 保留前六后三, 适用于15位和18位身份证号：
     * 原身份证号(15位)：210122198401187，脱敏后的身份证号：210122******187
     * 原身份证号(18位)：210122198401187672，脱敏后的身份证号：210122*********672
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:31:23
     * @Version: 1.0
     * @param idNumber
     * @return
     */
    public static String desensitizedIdNumber(String idNumber){
        if (!Strings.isNullOrEmpty(idNumber)) {
            int length = idNumber.length();
            if (length == 15){
               return idNumber.replaceAll("(\\w{1})\\w*(\\w{2})", "$1******$2");
            }else if (length == 18){
                return idNumber.replaceAll("(\\w{1})\\w*(\\w{2})", "$1*********$2");
            }else if(length > 9){
                return StringUtils.left(idNumber,6).concat(generateAsterisk(idNumber.substring(6,length-3).length())).concat(StringUtils.right(idNumber,3));
            }
        }
        return idNumber;
    }


    /**
     * 电子邮箱脱敏，脱敏规则：电子邮箱隐藏@前面的3个字符
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:30:38
     * @Version: 1.0
     * @param email
     * @return
     */
    public static String desensitizedEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            return email;
        }
//        String encrypt = email.replaceAll("(\\w{1})\\w*@(\\w+)", "$1******@$2");
        String encrypt = email.replaceAll("(\\w+)\\w{3}@(\\w+)", "$1***@$2");
        if (email.equalsIgnoreCase(encrypt)) {
            encrypt = email.replaceAll("(\\w*)\\w{1}@(\\w+)", "$1*@$2");
        }
        return encrypt;
    }


    /**
     * 地址脱敏，脱敏规则：从第4位开始隐藏,隐藏8位
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:30:27
     * @Version: 1.0
     * @param address
     * @return
     */
    public static String desensitizedAddress(String address){
        if (!Strings.isNullOrEmpty(address)) {
            int length = address.length();
            if(length > 4 && length <= 12){
                return StringUtils.left(address, 3).concat(generateAsterisk(address.substring(3).length()));
            }else if(length > 12){
                return StringUtils.left(address,3).concat("********").concat(address.substring(11));
            }else {
                return address;
            }
        }
        return address;
    }


    /**
     * 银行账号脱敏, 脱敏规则：银行账号保留前六后四
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:30:19
     * @Version: 1.0
     * @param acctNo
     * @return
     */
    public static String desensitizedBankCardNum(String acctNo) {
        if (StringUtils.isNotEmpty(acctNo)) {
            String regex = "(\\w{0})(.*)(\\w{4})";
            Matcher m = Pattern.compile(regex).matcher(acctNo);
            if (m.find()) {
                String rep = m.group(2);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < rep.length(); i++) {
                    sb.append("*");
                }
                acctNo = acctNo.replaceAll(rep, sb.toString());
            }
        }
        return acctNo;
    }
    
    /**
     * 全部脱敏
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:43:16
     * @Version: 1.0
     * @param str
     * @return
     */
    public static String desensitizedAll(String str) {
        if (StringUtils.isNotEmpty(str)) {
            str = generateAsterisk(str.length());
        }
        return str;
    }


    /**
     * 返回指定长度*字符串
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2022-9-915:30:10
     * @Version: 1.0
     * @param length
     * @return
     */
    private static String generateAsterisk(int length){
        String result = "";
        for (int i = 0; i < length; i++) {
            result += "*";
        }
        return result;
    }
}

