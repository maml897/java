package jingdu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Wenti
{
	public static void main(String[] args)
	{
		Double d = 301353.040998999883584678173065185546875d;
		System.out.println(d);
		System.out.println(String.valueOf(d));
		System.out.println(d.toString());

		double d1 = 301353.0499999999883584678173065185546875d;
		System.out.println(d1);
		System.out.println(String.valueOf(d1));

		float f = 172.252f;
		double d2 = f;
		System.out.println(d2);


		// 使用BigDecimal(double val)构造函数时仍会存在精度丢失问题，建议使用BigDecimal(String val)
		// BigDecimal都建议使用字符串传值，传double也不行
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println(decimalFormat.format(162.535d));// 错误
		System.out.println(decimalFormat.format(new BigDecimal(162.535d)));// 错误
		System.out.println(decimalFormat.format(new BigDecimal(String.valueOf(162.535d))));// 正确
		System.out.println(decimalFormat.format(new BigDecimal(String.valueOf(301353.0499999999883584678173065185546875d))));// 但是如果String.valueOf都不对的话，那结果也不对
	}
}
