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
		CellEdge edge = new CellEdge(edgeStart,edgeEnd);
		edge.setCells(cell, null);
				
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.add(sizeY);
		edge = new CellEdge(edgeStart, edgeEnd);
		edge.setCells(cell, null);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeX);
		edge = new CellEdge(edgeStart, edgeEnd);
		edge.setCells(cell, null);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeY);
		edge = new CellEdge(edgeStart, edgeEnd);
		edge.setCells(cell, null);
		
		return cell;
	}
	
	public static Cell makeNeighborCell(Vector2f size, CellEdge neighborEdge, Cell neighbor)
	{
		if(!neighborEdge.isBorder())
		{
			System.out.println("Overriding cell edge!");
		}
		
		Vector2f center = neighborEdge.midPoint();
		Vector2f nPos = neighbor.getPosition();
		if(center.x==nPos.x)
		{
			if(center.y < nPos.y)
			{
				center.y = nPos.y-size.y;
			}
			else
			{
				center.y = nPos.y+size.y;
			}
		}
		else if(center.x < nPos.x)
		{
			center.x = nPos.x-size.x;
		}
		else
		{
			center.x = nPos.x+size.x;
		}
			
		
		Cell cell = new Cell(center);
				
		Vector2f sizeX = new Vector2f(size);
		sizeX.y=0.f;
		Vector2f sizeY = new Vector2f(size);
		sizeY.x = 0.f;
		
		Vector2f edgeStart = center.sub(size.mul(0.5f));
		Vector2f edgeEnd = edgeStart.add(sizeX);
		

		
		CellEdge[] edges = new CellEdge[4];
		/*Bottom edge*/
		edges[0] = new CellEdge(edgeStart,edgeEnd);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.add(sizeY);
		
		edges[1] = new CellEdge(edgeStart, edgeEnd);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeX);
		
		edges[2]= new CellEdge(edgeStart, edgeEnd);
		
		edgeStart = new Vector2f(edgeEnd);
		edgeEnd = edgeStart.sub(sizeY);
		
		edges[3] = new CellEdge(edgeStart, edgeEnd);
		
		Vector2f z = new Vector2f();
		for(int i = 0; i < edges.length; i++)
		{
			if(edges[i].compare(z,neighborEdge) == 0)
			{
				System.out.printf("Edge %d linked.\n", i);
				neighborEdge.setCells(neighbor, cell);
			}
			else
				edges[i].setCells(cell, null);
		}
		
		return cell;
		
	}
}
