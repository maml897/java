package test.java.utils.concurrent;

import java.util.concurrent.TimeUnit;

public class TestTimeUnit
{
	public static void main(String[] args) throws InterruptedException
	{
		TimeUnit.SECONDS.sleep(10);
		System.out.println("10s之后");
	}
}
