package com.ab.util.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wolf on 2015/8/28.
 * 计算时间差
 */
public class JudgeTime {
    public static String getWords(String time) {
        try {
            SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin = dfs.parse(time);
            long between = (new Date().getTime() - begin.getTime()) / 1000;//除以1000是为了转换成秒
            long day1 = between / (24 * 3600);
            long hour1 = between % (24 * 3600) / 3600;
            long minute1 = between % 3600 / 60;
            if (day1 > 0) {
                return day1 + "天前";
            }
            if (hour1 > 0) {
                return hour1 + "小时前";
            }
            if (minute1 > 0) {
                return minute1 + "分钟前";
            }
            return "刚刚";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getMonth(String time) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = dateFormat.parse(time);
            switch (date.getMonth()) {
                case 0:
                    return "一月";
                case 1:
                    return "二月";
                case 2:
                    return "三月";
                case 3:
                    return "四月";
                case 4:
                    return "五月";
                case 5:
                    return "六月";
                case 6:
                    return "七月";
                case 7:
                    return "八月";
                case 8:
                    return "九月";
                case 9:
                    return "十月";
                case 10:
                    return "十一月";
                case 11:
                    return "十二月";


            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
