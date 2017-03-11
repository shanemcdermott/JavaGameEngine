package javagames.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.state.StateController;

public class StateFramework extends WindowFramework 
{

	private StateController controller;

	public StateFramework()
	{
		appBorder = Color.DARK_GRAY;
		appWidth = 1680;
		appHeight = 1050;
		appSleep = 10L;
		appTitle = "State Framework";
		appWorldWidth = 2.0f;
		appWorldHeight = 2.0f;
		appBorderScale = 0.95f;
		appDisableCursor = true;
		appMaintainRatio = true;
	}
	
	@Override
	protected void initialize()
	{
		super.initialize();
		controller = new StateController();
		controller.setAttribute("app", this);
		controller.setAttribute("keys", keyboard);
		controller.setAttribute("viewport", getViewportTransform());
		//controller.setState(new GameLoading());
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
	}
	
	public static void main(String[] args)
	{
		launchApp(new StateFramework());
	}
}
