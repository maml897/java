package test.java.utils.concurrent.hashmap;

import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 如果你只调用get（），或只调用put（）时，ConcurrentHashMap是线程安全的。
/***
 * 但是，在你调用完get后，调用put之前，如果有另外一个线程调用了map.put(name, x)，你再去执行map.put(name,x)，就很可能把前面的操作结果覆盖掉了。所以，即使在线程安全的情况下，你还是有可能违反原子操作的规则。
 * @author maml
 */
public class TestConcurrentHashMap
{
	static Map<Integer, Integer> map = new ConcurrentHashMap<Integer, Integer>();// ConcurrentHashMap

	static
	{
		map.put(0, 0);
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		ExecutorService es = Executors.newCachedThreadPool();
		final CyclicBarrier barrier = new CyclicBarrier(2000, new Runnable()// 等到执行4次 barrier.await();之后，执行该方法
		{
			@Override
			public void run()
			{
				System.out.println("大家都到齐了，开始happy去" + map.get(0));
			}
		});
		for (int i = 0; i < 2000; i++)
		{
			es.execute(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						// 直接使用put还是不行啊
						Integer oldValue, newValue;
						while (true)
						{
							oldValue = map.get(0);
							newValue = oldValue + 10;
							if (map.replace(0, oldValue, newValue))
							{
								break;
							}
						}
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
