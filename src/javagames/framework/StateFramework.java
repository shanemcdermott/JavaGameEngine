package javagames.framework;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.player.Viewport;
import javagames.state.LoadingState;
import javagames.state.StateController;
import javagames.util.GameConstants;

public class StateFramework extends WindowFramework 
{

	protected StateController controller;

	public StateFramework()
	{
		appBorder = GameConstants.APP_BORDER;
		appWidth = GameConstants.APP_WIDTH;
		appHeight = GameConstants.APP_HEIGHT;
		appSleep = GameConstants.APP_SLEEP;
		appTitle = GameConstants.APP_TITLE;
		appWorldWidth = GameConstants.WORLD_WIDTH;
		appWorldHeight = GameConstants.WORLD_HEIGHT;
		appBorderScale = GameConstants.BORDER_SCALE;
		appDisableCursor = GameConstants.DISABLE_CURSOR;
		appMaintainRatio = GameConstants.MAINTAIN_RATIO;
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		controller = new StateController();
		controller.setAttribute("app", this);
		controller.setAttribute("keys", keyboard);
		controller.setAttribute("viewport", new Viewport());
		controller.setAttribute("mouse", mouse);
		controller.setState(new LoadingState("Genesis"));
	}
	
	public void shutDownGame()
	{
		shutDown();
	}
	
	@Override
	protected void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		controller.processInput(deltaTime);
	}
	
	@Override
	protected void updateObjects(float deltaTime)
	{
		controller.updateObjects(deltaTime);
	}
                                   
	@Override
	protected void render(Graphics g)
	{
		controller.render((Graphics2D) g, getViewportTransform());
		super.render(g);
	}
	
	public static void main(String[] args)
	{
		launchApp(new StateFramework());
	}
}
