package javagames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import com.sun.corba.se.impl.orbutil.graph.Graph;

import javagames.g2d.Drawable;
import javagames.g2d.Sprite;
import javagames.util.Matrix3x3f;
import javagames.util.Transform;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class GameObject implements Drawable
{
	private Sprite sprite;
	private int zOrder;
	protected BoundingShape bounds;
	private float rotation;
	private float rotationDelta;
	private Vector2f velocity;
	private Vector2f scale;
	private String name;
	private Color color;

	public GameObject()
	{
		bounds = new BoundingBox();
		velocity = new Vector2f();
		scale = new Vector2f();
		rotation = 0.f;
		zOrder = 0;
	}
	
	public BoundingShape getBounds()
	{
		return bounds;
	}
	
	public void setBounds(BoundingShape inBounds)
	{
		bounds = inBounds;
	}
	public void setVelocity(Vector2f vel)
	{
		velocity = vel;
	}
	
	public void setPosition(Vector2f pos)
	{
		bounds.setPosition(pos);
	}
	
	public Vector2f getPosition()
	{
		return bounds.getPosition();
	}
	
	public Vector2f getScale()
	{
		return scale;
	}
	
	public void setScale(Vector2f scale)
	{
		this.scale=scale;
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
		setPosition(getPosition().add(velocity.mul(deltaTime)));
		rotation += rotationDelta * deltaTime;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		if(sprite != null)
			sprite.render((Graphics2D)g, view, getPosition(), rotation);
		else
		{
			g.setColor(getColor());
			bounds.render(g, view);
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Drawable arg0) 
	{
		return getZOrder() - arg0.getZOrder();
	}

	@Override
	public void setZOrder(int order) 
	{
		zOrder = order;
		
	}

	@Override
	public int getZOrder() 
	{
		return zOrder;
	}
}
