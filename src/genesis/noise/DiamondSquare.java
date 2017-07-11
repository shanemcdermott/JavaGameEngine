package genesis.noise;

import java.util.Random;

public class DiamondSquare extends NoiseFunction
{

	public DiamondSquare(Random random, float[][] heightmap)
	{
		super(random, heightmap);
		setName("Diamond Square");
	}
	
	public DiamondSquare(Random random, float[][] heightmap, boolean[][] mask) {
		super(random, heightmap, mask);
		setName("Diamond Square");
	}

	
	
	public void exec(int x0, int y0, int x3, int y3)
	{
		resetMinMax();
		recurse(x0,y0,x3,y3);
		normalize();
	}
	
	@Override
	public void exec()
	{
		exec(0,0,getWidth()-1, getDepth()-1);
	}
	
	
    private void diamond(int x0, int y0, int x3, int y3, int midX, int midY)
    {
        
        float diff = x3 - x0;
        diff /= 2;
        setHeight(x0,midY, (float) ((getHeight(x0,y0) + getHeight(x0,y3))/2 + diff * (Math.random() * 2 - 1)));
        setHeight(midX,y0, (float) ((getHeight(x0,y0) + getHeight(x3,y0))/2 + diff * (Math.random() * 2 - 1)));
        setHeight(x3,midY, (float) ((getHeight(x3,y0) + getHeight(x3,y3))/2 + diff * (Math.random() * 2 - 1)));
        setHeight(midX,y3, (float) ((getHeight(x0,y3) + getHeight(x3,y3))/2 + diff * (Math.random() * 2 - 1)));
        
        
        recurse(x0, y0, midX, midY);
        recurse(x0, midY, midX, y3);
        recurse(midX, y0, x3, midY);
        recurse(midX, midY, x3, y3);
        
    }
    
    private void recurse(int x0, int y0,  int x3, int y3) 
    {
        if(Math.abs(x0 - x3) <= 1 || Math.abs(y0 - y3) <= 1)
            return;
        
        float diff = x3 - x0;
        
        int midX = (x0 + x3)/2;
        int midY = (y0 + y3)/2;
        
        float rand = (float) ((Math.random() * 2) - 1);
        float offset = rand * diff;
        
        float startHeight = (getHeight(x0,y0) + getHeight(x3,y3) ) /2.0f;
        
        setHeight(midX,midY, startHeight + offset);
        
        diamond(x0, y0, x3, y3, midX, midY);
                        
    }


}
