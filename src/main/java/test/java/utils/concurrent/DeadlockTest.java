package test.java.utils.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 死锁测试，下一个测试是解决方案 http://www.cnblogs.com/duanjie/archive/2012/05/05/2489177.html
 * @author maml
 */
public class DeadlockTest
{
	public static byte[] locker1 = new byte[0];

	public static byte[] locker2 = new byte[0];

	public static void main(String[] args)
	{
		ExecutorService executor = Executors.newFixedThreadPool(100);
		for (int i = 0; i < 50; i++)
		{
			executor.execute(new DeadlockTask1());
			executor.execute(new DeadlockTask2());
		}
		executor.shutdown();
		while (!executor.isTerminated())
		{

		}
		System.exit(0);
	}

	private static class DeadlockTask1 implements Runnable
	{
		public void run()
		{
			synchronized (locker1)
			{
				System.out.println(" DeadlockTask1 获得锁 locker1 没有死锁！");
				synchronized (locker2)
				{
					System.out.println(" DeadlockTask1 获得锁 locker2 没有死锁！");
				}
			}
		}
	}

	private static class DeadlockTask2 implements Runnable
	{
		public void run()
		{
			synchronized (locker2)
			{
				System.out.println(" DeadlockTask2 获得锁 locker2 没有死锁！");
				synchronized (locker1)
				{
					System.out.println(" DeadlockTask2 获得锁 locker1 没有死锁！");
				}
			}
		}
	}
}
