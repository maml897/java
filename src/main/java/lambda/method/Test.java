package lambda.method;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test
{
	public static void main(String[] args)
	{
		//一定要记录String::toUpperCase 返回的不是什么具体的类型
		
		// 对象的方法
		Function<String, String> f1 = String::toUpperCase;
		Function<String, String> f11 = x -> x.toUpperCase();
		
		MyInterface<String, String> f3 = String::toUpperCase;
		MyInterface<String, String> f31 = x -> x.toUpperCase();

		Function<File, Boolean> f2 = File::isHidden;
		Function<File, Boolean> f22 = x -> x.isHidden();

		// ?
		Comparator<? super String> c = (x, y) -> x.compareToIgnoreCase(y);
		Comparator<? super String> c1 = String::compareToIgnoreCase;

		UnaryOperator<String> upperfier2 = (x) -> x.toUpperCase();// 这里没有参数，即0个
		List<String> list = Stream.of("a").map(upperfier2).collect(Collectors.toList());
		System.out.println(list);
	}
}
