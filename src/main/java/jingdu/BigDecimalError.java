package jingdu;

import java.math.BigDecimal;

public class BigDecimalError {

	public static void main(String[] args) {
		System.out.println(new BigDecimal("172").floatValue()); //多了.0
	}
}
