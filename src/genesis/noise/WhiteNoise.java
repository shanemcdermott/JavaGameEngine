package genesis.noise;

import java.util.Random;

public class WhiteNoise extends NoiseFunction {


	public WhiteNoise(Random random, float[][] heightmap)
	{
		super(random, heightmap);
		setName("White Noise");
	}
	
	public WhiteNoise(Random random, float[][] heightmap, boolean[][] mask) {
		super(random, heightmap, mask);
		setName("White Noise");
	}

	@Override
	public void exec() 
	{
		for(int x = 0; x < getWidth();  x++)
		{
			for(int y = 0; y < getDepth(); y++)
			{
				setHeight(x,y,randy.nextFloat());
			}
		}

	}

}
