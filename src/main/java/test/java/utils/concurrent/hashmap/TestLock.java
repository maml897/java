package test.java.utils.concurrent.hashmap;

import java.util.concurrent.TimeUnit;

public class TestLock
{
	public static void main(String[] args) throws InterruptedException
	{
		System.out.println(TimeUnit.DAYS.toMicros(10));

		MyEnum.B.haha(10);
		MyEnum.B.normal(20);

		System.out.println(TimeUnit.HOURS.toDays(1));
		System.out.println(TimeUnit.HOURS.toHours(1));
		System.out.println(TimeUnit.HOURS.toMinutes(1));
		System.out.println(TimeUnit.HOURS.toSeconds(1));
		System.out.println(TimeUnit.HOURS.toMillis(1));
		System.out.println(TimeUnit.HOURS.toMicros(1));
		System.out.println(TimeUnit.HOURS.toNanos(1));

		System.out.println(TimeUnit.HOURS.convert(1, TimeUnit.DAYS));// 一天等于多少小时
		TimeUnit.HOURS.timedJoin(new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println(456);
			}
		}), 0);

		// TimeUnit.HOURS.timedWait(obj, timeout);
	}
}
