package genesis.grammar;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javagames.game.GameRoom;
import javagames.util.Vector2f;

public class AddRoomRule implements GrammarRule 
{
	private Random rando;
		
	private GameRoom[][] rooms;
	private ArrayList<Point> openRooms;
	
	@Override
	public void init(Map<String, Object> params) 
	{
		rooms = (GameRoom[][])params.get("rooms");
		rando = (Random)params.get("random");
		
		openRooms = new ArrayList<Point>();
		
		for(int x = 0; x < rooms.length; x++)
		{
			for(int y = 0; y < rooms[x].length; y++)
			{
				if(rooms[x][y] == null)
					openRooms.add(new Point(x,y));
			}
		}
	}
	
	@Override
	public boolean canEnter() 
	{
		return !isComplete() && !isFailed();
	}

	@Override
	public boolean isComplete() 
	{	
		return openRooms.isEmpty();
	}

	@Override
	public boolean isFailed() 
	{
		return false;
	}

	@Override
	public void execute() 
	{
		if(isComplete())return;
		
		Point p = openRooms.remove(rando.nextInt(openRooms.size()));
		rooms[p.x][p.y] = new GameRoom(new Vector2f(p.x,p.y));
	}



	@Override
	public Map<String, Object> getResults() 
	{
		HashMap<String,Object> results = new HashMap<String,Object>();
		results.put("rooms", rooms);
		
		return results;
	}

	@Override
	public void iterate(int n) 
	{
		for(int i = 0; i < n; i++)
		{
			if(isComplete())return;
			
			execute();
		}
	}

}
