package stack;

public class StackModel
{
	private long id = 0;

	private String name = "";

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public StackModel(long id)
	{
		this.id = id;
	}

}
