package javagames.player;

import java.awt.event.KeyEvent;

import javagames.game.GameObject;
import javagames.util.GameConstants;
import javagames.util.Transform;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class Viewport extends Transform implements PlayerControls
{
	public BoundingBox bounds;
	public float speed = 10.f;
	
	public Viewport() {
		bounds = new BoundingBox(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
		// TODO Auto-generated constructor stub
	}

	public Viewport(Vector2f translation, Vector2f scale, float rotation) {
		super(translation, scale, rotation);
		bounds = new BoundingBox(GameConstants.WORLD_WIDTH, GameConstants.WORLD_HEIGHT);
		// TODO Auto-generated constructor stub
	}
	
	public void setPostion(Vector2f pos)
	{
		bounds.setPosition(pos);
		translation = pos.mul(-1);
	}
	
	@Override
	public void processInput(RelativeMouseInput mouse, KeyboardInput keyboard, float deltaTime) 
	{
		
		if(keyboard.keyDown(KeyEvent.VK_W))
			translation.y -= speed * deltaTime;
		else if(keyboard.keyDown(KeyEvent.VK_S))
			translation.y += speed * deltaTime;
		else if(keyboard.keyDown(KeyEvent.VK_A))
			translation.x += speed * deltaTime;
		else if(keyboard.keyDown(KeyEvent.VK_D))
			translation.x -= speed * deltaTime;
		
	}
	
	public boolean contains(GameObject g)
	{
		return bounds.intersects(g.getBounds());
	}
	
}
