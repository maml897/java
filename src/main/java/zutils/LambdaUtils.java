package zutils;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LambdaUtils
{
	
	// list 转 map，指定一个属性当key，value默认U
	public static <T, U> Map<T, U> list2map(List<U> list, Function<U, T> key)
	{
		return list2map(list, key, x -> x);
	}

	// list 转 map，指定一个属性当key，执行一个属性当value
	public static <U, T, K> Map<T, K> list2map(List<U> list, Function<U, T> key, Function<U, K> value)
	{
		return list.stream().collect(Collectors.toMap(key, value, (key1, key2) -> key2, LinkedHashMap::new));
	}

	// list抽取属性
	public static <U, T> List<T> list2list(Collection<U> list, Function<U, T> fun)
	{
		return list.stream().map(fun).collect(Collectors.toList());
	}
	
	/**
	 * map排序：
	 * @param map
	 * @param c：Entry.<Double,Long>comparingByKey().reversed()
	 * @return
	 */
	public static <T, K> Map<T, K> mapOrder(Map<T, K> map,Comparator<Entry<T, K>> c)
	{
		Map<T, K> result=map.entrySet().stream().sorted(c).collect(Collectors.toMap(x->x.getKey(), x->x.getValue(), (key1, key2) -> key2, LinkedHashMap::new));
		return result;
	}

	// list任意属性排序
	public static <T, U> void order(List<T> list, Comparator<U> c, Function<T, U> keyExtractor)
	{
		//list.stream().sorted(Comparator.comparing(keyExtractor, c));
		list.sort(Comparator.comparing(keyExtractor, c));
	}

	// 过滤
	public static <U> List<U> filter(Collection<U> list, Predicate<U> keyExtractor)
	{
		return list.stream().filter(keyExtractor).collect(Collectors.toList());
	}

	// groupby
	public static <T, U> Map<U, List<T>> groupby(List<T> list, Function<T, U> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(keyExtractor, LinkedHashMap::new, Collectors.toList()));
	}
	
	public static <T, U, K> Map<U, K> groupby(List<T> list, Function<T, U> groupExtractor, Collector<T, ?, K> c)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new,c));
	}

	public static <T, U, K> Map<U, Map<K, T>> groupby(List<T> list, Function<T, U> groupExtractor, Function<T, K> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new, Collectors.toMap(keyExtractor, x -> x, (key1, key2) -> key2, LinkedHashMap::new)));
	}

	public static <T, U, K> Map<U, Map<K, List<T>>> groupby2(List<T> list, Function<T, U> groupExtractor, Function<T, K> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new, Collectors.groupingBy(keyExtractor, LinkedHashMap::new, Collectors.toList())));
	}
	
	
}