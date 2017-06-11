package javagames.player;

import javagames.game.GameObject;

public class GameCursor extends GameObject implements MouseControls 
{

	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		setPosition(mouse.getWorldPosition());
	}

}
