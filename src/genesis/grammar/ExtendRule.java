package genesis.grammar;

import java.util.ArrayList;
import java.util.Random;

import genesis.cell.Cell;
import genesis.cell.CellEdge;
import genesis.cell.CellFactory;
import javagames.util.Vector2f;

public class ExtendRule 
{
	private Vector2f size;
	private Cell root;
	private Cell leaf;
	private Random rng;
	
	public ExtendRule(Cell root, Vector2f size)
	{
		this(root,size,new Random());
	}
	
	public ExtendRule(Cell root, Vector2f size, Random rng)
	{
		this.root = root;
		this.size = size;
		this.rng = rng;
		leaf = null;
	}
	
	public void execute()
	{
		ArrayList<CellEdge> edges = root.getEdges();
		if(edges.isEmpty() || edges.size() == root.numNeighbors()) return;
		
		int index = rng.nextInt(edges.size());
		
		while(!edges.get(index).isBorder())
			index = rng.nextInt(edges.size());
		
		leaf = CellFactory.makeNeighborCell(size, edges.get(index), root);
		
	}
	
	public Cell getResults()
	{
		return leaf;
	}
	
}
