/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ab.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：Abjava 
 * 描述：日期处理类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-18 下午11:52:13
 */
public class AbDateUtil {
	
	/** 时间日期格式化到年月日时分秒. */
	public static final String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
	
	/** 时间日期格式化到年月日. */
	public static final String dateFormatYMD = "yyyy-MM-dd";
	
	/** 时间日期格式化到年月. */
	public static final String dateFormatYM = "yyyy-MM";
	
	/** 时间日期格式化到年月日时分. */
	public static final String dateFormatYMDHM = "yyyy-MM-dd HH:mm";
	
	/** 时间日期格式化到月日. */
	public static final String dateFormatMD = "MM/dd";
	
	/** 时分秒. */
	public static final String dateFormatHMS = "HH:mm:ss";
	
	/** 时分. */
	public static final String dateFormatHM = "HH:mm";
	
	/** 上午. */
    public static final String AM = "AM";

    /** 下午. */
    public static final String PM = "PM";


    /**
	 * 描述：String类型的日期时间转化为Date类型.
	 *
	 * @param strDate String形式的日期时间
	 * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return Date Date类型日期时间
	 */
	public static Date getDateByFormat(String strDate, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = mSimpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 描述：获取偏移之后的Date.
	 * @param date 日期时间
	 * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
	 * @param offset 偏移(值大于0,表示+,值小于0,表示－)
	 * @return Date 偏移之后的日期时间
	 */
	public Date getDateByOffset(Date date,int calendarField,int offset) {
		Calendar c = new GregorianCalendar();
		try {
			c.setTime(date);
			c.add(calendarField, offset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
	
	/**
	 * 描述：获取指定日期时间的字符串(可偏移).
	 *
	 * @param strDate String形式的日期时间
	 * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
	 * @param offset 偏移(值大于0,表示+,值小于0,表示－)
	 * @return String String类型的日期时间
	 */
	public static String getStringByOffset(String strDate, String format,int calendarField,int offset) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			c.setTime(mSimpleDateFormat.parse(strDate));
			c.add(calendarField, offset);
			mDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mDateTime;
	}
	
	/**
	 * 描述：Date类型转化为String类型(可偏移).
	 *
	 * @param date the date
	 * @param format the format
	 * @param calendarField the calendar field
	 * @param offset the offset
	 * @return String String类型日期时间
	 */
	public static String getStringByOffset(Date date, String format,int calendarField,int offset) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			c.setTime(date);
			c.add(calendarField, offset);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	

	/**
	 * 描述：Date类型转化为String类型.
	 *
	 * @param date the date
	 * @param format the format
	 * @return String String类型日期时间
	 */
	public static String getStringByFormat(Date date, String format) {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
		String strDate = null;
		try {
			strDate = mSimpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 描述：获取指定日期时间的字符串,用于导出想要的格式.
	 *
	 * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
	 * @param format 输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String 转换后的String类型的日期时间
	 */
	public static String getStringByFormat(String strDate, String format) {
		String mDateTime = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(dateFormatYMDHMS);
			c.setTime(mSimpleDateFormat.parse(strDate));
			SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
			mDateTime = mSimpleDateFormat2.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;
	}
	
	/**
	 * 描述：获取milliseconds表示的日期时间的字符串.
	 *
	 * @param milliseconds the milliseconds
	 * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String 日期时间字符串
	 */
	public static String getStringByFormat(long milliseconds,String format) {
		String thisDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			thisDateTime = mSimpleDateFormat.format(milliseconds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return thisDateTime;
	}
	
	/**
	 * 描述：获取表示当前日期时间的字符串.
	 *
	 * @param format  格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @return String String类型的当前日期时间
	 */
	public static String getCurrentDate(String format) {
		String curDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			curDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return curDateTime;

	}

	/**
	 * 描述：获取表示当前日期时间的字符串(可偏移).
	 *
	 * @param format 格式化字符串，如："yyyy-MM-dd HH:mm:ss"
	 * @param calendarField Calendar属性，对应offset的值， 如(Calendar.DATE,表示+offset天,Calendar.HOUR_OF_DAY,表示＋offset小时)
	 * @param offset 偏移(值大于0,表示+,值小于0,表示－)
	 * @return String String类型的日期时间
	 */
	public static String getCurrentDateByOffset(String format,int calendarField,int offset) {
		String mDateTime = null;
		try {
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			Calendar c = new GregorianCalendar();
			c.add(calendarField, offset);
			mDateTime = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mDateTime;

	}

	/**
	 * 描述：计算两个日期所差的天数.
	 *
	 * @param milliseconds1 the milliseconds1
	 * @param milliseconds2 the milliseconds2
	 * @return int 所差的天数
	 */
	public static int getOffectDay(long milliseconds1, long milliseconds2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(milliseconds1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(milliseconds2);
		//先判断是否同年
		int y1 = calendar1.get(Calendar.YEAR);
		int y2 = calendar2.get(Calendar.YEAR);
		int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
		int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
		int maxDays = 0;
		int day = 0;
		if (y1 - y2 > 0) {
			maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 + maxDays;
		} else if (y1 - y2 < 0) {
			maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
			day = d1 - d2 - maxDays;
		} else {
			day = d1 - d2;
		}
		return day;
	}
	
	/**
	 * 描述：计算两个日期所差的小时数.
	 *
	 * @param date1 第一个时间的毫秒表示
	 * @param date2 第二个时间的毫秒表示
	 * @return int 所差的小时数
	 */
	public static int getOffectHour(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
		int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
		int h = 0;
		int day = getOffectDay(date1, date2);
		h = h1-h2+day*24;
		return h;
	}
	
	/**
	 * 描述：计算两个日期所差的分钟数.
	 *
	 * @param date1 第一个时间的毫秒表示
	 * @param date2 第二个时间的毫秒表示
	 * @return int 所差的分钟数
	 */
	public static int getOffectMinutes(long date1, long date2) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTimeInMillis(date1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTimeInMillis(date2);
		int m1 = calendar1.get(Calendar.MINUTE);
		int m2 = calendar2.get(Calendar.MINUTE);
		int h = getOffectHour(date1, date2);
		int m = 0;
		m = m1-m2+h*60;
		return m;
	}

	/**
	 * 描述：获取本周一.
	 *
	 * @param format the format
	 * @return String String类型日期时间
	 */
	public static String getFirstDayOfWeek(String format) {
		return getDayOfWeek(format,Calendar.MONDAY);
	}

	/**
	 * 描述：获取本周日.
	 *
	 * @param format the format
	 * @return String String类型日期时间
	 */
	public static String getLastDayOfWeek(String format) {
		return getDayOfWeek(format,Calendar.SUNDAY);
	}

	/**
	 * 描述：获取本周的某一天.
	 *
	 * @param format the format
	 * @param calendarField the calendar field
	 * @return String String类型日期时间
	 */
	private static String getDayOfWeek(String format,int calendarField) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			int week = c.get(Calendar.DAY_OF_WEEK);
			if (week == calendarField){
				strDate = mSimpleDateFormat.format(c.getTime());
			}else{
				int offectDay = calendarField - week;
				if (calendarField == Calendar.SUNDAY) {
					offectDay = 7-Math.abs(offectDay);
				} 
				c.add(Calendar.DATE, offectDay);
				strDate = mSimpleDateFormat.format(c.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}
	
	/**
	 * 描述：获取本月第一天.
	 *
	 * @param format the format
	 * @return String String类型日期时间
	 */
	public static String getFirstDayOfMonth(String format) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			//当前月的第一天
			c.set(GregorianCalendar.DAY_OF_MONTH, 1);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;

	}

	/**
	 * 描述：获取本月最后一天.
	 *
	 * @param format the format
	 * @return String String类型日期时间
	 */
	public static String getLastDayOfMonth(String format) {
		String strDate = null;
		try {
			Calendar c = new GregorianCalendar();
			SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
			// 当前月的最后一天
			c.set(Calendar.DATE, 1);
			c.roll(Calendar.DATE, -1);
			strDate = mSimpleDateFormat.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDate;
	}

	

	/**
	 * 描述：获取表示当前日期的0点时间毫秒数.
	 *
	 * @return the first time of day
	 */
	public static long getFirstTimeOfDay() {
			Date date = null;
			try {
				String currentDate = getCurrentDate(dateFormatYMD);
				date = getDateByFormat(currentDate+" 00:00:00",dateFormatYMDHMS);
				return date.getTime();
			} catch (Exception e) {
			}
			return -1;
	}
	
	/**
	 * 描述：获取表示当前日期24点时间毫秒数.
	 *
	 * @return the last time of day
	 */
	public static long getLastTimeOfDay() {
			Date date = null;
			try {
				String currentDate = getCurrentDate(dateFormatYMD);
				date = getDateByFormat(currentDate+" 24:00:00",dateFormatYMDHMS);
				return date.getTime();
			} catch (Exception e) {
			}
			return -1;
	}
	
	/**
	 * 描述：判断是否是闰年()
	 * <p>(year能被4整除 并且 不能被100整除) 或者 year能被400整除,则该年为闰年.
	 *
	 * @param year 年代（如2012）
	 * @return boolean 是否为闰年
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0 && year % 400 != 0) || year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 描述：根据时间返回格式化后的时间的描述.
	 * 小于1小时显示多少分钟前  大于1小时显示今天＋实际日期，大于今天全部显示实际时间
	 *
	 * @param strDate the str date
	 * @param outFormat the out format
	 * @return the string
	 */
	public static String formatDateStr2Desc(String strDate,String outFormat) {
		
		DateFormat df = new SimpleDateFormat(dateFormatYMDHMS);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c2.setTime(df.parse(strDate));
			c1.setTime(new Date());
			int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
			if(d==0){
				int h = getOffectHour(c1.getTimeInMillis(), c2.getTimeInMillis());
				if(h>0){
					return "今天"+getStringByFormat(strDate,dateFormatHM);
					//return h + "小时前";
				}else if(h<0){
					//return Math.abs(h) + "小时后";
				}else if(h==0){
					int m = getOffectMinutes(c1.getTimeInMillis(), c2.getTimeInMillis());
					if(m>0){
						return m + "分钟前";
					}else if(m<0){
						//return Math.abs(m) + "分钟后";
					}else{
						return "刚刚";
					}
				}
				
			}else if(d>0){
				if(d == 1){
					//return "昨天"+getStringByFormat(strDate,outFormat);
				}else if(d==2){
					//return "前天"+getStringByFormat(strDate,outFormat);
				}
			}else if(d<0){
				if(d == -1){
					//return "明天"+getStringByFormat(strDate,outFormat);
				}else if(d== -2){
					//return "后天"+getStringByFormat(strDate,outFormat);
				}else{
				    //return Math.abs(d) + "天后"+getStringByFormat(strDate,outFormat);
				}
			}
			
			String out = getStringByFormat(strDate,outFormat);
			if(!AbStrUtil.isEmpty(out)){
				return out;
			}
		} catch (Exception e) {
		}
		
		return strDate;
	}
	
	
	/**
	 * 取指定日期为星期几.
	 *
	 * @param strDate 指定日期
	 * @param inFormat 指定日期格式
	 * @return String   星期几
	 */
    public static String getWeekNumber(String strDate,String inFormat) {
      String week = "星期日";
      Calendar calendar = new GregorianCalendar();
      DateFormat df = new SimpleDateFormat(inFormat);
      try {
		   calendar.setTime(df.parse(strDate));
	  } catch (Exception e) {
		  return "错误";
	  }
      int intTemp = calendar.get(Calendar.DAY_OF_WEEK) - 1;
      switch (intTemp){
        case 0:
          week = "星期日";
          break;
        case 1:
          week = "星期一";
          break;
        case 2:
          week = "星期二";
          break;
        case 3:
          week = "星期三";
          break;
        case 4:
          week = "星期四";
          break;
        case 5:
          week = "星期五";
          break;
        case 6:
          week = "星期六";
          break;
      }
      return week;
    }
    
    /**
     * 根据给定的日期判断是否为上下午.
     *
     * @param strDate the str date
     * @param format the format
     * @return the time quantum
     */
    public static String getTimeQuantum(String strDate, String format) {
        Date mDate = getDateByFormat(strDate, format);
        int hour  = mDate.getHours();
        if(hour >=12)
           return "PM";
        else
           return "AM";
    }
    
    /**
     * 根据给定的毫秒数算得时间的描述.
     *
     * @param milliseconds the milliseconds
     * @return the time description
     */
    public static String getTimeDescription(long milliseconds) {
        if(milliseconds > 1000){
            //大于一分
            if(milliseconds/1000/60>1){
                long minute = milliseconds/1000/60;
                long second = milliseconds/1000%60;
                return minute+"分"+second+"秒";
            }else{
                //显示秒
                return milliseconds/1000+"秒";
            }
        }else{
            return milliseconds+"毫秒";
        }
    }

	//showType 的取值：例如“yyyy年MM月dd日hh时mm分ss秒”，“yyyy-MM-dd HH:mm:ss” 等
	public static Calendar cal = new GregorianCalendar();
	public static int YEAR = cal.get(Calendar.YEAR);
	public static int MONTH = cal.get(Calendar.MONTH) + 1;
	public static int DAY_OF_MONTH = cal.get(Calendar.DAY_OF_MONTH);
	public static int DAY_OF_WEEK = cal.get(Calendar.DAY_OF_WEEK) - 1;

	public static Date getNowTime() {
		return new Date();
	}

	//以showType的格式，返回String类型的当前时间
	public static String getNowTime(String showType) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(showType);
		return sdf.format(date);
	}

	//将date按照showType转换成string
	public static String turnDateToString(Date dDate, String showType) {
		DateFormat df = new SimpleDateFormat(showType);
		String dateString = df.format(dDate);
		return dateString;
	}

	//返回将string转换成date类型
	public static Date turnStringToDate(String sDate, String showType) {
		//sDate的格式要和showType相符
		DateFormat df = new SimpleDateFormat(showType);
		Date date = null;
		try {
			date = df.parse(sDate);
		} catch (ParseException e) {
			System.out.println("格式不一致，返回null");
		}
		return date;
	}

	//获得时间差
	public static boolean getTimeDifference(Long timeBegin, Long timeEnd, Long timeStanding) {
		//大于指定时间返回true，否则返回false
		Long difference = timeEnd - timeBegin;
		if (difference >= timeStanding) {
			return true;
		} else {
			return false;
		}
	}

	//计算两个日期相隔的天数
	public static int getTimeDifference(Date firstDate, Date secondDate) {
		if (firstDate == null || secondDate == null)
			return 0;
		int nDay = (int) ((firstDate.getTime() - secondDate.getTime()) / (24 * 60 * 60 * 1000));
		return nDay;
	}

	/**
	 * 获取一年中的第几周
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 *
	 * @param sDate
	 * @return
	 */
	public static int getWeek(String sDate) {
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		int weekno = cal.get(Calendar.WEEK_OF_YEAR);
		return weekno;
	}

	/**
	 * 获取一年中的第几季度
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 *
	 * @param sDate
	 * @return
	 */
	public static int getQuarter(String sDate) {
		int month = new Integer(sDate.substring(5, 7));
		switch (month) {
			case 1:
				return 1;
			case 2:
				return 1;
			case 3:
				return 1;
			case 4:
				return 2;
			case 5:
				return 2;
			case 6:
				return 2;
			case 7:
				return 3;
			case 8:
				return 3;
			case 9:
				return 3;
			case 10:
				return 4;
			case 11:
				return 4;
			case 12:
				return 4;
			default:
				return 1;
		}
	}

	/**
	 * 获取半年，上半年0，下半年1
	 * 时间格式'yyyy-MM-dd'
	 * cuiqinglong create 2008-3-8
	 *
	 * @param sDate
	 * @return
	 */
	public static int getHYear(String sDate) {
		int month = new Integer(sDate.substring(5, 7));
		if (month >= 1 && month <= 6) {
			return 0;
		} else {
			return 1;
		}
	}

	public static String getYesterday(String sDate) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day - 1);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getTomorrow(String sDate) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day + 1);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getOneWeekAgo(String sDate) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day - 7);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getOneMonthAgo(String sDate) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getMonthChange(Date date, int months, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date == null ? new Date() : date);
		cal.add(Calendar.MONTH, months);
		return turnDateToString(cal.getTime(), format == null || format.equals("") ? "yyyy-MM-dd" : format);
	}

	public static String getSomeDaysAgo(String sDate, int days) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day - days);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getSomeDaysAfter(String sDate, int days) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day + days);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd");
	}

