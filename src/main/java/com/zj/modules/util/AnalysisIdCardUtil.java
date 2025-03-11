package com.zj.modules.util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
 
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * 身份证信息分析工具类Util
 * @Description: 类功能介绍
 * @Copyright 深圳金雅福控股集团有限公司
 * @author zhouzj
 * @Date 2024-10-1419:42:21
 * @Version: 1.0
 */
@Slf4j
public class AnalysisIdCardUtil {
	
	static String directCityArr[] = {"北京", "重庆", "上海", "天津"};
	static String directCityJudgeArr[] = {"北京北京市", "重庆重庆市", "上海上海市", "天津天津市"};
	static String directCityJudgeReplaceArr[] = {"北京市北京市", "重庆市重庆市", "上海市上海市", "天津市天津市"};
 
    /**
     * 获取详细地址
     *
     * @param fullAddress 身份证完整地址
     */
    public static String getDetailedAddress(String fullAddress) {
        String[] addressArrays = spliceDetailedAddress(fullAddress);
        return addressArrays[addressArrays.length - 1];
    }
 
    /**
     * 获取省市区地址，如：安徽省合肥市包河区
     *
     * @param fullAddress 身份证完整地址
     */
    public static String getPcaAddress(String fullAddress) {
        String[] addressArrays = spliceDetailedAddress(fullAddress);
        StringBuilder areaBuffer = new StringBuilder();
        for (int i = 0; i < addressArrays.length - 1; i++) {
            if (StringUtils.isNotEmpty(addressArrays[i])) {
                areaBuffer.append(addressArrays[i]);
            }
        }
        return areaBuffer.toString();
    }
 
    /**
     * 身份证完成地址拆分为[省、市、区、详细地址]数组，如：[安徽省, 合肥市, 包河区, 幸福大街001号]
     *
     * @param fullAddress 身份证完整地址
     */
    public static String[] spliceDetailedAddress(String fullAddress) {
        String[] arr = new String[4];
        try {
            String tempStr = fullAddress;
            // 省
            String province = null;
            int provinceIdx = processProvince(tempStr);
            if (provinceIdx > -1) {
                province = tempStr.substring(0, provinceIdx + 1);
                tempStr = tempStr.substring(provinceIdx + 1);
            }
            // 市
            String city = null;
            int cityIdx = processCity(tempStr);
            if (cityIdx > -1) {
                city = tempStr.substring(0, cityIdx + 1);
                tempStr = tempStr.substring(cityIdx + 1);
            }
            // 区
            String county = null;
            int countyIdx = processCounty(tempStr);
            if (countyIdx > -1) {
                county = tempStr.substring(0, countyIdx + 1);
                tempStr = tempStr.substring(countyIdx + 1);
            }
            String street = tempStr;
            arr[0] = province;
            arr[1] = city;
            arr[2] = county;
            arr[3] = street;
        } catch (Exception e) {
            log.error("身份证详细地址转义失败：{}", e.getMessage());
        }
        return arr;
    }
 
    /**
     * 拆分身份证完整地址中的省份信息
     *
     * @param address 地址
     */
    private static int processProvince(String address) {
        int[] idx = new int[3];
        int provinceIdx;
        if ((provinceIdx = address.indexOf("省")) > -1) {
            idx[0] = provinceIdx;
        }
        if ((provinceIdx = address.indexOf("市")) > -1) {
            idx[1] = provinceIdx;
        }
        if ((provinceIdx = address.indexOf("区")) > -1) {
            idx[2] = provinceIdx;
        }
        Arrays.sort(idx);
        for (int j : idx) {
            if (j > 0) {
                return j;
            }
        }
        return provinceIdx;
    }
 
    /**
     * 拆分身份证完整地址中的市区/县/自治州信息
     *
     * @param address 地址
     */
    private static int processCity(String address) {
        int[] idx = new int[7];
        int cityIdx = -1;
        if ((cityIdx = address.indexOf("县")) > -1) {
            idx[0] = cityIdx;
        }
        if ((cityIdx = address.indexOf("自治州")) > -1) {
            idx[1] = cityIdx + 2;
        }
        if ((cityIdx = address.indexOf("市辖区")) > -1) {
            idx[2] = cityIdx + 2;
        }
        if ((cityIdx = address.indexOf("市")) > -1) {
            idx[3] = cityIdx;
        }
        if ((cityIdx = address.indexOf("区")) > -1) {
            idx[4] = cityIdx;
        }
        if ((cityIdx = address.indexOf("地区")) > -1) {
            idx[5] = cityIdx + 1;
        }
        if ((cityIdx = address.indexOf("盟")) > -1) {
            idx[6] = cityIdx;
        }
        Arrays.sort(idx);
        for (int j : idx) {
            if (j > 0) {
                return j;
            }
        }
        return cityIdx;
    }
 
