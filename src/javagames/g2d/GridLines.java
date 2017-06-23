package javagames.g2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import genesis.editor.EditorFramework;
import genesis.editor.tool.EditorTool;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class GridLines extends EditorTool
{
	private BoundingBox bbox;
	private int numX;
	private int numY;
	private Vector2f line;
	
	
	public GridLines(EditorFramework editor)
	{
		super(editor);
		bbox = new BoundingBox(new Vector2f(),editor.getWorldSize());
		setNumLines(4,4);
		setZOrder(Integer.MAX_VALUE);
	}
	
	public void setNumLines(int x, int y)
	{
		numX = x;
		numY = y;
		line = new Vector2f(bbox.getWidth() / numX, bbox.getHeight() / numY);
		System.out.printf("Grid line count: %d x %d\n", x, y);
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		super.render(g,view);
		g.setColor(Color.BLACK);
		Point p = view.mul(getPosition()).toPoint();
		StringBuffer str = new StringBuffer();
	
		Vector2f p0 = bbox.getMin();
		p0.y += line.y;
		Vector2f p1 = p0.add(new Vector2f(bbox.getWidth(),0.f));
		
		for(int y = 0; y < numY; y++)
		{
			Point line0 = view.mul(p0).toPoint();
			Point line1 = view.mul(p1).toPoint();
			g.drawLine(line0.x,line0.y,line1.x,line1.y);
			str.append(p0.toString());
			str.append(p1.toString());
			p0.y+=line.y;
			p1.y+=line.y;
		}
		
		p0 = bbox.getMin();
		p0.x += line.x;
		p1 = p0.add(new Vector2f(0.f,bbox.getHeight()));
		for(int x = 0; x < numX; x++)
		{
			Point line0 = view.mul(p0).toPoint();
			Point line1 = view.mul(p1).toPoint();
			g.drawLine(line0.x,line0.y,line1.x,line1.y);
			str.append(p0.toString());
			str.append(p1.toString());
			p0.x+=line.x;
			p1.x+=line.x;
		}
		
		//g.drawString(str.toString(), p.x, p.y);
	}

}
