package test.java.utils.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock
{
	private ReentrantLock lock = new ReentrantLock();

	public void untimed()
	{
		boolean captured = lock.tryLock();
		try
		{
			System.out.println("tryLock(): " + captured);
		}
		finally
		{
			if (captured)
				lock.unlock();
		}
	}

	public void timed()
	{
		boolean captured = false;
		try
		{
			captured = lock.tryLock(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
		try
		{
			System.out.println("tryLock(2, TimeUnit.SECONDS): " + captured);
		}
		finally
		{
			if (captured)
				lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException
	{
		final TestReentrantLock al = new TestReentrantLock();
		al.untimed(); // True -- 可以成功获得锁
		al.timed(); // True --可以成功获得锁
		// 新创建一个线程获得锁并且不释放
		new Thread()
		{
			{
				setDaemon(true);
			}

			public void run()
			{
				al.lock.lock();
				System.out.println("acquired");
				try
				{
					TimeUnit.SECONDS.sleep(3);
					System.out.println(al.lock.getQueueLength());
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

			}
		}.start();
		Thread.sleep(100);// 保证新线程能够先执行

		for (int i = 0; i < 3; i++)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					al.untimed(); // False -- 马上中断放弃
					al.timed(); // False -- 等两秒超时后中断放弃
				}
			}).start();
		}

	}
}
