package javagames.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javagames.util.Matrix3x3f;
import javagames.util.Sprite;
import javagames.util.Vector2f;

public class GameObject 
{
	private Sprite sprite;
	
	private ArrayList<Vector2f[]> collisionList;
	
	private float rotation;
	private float rotationDelta;
	private Vector2f position;
	private Vector2f velocity;

	public GameObject()
	{
		collisionList = new ArrayList<Vector2f[]>();
		position = new Vector2f();
		velocity = new Vector2f();
	}
	
	public void setPosition(Vector2f pos)
	{
		this.position=pos;
	}
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public void setSprite(Sprite sprite) 
	{
		this.sprite = sprite;
	}

	public Sprite getSprite() 
	{
		return sprite;
	}
	
	public void update(float deltaTime)
	{
		position = position.add(velocity.mul(deltaTime));
		rotation += rotationDelta * deltaTime;
		//collisionList.clear();
		//Vector2f[] world = transformPolygon();
		//collisionList.add(world);
		
	}
	
	public void render(Graphics2D g, Matrix3x3f view)
	{
		sprite.render(g, view, position, rotation);
	}
}
