package javagames.util.geom;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingBox extends BoundingShape 
{

	protected float width;
	protected float height;
	
	public BoundingBox()
	{
		this(0.f, 0.f);
	}
	
	public BoundingBox(float size)
	{
		this(size,size);
	}
	
	public BoundingBox(float width, float height)
	{
		this(new Vector2f(), new Vector2f(width,height));
	}
	
	public BoundingBox(Vector2f position, Vector2f size)
	{
		this.position = position;
		width = size.x;
		height = size.y;
	}
	
	public BoundingBox(BoundingBox bb)
	{
		this.position = new Vector2f(bb.position);
		this.width = bb.width;
		this.height = bb.height;
	}
	
	public BoundingBox(Rectangle rectangle)
	{
		width = rectangle.width;
		height= rectangle.height;
		position = new Vector2f();
		position.x= (float) rectangle.getCenterX();
		position.y = (float) rectangle.getCenterY();
	}

	public void resize(float width, float height)
	{
		this.width=width;
		this.height = height;
	}
	
	public float getWidth()
	{
		return width;
	}
	
	public float getHeight()
	{
		return height;
	}
	
	public Vector2f getTopLeft()
	{
		return getMin().add(new Vector2f(0.f,height));
	}
	
	public Vector2f getSize()
	{
		return new Vector2f(width,height);
	}
	
	public Vector2f getHalfSize()
	{
		return new Vector2f(width * 0.5f, height * 0.5f);
	}
	
	public Vector2f getMin()
	{
		return position.sub(getHalfSize());
	}
	
	public Vector2f getMax()
	{
		return position.add(getHalfSize());
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
		return intersectAABB(otherBox.getMin(), otherBox.getMax());
	}
	
	
	/**
	 * Intersection test with another box
	 * @param minB Bottom Left corner of AABB
	 * @param maxB Top Right corner of AABB
	 * @return true if this intersects with other AABB
	 */
	public boolean intersectAABB(Vector2f minB, Vector2f maxB)
	{
		Vector2f min = getMin();
		Vector2f max = getMax();
		//Horizontal Check
		if(min.x > maxB.x || minB.x > max.x) return false;
		//Vertical Check
		if(min.y > maxB.y || minB.y > max.y) return false;
		
		return true;
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
		Vector2f min = getMin();
		Vector2f max = getMax();
		if(center.x < min.x) d+= (center.x - min.x) * (center.x - min.x);
		if(center.x > max.x) d+= (center.x - max.x) *(center.x - max.x);
		if(center.y < min.y) d+= (center.y - min.y) * (center.y - min.y);
		if(center.y > max.y) d+= (center.y - max.y) * (center.y - max.y);
		
		return d < radius*radius;
	}
	
	@Override
	public boolean contains(Vector2f point) 
	{
		Vector2f min = getMin();
		Vector2f max = getMax();
		return point.x > min.x && point.x < max.x && point.y > min.y && point.y < max.y;
	}

	public Rectangle toRectangle()
	{
		Rectangle rec = new Rectangle();
		Vector2f tl = getTopLeft();
		rec.x = (int)tl.x;
		rec.y = (int)tl.y;
		rec.width = (int)width;
		rec.height = (int)height;
		return rec;
	}


	@Override
	public String toString()
	{
		return String.format("Center: %s Min: %s Max: %s", position, getMin(), getMax());
	}
	
	public void render(Graphics g, Matrix3x3f viewport)
	{

		Vector2f size = new Vector2f(width,height);
		Vector2f topLeft = getTopLeft();
		topLeft = viewport.mul(topLeft);
		Vector2f sc = viewport.getScale().abs();
		size.scale(sc.x, sc.y);
	
		g.drawRect((int)topLeft.x, (int)topLeft.y, (int)size.x, (int)size.y);
	
		if(fill)
			g.fillRect((int)topLeft.x, (int)topLeft.y, (int)size.x, (int)size.y);
		
	}
}