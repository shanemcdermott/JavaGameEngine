package genesis.grammar;

import java.awt.Color;

import genesis.world.env.Biome;

public enum RoomState 
{
	NULL(Biome.NULL),
	WATER(Biome.OCEAN),
	
	LAND(Biome.BOREAL_FOREST);
	
	private final Biome biome;
	
	
	RoomState(Biome b)
	{
		biome = b;
	}

	public Color color()
	{
		return biome.getColor();
	}
	
}
