package genesis.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javagames.game.GameObject;
import javagames.player.KeyboardInput;
import javagames.player.PlayerController;
import javagames.player.RelativeMouseInput;
import javagames.state.State;
import javagames.util.Matrix3x3f;
import javagames.util.Sprite;
import javagames.util.Vector2f;
import javagames.world.InfluenceObject;

public class GameState extends State 
{
	protected Color fontColor = Color.GREEN;
	protected List<GameObject> gameObjects;
	protected PlayerController player;
	protected RelativeMouseInput mouse;
	protected KeyboardInput keys;
		
	@Override
	public void enter()
	{
		super.enter();
		gameObjects = new ArrayList<GameObject>();
		keys = (KeyboardInput) controller.getAttribute("keys");
		mouse = (RelativeMouseInput)controller.getAttribute("mouse");
		player = new PlayerController(this);
	}
		
	@Override
	public void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		player.processInput(mouse, keys,deltaTime);
			
	}
		
	public void addObject(GameObject object)
	{
		gameObjects.add(object);
	}
	
	public void render(Graphics2D g, Matrix3x3f view) 
	{
		super.render(g, view);
		int width = app.getScreenWidth();
		int height = app.getScreenHeight();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawGrid(g,view);
		for(GameObject go : gameObjects)
		{
			go.render(g, view);
		}
		
	}

	
	public void drawGrid(Graphics2D g, Matrix3x3f view)
	{
		for(float i= -100; i<=100;i+=10.f)
		{
			Vector2f v = view.mul(new Vector2f(i,-1));
			Vector2f v2 = view.mul(new Vector2f(i,1));
			Vector2f v3 = view.mul(new Vector2f(-1,i));
			Vector2f v4 = view.mul(new Vector2f(1,i));
			g.drawLine((int)v.x, (int)v.y, (int)v2.x, (int)v2.y);
			g.drawLine((int)v3.x, (int)v3.y, (int)v4.x,(int)v4.y);
		}
	}
}
