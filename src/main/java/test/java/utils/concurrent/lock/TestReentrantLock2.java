package test.java.utils.concurrent.lock;

import java.util.concurrent.TimeUnit;

/**
 * 先启动t1线程，t1一直在执行，如果另外一个线程t2，条用t1线程的interrupt方法， 如果t1线程处于sleep或wait,join则会立即中断，被唤醒并被要求处理InterruptedException， 如果t1此线程在运行中， 则不会收到提醒。但是 此线程的 “打扰标志”会被设置， 可以通过isInterrupted()查看并 作出处理。
 * @author maml
 */
public class TestReentrantLock2
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread t1 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					TimeUnit.SECONDS.sleep(60);
				}
				catch (InterruptedException e)
				{
					System.out.println("被中断了");
					// e.printStackTrace();
				}
			}
		});
		t1.start();

		TimeUnit.SECONDS.sleep(5);
		Thread t2 = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				t1.interrupt();
			}
		});
		t2.start();
	}
}
