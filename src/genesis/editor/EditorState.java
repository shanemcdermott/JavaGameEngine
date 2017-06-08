package genesis.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javagames.game.GameCursor;
import javagames.game.GameObject;
import javagames.state.State;
import javagames.util.KeyboardInput;
import javagames.util.Matrix3x3f;
import javagames.util.RelativeMouseInput;
import javagames.util.Sprite;
import javagames.util.Vector2f;
import javagames.world.Dungeon;
import javagames.world.InfluenceObject;

public class EditorState extends State 
{
	protected Color fontColor = Color.GREEN;
	protected List<GameObject> objects;
	protected RelativeMouseInput mouse;
	protected KeyboardInput keys;
		
	@Override
	public void enter()
	{
		super.enter();
		keys = (KeyboardInput) controller.getAttribute("keys");
		mouse = (RelativeMouseInput)controller.getAttribute("mouse");
		objects = new ArrayList<GameObject>();
	}
		
	@Override
	public void processInput(float deltaTime)
	{
		super.processInput(deltaTime);

			
	}
		
	public void render(Graphics2D g, Matrix3x3f view) 
	{
		super.render(g, view);
		int width = app.getScreenWidth();
		int height = app.getScreenHeight();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		//drawGrid(g,view);
		for(GameObject go : objects)
		{
			go.render(g, view);
		}
		
	}

	
	public void drawGrid(Graphics2D g, Matrix3x3f view)
	{
		for(float i= -1; i<=1;i+=0.5f)
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
