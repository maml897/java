package test.java.utils.concurrent.other;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecuteArch
{
	/**
	 * 该线程输出一行字符串
	 */
	public static class MyThread implements Runnable
	{
		public void run()
		{
			System.out.println("Task repeating. " + System.currentTimeMillis());
			try
			{
				Thread.sleep(1000);
				System.out.println("这里的会执行吗");
			}
			catch (InterruptedException e)
			{
				System.out.println("Task interrupted. " + System.currentTimeMillis());
			}
		}
	}

	/**
	 * 该Callable结束另一个任务
	 */
	public static class MyCallable implements Callable
	{
		private Future future;

		public MyCallable(Future future)
		{
			this.future = future;
		}

		public String call()
		{
			System.out.println("To cancell Task..." + +System.currentTimeMillis());
			this.future.cancel(true);
			return "Task cancelled!";
		}
	}

	/**
	 * @param args
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		// 产生一个ExecutorService对象，这个对象带有一个线程池，线程池的大小会根据需要调整，
		// 线程执行完任务后返回线程池，供执行下一次任务使用。
		ExecutorService cachedService = Executors.newCachedThreadPool();
		Future myThreadFuture = cachedService.submit(new MyThread());
		Future myCallableFuture = cachedService.submit(new MyCallable(myThreadFuture));
		System.out.println(myCallableFuture.get());
		System.out.println("-----------------");

		// 将Runnable任务转换成Callable任务
		Callable myThreadCallable = Executors.callable(new MyThread());
		Future myThreadCallableFuture = cachedService.submit(myThreadCallable);
		// 对于Runnable任务，转换成Callable任务后，也没有返回值
		System.out.println(myThreadCallableFuture.get());
		cachedService.shutdownNow();
		System.out.println("-----------------");

		// 产生一个ExecutorService对象，这个对象带有一个大小为poolSize的线程池，
		// 若任务数量大于poolSize，任务会被放在一个queue里顺序执行
		ExecutorService fixedService = Executors.newFixedThreadPool(2);
		fixedService.submit(new MyThread());
		fixedService.submit(new MyThread());
		// 由于线程池大小为2，所以后面的任务必须等待前面的任务执行完后才能被执行。
		myThreadFuture = fixedService.submit(new MyThread());
		myCallableFuture = fixedService.submit(new MyCallable(myThreadFuture));
		System.out.println(myCallableFuture.get());
		fixedService.shutdownNow();
		System.out.println("-----------------");

		// 产生一个ScheduledExecutorService对象，这个对象的线程池大小为poolSize，
		// 若任务数量大于poolSize，任务会在一个queue里等待执行
		ScheduledExecutorService fixedScheduledService = Executors.newScheduledThreadPool(2);
		// 新建任务1
		MyThread task1 = new MyThread();
		// 使用任务执行服务立即执行任务1，而且此后每隔2秒执行一次任务1。
		myThreadFuture = fixedScheduledService.scheduleAtFixedRate(task1, 0, 2, TimeUnit.SECONDS);
		// 新建任务2
		MyCallable task2 = new MyCallable(myThreadFuture);
		// 使用任务执行服务等待5秒后执行任务2，执行它后会将任务1关闭。
		myCallableFuture = fixedScheduledService.schedule(task2, 5, TimeUnit.SECONDS);
		System.out.println(myCallableFuture.get());
		fixedScheduledService.shutdownNow();
	}
}
