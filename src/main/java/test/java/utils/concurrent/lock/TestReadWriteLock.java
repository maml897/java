package test.java.utils.concurrent.lock;

public class TestReadWriteLock
{
	public static void main(String[] args)
	{
		LockObj test = new LockObj();

		for (int i = 0; i < 10; i++)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						test.read();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}

		for (int i = 0; i < 10; i++)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						test.write();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

}
