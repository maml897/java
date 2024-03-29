package ok;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

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

	// 过滤
	public static <U> List<U> filter(Collection<U> list, Predicate<U> keyExtractor)
	{
		return list.stream().filter(keyExtractor).collect(Collectors.toList());
	}

	/**
	 * 自定义分组，可以分组-数量等
	 * @param list
	 * @param groupExtractor
	 * @param c
	 * @return
	 */
	public static <T, U, K> Map<U, K> groupby(Collection<T> list, Function<T, U> groupExtractor, Collector<T, ?, K> c)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new,c));
	}
	
	
	
	public static void main(String[] args) {
		List<User> list=new ArrayList<>();
		
		Collection<? extends GrantedAuthority> authorities=new ArrayList<>();
		User user=new User("a", "aa", authorities);
		list.add(user);
		user=new User("b", "bb", authorities);
		list.add(user);
		user=new User("d", "cc", authorities);
		list.add(user);
		user=new User("d", "dd", authorities);
		list.add(user);
		
		Map<String, List<User>> map=list.stream().collect(Collectors.groupingBy(User::getUsername));
		System.out.println(map.getClass());
	}
	
	
	/**
	 * 单层分组
	 * @param list
	 * @param keyExtractor
	 * @return
	 */
	public static <T, U> Map<U, List<T>> groupby(List<T> list, Function<T, U> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(keyExtractor, LinkedHashMap::new, Collectors.toList()));
	}
	
	/**
	 *  单层分组之后转map
	 * @param list
	 * @param groupExtractor
	 * @param keyExtractor
	 * @return
	 */
	public static <T, U, K> Map<U, Map<K, T>> groupbymap(List<T> list, Function<T, U> groupExtractor, Function<T, K> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new, Collectors.toMap(keyExtractor, x -> x, (key1, key2) -> key2, LinkedHashMap::new)));
	}
	
	/**
	 * 二层分组
	 * @param list
	 * @param groupExtractor
	 * @param keyExtractor
	 * @return
	 */
	public static <T, U, K> Map<U, Map<K, List<T>>> groupby2(List<T> list, Function<T, U> groupExtractor, Function<T, K> keyExtractor)
	{
		return list.stream().collect(Collectors.groupingBy(groupExtractor, LinkedHashMap::new, Collectors.groupingBy(keyExtractor, LinkedHashMap::new, Collectors.toList())));
	}
	
	/**
	 * 二层分组之后转map
	 * @param list
	 * @param firstKey
	 * @param secondKey
	 * @param mapKey
	 * @return
	 */
	public static <T, U, K,A> Map<U, Map<K, Map<A, T>>> groupby2map(List<T> list, Function<T, U> firstKey, Function<T, K> secondKey, Function<T,A> mapKey) {
		return list.stream().collect(Collectors.groupingBy(firstKey, LinkedHashMap::new,  Collectors.groupingBy(secondKey, LinkedHashMap::new, Collectors.toMap(mapKey, x -> x, (key1, key2) -> key2, LinkedHashMap::new))));
	}
	
	
	/**
	 * boolean分组
	 * @param list
	 * @param keyExtractor
	 * @return
	 */
	public static <T> Map<Boolean, List<T>> groupbyboolean(List<T> list, Predicate<T> keyExtractor)
	{
		return list.stream().collect(Collectors.partitioningBy(keyExtractor));
	}
	
	
	public static <T, U, X> Map<U, T> whole4group(Map<U, T> map, List<X> list, Function<X, U> fun,T defaultt)
	{
		Map<U, T> newmap=new LinkedHashMap<>();
		for(X x:list) {
			U u = fun.apply(x);
			if(map.containsKey(u)) {
				newmap.put(u, map.get(u));
			}
			else {
				newmap.put(u, defaultt);
			}
		}
		return newmap;
	}
}