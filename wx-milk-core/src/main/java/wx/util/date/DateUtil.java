package wx.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * 日期时间工具类
 *
 * @author zhong.h
 */
public class DateUtil {

	public static String formatDate(Date date) {
		return formatDateByFormat(date, "yyyy-MM-dd");
	}

	// 获取自定义格式的日期
	public static String formatDateByFormat(Date date, String format) {
		String result = "";
		if (date != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result = sdf.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static Date parseDate(java.sql.Date date) {
		return date;
	}

	public static java.sql.Date parseSqlDate(Date date) {
		if (date != null) {
			return new java.sql.Date(date.getTime());
		} else {
			return null;
		}
	}

	public static String format(Date date, String format) {
		String result = "";
		try {
			if (date != null) {
				DateFormat df = new SimpleDateFormat(format);
				result = df.format(date);
			}
		} catch (Exception e) {
		}
		return result;
	}

	public static String format(Date date) {
		if (null != date) {
			return format(date, "yyyy/MM/dd");
		}
		return null;
	}

	public static String format1(Date date) {
		if (null != date) {
			return format(date, "yyyy-MM-dd");
		}
		return null;
	}

	public static String format2(Date date) {
		if (null != date) {
			return format(date, "yyyyMMdd");
		}
		return null;
	}

	/**
	 * yyyy年MM月
	 * @param date
	 * @return
	 */
	public static String format3(Date date) {
		if (null != date) {
			return format(date, "yyyyMM");
		}
		return null;
	}

	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	public static String getDate(Date date) {
		return format(date, "yyyy/MM/dd");
	}

	public static String getDate(Date date, String formatStr) {
		return format(date, formatStr);
	}

	public static String getTime(Date date) {
		return format(date, "HH:mm:ss");
	}

	public static String getDateTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 日期月份相加减
	 * @param date 具体的时间
	 * @param month 相加减的月份
	 * @return 处理后的时间
	 */
	public static Date addMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 日期相加
	 * @param date Date
	 * @param day int
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + day * 24L * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 * @param date Date
	 * @param date1 Date
	 * @return int
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	/**
	 * 日期相减(返回秒值)
	 * @param date Date
	 * @param date1 Date
	 * @return int
	 * @author
	 */
	public static Long diffDateTime(Date date, Date date1) {
		return (Long) ((getMillis(date) - getMillis(date1)) / 1000);
	}

	public static Date getdate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.parse(date);
	}

	public static Date getdate1(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date);
	}

