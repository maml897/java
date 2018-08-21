package test.java.utils.concurrent.lock;

import java.util.concurrent.TimeUnit;

/**
 * 此时线程t1正在运行，不会收到中断提醒。但是 此线程的 “打扰标志”会被设置， 可以通过isInterrupted()查看并 作出处理
 * @author maml
 */
public class TestReentrantLock21
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread t1 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int i = 1;
				while (true)
				{
					i = i + 1;
					if (i % 10000 == 0)
					{
						System.out.println(i + "==" + Thread.currentThread().isInterrupted());
					}

				}
			}
		});
		t1.start();

		TimeUnit.SECONDS.sleep(5);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				t1.interrupt();
			}
		}).start();

	}
}
