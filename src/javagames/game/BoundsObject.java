package javagames.game;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;
import javagames.util.geom.CollisionChannel;

import java.awt.*;


public class BoundsObject implements Drawable
{
    protected BoundingShape bounds;
    protected int zOrder;
	private String name;
	private Color color;

    public BoundsObject()
    {
        bounds = new BoundingBox();
        name = "BoundsObject";
    }

    public BoundsObject(BoundsObject toCopy)
    {
        bounds = toCopy.bounds;
        name = toCopy.getName();
    }

	public void setPosition(Vector2f pos) {
		bounds.setPosition(pos);
	}

	public Vector2f getPosition() {
		return bounds.getPosition();
	}


	public BoundingShape getBounds() {
		return bounds;
	}

	public void setBounds(BoundingShape inBounds)
	{
		if(bounds!=null)
		{
			Vector2f pos = bounds.getPosition();
			bounds = inBounds;
			bounds.setPosition(pos);
		}
		else
		{
			bounds = inBounds;
		}
	}

	public boolean intersects(BoundingShape bounds)
	{
		return this.bounds.intersects(bounds);
	}

	/**
	 * Handles collision with other objects. Defaults to reverting position and stopping motion.
	 * @param otherObject - Object that was collided with.
	 * @param deltaTime - amount of time that has passed since last update.
	 */
	public void onOverlap(BoundsObject otherObject, float deltaTime)
	{
		System.out.printf("%s overlapped with %s at %s.\n", getName(), otherObject.getName(), otherObject.getPosition());

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
	public void render(Graphics g, Matrix3x3f view)
	{
		renderBounds(g,view);
	}

	public void renderBounds(Graphics g, Matrix3x3f view) {
		g.setColor(getColor());
		bounds.render(g, view);
	}


	@Override
	public int compareTo(Drawable arg0) {
		return getZOrder() - arg0.getZOrder();
	}

	@Override
	public void setZOrder(int order) {
		zOrder = order;
	}

	@Override
	public int getZOrder() {
		return zOrder;
	}



	@Override
	public String toString()
	{
		return String.format("%s (%s)", getName(), getPosition().toString());
	}

}
