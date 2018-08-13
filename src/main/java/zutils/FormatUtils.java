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

	public static String float2String(float value) {
		String stringValue = String.valueOf(value);
		String result = formatString(stringValue);
		return result;
	}

	public static String double2String(double value) {
		String stringValue = String.valueOf(value);
		String result = formatString(stringValue);
		return result;
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
}
