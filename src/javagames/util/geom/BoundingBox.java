package javagames.util.geom;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingBox extends BoundingShape 
{

	protected float width;
	protected float height;
	
	public Vector2f min;
	public Vector2f max;
	
	public BoundingBox()
	{
		this(new Vector2f(), new Vector2f());
	}
	
	public BoundingBox(Vector2f min, Vector2f max)
	{
		this.min = min;
		this.max = max;
		width = max.x-min.x;
		height = max.y-min.y;
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		if(otherShape instanceof BoundingBox)
			return intersectAABB((BoundingBox) otherShape);
		else if(otherShape instanceof BoundingCircle)
			return intersectsCircle((BoundingCircle) otherShape);
		System.err.println("otherShape is not recognized!");
		return false;
	}
	
	/**
	 * Intersection test with another box
	 * @param 	otherBox other BoundingBox to test against
	 * @return 	true if otherBox intersects with this.
	 */
	public boolean intersectAABB(BoundingBox otherBox)
	{	
		return intersectAABB(otherBox.min, otherBox.max);
	}
	
	
	/**
	 * Intersection test with another box
	 * @param minB Bottom Left corner of AABB
	 * @param maxB Top Right corner of AABB
	 * @return true if this intersects with other AABB
	 */
	public boolean intersectAABB(Vector2f minB, Vector2f maxB)
	{
		//Horizontal Check
		if(min.x > maxB.x || minB.x > max.x) return false;
		//Vertical Check
		if(min.y > maxB.y || minB.y > max.y) return false;
		
		return true;
	}
	
	@Override
	public void setPosition(Vector2f point)
	{
		super.setPosition(point);
		Vector2f halfSize = new Vector2f();
		getHalfSize(halfSize);
		min.x = point.x - halfSize.x;
		min.y = point.y - halfSize.y;
		max.x =point.x + halfSize.x;
		max.y = point.y + halfSize.y;
	}
	
	public float getHalfWidth()
	{
		return width * 0.5f;
	}
	
	public float getHalfHeight()
	{
		return height * 0.5f;
	}
	
	public void getHalfSize(Vector2f outHalfSize)
	{
		outHalfSize.x = getHalfWidth();
		outHalfSize.y = getHalfHeight();
	}
	
	/**
	 * Intersection test with a circle
	 * @param circle -circle to test
	 * @return true if the circle intersects with this.
	 */
	public boolean intersectsCircle(BoundingCircle circle)
	{
		return intersectsCircle(circle.position, circle.radius);
	}
	
	/**
	 * Intersection test with a circle
	 * @param center	-Center of circle to test against
	 * @param radius	-Radius of circle to test against
	 * @return			true if the circle intersects
	 */
	public boolean intersectsCircle(Vector2f center, float radius)
	{
		float d = 0.f;
		if(center.x < min.x) d+= (center.x - min.x) * (center.x - min.x);
		if(center.x > max.x) d+= (center.x - max.x) *(center.x - max.x);
		if(center.y < min.y) d+= (center.y - min.y) * (center.y - min.y);
		if(center.y > max.y) d+= (center.y - max.y) * (center.y - max.y);
		
		return d < radius*radius;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		return point.x > min.x && point.x < max.x && point.y > min.y && point.y < max.y;
	}

	/**
	 * Copies min x and y values into outMin
	 * @param outMin- Vector2f which will contain copy of min values.
	 */
	public void minCopy(Vector2f outMin)
	{
		outMin.x = min.x;
		outMin.y = min.y;
	}

	/**
	 * Copies max x and y values into outMax
	 * @param outMax- Vector2f which will contain copy of max values.
	 */
	public void maxCopy(Vector2f outMax)
	{	
		outMax.x = max.x;
		outMax.y = max.y;
	}
	
	/**
	 * Copies min and max into outMin and outMax
	 * @param outMin- Vector2f which will contain copy of min values.
	 * @param outMax- Vector2f which will contain copy of max values.
	 */
	public void getAABB(Vector2f outMin, Vector2f outMax)
	{
		minCopy(outMin);
		maxCopy(outMax);
	}

	@Override
	public String toString()
	{
		return String.format("Min: %s Max: %s", min, max);
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		Vector2f tl = view.mul(position.add(new Vector2f(-getHalfWidth(),getHalfHeight())));
		Vector2f s = view.mul(new Vector2f(width,height));
		Point d = s.toPoint();
		Point p = tl.toPoint();
		g.drawRect(p.x, p.y, d.x,d.y);
		
	}
}
