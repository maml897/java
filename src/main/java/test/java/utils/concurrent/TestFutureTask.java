package test.java.utils.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/***
 * 该工具实现异步等待，类似于js中的
 * @author maml
 */
public class TestFutureTask
{
	public static void main(String[] args)
	{
		ExecutorService exec = Executors.newCachedThreadPool();

		FutureTask<String> task = new FutureTask<String>(new Callable<String>()
		{// FutrueTask的构造参数是一个Callable接口
			@Override
			public String call() throws Exception
			{
				Thread.sleep(1000 * 10);
				return Thread.currentThread().getName();// 这里可以是一个异步操作
			}
		});

		try
		{
			System.out.println("====");
			exec.execute(task);// FutureTask实际上也是一个线程
			System.out.println("==**==");
			String result = task.get();// 取得异步计算的结果，如果没有返回，就会一直阻塞等待
			System.out.printf("get:%s%n", result);

			System.out.println("*************");
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
