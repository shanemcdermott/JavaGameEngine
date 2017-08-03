package javagames.player;

import javagames.game.GameObject;
import javagames.util.Vector2f;

public class GameCursor extends GameObject implements MouseControls 
{

	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		Vector2f pos = mouse.getWorldPosition();
		if(pos != null)
			setPosition(pos);
	}

}
