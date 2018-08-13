package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test3
{
	public static void main(String[] args)
	{
		List<Student> list = new ArrayList<>();
		list.add(new Student("hello"));

		Function<Object, Object> f1 = s -> s;
		test(list, f1);
	}

	public static void test(List<?> list, Function<Object, Object> fun)
	{
		Map<Object, Object> result3 = list.stream().collect(Collectors.toMap(fun, x -> x));
		System.out.println(result3);
	}

}

// 静态内部类
class Student
{
	private String name;

	public Student(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}