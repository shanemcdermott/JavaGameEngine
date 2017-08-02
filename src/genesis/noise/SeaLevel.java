package genesis.noise;

import java.util.Random;

import javagames.world.Dungeon;

public class SeaLevel extends NoiseFunction 
{
	private Dungeon d;
	public float seaHeight = 0.2f;

	public SeaLevel(Random random, float[][] heightmap, Dungeon d) 
	{
		super(random, heightmap);
		setName("Sea Level");
		this.d=d;
		// TODO Auto-generated constructor stub
	}

	public SeaLevel(Random random, float[][] heightmap, boolean[][] mask, Dungeon d) 
	{
		super(random, heightmap, mask);
		setName("Sea Level");
		this.d=d;
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public void exec() 
	{
		/*
		for(int x = 0; x < getWidth();  x++)
		{
			for(int y = 0; y < getDepth(); y++)
			{
				setHeight(x,y, seaHeight * getHeight(x,y));
			}
		}
		
		d.setHeightMap(getHeightmap());
		*/
		
		d.markOceanCells(seaHeight);
		//d.recurseFill();
	}

}
