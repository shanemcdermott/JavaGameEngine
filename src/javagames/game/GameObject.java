package javagames.game;

import java.awt.*;


import javagames.g2d.Drawable;
import javagames.util.Direction;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.CollisionChannel;

public class GameObject extends BoundsObject {


	protected float rotation;
	protected float rotPerSec;
	protected Direction direction;
	protected Vector2f velocity;
	protected Vector2f scale;


	public GameObject() {
		super();
		velocity = new Vector2f();
		scale = new Vector2f();
		rotation = 0.f;
		zOrder = 0;
		setName("GameObject");
	}

	public GameObject(GameObject toCopy) {
		super(toCopy);
		setBounds(toCopy.getBounds());
		setPosition(toCopy.getPosition());
		setVelocity(new Vector2f());
		setScale(toCopy.getScale());
		setRotation(toCopy.rotation);
		setDirection(toCopy.getDirection());
		setZOrder(toCopy.getZOrder());
		setColor(toCopy.getColor());
	}

	@Override
	public void onOverlap(BoundsObject otherObject, float delta) {
		super.onOverlap(otherObject, delta);

		if (otherObject.getBounds().getChannel() == CollisionChannel.SOLID) {
			setPosition(getPosition().sub(velocity.mul(delta)));
			setVelocity(new Vector2f());
		} else if (otherObject.getBounds().getChannel() == CollisionChannel.NONE)
			setColor(Color.RED);
	}

	public void setVelocity(Vector2f vel) {
		velocity = vel;
	}

	public void setRotation(float angle) {
		rotation = angle;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean isMoving() {
		return !velocity.equals(new Vector2f());
	}

	public void move(Direction direction, float speed) {
		setDirection(direction);
		setVelocity(direction.getV().mul(speed));
	}

	public Vector2f getScale() {
		return scale;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public void update(float deltaTime) {
		setPosition(getPosition().add(velocity.mul(deltaTime)));
		rotation += rotPerSec * deltaTime;
	}


}
