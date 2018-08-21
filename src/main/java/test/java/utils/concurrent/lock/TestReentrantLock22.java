package test.java.utils.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lockInterruptibly 与 lock比较区别在于 lockInterruptibly 优先考虑响应中断，而不是响应锁定的普通获取或重入获取 http://blog.csdn.net/woaieillen/article/details/8046876
 * @author maml
 */
public class TestReentrantLock22
{
	public static void main(String[] args) throws InterruptedException
	{
		Thread i1 = new Thread(new RunIt3());
		Thread i2 = new Thread(new RunIt3());
		i1.start();
		i2.start();
		
		//用lock.lock();，虽然执行了线程的中断，但是线程目前一直是等待状态所以也执行不了中断，要等到线程获取到锁开始执行的时候才能执行中断
		//如果用lock.lockInterruptibly(); 就不一样了，线程虽然是等待获取锁，但是可以理解执行中断
		i2.interrupt();

	}
}

class RunIt3 implements Runnable
{

	private static Lock lock = new ReentrantLock();

	public void run()
	{
		try
		{
			// ---------------------------------a
			lock.lock();
			// lock.lockInterruptibly();

			System.out.println(Thread.currentThread().getName() + " running");
			TimeUnit.SECONDS.sleep(20);
			lock.unlock();
			System.out.println(Thread.currentThread().getName() + " finished");
		}
		catch (InterruptedException e)
		{
			System.out.println(Thread.currentThread().getName() + " interrupted");

		}

	}
}

/**
如果a处 是lock.lock(); 
输出 
Thread-0 running 
(这里休眠了20s) 
Thread-0 finished 
Thread-1 running 
Thread-1 interrupted 


============================ 

如果a处是lock.lockInterruptibly() 
Thread-0 running 
Thread-1 interrupted 
(这里休眠了20s) 
Thread-0 finished 
========================= 

如果a处是lock.tryLock(); 


Thread-1 running 
Thread-0 running 
(这里休眠了20s) 
Thread-1 finished 
Exception in thread "Thread-0" java.lang.IllegalMonitorStateException 
at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(Unknown Source) 
at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(Unknown Source) 
at java.util.concurrent.locks.ReentrantLock.unlock(Unknown Source) 
at test.RunIt3.run(TTTT.java:32) 
at java.lang.Thread.run(Unknown Source) 


========================= 

如果a处是lock.tryLock(10,TimeUnit.SECONDS); 

Thread-0 running 
(这里休眠了10s) 
Thread-1 running 

(这里休眠了10s) 
Thread-0 finished 
(这里休眠了10s) 
Exception in thread "Thread-1" java.lang.IllegalMonitorStateException 
at java.util.concurrent.locks.ReentrantLock$Sync.tryRelease(Unknown Source) 
at java.util.concurrent.locks.AbstractQueuedSynchronizer.release(Unknown Source) 
at java.util.concurrent.locks.ReentrantLock.unlock(Unknown Source) 
at test.RunIt3.run(TTTT.java:32) 
at java.lang.Thread.run(Unknown Source) 
*/