package genesis.noise;

import java.awt.Point;

public class Erosion 
{

	//Number of cells to check for local min/max
	private int radius;
	//percentage of elevation to shift
	private float strength;
	
	private float[][] heightmap;
	private float[][] duplicate;
				
	public Erosion(float[][] heightmap)
	{
		this.heightmap = heightmap;
		duplicate = new float[heightmap.length][heightmap[0].length];
		for(int x = 0; x < heightmap.length; x++)
		{
			for(int y =0; y < heightmap[x].length; y++)
				duplicate[x][y]=heightmap[x][y];
		}
	}
	
	public void exec()
	{
		exec(2,2);
	}
	
	public void exec(int radius)
	{
		exec(radius,1);
	}
		
	public void exec(int radius, int numItr)
	{
		this.strength = 0.25f;
		this.radius = radius;
		for(int i = 0; i < numItr; i++)
		{
			erode();
		}
	}
	
	private void erode()
	{
		for(int x = 0; x < heightmap.length; x++)
		{
			for(int y = 0; y < heightmap[x].length; y++)
			{
				localErode(localMin(x,y), localMax(x,y));
				update();
			}
		}
	}
	
	private void localErode(Point min, Point max)
	{
		duplicate[max.x][max.y] = heightmap[max.x][max.y];
		duplicate[min.x][min.y] = heightmap[min.x][min.y];
		float sub = strength * (heightmap[max.x][max.y]-heightmap[min.x][min.y]);
		duplicate[max.x][max.y]-=sub;
		duplicate[min.x][min.y]+=sub;
	}
	
	private Point localMin(int x, int y)
	{
		Point p = new Point(x,y);
		float h = heightmap[x][y];
		
		for(int r = -radius; r <= radius && x+r < heightmap.length; r++)
		{
			if(x + r < 0) continue;
			for(int c = -radius; c <= radius && y+c < heightmap.length; c++)
			{
				if(y + c < 0) continue;
				if(heightmap[x+r][y+c]<h)
				{
					h=heightmap[x+r][y+c];
					p.x=x+r;
					p.y=y+c;
				}
			}
		}
		
		return p;
	}
	
	private Point localMax(int x, int y)
	{
		Point p = new Point(x,y);
		float h = heightmap[x][y];
		
		for(int r = -radius; r <= radius && x+r < heightmap.length; r++)
		{
			if(x + r < 0) continue;
			for(int c = -radius; c <= radius && y+c < heightmap.length; c++)
			{
				if(y + c < 0) continue;
				
				if(heightmap[x+r][y+c]>h)
				{
					h=heightmap[x+r][y+c];
					p.x=x+r;
					p.y=y+c;
				}
			}
		}
		
		return p;
	}
	
	private void update()
	{
		for(int x = 0 ; x < heightmap.length; x++)
		{
			for(int y = 0; y < heightmap.length; y++)
			{
				heightmap[x][y] = duplicate[x][y];
			}
		}
	}
}
