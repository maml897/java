package lambda;

import java.util.function.Function;

public class Test2
{
	// 静态内部类
	private static class Student
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

	public static void main(String[] args)
	{
		/* 用户注册输入一个名字tom */
		String name = "tom";

		/* 使用用户的输入的名字创建一个对象 */
		Function<String, Student> f1 = (s) -> new Student(s);
		// 注意上面的代码也可以写出这样，引用类中的构造器
		// Function<String, Student> f1 =Student::new;
		Student stu1 = f1.apply(name);
		System.out.println(stu1.getName());

		/* 需求改变,使用name创建Student对象之前需要给name加一个前缀 */
		Function<String, String> before = (s) -> "briup_" + s;
		// 表示f1调用之前先执行before对象的方法,把before对象的方法返回结果作为f1对象方法的参数
		Student stu2 = f1.compose(before).apply(name);
		System.out.println(stu2.getName());

		/* 获得创建好的对象中的名字的长度 */
		Function<Student, Integer> after = (stu) -> stu.getName().length();
		// before先调用方法,结果作为参数传给f1来调用方法,结果再作为参数传给after,结果就是我们接收的数据
		int len = f1.compose(before).andThen(after).apply(name);
		System.out.println(len);

	}

	public static void test(Function<String, String> fun)
	{

	}


}
