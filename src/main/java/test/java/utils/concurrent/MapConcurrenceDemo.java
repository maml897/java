package test.java.utils.concurrent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// http://blog.sina.com.cn/s/blog_6145ed810100yvng.html
public class MapConcurrenceDemo
{
	private static Map<String, String> map = new HashMap<String, String>();// HashMap出错

	static
	{
		for (int i = 0; i < 1000; i++)
		{
			String str = String.valueOf(i);
			map.put(str, str);
		}
	}

	public static void main(String[] args)
	{
		new Thread(new MapListThread(map)).start();
		new Thread(new MapRemoveThread(map)).start();
	}
}

class MapListThread implements Runnable
{
	private Map<String, String> map;

	public MapListThread(Map<String, String> map)
	{
		this.map = map;
	}

	public void run()
	{
		Iterator<String> iter = map.values().iterator();
		while (iter.hasNext())
		{
			iter.next();
			try
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}

class MapRemoveThread implements Runnable
{
	private Map<String, String> map;

	public MapRemoveThread(Map<String, String> map)
	{
		this.map = map;
	}

	public void run()
	{
		for (int i = 200; i < 900; i++)
		{
			String str = String.valueOf(i);
			map.remove(str);
			try
			{
				Thread.sleep(5);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Remove OK. map.size:" + map.size());
	}
}