    /**
     * 拆分身份证完整地址中的县/旗/岛信息
     *
     * @param address 地址
     */
    private static int processCounty(String address) {
        int[] idx = new int[6];
        int countyIdx;
        if ((countyIdx = address.indexOf("县")) > -1) {
            idx[0] = countyIdx;
        }
        if ((countyIdx = address.indexOf("旗")) > -1) {
            idx[1] = countyIdx;
        }
        if ((countyIdx = address.indexOf("海域")) > -1) {
            idx[2] = countyIdx + 1;
        }
        if ((countyIdx = address.indexOf("市")) > -1) {
            idx[3] = countyIdx;
        }
        if ((countyIdx = address.indexOf("区")) > -1) {
            idx[4] = countyIdx;
        }
        if ((countyIdx = address.indexOf("岛")) > -1) {
            idx[5] = countyIdx;
        }
        Arrays.sort(idx);
        for (int j : idx) {
            if (j > 0) {
                return j;
            }
        }
        return countyIdx;
    }
 
    /**
     * 身份证地址提取省市区
     *
     * @param fullAddress 身份证完整地址
     */
    public static Map<String, String> addressResolution(String fullAddress) {
    	Integer replaceIndex = null;
    	for (int i = 0; i < directCityJudgeArr.length; i ++) {
    		String item = directCityJudgeArr[i];
    		if (fullAddress.contains(item)) {//说明有匹配上得直辖市了，那么开始替换
    			fullAddress = fullAddress.replace(item, directCityJudgeReplaceArr[i]);
    			replaceIndex = i;
    		}
    	}
        // 定义正则
//        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<area>[^县]+县|.+区|.*?市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<detail>.*)";
        String regex = "(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<area>[^县]+县|.*?区|.*?市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<detail>.*)";
 
        Matcher matcher = Pattern.compile(regex).matcher(fullAddress);
        String province, city, area, town, detail;
        Map<String, String> map = new LinkedHashMap<>();
 
        while (matcher.find()) {
            province = matcher.group("province");
            if (StringUtils.isNotBlank(province) && replaceIndex != null) {
            	//把直辖市替换回来
            	province =  directCityArr[replaceIndex];
            }
            map.put("province", StringUtils.isEmpty(province) ? "" : province.trim());
 
            city = matcher.group("city");
            map.put("city", StringUtils.isEmpty(city) ? "" : city.trim());
 
            area = matcher.group("area");
            map.put("area", StringUtils.isEmpty(area) ? "" : area.trim());
 
            town = matcher.group("town");
            map.put("town", StringUtils.isEmpty(town) ? "" : town.trim());
 
            detail = matcher.group("detail");
            map.put("detail", StringUtils.isEmpty(detail) ? "" : detail.trim());
        }
        return map;
    }
 
//    public static void main(String[] args) {
// 
//        String address1 = "上海市上海市浦东新区世纪大道xx号上海中心大厦xx楼A座";
//        String address2 = "内蒙古自治区呼伦贝尔市鄂温克族自治旗额尔古纳市阿尔山北路xxx号蒙古民族文化博物馆x楼xx展厅";
//        String address3 = "广东省广州市番禺区沙湾镇大巷涌路xxx号";
// 
//        System.out.println("详细地址1：" + getDetailedAddress(address1));
//        System.out.println("详细地址2：" + getDetailedAddress(address2));
//        System.out.println("省市区地址拼接1：" + getPcaAddress(address1));
//        System.out.println("省市区地址拼接2：" + getPcaAddress(address2));
//        System.out.println("地址数组1：" + Arrays.toString(spliceDetailedAddress(address1)));
//        System.out.println("地址数组2：" + Arrays.toString(spliceDetailedAddress(address2)));
// 
//        System.out.println("地址提取省市区：" + addressResolution(address2));
//        System.out.println("地址提取省市区：" + addressResolution(address3));
//    }
}