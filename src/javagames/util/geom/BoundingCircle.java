package javagames.util.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingCircle extends BoundingShape
{
	public boolean fill = false;
	public float radius;
	
	public BoundingCircle(float radius)
	{
		this(new Vector2f(), radius);
	}
	
	public BoundingCircle(Vector2f center, float radius)
	{
		setPosition(center);
		this.radius = radius;
	}
	
	public void setRadius(float radius)
	{
		this.radius=radius;
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		if(otherShape instanceof BoundingBox)
		{
			BoundingBox aabb = (BoundingBox)otherShape;
			return aabb.intersectsCircle(this);
		}	
		if(otherShape instanceof BoundingCircle)
			return intersectsCircle((BoundingCircle) otherShape);
			
		System.err.println("otherShape is not recognized!");
		return false;
	}

	public boolean intersectsCircle(BoundingCircle otherCircle)
	{
		return intersectsCircle(otherCircle.position, otherCircle.radius);
	}
	
	/**
	 * Intersection test with another circle
	 * @param center	Other circle's Center
	 * @param radius	Other circle's Radius
	 * @return	true if the circles intersect
	 */
	public boolean intersectsCircle(Vector2f center, float radius)
	{
		Vector2f c = this.position.sub(center);
		float r = this.radius + radius;
		return c.lenSqr() < r * r;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		Vector2f dist = point.sub(position);
		return dist.lenSqr() < radius*radius;
	}
	
	@Override
	public void setPosition(Vector2f point)
	{
		super.setPosition(point);
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		Vector2f size = new Vector2f(radius * 2.f, radius * 2.f);
		Vector2f topLeft = getPosition().add(new Vector2f(-radius, radius));
		topLeft = view.mul(topLeft);
		Vector2f sc = view.getScale().abs();
		size.scale(sc.x, sc.y);
		
	
	
		Point s = size.toPoint();
		Point p = topLeft.toPoint();
		
		
		if(fill)
			g.fillOval(p.x, p.y, s.x,s.y);
		else
			g.drawOval(p.x, p.y, s.x,s.y);
	}

}
