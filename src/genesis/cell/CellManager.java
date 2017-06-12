package genesis.cell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import genesis.editor.WorldEditor;
import genesis.editor.swing.SwingConsole;
import javagames.g2d.Drawable;
import javagames.util.GMath;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Utility;
import javagames.util.Vector2f;


public class CellManager implements Drawable
{
	private static final int MAX_RANGE = 4;
	private ArrayList<Cell> cells;
	private Random random;
	private boolean bOutlineCurrentCell = true;
	private boolean bDisplayCellNumbers = true;
	
	private int currentCellIndex = 0;
	private int neighborRange = 0;
	

	public CellManager(Random random, ArrayList<Cell> cells)
	{
		this.random = random;
		this.cells = cells;
	}
	
	
	public void reset()
	{
		cells = new ArrayList<Cell>();
	}
	
	public ArrayList<Cell> getCells()
	{
		return cells;
	}
	
	public void addNeighborRange(int range)
	{
		neighborRange = GMath.clamp(neighborRange + range, 0, MAX_RANGE);
	}
	
	public void sortCells(Point origin)
	{
		Collections.sort(cells, (a,b) ->  a.compare(b, origin));
	}

	public void setCellIndex(int index)
	{
		if(index < 0 || index >= cells.size())
		{
			System.out.println("Invalid Cell Index!");
		}
		else
		{
			currentCellIndex = index;
			System.out.println(cells.get(currentCellIndex).toString());
		}
	}
	
	public int getNumCells()
	{
		return cells.size();
	}
	
	public void addCell(Cell c)
	{
		Cell existing = getCellAt(c.getPosition().toPoint());
		if(existing == null) 
			cells.add(c);
		else if(existing.merge(c))
			System.out.println("merged");
		else
		{
			System.out.println("Failed to merge.");
			cells.add(c);
		}
	}
	
	public int getCellIndexContainingPoint(Point p)
	{
		//sortCells(p);
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i).toPolygon().contains(p))
				return i;
		}
		return -1;
	}
	
	public Cell getCellAt(Point p)
	{
		for(Cell c : cells)
		{
			if(c.toPolygon().contains(p))
				return c;
		}
		
		return null;
	}
	
	public Cell getCellAt(int index)
	{
		return cells.get(index);
	}
	
	public void render(Graphics g, Matrix3x3f viewport)
	{
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i) == null) continue;
			cells.get(i).render(g, viewport);
		}
		
	}
	
	public void render(Graphics g)
	{
		
		drawCells(g);
		
		if(bDisplayCellNumbers)
			drawCellNumbers(g);
		if(bOutlineCurrentCell)
			outlineCell(g, currentCellIndex);
		
		
	}
	
	public void drawCells(Graphics g)
	{
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i) == null) continue;
			cells.get(i).render(g);
		}
	}
	
	public void drawCellNumbers(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i = 0; i < cells.size(); i++)
		{
			if(cells.get(i) == null) continue;
			Utility.drawString(g, (int)cells.get(i).center.x, (int)cells.get(i).center.y, String.format("%d", i));	
		}
	}
	
	public void outlineCell(Graphics g, int index)
	{
		if(cells.get(index) != null)
		{
			cells.get(index).drawOutline(g, Color.CYAN);
		}
	}
}
