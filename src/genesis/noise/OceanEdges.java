package genesis.noise;

public class OceanEdges 
{
	private float max;
	private float min;
	private float seaLevel;
	private int edgeSize;
	private float[][] heightmap;
	
	public OceanEdges(float[][] heightmap)
	{
		this.heightmap = heightmap;
	}
	
	public void exec()
	{
		exec(0.2f);
	}
	
	public void exec(float seaLevel)
	{
		this.seaLevel=seaLevel;
		this.edgeSize=3;
		findMinMax();
		normalize();
	}
	
	private void findMinMax()
	{
		min=max=heightmap[0][0];
		for(int x=0; x < heightmap.length; x++)
		{
			for(int y = 0; y < heightmap[x].length; y++)
			{
				if(y>edgeSize && y < heightmap[x].length-edgeSize && x > edgeSize && x < heightmap[x].length-edgeSize) continue;
				
				min = Math.min(min,heightmap[x][y]);
				max = Math.max(max,heightmap[x][y]);
			}
			
		}
	}
	
	private void normalize()
	{
		float diff = max-min;
		for(int x=0; x < heightmap.length; x++)
		{
			for(int y = 0; y < heightmap[x].length; y++)
			{
				if(y>edgeSize && y < heightmap[x].length-edgeSize && x > edgeSize && x < heightmap[x].length-edgeSize) continue;
				
				heightmap[x][y] = seaLevel * (heightmap[x][y]-min)/diff;
			}
			
		}
	}

}
