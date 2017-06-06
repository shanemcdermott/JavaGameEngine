package genesis.cell;

import java.awt.Color;
import java.awt.Graphics;

public class ColoredEdge extends CellEdge 
{
	public Color color;
	
	public ColoredEdge(CellEdge e)
	{
		this(e, Color.BLACK);
	}
	
	public ColoredEdge(CellEdge e, Color color)
	{
		this.cell1 = e.cell1;
		this.cell2 = e.cell2;
		this.start = e.start;
		this.end = e.end;
		this.color = color;
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
	}

}
