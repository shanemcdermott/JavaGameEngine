package javagames.player;

import java.awt.event.KeyEvent;

import javagames.game.Construct;
import javagames.game.GameObject;
import javagames.state.AttractState;
import javagames.util.Direction;
import javagames.util.GameConstants;
import javagames.util.Vector2f;

public class PlayerController extends GameObject implements PlayerControls
{

	private Viewport viewport;
	private float speed;
	private AttractState gameState;
	public Construct testItem;
	
	public PlayerController() 
	{
		super();
		speed = 2.f;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processInput(RelativeMouseInput mouse, KeyboardInput keyboard, float deltaTime) 
	{
		viewport.processInput(mouse, keyboard, deltaTime);
		// TODO Auto-generated method stub
		if(keyboard.keyDown(KeyEvent.VK_W))
		{
			move(Direction.UP,speed);
		}
		else if(keyboard.keyDown(KeyEvent.VK_S))
		{
			move(Direction.DOWN, speed);
		}
		else if(keyboard.keyDown(KeyEvent.VK_A))
		{
			move(Direction.LEFT, speed);
		}
		else if(keyboard.keyDown(KeyEvent.VK_D))
		{
			move(Direction.RIGHT, speed);
		}
		else
		{
			setVelocity(new Vector2f());
		}
		
		if(keyboard.keyDownOnce(KeyEvent.VK_0))
		{
			if(testItem != null)
			{
				Construct c = new Construct(testItem.getIngredients());
				c.setSprite(testItem.getSprite());
				c.setPosition(getPosition().add(getDirection().getV().mul(GameConstants.UNIT_SIZE)));
				gameState.addGameObject(c);
			}
		}
		
		viewport.setPostion(getPosition());
	}
	
	public Viewport getViewport() 
	{
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
		this.speed = viewport.speed;
	}

	public void setGameState(AttractState gameState) {
		this.gameState = gameState;
	}


}
