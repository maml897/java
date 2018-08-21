package test.java.utils.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestFuture1
{
	public static void main(String[] args) throws TimeoutException
	{
		ExecutorService threadPool = Executors.newSingleThreadExecutor();

		Future<String> future = threadPool.submit(new Callable<String>()
		{
			@Override
			public String call() throws Exception
			{
				Thread.sleep(3000);
				return "future";
			}

		});
		try
		{
			System.out.println("waiting...");
			System.out.println(future.get(20000, TimeUnit.MILLISECONDS));
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
	}
}
