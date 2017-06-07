package javagames.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class GameRoom extends GameObject 
{
	
	public GameRoom(Vector2f location)
	{
		super();
		position = new Vector2f(location);
		bounds = new BoundingBox();
		bounds.setPosition(position);
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
	
}
