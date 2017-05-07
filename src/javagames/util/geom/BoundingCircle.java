package javagames.util.geom;

import java.awt.Graphics2D;
import java.awt.Point;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingCircle extends BoundingShape
{
	public boolean fill = true;
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
	public void render(Graphics2D g, Matrix3x3f view)
	{
		Vector2f pos = view.mul(position);
		Vector2f dim = view.mul(new Vector2f(radius,radius));
		dim.x*= 0.625f;
		dim = dim.mul(0.5f);
		Point p = pos.sub(dim.mul(0.5f)).toPoint();
		Point d = dim.toPoint();
	
		if(fill)
			g.fillOval(p.x, p.y, d.x,d.y);
		else
			g.drawOval(p.x, p.y, d.x,d.y);
	}
}
