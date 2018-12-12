package wx.util.bigdecimal;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 这个工具类，专们用来处理 BigDecimal 的，可以对其格式化，保留想要的数据类型
 * @author Kevin
 *	
 * try it,do it best!
 */
public class BigDecimalUtils {

	// 默认的数据保留格式 7位小数
	private final static String POINT_DEFAULT_PATTERN = "0.#######";

	/**
	 * 这个是默认的保留 7位小数
	 * @param value
	 * @return
	 */
	public static String format(BigDecimal value) {
		if(value == null) {
			value = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat(POINT_DEFAULT_PATTERN);
		return df.format(value);
	}
	
	/**
	 * 	将BigDecimal类型的数据转换为String类型
	 *  @param value 要转换的数据
	 * 
	 * @pattern 保留小数的样式
	 * 
	 * @return 转换后的数据
	 */
	public static String formatToStr(BigDecimal value,String pattern) {
		if(value == null) {
			value = BigDecimal.ZERO;
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}
	
}
