package jingdu;

import java.math.BigDecimal;

import zutils.FormatUtils;

public class Float2Double {
	public static void main(String[] args) {
		float f=172.525f;
		
		System.out.println(right(f));
		System.out.println(error(f));
		System.out.println(error1(f));
		System.out.println(error2(f));
	}
	
	private static double right(float f){
		return FormatUtils.float2double(f);
	}
	
	private static double  error(float f){
		return f;
	}
	
	private static double error1(float f){
		return new BigDecimal(f).doubleValue();
	}
	
	private static double error2(float f){
		return ((Float)f).doubleValue();
	}
	
	
	
}
