package wx.util;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * pe date utils
 *
 * @author LiYanCheng@HF
 * @version 1.0.0
 * @since 2016年10月9日14:34:01
 */
public class WxDateUtils {

    public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public static final String FORMAT_YYYYMMDD = "yyyy/MM/dd";

    public static String format(Date date, String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        return dateFormat.format(date);
    }

    public static Date parse(String dateString, String formatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }


    public static Date parse(Date date, String formatStr) {
        String dateString = format(date, formatStr);
        return parse(dateString, formatStr);
    }


    public static long reduceDate(Date date, Date reduceDate) {
        if (date == null || reduceDate == null) {
            return NumberUtils.INTEGER_ZERO;
        }

        return (date.getTime() - reduceDate.getTime()) / 1000;
    }

    public static Date getFirstDate(Date date) {
        Date firstDate = DateUtils.setHours(date, 0);
        firstDate = DateUtils.setMinutes(firstDate, 0);
        return DateUtils.setSeconds(firstDate, 0);
    }

    public static Date getEndDate(Date date) {
        Date lastTime = DateUtils.setHours(date, 23);
        lastTime = DateUtils.setMinutes(lastTime, 59);
        return DateUtils.setSeconds(lastTime, 59);
    }

    public static String secToTime(int time) {
        String timeStr = "00:00";
        if (time <= 0) {
            return timeStr;
        }

        int minute = time / 60;
        if (minute < 60) {
            int second = time % 60;
            timeStr = unitFormat(minute) + ":" + unitFormat(second);
        } else {
            int hour = minute / 60;
            if (hour > 99) {
                return "99:59:59";
            }

            minute = minute % 60;
            int second = time - hour * 3600 - minute * 60;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }

        return timeStr;
    }

    private static String unitFormat(int i) {
        if (i >= 0 && i < 10) {
            return "0" + i;
        }

        return "" + i;
    }

    /**
     * 获取指定时间的前一天
     *
     * @param date
     * @return
     */
    public static Date getSpecifiedDayBefore(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取每天的开始时间
     *
     * @param date date
     * @return 当天的开始时间
     */
    public static Date getStartOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取当天的过去一周的时间
     *
     * @param toDay
     * @return
     */
    public static List<Date> getLastWeek(Date toDay) {
        List<Date> weeks = new LinkedList<Date>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= 7; i++) {
            calendar.setTime(toDay);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            weeks.add(calendar.getTime());
        }

        return weeks;
    }

    /**
     * 获取某一天的前一周时间
     *
     * @param toDay
     * @return 返回封装好的时间集合
     */
    public static List<String> getLastWeekTime(Date toDay) {
        List<String> weeks = new LinkedList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i <= 7; i++) {
            calendar.setTime(toDay);
            int day = calendar.get(Calendar.DATE);
            calendar.set(Calendar.DATE, day - i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date time = calendar.getTime();
            String format = sdf.format(time);
            weeks.add(format);
        }

        return weeks;
    }

}
