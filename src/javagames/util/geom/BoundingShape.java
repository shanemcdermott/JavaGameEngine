package javagames.util.geom;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public abstract class BoundingShape implements Drawable
{
	private int zOrder;
	public boolean fill=false;
	protected Vector2f position;
	private CollisionChannel channel;
	
	/**
	 * Checks intersection with another shape.
	 * @param otherShape -Bounding Shape to compare against
	 * @return			true if the two shapes intersect
	 */
	public abstract boolean intersects(BoundingShape otherShape);
	
	public abstract boolean contains(Vector2f point);
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public void setPosition(Vector2f point)
	{
		this.position= point;
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

	public CollisionChannel getChannel() {
		return channel;
	}

	public void setChannel(CollisionChannel channel) {
		this.channel = channel;
	}

	@Override
	public int getZOrder() 
	{
		return zOrder;
	}
	
	public void render(Graphics g, Matrix3x3f view, Color c)
	{
		g.setColor(c);
		render(g,view);
	}
	public abstract void render(Graphics g, Matrix3x3f view);
}
