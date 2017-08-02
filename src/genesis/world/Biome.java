package genesis.world;

import java.awt.Color;

public enum Biome 
{
	NULL(BiomeMajor.NONE, Color.BLACK, 0,0),
	OCEAN(BiomeMajor.AQUATIC, new Color(25,75,200), 0.f, 0.2f),
	INTERTIDAL(BiomeMajor.AQUATIC, new Color(25,75,150),0.2f, 0.22f),
	CORAL_REEF(BiomeMajor.AQUATIC, new Color(25,150,250),0.2f,0.219f),
	ESTUARY(BiomeMajor.AQUATIC, new Color(100,75,100),0.22f, 0.25f),
	POND(BiomeMajor.AQUATIC, new Color(25,100,175), 0.25f, 0.8f),
	LAKE(BiomeMajor.AQUATIC, new Color(0,75,250), 0.25f, 0.8f),
	RIVER(BiomeMajor.AQUATIC, new Color(0,75,250), 0.25f, 0.8f),
	STREAM(BiomeMajor.AQUATIC, new Color(0,75,250),0.25f, 0.8f),
	WETLAND(BiomeMajor.AQUATIC, new Color(50, 150, 150), 0.25f, 0.3f),
	TROPICAL_FOREST(BiomeMajor.FOREST, new Color(50,150,125), 0.3f,0.7f),
	TEMPERATE_FOREST(BiomeMajor.FOREST, new Color(25, 150, 75), 0.4f, 0.8f),
	BOREAL_FOREST(BiomeMajor.FOREST, new Color(75,150,100), 0.7f, 0.95f),
	HOT_AND_DRY_DESERT(BiomeMajor.DESERT, new Color(203,180,122), 0.3f,0.6f),
	COASTAL_DESERT(BiomeMajor.DESERT, new Color(203,180,122), 0.25f,0.3f),
	SEMIARID_DESERT(BiomeMajor.DESERT, new Color(203,180,122), 0.6f,0.8f),
	COLD_DESERT(BiomeMajor.DESERT, new Color(203,180,122), 0.8f,0.1f),
	SAVANNAH(BiomeMajor.GRASSLAND, new Color(125,251,222), 0.25f,0.3f),
	TEMPERATE_GRASSLAND(BiomeMajor.GRASSLAND, new Color(125,251,222), 0.3f,0.55f),
	ARCTIC_TUNDRA(BiomeMajor.TUNDRA, new Color(200,240,200),0.55f, 0.8f),
	ARCTIC_ALPINE(BiomeMajor.TUNDRA, new Color(200,240,200),0.55f, 0.8f)	;
	
	private BiomeMajor 	majorType;
	private Color	color;
	private float heightMin;
	private float heightMax;
	
	Biome(BiomeMajor major, Color color, float heightMin, float heightMax)
	{
		this.majorType = major;
		//this.climate = climate;
		this.color = color;
		this.heightMin = heightMin;
		this.heightMax = heightMax;
	}

	public BiomeMajor getMajorType()
	{
		return majorType;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public float getHeightMin()
	{
		return heightMin;
	}
	
	public float getHeightMax()
	{
		return heightMax;
	}
	
	public boolean canExistIn(float height)
	{
		return height >= heightMin && height <= heightMax;
	}
	
}
