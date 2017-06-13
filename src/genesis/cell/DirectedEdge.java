package genesis.cell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class DirectedEdge extends CellEdge 
{

	public DirectedEdge() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DirectedEdge(Cell cell1, Cell cell2, Vector2f start, Vector2f end) {
		super(cell1, cell2, start, end);
		// TODO Auto-generated constructor stub
	}

	public DirectedEdge(CellEdge toCopy) {
		super(toCopy);
		// TODO Auto-generated constructor stub
	}

	public DirectedEdge(Vector2f start, Vector2f end) {
		super(start, end);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		super.render(g, view);
		Vector2f m = midPoint();
		if(!isBorder())
		{
			
			Point v1 = view.mul(cell1.getPosition().mid(m)).toPoint();
			Point v2 = view.mul(cell2.getPosition().mid(m)).toPoint();
			g.setColor(cell1.color);
			g.drawLine(v1.x,v1.y,v2.x,v2.y);
		}
		g.setColor(Color.BLACK);
		Point p = view.mul(m).toPoint();
		g.drawString("D", p.x, p.y);
	}
}
