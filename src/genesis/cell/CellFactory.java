package genesis.cell;

import javagames.util.Vector2f;

public class CellFactory 
{
	/*Makes a square cell*/
	public static Cell makeCell(Vector2f center, Vector2f size)
	{
		Cell cell = new Cell(center);
		
		Vector2f sizeX = new Vector2f(size);
		sizeX.y=0.f;
		Vector2f sizeY = new Vector2f(size);
		sizeY.x = 0.f;
		
		Vector2f edgeStart = center.sub(size.mul(0.5f));
		Vector2f edgeEnd = edgeStart.add(sizeX);
		
		/*Bottom edge*/
		CellEdge edge = new CellEdge(cell,null,edgeStart,edgeEnd);
		cell.addEdge(edge);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.add(sizeY);
		edge = new CellEdge(cell, null, edgeStart, edgeEnd);
		cell.addEdge(edge);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeX);
		edge = new CellEdge(cell, null, edgeStart, edgeEnd);
		cell.addEdge(edge);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeY);
		edge = new CellEdge(cell, null, edgeStart, edgeEnd);
		cell.addEdge(edge);
		
		return cell;
	}
}
