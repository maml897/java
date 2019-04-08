package lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ParallelStreamTest
{

	public static void main(String[] args)
	{
		// 生成含有1百万随机字符串，且每个字符串长度不超过10的列表
		List<String> strList = geneRandomStringList(1_000_000, 10);
		long count = 0;

		// java 6常用的for循环方法
		long start = System.currentTimeMillis();
		for (String str : strList)
		{
			if (str.length() > 4)
				count++;
		}
		long end = System.currentTimeMillis();
		System.out.println("None Stream Time Used:" + (end - start));

		// Java 8 stream
		start = System.currentTimeMillis();
		count = strList.stream().filter(str -> str.length() > 4).count();
		end = System.currentTimeMillis();
		System.out.println("Normal Stream Time Used:" + (end - start));

		// Java 8 parallelStream
		start = System.currentTimeMillis();
		count = strList.parallelStream().filter(str -> str.length() > 4).count();
		// System.out.println("Long Names Count:" + count);
		end = System.currentTimeMillis();
		System.out.println("Parallel Stream Time Used:" + (end - start));

	}

	/**
	 * 生成随机字符串List
	 * @param total_Length
	 *            //list的总长度
	 * @param str_maxlength
	 *            //每个字符串最大长度
	 * @return
	 */
	private static List<String> geneRandomStringList(int total_Length, int str_maxlength)
	{

		Random r = new Random();
		StringBuilder sb = new StringBuilder(total_Length);
		for (int i = 0; i < total_Length; i++)
		{
			for (int j = r.nextInt(str_maxlength); j > 0; j--)
			{
				sb.append((char) ('a' + r.nextInt(26)));
			}
			sb.append(" ");
		}

		return Arrays.asList(sb.toString().split(" "));
	}

}