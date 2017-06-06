package genesis.grammar;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import javagames.game.GameRoom;

public class AddGoalRule implements GrammarRule 
{
	private Random rando;
	private GameRoom startRoom;
	private GameRoom goalRoom;
	private Vector<Goal> goalOptions; 
	
	@Override
	public void init(Map<String, Object> params) 
	{
		
		rando = (Random)params.get("random");
		startRoom = (GameRoom)params.get("startRoom");
		goalRoom = (GameRoom)params.get("goalRoom");
		goalOptions = (Vector<Goal>)params.get("goals");
		if(goalOptions == null)
		{
			goalOptions = new Vector<Goal>();
			goalOptions.add(Goal.BOSS_FIGHT);
		}

	}

	@Override
	public boolean canEnter() 
	{
		return !isComplete() && !isFailed();
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

	@Override
	public void execute() 
	{
		if(isComplete()) return;
		
		if(goalRoom != null)
		{
			startRoom.addNeighbor(goalRoom);
			goalRoom.addNeighbor(startRoom);
			Goal g = goalOptions.get(rando.nextInt(goalOptions.size()));
			goalRoom.setComponent("goal", g);
		}
		

	}

	@Override
	public boolean isComplete() 
	{
		return goalRoom.getComponent("goal") != null;
	}

	@Override
	public boolean isFailed() 
	{
		return startRoom == null;
	}

	@Override
	public Map<String, Object> getResults() 
	{
		HashMap<String,Object> results = new HashMap<String,Object>();
		results.put("startRoom",startRoom);
		results.put("goalRoom", goalRoom);
		
		return results;
	}

}
