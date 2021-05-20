package com.zj.modules.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
 
/**
 * 通过某年的几周获取该周的开始时间和结束时间
 *
 * @author zj
 * 
 * 2018年11月7日
 */
public class WeekUtils {
	    
		final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	    public static void main(String[] args) {
	        
	    	WeekUtils cd = new WeekUtils();
	        System.out.println("开始时间: " + cd.getStartDayOfWeekNo(2015,47) );
	        System.out.println("结束时间:" + cd.getEndDayOfWeekNo(2015,48) );    
	        
	    }
	    
	    /**
	     * get first date of given month and year
	     * @param year
	     * @param month
	     * @return
	     */
	    public static String getFirstDayOfMonth(int year,int month){
	        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
	        return year + "-"+monthStr+"-" +"01";
	    }
	    
	    /**
	     * get the last date of given month and year
	     * @param year
	     * @param month
	     * @return
	     */
	    public static String getLastDayOfMonth(int year,int month){
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.YEAR , year);
	        calendar.set(Calendar.MONTH , month - 1);
	        calendar.set(Calendar.DATE , 1);
	        calendar.add(Calendar.MONTH, 1);
	        calendar.add(Calendar.DAY_OF_YEAR , -1);
	        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
	               calendar.get(Calendar.DAY_OF_MONTH);
	    }
	    
	    /**
	     * get Calendar of given year
	     * @param year
	     * @return
	     */
	    private static Calendar getCalendarFormYear(int year){
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
	        cal.set(Calendar.YEAR, year);
	        return cal;
	    }
	    
	    /**
	     * get start date of given week no of a year
	     * @param year
	     * @param weekNo
	     * @return
	     */
	    public static String getStartDayOfWeekNo(int year,int weekNo){
	        Calendar cal = getCalendarFormYear(year);
	        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
	        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
	               cal.get(Calendar.DAY_OF_MONTH);    
	        
	    }
	    
	    /**
	     * get the end day of given week no of a year.
	     * @param year
	     * @param weekNo
	     * @return
	     */
	    public static String getEndDayOfWeekNo(int year,int weekNo){
	        Calendar cal = getCalendarFormYear(year);
	        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
	        cal.add(Calendar.DAY_OF_WEEK, 6);
	        return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" +
	               cal.get(Calendar.DAY_OF_MONTH);    
	    }
	    
	    /**
	     * 通过时间获取当前所在一年的第几周
	     * zj
	     * 2018年11月7日
	     */
	    public static int getWhereWeek(String time){
	    	int num = 0;
	    	try {
	    		Calendar calNow = Calendar.getInstance(); 
	    		int year = calNow.get(Calendar.YEAR);
				Date thisDate = sdf.parse(time);
				Calendar cal = Calendar.getInstance();
				cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
				cal.setTime(thisDate);
				cal.set(Calendar.YEAR, year);
				num = cal.get(Calendar.WEEK_OF_YEAR);
			} catch (ParseException e) {
				e.printStackTrace();
			}
	    	return num;
	    }
	    
	    public static int getWhereWeek(Date time){
	    	int num = 0;
	    	try {
	    		Calendar calNow = Calendar.getInstance(); 
	    		int year = calNow.get(Calendar.YEAR);
				Calendar cal = Calendar.getInstance();
				cal.setFirstDayOfWeek(Calendar.MONDAY);//设置周一为一周的第一天
				cal.setMinimalDaysInFirstWeek(7);
				cal.setTime(time);
				cal.set(Calendar.YEAR, year);
				num = cal.get(Calendar.WEEK_OF_YEAR);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return num;
	    }
	    
	    // 获取当前时间所在年的周数
		public static int getWeekOfYear(Date date) {
			Calendar c = new GregorianCalendar();
			c.setFirstDayOfWeek(Calendar.MONDAY);
			c.setMinimalDaysInFirstWeek(7);
			c.setTime(date);
			return c.get(Calendar.WEEK_OF_YEAR);
		}
	    
	    // 获取当前时间所在年的最大周数
		public static int getMaxWeekNumOfYear(int year) {
			Calendar c = new GregorianCalendar();
			c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

			return getWeekOfYear(c.getTime());
		}

	    
	}