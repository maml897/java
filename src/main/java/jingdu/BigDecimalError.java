package jingdu;

import java.math.BigDecimal;

import zutils.FormatUtils;

public class BigDecimalError {

	public static void main(String[] args) {
		System.out.println(FormatUtils.float2String(new BigDecimal("172").floatValue())); //多了.0
		System.out.println(new BigDecimal("172").floatValue()); //多了.0
	}
}
