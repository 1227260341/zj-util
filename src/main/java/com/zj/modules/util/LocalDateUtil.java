package com.zj.modules.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocalDateUtil {

	
	/**
	 * 获取两个日期之间的日期
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	private List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	
	private static LocalDate getDateByYearAndWeekNumAndDayOfWeek(Integer year, Integer num, DayOfWeek dayOfWeek) {
		//周数小于10在前面补个0
        String numStr = num < 10 ? "0" + String.valueOf(num) : String.valueOf(num);
        //2019-W01-01获取第一周的周一日期，2019-W02-07获取第二周的周日日期
        String weekDate = String.format("%s-W%s-%s", year, numStr, dayOfWeek.getValue());
        return LocalDate.parse(weekDate, DateTimeFormatter.ISO_WEEK_DATE);
    }
	
	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		// 今天是几号
		int dayofMonth = today.getDayOfMonth();
		// 今天是周几（返回的是个枚举类型，需要再getValue()）
		int dayofWeek = today.getDayOfWeek().getValue();
		// 今年是哪一年
		int dayofYear = today.getDayOfYear();
		
		LocalDate reviewTime = today.with(DayOfWeek.THURSDAY);//DayOfWeek 枚举 可以随意获取星期几
		
		// 根据字符串取：
		LocalDate endOfFeb = LocalDate.parse("2018-02-28"); 

		
		// 取本月第1天：
		LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2018-04-01
		// 取本月第2天：
		LocalDate secondDayOfThisMonth = today.withDayOfMonth(2); // 2018-04-02
		// 取本月最后一天，再也不用计算是28，29，30还是31：
		LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth()); // 2018-04-30
		// 取下一天：
		LocalDate firstDayOfNextMonth = lastDayOfThisMonth.plusDays(1); // 变成了2018-05-01
		// 取上一天：
		LocalDate yesterDayOfNextMonth = lastDayOfThisMonth.minusDays(1); // 变成了2018-04-29
		// 取2017年1月第一个周一：
		LocalDate firstMondayOf2017 = LocalDate.parse("2017-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2017-01-02 
		
		//初始化，第一周至少四天
//		WeekFields wfs= WeekFields.of(DayOfWeek.MONDAY, 4);
		//一年最后一天日期的LocalDate，如果该天获得的周数为1或52，那么该年就只有52周，否则就是53周
		//获取指定时间所在年的周数
//		int num= LocalDate.of(2019, 12, 31).get(wfs.weekOfWeekBasedYear())
		
		int num = 53;
		System.out.println("第" + num + "周，周一日期:" + getDateByYearAndWeekNumAndDayOfWeek(2020, num, DayOfWeek.MONDAY));
		System.out.println("第" + num + "周，周一日期:" + getDateByYearAndWeekNumAndDayOfWeek(2020, num, DayOfWeek.SUNDAY));
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Duration between = Duration.between(LocalDateTime.parse("2020-10-12 12:00:00", df), LocalDateTime.parse("2020-10-17 12:00:00", df));
		long betweenDays = between.toDays();
		System.out.println("betweenDays = " + betweenDays);
		
		System.out.println("0/1" + 0/1);
	}
}
