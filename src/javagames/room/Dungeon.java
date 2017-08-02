package javagames.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genesis.world.Biome;
import genesis.world.BiomeMajor;
import javagames.game.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingPoly;

public class Dungeon extends GameRoom 
{
	protected Vector2f roomSize;
	
	protected GameRoom[][] rooms;
	public float seaLevel;
	public float waterRatio;
	
	public Dungeon(String name, float size, int xRooms, int yRooms)
	{
		super(new Vector2f());
		setName(name);
		bounds = new BoundingBox(size);
		roomSize = new Vector2f(xRooms/size, yRooms/size);
		setRoomCounts(xRooms,yRooms);
		seaLevel = 0.2f;
		waterRatio = 0.3f;
	}
	
	public Dungeon(String name) 
	{
		super(new Vector2f());
		setName(name);
		bounds = new BoundingBox(2.f);
		roomSize = new Vector2f(0.2f,0.2f);
		setRoomCounts(10,10);
		seaLevel = 0.2f;
		waterRatio=0.3f;
	}

	public Dungeon(GameRoom room)
	{
		super(room.getPosition());
		setName(room.getName());
		bounds = room.getAABB();
		roomSize = new Vector2f(0.2f, 0.2f);
		setRoomCounts(10,10);
		seaLevel = 0.2f;
		waterRatio = 0.3f;
	}
	
	public void resize(Vector2f newSize)
	{
		BoundingBox aabb = getAABB();
		if(aabb == null) return;
		
		aabb.resize(newSize.x, newSize.y);
		updateRoomSize(newSize);
	}
	
	public Point getRoomIndices(Vector2f location)
	{
		Vector2f bottomLeft = location.sub(rooms[0][0].getPosition()).add(roomSize.mul(0.5f));
		bottomLeft.x /= roomSize.x;
		bottomLeft.y /= roomSize.y;
		return bottomLeft.abs().toPoint();
	}
	
	public GameRoom[][] getRooms()
	{
		return rooms;
	}
	
	
	public GameRoom getRoomAt(Vector2f location)
	{
		if(!bounds.contains(location)) return null;
		Point p = getRoomIndices(location);
		return rooms[p.x][p.y];
	}
	
	public void setRoomAt(Vector2f location, GameRoom inRoom)
	{
		if(!bounds.contains(location)) return;
		
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms.length; y++)
			{
				if(rooms[x][y].contains(location))
				{
					rooms[x][y] = inRoom;
					return;
				}
			}
		}
	}
	
	public void getHeightmap(float[][] heightmap)
	{
		for(int x =0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms.length; y++)
			{
				heightmap[x][y] = rooms[x][y].getElevation();
			}
		}
	}
	
	public boolean[][] getFlagmap(String name)
	{
		boolean[][] flagmap = new boolean[rooms.length][rooms[0].length];
		for(int x =0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms.length; y++)
			{
				flagmap[x][y] = rooms[x][y].getFlag(name);
			}
		}
		return flagmap;
	}
	
	public void addMask(String maskName, BoundingPoly polyMask)
	{
		for(int x = 0; x < rooms.length; x++)
			for(int y = 0; y < rooms[x].length; y++)
				if(polyMask.contains(rooms[x][y].getPosition()))
					rooms[x][y].setFlag(maskName, true);
	}
	
	
	public Point getNumRooms()
	{
		return new Point(rooms.length, rooms[0].length);
	}
	
	private void updateRoomSize(Vector2f dungeonBounds)
	{
		roomSize.x = dungeonBounds.x / rooms.length;
		roomSize.y = dungeonBounds.y / rooms[0].length;
	}
	
	public void setRoomCounts(int numRoomsX, int numRoomsY)
	{
		
		rooms = new GameRoom[numRoomsX][numRoomsY];
		updateRoomSize(getDimensions());
		
		Vector2f roomLoc = getPosition().sub(getDimensions().mul(0.5f)).add(roomSize.mul(0.5f));
		
		for(int x = 0; x < numRoomsX; x++)
		{
			for(int y = 0; y < numRoomsY; y++)
			{
				if(rooms[x][y] == null)
					rooms[x][y] = new GameRoom(roomLoc);
				else
					rooms[x][y].setPosition(roomLoc);
			
				rooms[x][y].resize(roomSize);
				if(x==0 || x == rooms.length-1 || y==0 || y==rooms.length-1)
				{
					rooms[x][y].setBiome(Biome.OCEAN);
				}
				else
				{
					rooms[x][y].setBiome(Biome.INTERTIDAL);
				}
				roomLoc.y += roomSize.y;
			}
			roomLoc.x += roomSize.x;
			roomLoc.y -= roomSize.y*numRoomsY;
		}
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
				/*if(rooms[x][y].getElevation()< seaLevel)
					rooms[x][y].setBiome(Biome.OCEAN);
				else if(rooms[x][y].getElevation() < seaLevel + seaLevel * 0.1)
					rooms[x][y].setBiome(Biome.INTERTIDAL);
				else
					rooms[x][y].setBiome(Biome.WETLAND);
				*/
				rooms[x][y].transform(this);
			}
		}
	}
	
	public void recurseFill()
	{
		recurseFill(2, rooms.length-3);
	}
	
	private void recurseFill(int min, int max)
	{
		float elev = (float)(min) / rooms.length;
		for(int i = min; i <=max; i++)
		{
			for(int j =min; j <= max; j++)
			{
				rooms[i][j].transform(this);
			}
		}
		
		if(min>=max) return;
		
		recurseFill(min+1,max-1);
	}
	
	public void setHeightMap(float[][] heightmap)
	{
		if(heightmap.length < rooms.length || heightmap[0].length < rooms[0].length) return;
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms.length; y++)
			{
				rooms[x][y].setElevation(heightmap[x][y]);
			}
		}
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport) 
	{
		super.render(g, viewport);
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms[x].length; y++)
			{
				rooms[x][y].render(g, viewport);
				//Point p = viewport.mul(rooms[x][y].getPosition()).toPoint();
				//g.setColor(Color.BLACK);
				//g.drawString(String.format("%d,%d", x,y), p.x, p.y);
			}
		}
	}
	
	@Override
	public void renderHeight(Graphics g, Matrix3x3f view)
	{
		super.renderHeight(g, view);
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms[x].length; y++)
			{
				rooms[x][y].renderHeight(g, view);
				//Point p = viewport.mul(rooms[x][y].getPosition()).toPoint();
				//g.setColor(Color.BLACK);
				//g.drawString(String.format("%d,%d", x,y), p.x, p.y);
			}
		}
	}
	
}
