package kobolds.characters.races;

import kobolds.characters.*;

public abstract class CharacterRace 
{
	public String name;
	public Trait[] traits;
	protected int[] abilityScores = new int[Attribute.NUM];
	protected int lifespan;
	protected Alignment[] alignments;
	protected CharacterSize size;
	protected int speed;
	protected Language[] languages;
	
}
