package javagames.world;

import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Graphics;

import javagames.game.GameObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingShape;

public class InfluenceObject extends GameObject
{
	public Color color;
	
	public InfluenceObject()
	{
		bounds=new BoundingCircle(new Vector2f(), 10.f);
		color = new Color(0,255,0,100);
	}
	
	public void setRadius(float radius)
	{
		((BoundingCircle)bounds).setRadius(radius);
	}
	
	public float getRadius()
	{
		return ((BoundingCircle)bounds).radius;
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	
	@Override
	public void setPosition(Vector2f pos)
	{
		super.setPosition(pos);
		bounds.setPosition(pos);
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		g.setColor(color);
		bounds.render(g,view);
	}

}
