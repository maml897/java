package test.java.utils.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/***
 * 实现的功能就类似厕所有5个坑，假如有10个人要上厕所，那么同时只能有多少个人去上厕所呢？同时只能有5个人能够占用，当5个人中
 * 的任何一个人让开后，其中等待的另外5个人中又有一个人可以占用了。另外等待的5个人中可以是随机获得优先机会，也可以是按照先来后到的顺序获得机会，这取决于构造Semaphore对象时传入的参数选项。单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 * @author maml
 * @see http://my.oschina.net/cloudcoder/blog/362974
 * @see http://www.cnblogs.com/whgw/archive/2011/09/29/2195555.html Semaphore
 * @see http://www.cnblogs.com/duanjie/archive/2012/05/05/2489177.html 以一个停车场运作为例。为了简单起见，假设停车场只有三个车位，一开始三个车位都是空的。这时如果同时来了五辆车，看门人允许其中三辆不受阻碍的进入，然后放下车拦，剩下的车则必须在入口等待，此后来的车也都不得不在入口处等待。这时，有一辆车离开停车场，看门人得知后，
 *      打开车拦，放入一辆，如果又离开两辆，则又可以放入两辆，如此往复。 在这个停车场系统中，车位是公共资源，每辆车好比一个线程，看门人起的就是信号量的作用。 更进一步，信号量的特性如下：信号量是一个非负整数（车位数），所有通过它的线程（车辆）都会将该整数减一（通过它当然是为了使用资源），当该整数值为零时，所有试图通过它的线程都将处于等待状态。在信号量上我们定义两种操作： Wait（等待）
 *      和 Release（释放）。 当一个线程调用Wait（等待）操作时，它要么通过然后将信号量减一，要么一直等下去，直到信号量大于一或超时。Release（释放）实际上是在信号量上执行加操作，对应于车辆离开停车场，该操作之所以叫做“释放”是因为加操作实际上是释放了由信号量守护的资源。
 */
public class TestSemaphore
{

	private static Account _account = new Account();

	public static void main(String[] args)
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++)
		{
			executor.execute(new depositTask());
		}
		executor.shutdown();
		while (!executor.isTerminated())
		{
		}
		System.out.println("任务完成");
		System.exit(0);
	}

	private static class depositTask implements Runnable
	{
		@Override
		public void run()
		{
			_account.deposit(1);

		}
	}

	public static class Account
	{
		private static Semaphore _semaphore = new Semaphore(1, true);// 改成2试试

		private int _menoeyy = 0;

		private void deposit(int monery)
		{
			try
			{
				_semaphore.acquire();// 多个线程访问同一个对象，只能有一个线程在访问，其他的线程在等待
				_menoeyy += monery;
				Thread.sleep(50);
				System.out.println("当前账户与额为：￥" + _menoeyy);
			}
			catch (InterruptedException ex)
			{

			}
			finally
			{
				_semaphore.release();// 释放之后，其他的人才能用
			}

		}

	}
}