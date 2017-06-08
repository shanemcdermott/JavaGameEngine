package javagames.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class GameRoom extends GameObject 
{
	public Vector<GameObject> contents;
	
	public GameRoom(Vector2f location)
	{
		super();
		position = new Vector2f(location);
		bounds = new BoundingBox();
		bounds.setPosition(position);
		contents = new Vector<GameObject>();
	}

	public boolean contains(Vector2f point)
	{
		return bounds.contains(point);
	}
	
	public void addGameObject(GameObject o)
	{
		if(bounds.contains(o.getPosition()))
			contents.add(o);
	}
	
	public void resize(Vector2f dimensions)
	{
		resize(dimensions.x,dimensions.y);
	}
	
	public void resize(float width, float height)
	{
		BoundingBox aabb = getAABB();
		if(aabb!=null)
		{
			aabb.resize(width, height);
		}
	}
	
	public float getWidth()
	{
		return getAABB().getWidth();
	}
	
	public float getHeight()
	{
		return getAABB().getHeight();
	}
	
	public Vector2f getDimensions()
	{
		return new Vector2f(getWidth(), getHeight());
	}
	
	public BoundingBox getAABB()
	{
		return (BoundingBox)bounds;
	}
	
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport)
	{
		super.render(g, viewport);
		for(GameObject o : contents)
			o.render(g, viewport);
	}
}
