package javagames.world;

import java.awt.Color;

import javagames.game.GameObject;
import javagames.util.ResourceLoader;
import javagames.util.Sprite;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class Construct extends GameObject 
{
	public Construct()
	{
		super();
		setName("Construct");
		bounds = new BoundingBox(0.5f);	
		bounds.fill=true;
		setColor(Color.ORANGE);
		try {
			setSprite(new Sprite(ResourceLoader.loadImage(getClass(), "Tree.png"), new Vector2f(-0.25f,-00.25f), new Vector2f(0.25f, 0.25f)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
