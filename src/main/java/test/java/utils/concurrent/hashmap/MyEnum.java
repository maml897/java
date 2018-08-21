package test.java.utils.concurrent.hashmap;

public enum MyEnum
{
	A
	{
		public long test(long d)
		{
			return d;
		}
	},
	B
	{
		public long test(long d)
		{
			return d;
		}

		public long haha(long d)
		{
			System.out.println(d);
			return d;
		}
	};

	public long haha(long a)
	{
		throw new AbstractMethodError();
	}

	abstract long test(long d);

	public void normal(long duration)
	{
		System.out.println("normal");
	}
}
