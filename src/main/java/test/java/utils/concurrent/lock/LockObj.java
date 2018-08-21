package test.java.utils.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockObj
{
	ReadWriteLock lock = new ReentrantReadWriteLock();

	Lock read = lock.readLock();

	Lock write = lock.writeLock();

	public void read() throws InterruptedException
	{
		read.lock();
		try
		{
			System.out.println(Thread.currentThread().getName() + "read开始");
			TimeUnit.SECONDS.sleep(5);
		}
		catch (Exception e)
		{
		}
		finally
		{
			System.out.println(Thread.currentThread().getName() + "read 结束");
			read.unlock();
		}
	}

	public synchronized void readOld() throws InterruptedException
	{
		try
		{
			System.out.println(Thread.currentThread().getName() + "read开始");
			TimeUnit.SECONDS.sleep(5);
		}
		catch (Exception e)
		{
		}
		finally
		{
			System.out.println(Thread.currentThread().getName() + "read 结束");
		}
	}

	public void write() throws InterruptedException
	{
		write.lock();
		try
		{
			System.out.println(Thread.currentThread().getName() + "write开始");
			TimeUnit.SECONDS.sleep(5);
		}
		catch (Exception e)
		{
		}
		finally
		{
			System.out.println(Thread.currentThread().getName() + "write 结束");
			write.unlock();
		}
	}

	public synchronized void writeOld() throws InterruptedException
	{
		try
		{
			System.out.println(Thread.currentThread().getName() + "write开始");
			TimeUnit.SECONDS.sleep(5);
		}
		catch (Exception e)
		{
		}
		finally
		{
			System.out.println(Thread.currentThread().getName() + "write 结束");
		}
	}
}
