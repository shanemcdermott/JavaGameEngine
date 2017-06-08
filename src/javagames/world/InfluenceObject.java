package javagames.world;

import java.awt.Graphics2D;

import java.awt.Color;
import java.awt.Graphics;

import javagames.game.GameObject;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingCircle;
import javagames.util.geom.BoundingShape;

public class InfluenceObject extends GameObject
{
	public InfluenceObject()
	{
		bounds=new BoundingCircle(new Vector2f(), 0.1f);
		color = new Color(0,255,0,100);
	}
	


	

}
