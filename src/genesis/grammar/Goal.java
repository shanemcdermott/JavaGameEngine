package genesis.grammar;

public enum Goal 
{
	BOSS_FIGHT(1.f);
	
	
	Goal(float cr)
	{
		this.cr = cr;
	}
	//Challenge Rating
	private final float cr;
	
	public float challengeRating()
	{
		return cr;
	}

}
