package javagames.player;

import java.awt.event.MouseEvent;

import com.sun.glass.events.KeyEvent;

import genesis.editor.GameState;
import javagames.game.GameObject;
import javagames.state.State;
import javagames.state.StateController;
import javagames.world.InfluenceObject;

public class PlayerController extends StateController implements PlayerControls 
{
	private GameObject pawn;
	private GameState gameState;

	public PlayerController(GameState state)
	{
		setGameState(state);
		setControlledPawn(new InfluenceObject());
		state.addObject(pawn);
	}
	
	public void setControlledPawn(GameObject o)
	{
		this.pawn = o;
	}
	
	public void setGameState(GameState state)
	{
		this.gameState=state;
	}
	
	@Override
	public void processInput(RelativeMouseInput mouse, KeyboardInput keyboard, float deltaTime) 
	{
		pawn.setPosition(mouse.getWorldPosition());
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			InfluenceObject i = new InfluenceObject();
			i.setPosition(pawn.getPosition());
			gameState.addObject(i);
		}

	}

}
