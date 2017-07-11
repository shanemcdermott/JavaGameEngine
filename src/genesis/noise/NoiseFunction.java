package genesis.noise;

import java.awt.Point;
import java.util.Random;

import javagames.util.Vector2f;
import javagames.util.geom.BoundingPoly;

public abstract class NoiseFunction 
{
	protected float min;
	protected float max;
	protected Random randy;
	protected String name;


	private float[][] heightmap;
	/*Any true value is ignored*/
	private boolean[][] mask;
	
	public NoiseFunction(Random random, float[][] heightmap)
	{
		this.setRandom(random);
		this.heightmap=heightmap;
		this.mask = new boolean[heightmap.length][heightmap[0].length];
		this.randy = new Random();
		for(int x=0; x < heightmap.length; x++)
			for(int y=0; y < heightmap[0].length; y++)
				mask[x][y]=false;
		
	}

	public NoiseFunction(Random random, float[][] heightmap, boolean[][] mask)
	{
		this.setRandom(random);
		this.heightmap=heightmap;
		this.mask = mask;
	}
	
	
	
	public abstract void exec();
	
	public void setRandom(Random random)
	{
		randy = random;
	}
	
	public int getWidth()
	{
		return heightmap.length;
	}
	
	public int getDepth()
	{
		return heightmap[0].length;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addMask(BoundingPoly polyMask)
	{
		for(int x = 0; x < getWidth(); x++)
			for(int y = 0; y < getDepth(); y++)
				if(polyMask.contains(new Vector2f(x,y)))
					mask[x][y]=true;
	}
	
	public void setIgnored(boolean[][] mask)
	{
		this.mask = mask;
	}
	
	public void setIgnored(boolean bIgnored, Point... points)
	{
		for(Point p : points)
			mask[p.x][p.y]=bIgnored;
	}
	
	public float getHeight(int x, int y)
	{
		return heightmap[x][y];
	}
	
	public void setHeight(int x, int y, float value)
	{
		if(isValid(x,y))
		{
			heightmap[x][y] = value;
			updateMinMax(value);
		}
	}
	
	public float[][] getHeightmap() {
		return heightmap;
	}

	public void setHeightmap(float[][] heightmap) {
		this.heightmap = heightmap;
	}
	
	public boolean isIgnored(int x, int y)
	{
		return mask[x][y];
	}

	public boolean isInBounds(int x, int y)
	{
		return x>= 0 && x < heightmap.length && y >= 0 && y < heightmap[x].length;
	}
	
	public boolean isValid(int x, int y)
	{
		return isInBounds(x,y) && !isIgnored(x,y);
	}
	
	protected void resetMinMax()
	{
		min = max = heightmap[0][0];
	}
	
	protected void normalize()
	{
		float diff = max-min;
		for(int x = 0; x < heightmap.length; x++)
		{
			for(int y = 0; y< heightmap[x].length; y++)
			{
				if(!isValid(x,y)) continue;
				
				heightmap[x][y] = (heightmap[x][y]-min)/diff;
			}
		}
	}
	
    protected void updateMinMax(float... values)
    {
    	for(float v : values)
    	{
    		max = Math.max(max, v);
    		min = Math.min(min, v);
    	}
    }
}
