package javagames.game;

import java.awt.Color;
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
	private int zOrder;
	protected BoundingShape bounds;
	private Transform transform;
	private float rotationDelta;
	private Vector2f velocity;
	private String name;
	private Color color;

	public GameObject()
	{
		bounds = new BoundingBox();
		velocity = new Vector2f();
		transform = new Transform();
		zOrder = 0;
	}
	
	public void setPosition(Vector2f pos)
	{
		transform.translation=pos;
		bounds.setPosition(pos);
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
		setPosition(transform.translation.add(velocity.mul(deltaTime)));
		transform.rotation += rotationDelta * deltaTime;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		if(sprite != null)
			sprite.render((Graphics2D)g, view, transform.translation, transform.rotation);
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
