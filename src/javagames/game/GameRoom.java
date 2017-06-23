package javagames.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import genesis.grammar.RoomState;
import genesis.grammar.Transformable;
import genesis.world.Biome;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;
import javagames.world.Dungeon;

public class GameRoom extends GameObject implements Transformable <Dungeon> 
{
	
	private RoomState state;
	private Biome biome;
	public Vector<GameObject> contents;
	public Vector<GameRoom> neighbors;
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
		state = RoomState.NULL;
		biome = Biome.NULL;
		setElevation(0.f);
	}

	public Biome getBiome()
	{
		return biome;
	}
	
	public void setBiome(Biome biome)
	{
		this.biome=biome;
		setColor(biome.getColor());
	}
	
	public void setState(RoomState state)
	{
		this.state = state;
		setColor(state.color());
	}
	
	public void transform(Dungeon context)
	{
		/*	
		switch(state)
		{
			case NULL:
				setState(RoomState.WATER);
				break;
			case WATER:
				setState(RoomState.LAND);
				break;
			case LAND:
				setState(RoomState.WATER);
				break;
		}
		*/
		switch(biome)
		{
		case BOREAL_FOREST:
			
			break; 
		case CORAL_REEF: case ESTUARY: case OCEAN: case NULL:
			break;
	
		case INTERTIDAL:
			setBiome(Biome.WETLAND);
			break;
		case LAKE:
			setBiome(Biome.POND);
			break;
		case POND:
			setBiome(Biome.LAKE);
			break;
		case RIVER:
			setBiome(Biome.STREAM);
			break;
		case STREAM:
			setBiome(Biome.RIVER);
			break;
		case TEMPERATE_FOREST:
			setBiome(Biome.BOREAL_FOREST);
			break;
		case TROPICAL_FOREST:
			setBiome(Biome.TEMPERATE_FOREST);
			break;
		case WETLAND:
			setBiome(Biome.TROPICAL_FOREST);
			break;
		default:
			break;
		
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
