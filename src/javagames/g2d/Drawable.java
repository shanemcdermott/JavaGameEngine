package javagames.g2d;

import java.awt.Graphics;

import javagames.util.Matrix3x3f;

public interface Drawable 
{
	void render(Graphics g, Matrix3x3f viewport); //Draw the object with passed Graphics
}
