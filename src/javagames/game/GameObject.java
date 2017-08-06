package javagames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javagames.g2d.Drawable;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.util.Direction;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.util.geom.BoundingShape;

public class GameObject implements Drawable
{
	protected Sprite sprite;
	protected int zOrder;
	protected BoundingShape bounds;
	protected float rotation;
	private float rotationDelta;
	protected Direction direction;
	protected Vector2f velocity;
	protected Vector2f scale;
	private String name;
	private Color color;

	public GameObject() 
	{
		super();
		bounds = new BoundingBox();
		velocity = new Vector2f();
		scale = new Vector2f();
		rotation = 0.f;
		zOrder = 0;
	}

	public BoundingShape getBounds() {
		return bounds;
	}

	public void setBounds(BoundingShape inBounds) {
		bounds = inBounds;
	}

	public void setVelocity(Vector2f vel) {
		velocity = vel;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void move(Direction direction, float speed) {
		setDirection(direction);
		setVelocity(direction.getV().mul(speed));
	}

	public void setPosition(Vector2f pos) {
		bounds.setPosition(pos);
	}

	public Vector2f getPosition() {
		return bounds.getPosition();
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale=scale;
	}

	public void update(float deltaTime) {
		updateSprite(deltaTime);
		setPosition(getPosition().add(velocity.mul(deltaTime)));
		rotation += rotationDelta * deltaTime;

	}

	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		renderSprite(g,view);
		renderBounds(g,view);
	}

	public void renderBounds(Graphics g, Matrix3x3f view) {
		g.setColor(getColor());
		bounds.render(g, view);
	}
	
	public void renderSprite(Graphics g, Matrix3x3f view)
	{
		if(sprite !=null)
			sprite.render((Graphics2D)g, view, getPosition(), rotation);
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


	public void setSprite(Sprite sprite) 
	{
		this.sprite = sprite;
		Vector2f v = sprite.getDimensions();
		bounds = new BoundingBox(v.x, v.y);
	}

	public Sprite getSprite() 
	{
		return sprite;
	}

	public void updateSprite(float deltaTime) 
	{
		if(sprite != null && sprite instanceof SpriteSheet)
		{
			SpriteSheet spr = (SpriteSheet)sprite;
			if(!velocity.equals(new Vector2f()))
				spr.startAnimation(getDirection().getAnim());
			spr.update(deltaTime);
		}
	}
}
