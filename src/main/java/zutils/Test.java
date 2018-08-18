package zutils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Test {
	public static void main(String[] args) {
//		float f=172f;
//		
//		System.out.println(FormatUtils.round(f));
//		System.out.println(FormatUtils.formatFloat(f));
//		
//		
//		double b=10/3.0;
//		DecimalFormat df = new DecimalFormat( "0.#####################################################"); 
//		System.out.println(df.format(b));
//		
//		
//		
//		System.out.println(String.valueOf(301353.0499999999883584678173065185546875d));
//		System.out.println(String.valueOf(3.0499999999883584678173065185546875d));
		
		
		double d=33333333.041111118835846799;
		System.out.println(String.valueOf(d));
		
		String x = new BigDecimal(d).toString();
		System.out.println(x);
		
		System.out.println("==========================");
		
		BigDecimal bigDecimal = new BigDecimal(String.valueOf(d));
		System.out.println(bigDecimal.doubleValue());
		
		
		
		DecimalFormat decimalFormat = new DecimalFormat("0.############################################");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println(decimalFormat.format(bigDecimal));
		
		
	}
}
