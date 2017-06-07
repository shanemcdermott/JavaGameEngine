package javagames.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingShape;

public class GameObject implements Drawable
{
	
	private String name;
	private Map<String, Object> components;
	
	protected BoundingShape bounds;
	
	protected Matrix3x3f transform;
	protected Vector2f scale;
	protected float rotation;
	protected Vector2f position;


	public GameObject()
	{
		name = "GameObject";
		transform = Matrix3x3f.identity();
		position = new Vector2f();
		rotation = 0.f;
		scale = new Vector2f(1.f,1.f);
		components = Collections.synchronizedMap(new HashMap<String, Object>());
		
	}
	
	public Object getComponent(String name) {
		return components.get(name);
	}

	public Object removeComponent(String name) {
		return components.remove(name);
	}

	public void setComponent(String name, Object component) {
		components.put(name, component);
	}

	public Set<String> getComponentNames() {
		return components.keySet();
	}

	
	public void setPosition(Vector2f pos)
	{
		this.position=pos;
	}
	
	public Vector2f getPosition()
	{
		return position;
	}
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	
	public void update(float deltaTime)
	{
		updateTransform(deltaTime);
		
	}
	
	public void updateTransform(float deltaTime) 
	{
		// TODO Auto-generated method stub
		transform = Matrix3x3f.identity();
		transform = transform.mul(Matrix3x3f.scale(scale));
		transform = transform.mul(Matrix3x3f.translate(position));
		transform = transform.mul(Matrix3x3f.rotate(rotation));
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport) 
	{
		if(bounds!=null)
			bounds.render(g, viewport);
		
	}
}
