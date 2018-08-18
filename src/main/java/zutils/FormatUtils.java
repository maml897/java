package zutils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FormatUtils {
	public static void main(String[] args) {
		float2String(172.525f);
		double2String(162.535d);
		formatString("162.535");

		// 下面这种直接用是会出现0的
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(172.5f));
		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
		System.out.println(bigDecimal);
		System.out.println(String.valueOf(172.50));
	}

	/**
	 * float 转成double
	 * 
	 * @param value
	 * @return
	 */
	public static double float2double(float value) {
		String stringValue = String.valueOf(value);
		String result = formatString(stringValue);
		return new BigDecimal(result).doubleValue();
	}

	/**
	 * 格式化float，返回float
	 * 
	 * @param value
	 * @return
	 */
	public static float formatFloat(float value) {
		String result = float2String(value);
		return new BigDecimal(result).floatValue();
	}

	/**
	 * 格式化float，返回float
	 * 
	 * @param value
	 * @return
	 */
	public static double formatDouble(double value) {
		String result = double2String(value);
		return new BigDecimal(result).doubleValue();
	}

	/**
	 * 格式化float，返回string
	 * 
	 * @param value
	 * @return
	 */
	public static String float2String(float value) {
		String stringValue = String.valueOf(value);
		String result = formatString(stringValue);
		return result;
	}

	/**
	 * 格式化double，返回string
	 * 
	 * @param value
	 * @return
	 */
	public static String double2String(double value) {
		String stringValue = String.valueOf(value);
		String result = formatString(stringValue);
		return result;
	}

	/**
	 * 除法，返回精度两位小数
	 * 
	 * @param a
	 * @param b
	 * @param multiply100，如果不乘以100.填写false，否则不用填写或者true
	 * @return
	 */
	public static float division(float a, float b, boolean... multiply100) {
		return division(a, b, 2, multiply100);
	}

	/**
	 * 除法，可以设置返回精度
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @param multiply100
	 * @return
	 */
	public static float division(float a, float b, int scale, boolean... multiply100) {
		if (b == 0) {
			return 0;
		}
		if (multiply100 != null && multiply100.length > 0 && !multiply100[0]) {
			return new BigDecimal(String.valueOf(a))
					.divide(new BigDecimal(String.valueOf(b)), scale, BigDecimal.ROUND_HALF_UP).floatValue();
		}
		return new BigDecimal(String.valueOf(a))
				.divide(new BigDecimal(String.valueOf(b)), scale + 2, BigDecimal.ROUND_HALF_UP)
				.multiply(new BigDecimal("100")).floatValue();
	}
	
	/**
	 * 四舍五入
	 * 和formatFloat效果一致
	 * @param value
	 * @return
	 */
	public static float round(float value, int... scales) {
		int scale = 2;
		if (scales != null && scales.length > 0) {
			scale = scales[0];
		}
		BigDecimal b = new BigDecimal(Float.toString(value));
		return b.divide(new BigDecimal("1"), scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	/**
	 * 除法
	 * 
	 * @param a
	 * @param b
	 * @param scale
	 * @return
	 */
	@SuppressWarnings("unused")
	private static BigDecimal division(double a, double b, int scale) {
		if (b == 0) {
			return new BigDecimal(0);
		}

		BigDecimal b1 = new BigDecimal(String.valueOf(a));
		BigDecimal b2 = new BigDecimal(String.valueOf(b));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}

	private static String formatString(String value) {
		String result = formatDecimal(new BigDecimal(value));
		return result;
	}

	private static String formatDecimal(BigDecimal value) {
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		return decimalFormat.format(value);
	}

	/**
	 * 去掉0.20 最后的0
	 * 
	 * @param f
	 * @return
	 */
	public static String formatFloatZero(float f) {
		int i = (int) f;
		if (i == f) {
			return i + "";
		} else {
			return f + "";
		}
	}

}
