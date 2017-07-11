package genesis.noise;

import java.util.Random;

public class Smoothing extends NoiseFunction 
{

	private int radius;
	private float[][] buffer;
	
	public Smoothing(Random random, float[][] heightmap, boolean[][] mask) 
	{
		super(random, heightmap, mask);
		setName("Smoothing");
		buffer = new float[getWidth()][getDepth()];
	}

	public Smoothing(Random random, float[][] heightmap) 
	{
		super(random, heightmap);
		setName("Smoothing");
		buffer = new float[getWidth()][getDepth()];
	}

	@Override
	public void exec() 
	{
		resetMinMax();
		radius = 1;
		for(int x = 0; x < getWidth(); x++)
		{
			for(int y = 0; y < getDepth(); y++)
			{
				smooth(x,y);
			}
		}

		copyBuffer();
	}
	
	private void smooth(int x, int y)
	{
		int sum = 0;
		float total = 0.f;
		for(int xx = x - radius; xx <= x+radius; xx++)
		{
			for(int yy = y - radius; yy <= y+radius; yy++)
			{
				if(isInBounds(xx,yy))
				{
					total+= getHeight(xx,yy);
					sum++;
				}
			}
		}
		buffer[x][y] = total / sum;
	}

	
	private void copyBuffer()
	{
		for(int x = 0; x < getWidth(); x++)
		{
			for(int y = 0; y < getDepth(); y++)
			{
				setHeight(x,y,buffer[x][y]);
			}
		}
	}
}
