package test.java.utils.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class TestCallable
{
	public static void main(String[] args)
	{

		Callable c = Executors.callable(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("==");
			}
		});

		System.out.println(c);
	}
}
