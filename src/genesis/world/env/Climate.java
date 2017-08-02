package genesis.world.env;

import java.awt.Color;

public class Climate 
{
	private float soil;
	private float tempScale;
	private float rainfall;
	private Biome biome;
	
	public Climate()
	{
		soil = 0.f;
		tempScale = 1.f;
		rainfall = 0.5f;
		biome = Biome.NULL;
	}
	
	public float getSoil() {
		return soil;
	}
	public void setSoil(float soil) {
		this.soil = soil;
	}
	
	public float getTemperature()
	{
		return tempScale;
	}
	
	public void setTemperatureScale(float tempScale){
		this.tempScale=tempScale;
	}
	
	public float getRainfall() {
		return rainfall;
	}
	
	public void setRainfall(float rainfall) {
		this.rainfall = rainfall;
	}
	
	public Biome getBiome() {
		return biome;
	}
	
	public void calculateBiome() {
		
		biome = Biome.NULL;
		
	}
	
	public Color getColor()
	{
		return biome.getColor();
	}
	
	
}
