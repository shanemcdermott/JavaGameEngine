
package javagames.room;

import java.awt.Point;

import genesis.world.Biome;
import genesis.world.BiomeMajor;

public class ChildEnvComponent extends ChildRoomsComponent 
{

	
	public float seaLevel;
	public float waterRatio;
	
	public ChildEnvComponent(GameRoom room) 
	{
		super(room);
		seaLevel = 0.2f;
		waterRatio = 0.3f;
	}

	public float getCurrentWaterRatio()
	{
		int waterCount=0;
		Point p = getNumRooms();
		for(int x=0;x<p.x; x++)
		{
			for(int y=0; y<p.y; y++)
			{
				if(rooms[x][y].getBiome().getMajorType()==BiomeMajor.AQUATIC)
					waterCount++;
			}
		}
		
		return waterCount / (float)(p.x + p.y);
	}
	
	public void markOceanCells(float seaLevel)
	{
		Point p = getNumRooms();
		for(int x = 0; x < p.x; x++)
		{
			for(int y= 0; y < p.y; y++)
			{
				if(rooms[x][y].getElevation()< seaLevel)
					rooms[x][y].setBiome(Biome.OCEAN);
				else if(rooms[x][y].getElevation() < seaLevel + seaLevel * 0.1)
					rooms[x][y].setBiome(Biome.INTERTIDAL);
				else
					rooms[x][y].setBiome(Biome.WETLAND);
			
			}
		}	
	}
	
}
