package genesis.cell;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Comparator;

import com.sun.corba.se.impl.io.TypeMismatchException;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class CellEdge implements Comparator
{
	public Cell cell1;
	public Cell cell2;
	public Vector2f start;
	public Vector2f end;

	public CellEdge()
	{}
	
	public CellEdge(Vector2f start, Vector2f end)
	{
		this(null,null,start,end);
	}
	
	public CellEdge(Cell cell1, Cell cell2, Vector2f start, Vector2f end)
	{
		this.cell1 = cell1;
		this.cell2 = cell2;
		this.start = start;
		this.end = end;
	}
	
	public CellEdge(CellEdge toCopy)
	{
		this.cell1 = toCopy.cell1;
		this.cell2 = toCopy.cell2;
		this.start = toCopy.start;
		this.end = toCopy.end;
	}
	
	public boolean setPoints(Vector2f start, Vector2f end)
	{
		if(start.equals(end)) return false;
		
		this.start = start;
		this.end = end;	
		return true;
	}
	
	public void setCells(Cell cell1, Cell cell2)
	{
		this.cell1 = cell1;
		this.cell2 = cell2;
		
		if(this.cell1 != null)
			this.cell1.addEdge(this);
		
		if(this.cell2 != null)
			this.cell2.addEdge(this);
	}
	
	public boolean setPointsAndCells(Point start, Point end, Cell cell1, Cell cell2)
	{
		return setPointsAndCells(new Vector2f(start), new Vector2f(end), cell1, cell2);
	}
	
	public boolean setPointsAndCells(Vector2f start, Vector2f end, Cell cell1, Cell cell2)
	{
		if(setPoints(start,end))
		{
			setCells(cell1,cell2);
			return true;
		}
		return false;
	}
	
	public void swap()
	{
		Vector2f temp = start;
		start = end;
		end = temp;
	}
	
	public Cell getOtherCell(Cell c)
	{
		if(c.equals(cell1))
			return cell2;
		return cell1;
	}
	
	public void setOtherCell(Cell c, Cell other)
	{
		if(c.equals(cell1))
		{
			if(cell2 != null)
			{
				cell2 = other;
			}
		}
		else if(c.equals(cell2))
		{
			if(cell1 != null)
			{
				cell1 = other;
			}
		}
	}
	
	public boolean isBorder()
	{
		return cell1 == null || cell2 == null;
	}
	
	public void render(Graphics g, Matrix3x3f view)
	{
		if(start == null || end == null) return;
		Point p1 = view.mul(start).toPoint();
		Point p2 = view.mul(end).toPoint();
		g.drawLine(p1.x,p1.y,p2.x,p2.y);
	}
	
	public void render(Graphics g)
	{
		if(start == null || end == null) return;
		
		g.drawLine((int)start.x,(int)start.y,(int)end.x,(int)end.y);
	}
	
	public float startDistSq()
	{
		return start.x * start.x + start.y * start.y;
	}
	
	public float endDistSq()
	{
		return end.x * end.x + end.y * end.y;
	}
	
	public float lenSq()
	{
		return startDistSq() + endDistSq();
	}
	
	public float distFromPointSq(Vector2f p)
	{
		return (start.x - p.x) * (start.x - p.x) + (start.y - p.y) * (start.y - p.y) + (end.x - p.x) * (end.x - p.x) + (end.y - p.y) * (end.y - p.y);
	}
	
	@Override
	public String toString()
	{
		return String.format("[%s - %s]", start.toString(), end.toString());
	}

	@Override
	public int compare(Object centerPoint, Object edge2) 
	{
		Vector2f center = (Vector2f)centerPoint;
		CellEdge e1 = (CellEdge)edge2;
		if(center == null || e1 == null) throw new TypeMismatchException();
		
		
		return (int)(this.distFromPointSq(center) - e1.distFromPointSq(center));
	}
}
