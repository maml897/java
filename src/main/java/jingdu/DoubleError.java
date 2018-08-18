package jingdu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleError {
	public static void main(String[] args) {
		
		double d=162.535d;
		
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		System.out.println(decimalFormat.format(d));// 错误
		System.out.println(decimalFormat.format(new BigDecimal(d)));// 错误
		
	}
}