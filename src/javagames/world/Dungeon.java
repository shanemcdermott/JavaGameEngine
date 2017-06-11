package javagames.world;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javagames.game.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class Dungeon extends GameRoom 
{
	protected Vector2f roomSize;
	
	protected GameRoom[][] rooms;
	
	public Dungeon(String name) 
	{
		super(new Vector2f());
		setName(name);
		bounds = new BoundingBox(2.f);
		roomSize = new Vector2f(0.2f,0.2f);
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
				roomLoc.y += roomSize.y;
			}
			roomLoc.x += roomSize.x;
			roomLoc.y -= roomSize.y*numRoomsY;
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
				Vector2f p = viewport.mul(rooms[x][y].getPosition());
				g.drawString(String.format("%d,%d", x,y), (int)p.x, (int)p.y);
			}
		}
	}
}
