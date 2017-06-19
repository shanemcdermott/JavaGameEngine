package genesis.world;

import java.awt.Color;

public enum Biome 
{
	
	NULL(BiomeMajor.NONE, Color.BLACK),
	OCEAN(BiomeMajor.AQUATIC, new Color(25,75,200)),
	INTERTIDAL(BiomeMajor.AQUATIC, new Color(25,75,150)),
	CORAL_REEF(BiomeMajor.AQUATIC, new Color(25,150,250)),
	ESTUARY(BiomeMajor.AQUATIC, new Color(100,75,100)),
	POND(BiomeMajor.AQUATIC, new Color(25,100,175)),
	LAKE(BiomeMajor.AQUATIC, new Color(0,75,250)),
	RIVER(BiomeMajor.AQUATIC, new Color(0,75,250)),
	STREAM(BiomeMajor.AQUATIC, new Color(0,75,250)),
	WETLAND(BiomeMajor.AQUATIC, new Color(50, 150, 150)),
	TROPICAL_FOREST(BiomeMajor.FOREST, new Color(50,150,125)),
	TEMPERATE_FOREST(BiomeMajor.FOREST, new Color(25, 150, 75)),
	BOREAL_FOREST(BiomeMajor.FOREST, new Color(75,150,100));
	
	private BiomeMajor 	majorType;
	private Color	color;
	
	Biome(BiomeMajor major, Color color)
	{
		this.majorType = major;
		//this.climate = climate;
		this.color = color;
	}

	public BiomeMajor getMajorType()
	{
		return majorType;
	}
	
	public Color getColor()
	{
		return color;
	}
}
