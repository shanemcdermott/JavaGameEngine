package genesis.noise;

public class DiamondSquare 
{

	private float min;
	private float max;
	private float[][] heightmap;
			
	public DiamondSquare(float[][] heightmap)
	{
		this.heightmap = heightmap;
	}
	
	public void exec(int x0, int y0, int x3, int y3)
	{
		min = max = heightmap[0][0];
		
		recurse(heightmap,x0,y0,x3,y3);
		normalize();
	}
	
	public void exec()
	{
		exec(0,0,heightmap.length-1, heightmap[0].length-1);
	}
	private void normalize()
	{
		float diff = max-min;
		for(int x = 0; x < heightmap.length; x++)
		{
			for(int y = 0; y< heightmap[x].length; y++)
			{
				heightmap[x][y] = (heightmap[x][y]-min)/diff;
			}
		}
	}
	
    private void diamond(float[][] heightmap, int x0, int y0, int x3, int y3, int midX, int midY) {
        
        float diff = x3 - x0;
        diff /= 2;
        
        heightmap[x0][midY] = (float) ((heightmap[x0][y0] + heightmap[x0][y3])/2 + diff * (Math.random() * 2 - 1));
        heightmap[midX][y0] = (float) ((heightmap[x0][y0] + heightmap[x3][y0])/2 + diff * (Math.random() * 2 - 1));
        heightmap[x3][midY] = (float) ((heightmap[x3][y0] + heightmap[x3][y3])/2 + diff * (Math.random() * 2 - 1));
        heightmap[midX][y3] = (float) ((heightmap[x0][y3] + heightmap[x3][y3])/2 + diff * (Math.random() * 2 - 1));
        
        updateMinMax(heightmap[x0][midY], heightmap[midX][y0], heightmap[x3][midY],heightmap[midX][y3]);
        
        recurse(heightmap, x0, y0, midX, midY);
        recurse(heightmap, x0, midY, midX, y3);
        recurse(heightmap, midX, y0, x3, midY);
        recurse(heightmap, midX, midY, x3, y3);
        
    }
	
    private void updateMinMax(float... values)
    {
    	for(float v : values)
    	{
    		max = Math.max(max, v);
    		min = Math.min(min, v);
    	}
    }
    
    private void recurse(float[][] heightmap, int x0, int y0,  int x3, int y3) {
        
        
        if(Math.abs(x0 - x3) <= 1 || Math.abs(y0 - y3) <= 1)
            return;
        
        float diff = x3 - x0;
        
        int midX = (x0 + x3)/2;
        int midY = (y0 + y3)/2;
        
        float rand = (float) ((Math.random() * 2) - 1);
        float offset = rand * diff;
        
        float startHeight = (heightmap[x0][y0] + heightmap[x3][y3] ) /2.0f;
        
        heightmap[midX][midY] = startHeight + offset;
        
        updateMinMax(heightmap[midX][midY]);
        
        diamond(heightmap, x0, y0, x3, y3, midX, midY);
                
        
        
    }


}
