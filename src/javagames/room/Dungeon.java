package javagames.room;

import java.awt.Graphics;
import java.awt.Point;

import genesis.world.Biome;
import genesis.world.BiomeMajor;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingPoly;

public class Dungeon extends GameRoom 
{
	protected Vector2f roomSize;
	
	protected GameRoom[][] rooms;
	protected ChildEnvComponent children;

	
	public Dungeon(String name, float size, int xRooms, int yRooms)
	{
		super(new Vector2f());
		setName(name);
		bounds = new BoundingBox(size);
		children = new ChildEnvComponent(this);
		children.roomSize = new Vector2f(xRooms/size, yRooms/size);
		setRoomCounts(xRooms,yRooms);
	}
	
	public Dungeon(String name) 
	{
		super(new Vector2f());
		setName(name);
		bounds = new BoundingBox(2.f);
		children = new ChildEnvComponent(this);
		setRoomCounts(10,10);
	}

	public Dungeon(GameRoom room)
	{
		super(room.getPosition());
		setName(room.getName());
		bounds = room.getAABB();
		children = new ChildEnvComponent(this);
		setRoomCounts(10,10);
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
		return children.getRoomIndices(location);
	}
	
	public GameRoom[][] getRooms()
	{
		return children.getRooms();
	}
	
	
	public GameRoom getRoomAt(Vector2f location)
	{
		return children.getRoomAt(location);
	}
	
	public void setRoomAt(Vector2f location, GameRoom inRoom)
	{
		children.setRoomAt(location, inRoom);
	}
	
	public void getHeightmap(float[][] heightmap)
	{
		children.getHeightmap(heightmap);
	}
	
	public boolean[][] getFlagmap(String name)
	{
		return children.getFlagmap(name);
	}
	
	public void addMask(String maskName, BoundingPoly polyMask)
	{
		children.addMask(maskName, polyMask);
	}
	
	
	public Point getNumRooms()
	{
		return children.getNumRooms();
	}
	
	private void updateRoomSize(Vector2f dungeonBounds)
	{
		children.updateRoomSize(dungeonBounds);
	}
	
	public Vector2f getRoomSize()
	{
		return children.getRoomSize();
	}
	
	public void setRoomCounts(int numRoomsX, int numRoomsY)
	{
		children.setRoomCounts(numRoomsX, numRoomsY);
	}
	
	public float getCurrentWaterRatio()
	{
		return children.getCurrentWaterRatio();
	}
	
	public void markOceanCells(float seaLevel)
	{
		children.markOceanCells(seaLevel);
	}
	
	public void recurseFill()
	{
		recurseFill(2, rooms.length-3);
	}
	
	private void recurseFill(int min, int max)
	{
		float elev = (float)(min) / children.rooms.length;
		for(int i = min; i <=max; i++)
		{
			for(int j =min; j <= max; j++)
			{
				children.rooms[i][j].transform(this);
			}
		}
		
		if(min>=max) return;
		
		recurseFill(min+1,max-1);
	}
	
	public void setHeightMap(float[][] heightmap)
	{
		children.setHeightMap(heightmap);
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport) 
	{
		super.render(g, viewport);
		children.render(g, viewport);
	}
	
	@Override
	public void renderHeight(Graphics g, Matrix3x3f view)
	{
		super.renderHeight(g, view);
		children.renderHeight(g, view);
	}
	
}
