package lambda.method;

import java.io.File;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

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

		Comparator<? super String> c = (x, y) -> x.compareToIgnoreCase(y);
		Comparator<? super String> c1 = String::compareToIgnoreCase;

		BinaryOperator<Integer> bo = (sum, item) -> sum + item;
		BinaryOperator<Integer> bo1 = Integer::sum;
		
		//构造器的方法
		Supplier<Test> sup=Test::new;
		MyInterface1<Test> sup1=Test::new;
		
		
		//静态方法
		Function<String, String> f4=Test::statictest;
		MyInterface<String, String> f41 = Test::statictest;
	}
	
	private static String statictest(String x){
		return "";
	}
}
