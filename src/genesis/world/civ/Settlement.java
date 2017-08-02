package genesis.world.civ;

import javagames.world.Dungeon;

public class Settlement extends Dungeon
{
	private int population;
	private float technology;
	private float economy;
	private float culture;
	private float education;
	
	public Settlement(String name)
	{
		super(name);
		
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public float getTechnology() {
		return technology;
	}

	public void setTechnology(float technology) {
		this.technology = technology;
	}

	public float getEconomy() {
		return economy;
	}

	public void setEconomy(float economy) {
		this.economy = economy;
	}

	public float getCulture() {
		return culture;
	}

	public void setCulture(float culture) {
		this.culture = culture;
	}

	public float getEducation() {
		return education;
	}

	public void setEducation(float education) {
		this.education = education;
	}

	
	
}
