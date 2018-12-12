package wx.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import wx.contants.WxConstant;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * 数值工具类
 *
 * @author LiYanCheng@HF
 * @version 1.0.0
 * @since 2016年10月10日15:49:49
 */
public class WxNumberUtils {

    /**
     * 数值转换为Long类型
     *
     * @param number 数值
     * @return 数值不合法时，默认返回0
     * @since 2016年10月10日16:14:03
     */
    public static long transformLong(Object number) {
        if (number == null) {
            return 0L;
        }

        String numberString = String.valueOf(number);
        if (!NumberUtils.isNumber(numberString)) {
            return 0L;
        }

        return Long.valueOf(numberString);
    }

    /**
     * 数值转换为Float类型
     *
     * @param number 数值
     * @return 数值不合法时，默认返回0
     * @since 2016年10月10日16:14:03
     */
    public static float transformFloat(Object number) {
        if (number == null) {
            return 0F;
        }

        String numberString = String.valueOf(number);
        if (!NumberUtils.isNumber(numberString)) {
            return 0F;
        }

        return Float.valueOf(numberString);
    }

    /**
     * 数值转换为Double类型
     *
     * @param number 数值
     * @return 数值不合法时，默认返回0
     * @since 2016年10月10日16:14:03
     */
    public static double transformDouble(Object number) {
        if (number == null) {
            return 0D;
        }

        String numberString = String.valueOf(number);
        if (!NumberUtils.isNumber(numberString)) {
            return 0D;
        }

        return Double.valueOf(numberString);
    }

    /**
     * 数值转换为int类型
     *
     * @param number 数值
     * @return 数值不合法时，默认返回0
     * @since 2016年10月10日16:14:03
     */
    public static int transformInt(Object number) {
        if (number == null) {
            return 0;
        }

        String numberString = String.valueOf(number);
        if (!NumberUtils.isNumber(numberString)) {
            return 0;
        }

        if (!numberString.contains(WxConstant.POINT)) {
            return Integer.valueOf(numberString);
        }

        return Integer.valueOf(numberString.substring(0,
                numberString.indexOf(WxConstant.POINT)));
    }

    /**
     * 数值集合相加
     *
     * @param numbers 数值集合
     * @return 加减操作
     * @since 2016年10月21日09:22:38
     */
    public static Long sum(List<Long> numbers) {
        if (CollectionUtils.isEmpty(numbers)) {
            return NumberUtils.LONG_ZERO;
        }

        Long sum = 0L;
        for (Long number : numbers) {
            sum = sum + WxNumberUtils.transformLong(number);
        }

        return sum;
    }

    public static double reservedDecimal(int digit, double number) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    private static BigInteger factorial(BigInteger n) {
        return (n.compareTo(BigInteger.valueOf(1L)) > 0) ? (n.multiply(factorial(n.subtract(BigInteger.valueOf(1L))))) : BigInteger.valueOf(1L);
    }

    /**
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public static BigInteger combination(long n, long m) {

        return (BigInteger.valueOf(n).compareTo(BigInteger.valueOf(m)) > 0)
                ? (factorial(BigInteger.valueOf(n)).divide(factorial(BigInteger.valueOf(n).subtract(BigInteger.valueOf(m)))))
                .divide(factorial(BigInteger.valueOf(m))) : BigInteger.valueOf(1L);
    }

    public static void main(String[] args) {
        System.out.println(reservedDecimal(1, 0));
    }
}
