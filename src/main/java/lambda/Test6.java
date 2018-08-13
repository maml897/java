package lambda;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Test6
{
	public static void main(String[] args)
	{
		TestInterface t = (a, b, c) -> a + b;
		System.out.println(t.operate(1, 2, 3));

		TestInterface t1 = (a, b, c) -> a * b;
		System.out.println(t1.operate(1, 2, 3));

		Function f1 = (a) -> a;
		Consumer c1 = (a) -> System.out.println(a);
		Supplier s1 = () -> "a";
		Predicate p1 = (a) -> true;

		Runnable r = () -> System.out.println("---");

		new Thread(r).start();
		new Thread(() -> System.out.println("---")).start();
	}
}

interface TestInterface
{
	public int operate(int a, int b, int c);
}