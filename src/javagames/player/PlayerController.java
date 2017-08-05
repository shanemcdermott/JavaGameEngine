package javagames.player;

import java.awt.event.KeyEvent;

import javagames.game.GameObject;
import javagames.util.Direction;
import javagames.util.Vector2f;

public class PlayerController extends GameObject implements PlayerControls
{

	private Viewport viewport;
	private float speed;
	
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
			setDirection(Direction.UP);
			setVelocity(getDirection().getV().mul(speed));
		}
		else if(keyboard.keyDown(KeyEvent.VK_S))
		{
			setDirection(Direction.DOWN);
			setVelocity(getDirection().getV().mul(speed));
		}
		else if(keyboard.keyDown(KeyEvent.VK_A))
		{
			setDirection(Direction.LEFT);
			setVelocity(getDirection().getV().mul(speed));
		}
		else if(keyboard.keyDown(KeyEvent.VK_D))
		{
			setDirection(Direction.RIGHT);
			setVelocity(getDirection().getV().mul(speed));
		}
		else
		{
			setVelocity(new Vector2f());
		}
	}
	
	public Viewport getViewport() 
	{
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
		this.speed = viewport.speed;
	}


}
