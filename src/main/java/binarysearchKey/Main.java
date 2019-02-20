package binarysearchKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import zutils.LambdaUtils;

public class Main
{
	
	public static double key(Collection<Double> values, double value, boolean... flags)
	{

		// 1.从小到大
		// 2.从大到小
		// 3.取低
		// 4.取高

		boolean up = true;// 默认取高,也就是5.5 会认为是6
		boolean toup = true;// 默认列表排序从小到大，也就是 1，2，3

		if (flags != null)
		{
			if (flags.length > 0)
			{
				up = flags[0];
				if (flags.length > 1)
				{
					toup = flags[1];
				}
			}
		}
		if (toup)
		{// 从小到大

			// 取低
			if (!up)
			{
				double result = -1;
				for (Double f : values)
				{
					if (f <= value)
					{
						result = f;
					}
					else
					{
						break;
					}
				}
				return result;
			}

			// 取高
			for (Double f : values)
			{
				if (f >= value)
				{
					return f;
				}
			}
			return -1;
		}
		else // 从大到小
		{
			// 取低
			if (!up)
			{
				for (Double f : values)
				{
					if (f <= value)
					{
						return f;
					}
				}
				return -1;
			}
			else// 取高
			{
				double result = -1;
				for (Double f : values)
				{
					if (f >= value)
					{
						result = f;
					}
					else
					{
						break;
					}
				}
				return result;
			}
		}
	}
	
	public static double binarySearch(List<Double> list, double findElem)
	{
		int size=list.size();
		
		int low = 0;
		int high = size - 1;
		int mid;
		while (low <= high)
		{
			mid = (low + high) / 2;
			double midValue = list.get(mid);
			if (findElem < midValue)
			{
				if (mid > 0 && findElem > list.get(mid - 1))
				{
					return list.get(mid);
				}
				high = mid - 1;
			}
			if (findElem > midValue)
			{
				if (mid + 1 < size && findElem < list.get(mid + 1))
				{
					return list.get(mid+1);
				}
				
				low = mid + 1;
			}
			if (midValue == findElem)
			{
				return list.get(mid);
			}
		}
		return -1;
	}

	public static void main(String[] args)
	{
		List<Double> list = new ArrayList<>();
		for (double i = 10; i <= 7500; i = i + 20)
		{
			list.add(i);
		}
		
		List<Double> list1 = new ArrayList<>();
		for (double i = 0; i < 50000; i++)
		{
			list1.add(750000 * Math.random());
		}
		
		long s = System.currentTimeMillis();
		Map<Double, List<Double>> map = LambdaUtils.groupby(list1, x -> {
			Double r = binarySearch(list, x);
			return r;
		});
		System.out.println(System.currentTimeMillis() - s);
		
//		s = System.currentTimeMillis();
//		Map<Double, List<Double>> map1 = LambdaUtils.groupby(list1, x -> {
//			Double r = key(list, x,false);
//			return r;
//		});
//		System.out.println(System.currentTimeMillis() - s);
		System.out.println(list);
	}
	
}
