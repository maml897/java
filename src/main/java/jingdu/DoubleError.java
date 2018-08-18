package jingdu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DoubleError {
	public static void main(String[] args) {
		
		double d=162.535d;
		
		System.out.println(new BigDecimal(d));//错误
		System.out.println(new BigDecimal(String.valueOf(d)));//正确
		System.out.println(BigDecimal.valueOf(d));//正确
		
		
		DecimalFormat decimalFormat = new DecimalFormat("0.##");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		
		System.out.println(decimalFormat.format(d));// 错误
		System.out.println(decimalFormat.format(new BigDecimal(d)));// 错误
		
		
		double d1=33333333.041111118835846799;
		System.out.println(BigDecimal.valueOf(d1));//正确,其实和new BigDecimal(String.valueOf(d)) 一个道理
		
		System.out.println(new BigDecimal(d1).toString());
	}
}
