package javagames.player;

import java.awt.event.KeyEvent;

import javagames.util.Transform;
import javagames.util.Vector2f;

public class Viewport extends Transform implements PlayerControls
{

	public float speed = 10.f;
	
	public Viewport() {
		// TODO Auto-generated constructor stub
	}

	public Viewport(Vector2f translation, Vector2f scale, float rotation) {
		super(translation, scale, rotation);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processInput(RelativeMouseInput mouse, KeyboardInput keyboard, float deltaTime) 
	{
		if(keyboard.keyDown(KeyEvent.VK_W))
			translation.y -= speed * deltaTime;
		if(keyboard.keyDown(KeyEvent.VK_S))
			translation.y += speed * deltaTime;
		if(keyboard.keyDown(KeyEvent.VK_A))
			translation.x += speed * deltaTime;
		if(keyboard.keyDown(KeyEvent.VK_D))
			translation.x -= speed * deltaTime;
		
	}

}
