package genesis.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Ecosystem 
{
	
	private Climate climate;
	private Biome biome;
	
	public Ecosystem()
	{
		this.climate=Climate.MILD;
		this.biome=Biome.OCEAN;
	}
	
	public Ecosystem(Climate climate, Biome biome)
	{
		this.climate=climate;
		this.biome=biome;
	}
	
	public void setClimate(Climate climate)
	{
		this.climate=climate;
	}
	
	public void setBiome(Biome biome)
	{
		this.biome=biome;
	}
	
	public Climate getClimate()
	{
		return climate;
	}

	public Biome getBiome()
	{
		return biome;
	}
	
	public BiomeMajor getMajorType()
	{
		return biome.getMajorType();
	}
	
	public Color getColor()
	{
		return biome.getColor();
	}
	
	public float getHeightMin()
	{
		return biome.getHeightMin();
	}
	
	public float getHeightMax()
	{
		return biome.getHeightMax();
	}
	
	public Biome[] getBiomeOptions(float height)
	{
		List<Biome> options = new ArrayList<Biome>();
		for(Biome b : Biome.values())
		{
			if(b.getMajorType()==BiomeMajor.DESERT) continue;
			if(b.canExistIn(height))
				options.add(b);
		}
		
		Biome[] results = new Biome[options.size()];
		results = options.toArray(results);
		return results;
		
	}
	
	public boolean canExistIn(float height)
	{
		return biome.canExistIn(height);
	}
	
	@Override
	public String toString()
	{
		return biome.toString();
	}
}
