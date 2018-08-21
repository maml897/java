package test.java.utils.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * http://janeky.iteye.com/blog/769965
 * @author maml
 */
public class TestCountDownLatch
{
	private static final int N = 10;

	public static void main(String[] args) throws InterruptedException
	{
		CountDownLatch doneSignal = new CountDownLatch(N);// 执行doneSignal.await();方法的函数，必须等到10个线程执行了 doneSignal.countDown();（也就是执行了10次）才会继续执行
		CountDownLatch startSignal = new CountDownLatch(1);// 执行startSignal.await();方法的函数，必须等到1个线程执行了 startSignal.countDown();才会继续执行

		for (int i = 1; i <= N; i++)
		{
			new Thread(new Worker(i, doneSignal, startSignal)).start();// 线程启动了
		}
		System.out.println("begin------------");
		startSignal.countDown();
		doneSignal.await();// 必须doneSignal.countDown();执行了十次才会往下执行
		System.out.println("Ok");

	}

	static class Worker implements Runnable
	{
		private final CountDownLatch doneSignal;

		private final CountDownLatch startSignal;

		private int beginIndex;

		Worker(int beginIndex, CountDownLatch doneSignal, CountDownLatch startSignal)
		{
			this.startSignal = startSignal;
			this.beginIndex = beginIndex;
			this.doneSignal = doneSignal;
		}

		public void run()
		{
			try
			{
				startSignal.await(); // 必须startSignal.countDown();执行了1次才会继续执行
				beginIndex = (beginIndex - 1) * 10 + 1;
				for (int i = beginIndex; i <= beginIndex + 10; i++)
				{
					System.out.println(i);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
			{
				doneSignal.countDown();
			}
		}
	}
}