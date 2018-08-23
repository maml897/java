package lambda;

import java.util.Arrays;
import java.util.List;

public class AverageTest {
	public static void main(String[] args) {
		List<Long> studentIDs = Arrays.asList();
		
		//如果list为空则下面会报错，要使用第二种方式
		
		//第一种方法
		double average = studentIDs.stream()
				.mapToDouble(x -> x).average().getAsDouble();//直接使用average().getAsDouble()会报错
		System.out.println(average);
		
		//第二种方法
		double average1 = studentIDs.stream()
				.mapToDouble(x -> x).summaryStatistics().getAverage();
		
		System.out.println(average1);
	}
}
