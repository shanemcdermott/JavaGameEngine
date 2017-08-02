package javagames.room;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;
import genesis.grammar.Transformable;
import genesis.world.Biome;
import genesis.world.BiomeMajor;
import genesis.world.Ecosystem;
import javagames.game.GameObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class GameRoom extends GameObject implements Transformable <Dungeon> 
{
	
	private Ecosystem ecosystem;
	public Vector<GameObject> contents;
	public Vector<GameRoom> neighbors;
	private Vector<RoomComponent> components;
	public HashMap<String, Boolean> flags;
	private Color elevColor;
	private float elevation;
	
	public GameRoom(Vector2f location)
	{
		super();
		bounds = new BoundingBox();	
		setPosition(new Vector2f(location));
		bounds.fill=true;
		contents = new Vector<GameObject>();
		neighbors = new Vector<GameRoom>();
		ecosystem = new Ecosystem();
		setElevation(0.f);
		flags = new HashMap<String,Boolean>();
		components = new Vector<RoomComponent>();
	}

	public boolean hasComponent(Class<?> componentClass)
	{
		for(RoomComponent rc : components)
		{
			if(componentClass.isInstance(rc))
				return true;
		}
		
		return false;
	}
	
	public void addUniqueComponent(RoomComponent component)
	{
		if(hasComponent(component.getClass())) return;
		addComponent(component);
	}
	
	public void addComponent(RoomComponent component)
	{
			
		try
		{
			Method[] compMethods = component.getClass().getMethods();
			System.out.print("Methods: ");
			for(Method m : compMethods)
			{
				System.out.println(m.getName());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		components.add(component);                                                                       
	}
	
	public boolean getFlag(String name)
	{
		if(flags.containsKey(name))
			return flags.get(name);
		return false;
	}
	
	public void setFlag(String name, boolean value)
	{
		flags.put(name, value);
	}
	
	public Ecosystem getEcosystem()
	{
		return ecosystem;
	}
	
	public Biome getBiome()
	{
		return ecosystem.getBiome();
	}
	
	public void setBiome(Biome biome)
	{
		this.ecosystem.setBiome(biome);
		setColor(ecosystem.getColor());
	}
	
	public void transform(Dungeon context)
	{	
		Biome[] options = ecosystem.getBiomeOptions(elevation);
		if(options.length>0)
		{
			Random r = new Random();
			if(elevation>0.25f)
			{
				for(int i =0; i < options.length;i++)
				{
					if(options[i].getMajorType()==BiomeMajor.AQUATIC)
					{
						options[i] = options[r.nextInt(options.length)];
					}
				}
			}
			
			setBiome(options[r.nextInt(options.length)]);
		}
	}
	
	public boolean isEmpty()
	{
		return contents.isEmpty();
	}
	
	public boolean contains(Vector2f point)
	{
		return bounds.contains(point);
	}
	
	public void setNeighbors(Vector<GameRoom> neighbors)
	{
		this.neighbors=neighbors;
	}
	
	public Vector<GameRoom> getNeighbors()
	{
		return neighbors;
	}
	
	public void addNeighbor(GameRoom neighbor)
	{
		neighbors.add(neighbor);
	}
	
	public void addGameObject(GameObject o)
	{
		if(bounds.contains(o.getPosition()))
			contents.add(o);
	}
	
	public void resize(Vector2f dimensions)
	{
		resize(dimensions.x,dimensions.y);
	}
	
	public void resize(float width, float height)
	{
		BoundingBox aabb = getAABB();
		if(aabb!=null)
		{
			aabb.resize(width, height);
		}
	}
	
	public float getWidth()
	{
		return getAABB().getWidth();
	}
	
	public float getHeight()
	{
		return getAABB().getHeight();
	}
	
	public Vector2f getDimensions()
	{
		return new Vector2f(getWidth(), getHeight());
	}
	
	public BoundingBox getAABB()
	{
		return (BoundingBox)bounds;
	}
	
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport)
	{
		super.render(g, viewport);
	
		for(GameObject o : contents)
			o.render(g, viewport);
	}
	

	public Color getElevColor()
	{
		return elevColor;
	}
	
	public float getElevation()
	{
		return elevation;
	}
	
	public void setElevation(float elevation)
	{
		this.elevation=elevation;
		elevColor = new Color(elevation,elevation,elevation);
	}
	
	public void renderHeight(Graphics g, Matrix3x3f view)
	{
		g.setColor(elevColor);
		bounds.render(g, view);
	}
}
