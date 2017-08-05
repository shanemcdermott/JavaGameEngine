package javagames.state;

import java.util.List;
import java.util.Vector;

import javagames.game.GameObject;

public class GeneratorState extends AttractState 
{

	public boolean finished;
	
	public GeneratorState() 
	{
		super();
	}

	public GeneratorState(List<GameObject> gameObjects) 
	{
		super(gameObjects);
		
	}

	@Override
	public void enter()
	{
		super.enter();
	}
	
	@Override
	protected boolean shouldChangeState()
	{
		return finished;
	}
	
	@Override
	protected AttractState getNextState() {
		// TODO Auto-generated method stub
		return null;
	}

}
