package ok;

import java.math.BigDecimal;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;

public class MathUtils {
	/**
	 * 加法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1,double v2) {
		return NumberUtil.add(v1, v2);
	}
	
	/**
	 * 减法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v1,double v2) {
		return NumberUtil.sub(v1, v2);
	}
	
	/**
	 * 乘法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double mul(double v1,double v2) {
		return NumberUtil.mul(v1, v2);
	}
	
	/**
	 * 除法
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double div(double v1,double v2,int...scales) {
		if(v2==0) {
			return 0;
		}
		if(scales == null || scales.length == 0) {
			return NumberUtil.div(v1, v2);
		}
		return round(NumberUtil.div(v1, v2), scales);
	}
	
	/**
	 * 保留几位小数,后面会出现.0，比如输入100，输出100.0
	 * @param d
	 * @param scales
	 * @return
	 */
	public static double round(double d,int...scales) {
		int scale = (scales == null || scales.length == 0) ? 2 : scales[0];
		return NumberUtil.round(d, scale).doubleValue();
	}
	
	/**
	 * 百分比展示，数值*100，保留位数，后面加%
	 * @param d
	 * @param scales
	 * @return
	 */
	public static String percent(double d,int...scales) {
		int scale = (scales == null || scales.length == 0) ? 2 : scales[0];
		double result=round(mul(d, 100), scale);
		return format(result)+"%";
	}
	
	/***
	 * 去掉数字后面的0，同下
	 * @param f
	 * @return
	 */
	public static String format(double f,int...scales)
	{
		f = round(f, scales);
		int i = (int) f;
		if (i == f)
		{
			return i + "";
		}
		return f + "";
	}
	
	/***
	 * 去掉数字后面的0，同上
	 * @param f
	 * @return
	 */
	public static String formatZero(double f,int...scales)
	{
		f = round(f, scales);
		BigDecimal value = new BigDecimal(String.valueOf(f)).stripTrailingZeros();
	    return value.toPlainString();
	}
	
	
	
	
	
	///////////////////////浮点数比较//////////////////////////
	
	/**
	 * hutool提供的比较方法
	 * @param x
	 * @param y
	 * @param n
	 * @return
	 */
	private static int compareNPoint(Object x, Object y, int n) {
		BigDecimal number1 = new BigDecimal(String.valueOf(x));
		BigDecimal number2 = new BigDecimal(String.valueOf(y));
		double n1 = NumberUtil.round(number1, n).doubleValue();
		double n2 = NumberUtil.round(number2, n).doubleValue();
		return NumberUtil.compare(n1, n2);
	}
	
	/**
	 * 三个数比较，可以指定位数，默认2位
	 * @param x
	 * @param type1
	 * @param data
	 * @param type2
	 * @param y
	 * @param scales
	 * @return
	 */
	private static boolean compareNPoint(Object x,String type1, Object data,String type2,Object y,int... scales) {
		int scale = (scales == null || scales.length == 0) ? 2 : scales[0];
		return compareNPoint(x, data,type1, scale) && compareNPoint(data, y,type2, scale);
	}
	
	/**
	 * 三个数比较，4位
	 * @param x
	 * @param type1
	 * @param data
	 * @param type2
	 * @param y
	 * @return
	 */
	public static boolean compare4Point(Object x, String type1, Object data,String type2,Object y) {
		return compareNPoint(x,type1,data, type2, y,4);
	}
	
	/**
	 * 两个数比较，4位
	 * @param x
	 * @param type
	 * @param y
	 * @return
	 */
	public static boolean compare4Point(Object x, String type,Object y) {
		return compareNPoint(x, y, type,4);
	}
	
	/**
	 * 两个数比较，可以设置位数，不设置则默认2位
	 * @param x 
	 * @param y
	 * @param type
	 * @param scale
	 * @return
	 */
	public static boolean compareNPoint(Object x, Object y,String type,int... scales) {
		int scale = (scales == null || scales.length == 0) ? 2 : scales[0];
		if(type.equals(">=")) {
			return compareNPoint(x, y, scale)>=0;
		}
		if(type.equals(">")) {
			return compareNPoint(x, y, scale)==1;
		}
		if(type.equals("<=")) {
			return compareNPoint(x, y, scale)<=0;
		}
		if(type.equals("<")) {
			return compareNPoint(x, y, scale)==-1;
		}
		if(type.equals("==") || type.equals("=")) {
			return compareNPoint(x, y, scale)==0;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {

		System.out.println(Convert.toDouble("1000"));
	    
	}
}