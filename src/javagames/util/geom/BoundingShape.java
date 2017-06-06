package javagames.util.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public abstract class BoundingShape implements Drawable
{
	protected Vector2f position;
	
	/**
	 * Checks intersection with another shape.
	 * @param otherShape -Bounding Shape to compare against
	 * @return			true if the two shapes intersect
	 */
	public abstract boolean intersects(BoundingShape otherShape);
	
	public abstract boolean contains(Vector2f point);
	
	public void setPosition(Vector2f point)
	{
		this.position=new Vector2f(point);
	}
	
	public abstract void render(Graphics g, Matrix3x3f view);
}
