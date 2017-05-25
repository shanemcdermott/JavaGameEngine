package kobolds.characters;

import javagames.game.GameObject;
import kobolds.characters.Attribute;
import kobolds.characters.Health;
import kobolds.characters.Skill;
import kobolds.characters.races.CharacterRace;

public class GameCharacter extends GameObject 
{
	protected int[] abilityScores = new int[Attribute.NUM];
	protected int[] savingThrows = new int[Attribute.NUM];
	protected int[] skillModifiers = new int[Skill.NUM];
	
	protected int[][] hitDice;
	protected Health hitPoints;
	protected int armorClass;
	protected int initiative;
	protected int speed;
	
	protected CharacterRace race;

}
