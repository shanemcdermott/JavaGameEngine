package javagames.game;

import javagames.util.MouseControls;
import javagames.util.RelativeMouseInput;

public class GameCursor extends GameObject implements MouseControls 
{

	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		setPosition(mouse.getWorldPosition());
	}

}