	public static String getSomeSecondsAgo(String sDate, int seconds) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd HH:mm:ss");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		int hour = new Integer(sDate.substring(11, 13));
		int minute = new Integer(sDate.substring(14, 16));
		int second = new Integer(sDate.substring(17, 19));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second - seconds);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	public static String getSomeSecondsAfter(String sDate, int seconds) {
		if (sDate.equals("")) {
			sDate = getNowTime("yyyy-MM-dd HH:mm:ss");
		}
		int year = new Integer(sDate.substring(0, 4));
		int month = new Integer(sDate.substring(5, 7)) - 1;
		int day = new Integer(sDate.substring(8, 10));
		int hour = new Integer(sDate.substring(11, 13));
		int minute = new Integer(sDate.substring(14, 16));
		int second = new Integer(sDate.substring(17, 19));
		//logger.debug("year:"+year+" month:"+month+" day:"+day+" hour:"+hour+" minute:"+minute+" second:"+second);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second + seconds);
		return turnDateToString(cal.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	//计算一个月有多少天
	public static List<Date> getMouthDayCount(String yearmonth) {
		String str[] = yearmonth.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(str[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(str[1]) - 1);//7月
		int maxDate = cal.getActualMaximum(Calendar.DATE);
		List<Date> list = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(turnStringToDate(yearmonth + "-" + maxDate, "yyyy-MM-dd"));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);//让日期减1 2
		list.add(turnStringToDate(yearmonth + "-01", "yyyy-MM-dd"));
		list.add(calendar.getTime());

		return list;
	}

	public static Date getAfterDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + days);
		return calendar.getTime();
	}

	public static Date getBeforeDate(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - days);
		return calendar.getTime();
	}

	/**
	 * 把日期和时间组合成一个日期完整形式
	 *
	 * @param date yyyy-MM-dd
	 * @param time HH:mm:ss
	 * @return 拼接好的日期形式
	 */
	public static Date addDateAndTime(Date date, Date time) {
		SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm:ss");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = sdfD.format(date);
		String formatTime = sdfT.format(time);
		try {
			Date parse = df.parse(formatDate + " " + formatTime);
			return parse;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	//把yyyymmdd转成yyyy-MM-dd hh:mm:ss格式
	public static String formatDatess(String str) {
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddhhmmss");
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sfstr = "";
		try {
			sfstr = sf2.format(sf1.parse(str));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sfstr;
	}

	//把yyyy-MM-dd hh:mm:ss转成yyyymmdd格式
	public static String formatDates(Date date) {
		SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String sfstr = "";
		try {
			sfstr = sf1.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sfstr;
	}
	
	/**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
		System.out.println(formatDateStr2Desc("2012-3-2 12:2:20","MM月dd日  HH:mm"));
	}

}
