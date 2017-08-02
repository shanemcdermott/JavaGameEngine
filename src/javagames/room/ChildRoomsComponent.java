package javagames.room;

import java.awt.Graphics;
import java.awt.Point;

import genesis.world.Biome;
import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingPoly;

public class ChildRoomsComponent extends RoomComponent implements Drawable
{

	protected GameRoom[][] rooms;
	protected Vector2f roomSize;
	
	
	public ChildRoomsComponent(GameRoom room) 
	{
		super(room);
		roomSize = new Vector2f(0.2f,0.2f);
		// TODO Auto-generated constructor stub
	}
	
	public Point getRoomIndices(Vector2f location)
	{
		Vector2f bottomLeft = location.sub(rooms[0][0].getPosition()).add(roomSize.mul(0.5f));
		bottomLeft.x /= roomSize.x;
		bottomLeft.y /= roomSize.y;
		return bottomLeft.abs().toPoint();
	}
	
	public GameRoom getRoomAt(Vector2f location)
	{
		if(!room.getBounds().contains(location)) return null;
		Point p = getRoomIndices(location);
		return rooms[p.x][p.y];
	}
	
	public void setRoomAt(Vector2f location, GameRoom inRoom)
	{
		if(!room.getBounds().contains(location)) return;
		
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
	
	public Point getNumRooms()
	{
		return new Point(rooms.length, rooms[0].length);
	}
	
	protected void updateRoomSize(Vector2f dungeonBounds)
	{
		roomSize.x = dungeonBounds.x / rooms.length;
		roomSize.y = dungeonBounds.y / rooms[0].length;
	}
	
	public Vector2f getRoomSize()
	{
		return roomSize;
	}
	
	public void setRoomCounts(int numRoomsX, int numRoomsY)
	{
		
		rooms = new GameRoom[numRoomsX][numRoomsY];
		updateRoomSize(room.getDimensions());
		
		Vector2f roomLoc = room.getPosition().sub(room.getDimensions().mul(0.5f)).add(roomSize.mul(0.5f));
		
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
	
	
	public void addMask(String maskName, BoundingPoly polyMask)
	{
		for(int x = 0; x < rooms.length; x++)
			for(int y = 0; y < rooms[x].length; y++)
				if(polyMask.contains(rooms[x][y].getPosition()))
					rooms[x][y].setFlag(maskName, true);
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
	
	public void getHeightmap(float[][] heightmap) {
		
		for(int x =0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms.length; y++)
			{
				heightmap[x][y] = rooms[x][y].getElevation();
			}
		}
		
	}

	
	
	public GameRoom[][] getRooms()
	{
		return rooms;
	}

	@Override
	public int compareTo(Drawable arg0) 
	{
		return room.compareTo(arg0);
	}

	@Override
	public int getZOrder() 
	{
		return room.getZOrder();
	}

	@Override
	public void setZOrder(int order) 
	{
		room.setZOrder(order);	
	}

	@Override
	public void render(Graphics g, Matrix3x3f viewport) 
	{
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
	
	public void renderHeight(Graphics g, Matrix3x3f view)
	{
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms[x].length; y++)
			{
				rooms[x][y].renderHeight(g, view);
			}
		}
	}
}
