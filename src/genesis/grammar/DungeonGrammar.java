package genesis.grammar;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import genesis.cell.Cell;
import javagames.game.GameRoom;
import javagames.util.Vector2f;
import javagames.world.Dungeon;

public class DungeonGrammar 
{
	private final Vector2f ROOM_SIZE = new Vector2f(0.2f,0.2f);
	private final Vector2f ENTRANCE_LOC = new Vector2f(-0.9f, -0.9f);
	
	private HashMap<String, Object> properties;
	private Random rando;
	
	//root node
	private Cell entrance;
	
	private Vector<GrammarRule> rules;
	private int currentRule;

	
	public DungeonGrammar()
	{
		properties = new HashMap<String,Object>();		
		rules = new Vector<GrammarRule>();
		rando = new Random();
		currentRule = 0;

	}

	public void execRecipe()
	{
		
	}

	
}
