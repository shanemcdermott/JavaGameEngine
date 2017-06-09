package genesis.grammar;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javagames.game.GameRoom;
import javagames.world.Dungeon;

public class DungeonGrammar 
{
	private HashMap<String, Object> properties;
	private Random rando;
	
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
