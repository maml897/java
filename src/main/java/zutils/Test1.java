package zutils;

import java.math.BigDecimal;

public class Test1 {
	public static void main(String[] args) {
		double d=172.535d;
		
		System.out.println(new BigDecimal(d));
		System.out.println(new BigDecimal(String.valueOf(d)));
		System.out.println(BigDecimal.valueOf(d));
	}
}
