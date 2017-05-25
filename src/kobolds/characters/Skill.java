package kobolds.characters;

public enum Skill 
{
	Acrobatics(Attribute.DEX),
	AnimalHandling(Attribute.WIS),
	Arcana(Attribute.INT),
	Athletics(Attribute.STR),
	Deception(Attribute.CHA),
	History(Attribute.INT),
	Insight(Attribute.WIS),
	Intimidation(Attribute.CHA),
	Investigation(Attribute.INT),
	Medicine(Attribute.INT),
	Nature(Attribute.INT),
	Perception(Attribute.WIS),
	Performance(Attribute.CHA),
	Persuasion(Attribute.CHA),
	Religion(Attribute.INT),
	SleightOfHand(Attribute.DEX),
	Stealth(Attribute.DEX),
	Survival(Attribute.WIS);

	public final int attribute;
	public static final int NUM = 18;
	Skill(int attribute)
	{
		this.attribute = attribute;
	}

}
