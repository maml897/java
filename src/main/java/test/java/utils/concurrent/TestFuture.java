package test.java.utils.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestFuture
{
	public static void main(String[] args)
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		executor.shutdown();

		try
		{
			// 这个期间我么可以做一些事情
			System.out.println("这个期间我么可以做一些事情");
			System.out.println(result.isDone());
			System.out.println("task运行结果" + result.get());// 要等结果出来才执行这里
			System.out.println(result.isDone());
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}

		System.out.println("所有任务执行完毕");
	}
}

class Task implements Callable<Integer>
{
	@Override
	public Integer call() throws Exception
	{
		Thread.sleep(3000);
		int sum = 0;
		for (int i = 0; i < 100; i++)
			sum += i;
		return sum;
	}
}