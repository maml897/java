package lambda;

import java.util.function.BinaryOperator;
import java.util.stream.Stream;

public class ReduceTest
{
	public static void main(String[] args)
	{
		Stream<String> stream = Stream.of("I", "love", "you", "too");
		Integer lengthSum = stream.reduce(0,// 初始值　// (1)
		        (sum, str) -> sum+str.length(), // 累加器 // (2)
		        (a, b) -> a+b);// 部分和拼接器，并行执行时才会用到 // (3)
		System.out.println(lengthSum);
		
		
		BinaryOperator<Integer> bo = (sum, item) -> sum + item;
		BinaryOperator<Integer> bo1 = Integer::sum;
		
		int value = Stream.of(1, 2, 3, 4).reduce(100, (sum, item) -> sum + item);
		System.out.println(value);
		value = Stream.of(1, 2, 3, 4).reduce(100, Integer::sum);
		
		System.out.println(value);
	}
}
