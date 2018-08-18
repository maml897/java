package jingdu;

import java.math.BigDecimal;

public class Division {
	public static void main(String[] args) {
		System.out.println(errorFloat(172.525f,1f, 2).floatValue());
		System.out.println(rightFloat(172.525f,1f, 2).floatValue());
		
		System.out.println(errorDouble(162.535d,1d, 2).floatValue());
		System.out.println(rightDouble(162.535d,1d, 2).floatValue());
	}
	
	public static BigDecimal errorFloat(float a, float b, int scale)
	{
		if (b == 0)
		{
			return new BigDecimal(0);
		}

		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal errorDouble(double a, double b, int scale)
	{
		if (b == 0)
		{
			return new BigDecimal(0);
		}

		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	
	public static BigDecimal rightFloat(float a, float b, int scale)
	{
		if (b == 0)
		{
			return new BigDecimal(0);
		}
		
		BigDecimal b1 = new BigDecimal(String.valueOf(a));
		BigDecimal b2 = new BigDecimal(String.valueOf(b));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal rightDouble(double a, double b, int scale)
	{
		if (b == 0)
		{
			return new BigDecimal(0);
		}
		
		BigDecimal b1 = new BigDecimal(String.valueOf(a));
		BigDecimal b2 = new BigDecimal(String.valueOf(b));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
	
	public static BigDecimal right(String a, String b, int scale)
	{

		if (b.equals("0"))
		{
			return new BigDecimal(0);
		}
		
		BigDecimal b1 = new BigDecimal(a);
		BigDecimal b2 = new BigDecimal(b);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
	}
}
