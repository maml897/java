package lambda;

public class Model1
{
	private long id1;

	private String name1;


	public long getId1()
	{
		return id1;
	}

	public void setId1(long id1)
	{
		this.id1 = id1;
	}

	public String getName1()
	{
		return name1;
	}

	public void setName1(String name1)
	{
		this.name1 = name1;
	}


	public Model1(long id, String name)
	{
		super();
		this.id1 = id;
		this.name1 = name;
	}

}
