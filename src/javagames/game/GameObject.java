package javagames.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Sprite;
import javagames.util.Transform;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class GameObject implements Drawable
{
	private Sprite sprite;
	protected BoundingShape bounds;
	private Transform transform;
	private float rotationDelta;
	private Vector2f velocity;

	public GameObject()
	{
		bounds = new BoundingBox();
		velocity = new Vector2f();
		transform = new Transform();
	}
	
	public void setPosition(Vector2f pos)
	{
		transform.translation=pos;
	}
	
	public Vector2f getPosition()
	{
		return transform.translation;
	}
	
	public Vector2f getScale()
	{
		return transform.scale;
	}
	
	public void setScale(Vector2f scale)
	{
		transform.scale=scale;
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
		transform.translation = transform.translation.add(velocity.mul(deltaTime));
		transform.rotation += rotationDelta * deltaTime;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		sprite.render((Graphics2D)g, view, transform.translation, transform.rotation);
	}
}
