package javagames.g2d;

import java.awt.Graphics;

import javagames.util.Matrix3x3f;

public interface Drawable 
{
	public void render(Graphics g, Matrix3x3f view);
}
