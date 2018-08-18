package zutils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {
	/**
	 * 返回values 中距离value 最近的值，可以上取，也可以下取
	 * 类似浮点数上取值，下取值
	 * @param value：
	 * @param values：排好序的value，从小到大
	 * @return
	 */
	public static float key(Collection<Float> values, float value, boolean... flag) {

		if (flag != null && flag.length > 0 && flag[0]) {// 取高
			float result = -1;
			for (Float f : values) {
				if (f <= value) {
					result = f;
				}
			}
			return result;

		} else {
			for (Float f : values) {
				if (f >= value) {
					return f;
				}
			}
			return -1;
		}
	}
	
	/**
	 * 首字母小写
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirst(String s)
	{
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	/**
	 * 获取一个列表中的数据，如果列表长度不够返回默认值
	 * @param list
	 * @param i
	 * @param defaultObject
	 * @return
	 */
	@SafeVarargs
	public static <U> U getListIndex(List<U> list, int i, U... us)
	{
		if (list.size() > i)
		{
			return list.get(i);
		}
		if (us.length > 0)
		{
			return us[0];
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		List<Float> list = Arrays.asList(0f, 5f, 10f, 15f, 20f);

		System.out.println(key(list, 5.2f, true));
	}
}
