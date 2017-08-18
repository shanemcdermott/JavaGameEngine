package javagames.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javagames.g2d.SpriteSheet;
import javagames.game.Construct;
import javagames.game.GameObject;
import javagames.game.SpriteObject;
import javagames.player.KeyboardInput;
import javagames.player.PlayerController;
import javagames.player.PlayerControls;
import javagames.player.RelativeMouseInput;
import javagames.player.Viewport;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.world.GameMap;

/*State that cycles to another State */
public abstract class AttractState extends State 
{
	private List<GameObject> gameObjects;
	private List<GameObject> pendingObjects;

	private float time;

	//private Sprite background;
	protected KeyboardInput keys;
	protected RelativeMouseInput mouse;
	protected Viewport viewport;
	protected PlayerControls player;
	protected GameMap map;

	public AttractState() {}

	public AttractState(List<GameObject> gameObjects)
	{
		this.gameObjects = gameObjects;
		pendingObjects = new ArrayList<GameObject>();
	}

	@Override
	public void enter()
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		mouse = (RelativeMouseInput) controller.getAttribute("mouse");
		map = (GameMap)controller.getAttribute("map");
		//background = (Sprite) controller.getAttribute("background");
		player = (PlayerControls) controller.getAttribute("player");
		viewport = (Viewport)controller.getAttribute("viewport");
		PlayerController pc = (PlayerController)player;
		pc.setViewport(viewport);
		pc.setGameState(this);
		player = pc;
		pc.testItem = (Construct) controller.getAttribute("item");
		if(gameObjects == null)
		{
			gameObjects = new Vector<GameObject>();
		}
		if(pendingObjects == null)
		{
			pendingObjects = new ArrayList<GameObject>();
		}
		time = 0.0f;
	}

	@Override
	public void updateObjects(float delta)
	{
		time += delta;

		Vector<GameObject> gameCopies = new Vector<GameObject>(gameObjects);
		Vector<GameObject> movingCopies = new Vector<GameObject>();

		for (GameObject g : gameCopies)
		{

			g.setColor(Color.black);
			g.update(delta);
			if(g.isMoving())
				movingCopies.add(g);
		}

		checkCollisions(movingCopies, delta);

		for(GameObject g: gameCopies)
		{
			if(g instanceof SpriteObject)
				((SpriteObject)g).updateSprite(delta);
		}

		setGameObjects(gameCopies);
		if(!pendingObjects.isEmpty())
		{
			gameObjects.addAll(pendingObjects);
			pendingObjects.clear();
		}

		if (shouldChangeState())
		{
			State state = getNextState();
			if(state instanceof AttractState)
				((AttractState)state).setGameObjects(gameObjects);

			getController().setState(state);
		}
	}

	protected void checkCollisions(List<GameObject> movingObjects, float deltaTime)
	{
		for(GameObject m : movingObjects)
		{
			for(GameObject s : gameObjects)
			{
				if(m == s) continue;
				if(m.intersects(s.getBounds()))
				{
					m.onOverlap(s, deltaTime);
					if(!m.isMoving()) break;
				}
			}
		}

	}

	protected abstract boolean shouldChangeState();

	private void setGameObjects(List<GameObject> gameObjects)
	{
		this.gameObjects = gameObjects;
	}

	/**
	 * Return the next State to switch to
	 * */
	protected abstract State getNextState();

	public void addGameObject(GameObject gameObject)
	{
		pendingObjects.add(gameObject);
	}

	public List<GameObject> getGameObjects()
	{
		return gameObjects;
	}

	@Override
	public void processInput(float delta)
	{
		if (keys.keyDownOnce(KeyEvent.VK_ESCAPE)) {
			app.shutDownGame();
		}
		if(player != null)
			player.processInput(mouse, keys, delta);
	}

	@Override
	public void render(Graphics2D g, Matrix3x3f view)
	{
		view = viewport.asMatrix().mul(view);
		map.render(g, view);
		//background.render(g, view);
		for (GameObject o : gameObjects)
		{
			if(viewport.contains(o))
			{
				o.render(g, view);
			}
		}
		Utility.drawString(g, 30, 30, gameObjects.toString());
		viewport.bounds.render(g, view);
	}
}
