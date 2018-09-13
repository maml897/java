package lambda.method;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Test
{
	public static void main(String[] args)
	{
		Function<String, String> upperfier1 = String::toUpperCase;
		UnaryOperator<String> upperfier2 = (x) -> x.toUpperCase();// 这里没有参数，即0个
	}
}
