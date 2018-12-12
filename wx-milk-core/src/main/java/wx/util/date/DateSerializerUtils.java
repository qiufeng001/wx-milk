package wx.util.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 这是对日期进行处理的工具类
 * 
 * @author Kevin
 * 
 * try it,do it best!
 */
public class DateSerializerUtils {
	
	// 这个是默认的日期格式样式
	private final static String DEFAULT_DATE_PATTERN = "yy-MM-dd HH:mm:ss";
	
	/**
	 * 这里是将传入的 日期转换为固定的格式，格式由 pattern 来确定
	 *   
	 * @date 要转换的日期
	 * 
	 * @pattern 要转换的日期固定格式
	 * 
	 * @return 要返回的转换后的日期格式
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	
	/**
	 * 这是将传入的日期转换为固定的日期形式
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDefalt(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
		return sdf.format(date);
	}
}
