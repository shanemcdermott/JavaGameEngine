package javagames.state;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

import javagames.g2d.Sprite;
import javagames.game.GameObject;
import javagames.player.KeyboardInput;
import javagames.player.PlayerController;
import javagames.player.PlayerControls;
import javagames.player.RelativeMouseInput;
import javagames.player.Viewport;
import javagames.util.Matrix3x3f;

/*State that cycles to another State */
public abstract class AttractState extends State 
{
	private List<GameObject> gameObjects;
	private float time;
	private Sprite background;
	protected KeyboardInput keys;
	protected RelativeMouseInput mouse;
	protected Viewport viewport;
	protected PlayerControls player;
	
	public AttractState() {}

	public AttractState(List<GameObject> gameObjects)
	{
		this.gameObjects = gameObjects;
	}
	
	@Override
	public void enter() 
	{
		keys = (KeyboardInput) controller.getAttribute("keys");
		mouse = (RelativeMouseInput) controller.getAttribute("mouse");
		background = (Sprite) controller.getAttribute("background");
		player = (PlayerControls) controller.getAttribute("player");
		viewport = (Viewport)controller.getAttribute("viewport");
		PlayerController pc = (PlayerController)player;
		pc.setViewport(viewport);
		player = pc;
		
		if(gameObjects == null)
		{
			gameObjects = new Vector<GameObject>();
		}
		time = 0.0f;
	}

	@Override
	public void updateObjects(float delta) 
	{
		time += delta;

		Vector<GameObject> gameCopies = new Vector<GameObject>(gameObjects);
		for (GameObject g : gameCopies) 
		{
			g.update(delta);
		}
		gameObjects = gameCopies;
		
		if (shouldChangeState()) 
		{
			State state = getNextState();
			if(state instanceof AttractState)
				((AttractState)state).setGameObjects(gameObjects);
			
			getController().setState(state);
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

	protected void addGameObject(GameObject gameObject)
	{
		gameObjects.add(gameObject);
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
	public void render(Graphics2D g, Matrix3x3f view) {
		view = viewport.asMatrix().mul(view);
		background.render(g, view);
		for (GameObject o : gameObjects) {
			o.render(g, view);
		}
		
	}
}
