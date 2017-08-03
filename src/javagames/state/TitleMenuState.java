package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

import javagames.g2d.Sprite;
import javagames.game.GameObject;
import javagames.player.GameCursor;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.util.Vector2f;

public class TitleMenuState extends AttractState 
{
	protected Color fontColor = Color.GREEN;
	protected GameCursor cursor = new GameCursor();
	protected SoundCue laser;
	protected SoundLooper thruster;
	protected AttractState nextState;
	private boolean bShouldLoopPlay = false;
	
	@Override
	public void enter()
	{
		super.enter();
		laser = (SoundCue) controller.getAttribute("fire-clip");
		thruster = (SoundLooper) controller.getAttribute("thruster");
		cursor.setSprite((Sprite)controller.getAttribute("spr_cursor"));
		addGameObject((GameObject)controller.getAttribute("tree"));
		addGameObject((GameObject)controller.getAttribute("tree_1"));
		nextState = null;
	}
	
	@Override
	protected AttractState getNextState() 
	{
		return nextState;
	}

	
	@Override
	protected boolean shouldChangeState()
	{
		return nextState != null;
	}
	
	@Override
	public void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		
		if(keys.keyDownOnce(KeyEvent.VK_ENTER))
			nextState = new GeneratorState(getGameObjects());
		
		//Sound Cue Example
		if(keys.keyDownOnce(KeyEvent.VK_SPACE))
		{
			laser.fire();
		}
		//Looping Sound Example
		if (keys.keyDown(KeyEvent.VK_W)) 
		{
			viewport = viewport.mul(Matrix3x3f.translate(new Vector2f(0,2 * deltaTime)));
			if (!bShouldLoopPlay) 
			{
				thruster.fire();
				bShouldLoopPlay = true;
			}
		} 
		else 
		{
			if (bShouldLoopPlay) 
			{
				thruster.done();
				bShouldLoopPlay = false;
			}
		}
		cursor.processInput(mouse, deltaTime);
	}
	
	@Override
	public void updateObjects(float deltaTime)
	{
		super.updateObjects(deltaTime);
		cursor.update(deltaTime);
	}
	
	public void render(Graphics2D g, Matrix3x3f view) {
		super.render(g, view);
		int width = app.getScreenWidth();
		int height = app.getScreenHeight();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(fontColor);
		String[] msg = { 
			GameConstants.APP_TITLE,
			"", 
			"", 
			"P R E S S  S P A C E  T O  P L A Y  S O U N D",
			"",
			"H O L D  'W'  T O  P L A Y  L O O P I N G  S O U N D",
			"",
			"P R E S S  ESC  T O  E X I T",
			"",
			"P R E S S  ENTER  T O  G E N E R A T E"
		};
		Utility.drawCenteredString(g, width, height / 3, msg);
		cursor.render(g, view);
	}
}
