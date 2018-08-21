package test.java.utils.concurrent.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestHashMap
{

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		Map<String, String> map = new HashMap<String, String>();

		ExecutorService es = Executors.newCachedThreadPool();
		final CyclicBarrier barrier = new CyclicBarrier(10000, new Runnable()// 等到执行4次 barrier.await();之后，执行该方法
		{
			@Override
			public void run()
			{
				System.out.println("大家都到齐了，开始happy去");

				for (String s : map.keySet())
				{
					String n = s.split("-")[3];
					String m = map.get(s).split("-")[3];
					if (!n.equals(m))
					{
						System.out.println(s + "==" + map.get(s));
					}
				}
			}
		});

		for (int i = 0; i < 10000; i++)
		{
			es.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						map.put(Thread.currentThread().getName(), Thread.currentThread().getName());
						barrier.await();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					catch (BrokenBarrierException e)
					{
						e.printStackTrace();
					}
				}
			});
		}

		es.shutdown();
	}

}
