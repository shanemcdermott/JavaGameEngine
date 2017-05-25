package kobolds.characters;

public class Health 
{
	private int current;
	private int max;
	private int temporary;
	
	public Health(int current, int max, int temporary)
	{
		this.current = current;
		this.max = max;
		this.temporary=temporary;
	}

	public int getCurrent()
	{
		return current;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getTemp()
	{
		return temporary;
	}
	
	public void setCurrent(int newCurrent)
	{
		if(newCurrent >=max)
			current = max;
		else if(newCurrent <=0)
			current = 0;
		else
			current = newCurrent;
	}
	
	public void setTemp(int temp)
	{
		temporary = temp;
	}
	
	public void reset()
	{
		temporary = 0;
		current = max;
	}
	
}