	/**
	 * yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static Date getdate2(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.parse(date);
	}

	public static Date getMaxTimeByStringDate(String date) throws Exception {
		String maxTime = date + " 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(maxTime);
	}

	/**
	 * 获得当前时间
	 * @return
	 * @throws ServiceException
	 */
	public static Date getCurrentDateTime() throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = DateUtil.getDateTime(date);
		return sdf.parse(result);
	}

	/**
	 * 获得当前时间
	 * @return
	 * @throws ServiceException
	 */
	public static Date getCurrentDate() throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String result = DateUtil.getDateTime(date);
		return sdf.parse(result);
	}

	public static String getCurrentDateTimeToStr() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(getCurrentDateTime());
	}

	public static String getCurrentDateTimeToStr2() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(getCurrentDateTime());
	}

	public static Long getWmsupdateDateTime() {
		Calendar cl = Calendar.getInstance();

		return cl.getTimeInMillis();
	}

	public static Integer getLeftSeconds(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date condition = sdf.parse(date);
		long n = condition.getTime();
		long s = sdf.parse(getCurrentDateTimeToStr2()).getTime();
		//    	System.out.println("开始时间:"+date+"-->"+(int)((s-n)/1000));
		return (int) ((s - n) / 1000);
	}

	/**
	 * 获得时间戳
	 * @return
	 * @throws Exception
	 */
	public static String getTime() {
		Date date = new Date();
		return String.valueOf(date.getTime());
	}

	/** 判断日期格式是否正确 */
	public static boolean validateDate(String dateString) {
		//使用正则表达式 测试 字符 符合 dddd-dd-dd 的格式(d表示数字)
		Pattern p = Pattern.compile("\\d{4}+[-]\\d{2}+[-]\\d{2}+");
		Matcher m = p.matcher(dateString);
		if (!m.matches()) {
			return false;
		}
		//得到年月日
		String[] array = dateString.split("-");
		int year = Integer.valueOf(array[0]);
		int month = Integer.valueOf(array[1]);
		int day = Integer.valueOf(array[2]);

		if (month < 1 || month > 12) {
			return false;
		}
		int[] monthLengths = new int[] { 0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if (isLeapYear(year)) {
			monthLengths[2] = 29;
		} else {
			monthLengths[2] = 28;
		}
		int monthLength = monthLengths[month];
		if (day < 1 || day > monthLength) {
			return false;
		}
		return true;
	}

	/** 是否是闰年 */
	private static boolean isLeapYear(int year) {
		return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * 将时间格式的字符串，转换为时间
	 *
	 * @param str 时间格式的字符串
	 * @param pattern 转换格式
	 * @return java.util.Date
	 */
	public static Date parseToDate(String str, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将时间格式的字符串，转换为时间
	 *
	 * @param str 时间格式的字符串
	 * @param pattern 转换格式
	 * @return java.util.Date
	 */
	public static Date parseToDateWithThrowException(String str, String pattern) throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(str);
	}

	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static String getLastDayOfMonthStr(int year, int month) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return sdf.format(cal.getTime());
	}

	public static String getFirstDayOfMonthStr(int year, int month) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return sdf.format(cal.getTime());
	}

	public static String addDate(String date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(parseToDate(date, "yyyy-MM-dd"));
		c.add(Calendar.DATE, day);
		return format1(c.getTime());
	}

	/**
	 * 返回当月最后一天的日期 
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 返回当月最前一天的日期 
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = convert(date);
		calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 获取一个月的有效天数
	 * @param date 月份中的某一天
	 * @return 这个月的有效天数
	 */
	public static int getDaysOfMonth(Date date) {
		return convert(date).getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取两个日期相隔的月份,只取月份的相差忽略天   最小为0 最大为12
	 * @param date1 日期1
	 * @param date2 日期2
	 * @return 相隔月份的绝对值
	 */
	public static int getBetweenMonths(Date date1, Date date2) {
		Calendar bef = Calendar.getInstance();
		Calendar aft = Calendar.getInstance();
		bef.setTime(date1);
		aft.setTime(date2);
		int diffMonth = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
		int diffYear = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
		return Math.abs(diffMonth + diffYear);
	}

	/**
	 * 获取两个日期的天数差（包含边界 例如1月1日到1月31日 就是31天）
	 * @param date1  天数1
	 * @param date2  天数2
	 * @return 天数差
	 */
	public static int getBetweenDays(Date date1, Date date2) {
		int fDays = convert(date1).get(Calendar.DAY_OF_YEAR);
		int lDays = convert(date2).get(Calendar.DAY_OF_YEAR);
		int days = Math.abs(fDays - lDays) + 1;
		return days;
	}

	/**
	 * 将日期转换为日历 
	 * @param date 日期 
	 * @return 日历
	 */
	private static Calendar convert(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	/**
	 *
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为  * minutes * seconds * msec 后的格式
	 * @author li.df
	 */
	public static String formatDuring(long mss) {
		long minutes = mss / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		long msec = mss - minutes*(1000 * 60) - seconds*1000;
		String str = "";
		if(minutes!=0){
			str = str + minutes + "分";
		}
		if(seconds!=0){
			str = str + seconds + "秒";
		}
		if(msec!=0){
			str = str + msec + "毫秒";
		}
		return str;
	}

	public static void main(String[] args) throws Exception {
		Date date = DateUtil.getdate("2017-08-22");
		Date date2 = DateUtil.getdate("2017-05-23");
		System.out.println(DateUtil.getDate(date));
		System.out.println(DateUtil.getDate(date2));
		System.out.println(DateUtil.getBetweenDays(date, date2));
	}
}
