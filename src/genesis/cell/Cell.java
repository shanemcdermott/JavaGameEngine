package genesis.cell;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.sun.corba.se.impl.io.TypeMismatchException;

import javagames.g2d.Drawable;
import javagames.util.Matrix3x3f;
import javagames.util.Utility;
import javagames.util.Vector2f;


//Quickhull source: Alexander Hrishov's website
//URL: http://www.ahristov.com/tutorial/geometry-games/convex-hull.html

public class Cell implements Comparator, Drawable
{

	private ArrayList<CellEdge> edges;
	//private Map<CellEdge, Color> edges;
	private boolean bIsBorderCell;
	public Vector2f center;
	public Color color;
	private Map<String, Object> attributes;
	
	public Cell(Point center)
	{
		this(new Vector2f(center));
	}
	
	public Cell(Vector2f center)
	{
		bIsBorderCell = false;
		this.center = center;
		color = Color.WHITE;
		//this.edges = Collections.synchronizedMap(new HashMap<CellEdge, Color>());
		edges = new ArrayList<CellEdge>();
		attributes = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	public Object removeAttribute(String name) {
		return attributes.remove(name);
	}

	public void setAttribute(String name, Object attribute) {
		attributes.put(name, attribute);
	}
	
	public boolean hasAttribute(String name)
	{
		return attributes.containsKey(name);
	}
	
	public Set<String> getAttributeNames() {
		return attributes.keySet();
	}
	
	public void addEdge(CellEdge edge)
	{
		edges.add(edge);
		//edges.put(edge, Color.BLACK);
		if(edge.isBorder())
		{
			bIsBorderCell = true;
		}
	}
	
	public CellEdge getClosestEdge(Vector2f point)
	{
		CellEdge closest = edges.get(0);
		float dist = closest.distFromPointSq(point);
		for(CellEdge e : edges)
		{
			float d = e.distFromPointSq(point);
			if(d < dist)
			{
				dist = d;
				closest = e;
			}
		}
		
		return closest;
	}
	
	public int numNeighbors()
	{
		return edges.size() - getBorderEdges().size();
	}
	
	public int countNeighborsWithAttribute(String key, Object... values)
	{
		ArrayList<Cell> neighbors = getNeighbors();
		int sum = 0;
		for(int i = 0; i < neighbors.size(); i++)
		{
			Object v = neighbors.get(i).getAttribute(key);
			for(Object value : values)
				if(v != null && v.equals(value))
					sum++;
		}
		return sum;
	}
	
	public ArrayList<CellEdge> getBorderEdges()
	{
		ArrayList<CellEdge> borders = new ArrayList<CellEdge>();
		for(int i = 0; i < edges.size(); i++)
		{
			CellEdge e = edges.get(i);
			if(e.isBorder())
				borders.add(e);
		}
		
		return borders;
	}
	
	public ArrayList<Cell> getNeighbors()
	{
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		for(int i = 0; i < edges.size(); i++)
		{
			 Cell c = edges.get(i).getOtherCell(this);
			 if(c != null)
				 neighbors.add(c);
		}
		return neighbors;
	}
	
	public boolean isBorderCell()
	{
		return bIsBorderCell;
	}

	public int distFromCenterSq(Point p)
	{
		return (int)((center.x - p.x) * (center.x - p.x) + (center.y - p.y) * (center.y - p.y)); 
	}

	public ArrayList<Point> quickHull()
	{
		return quickHull(getPoints());
	}
	
	public ArrayList<Point> quickHull(ArrayList<Point> points)
	{
		ArrayList<Point> convexHull = new ArrayList<Point>();
		if (points.size() < 3)
			return (ArrayList) points.clone();

		int minPoint = -1, maxPoint = -1;
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		for (int i = 0; i < points.size(); i++)
		{
			if (points.get(i).x < minX)
			{
				minX = points.get(i).x;
				minPoint = i;
			}
			if (points.get(i).x > maxX)
			{
				maxX = points.get(i).x;
				maxPoint = i;
			}
		}
		Point A = points.get(minPoint);
		Point B = points.get(maxPoint);
		convexHull.add(A);
		convexHull.add(B);
		points.remove(A);
		points.remove(B);

		ArrayList<Point> leftSet = new ArrayList<Point>();
		ArrayList<Point> rightSet = new ArrayList<Point>();

		for (int i = 0; i < points.size(); i++)
		{
			Point p = points.get(i);
			if (pointLocation(A, B, p) == -1)
				leftSet.add(p);
			else if (pointLocation(A, B, p) == 1)
				rightSet.add(p);
		}
		hullSet(A, B, rightSet, convexHull);
		hullSet(B, A, leftSet, convexHull);

		return convexHull;
	}

	public int distance(Point A, Point B, Point C)
	{
		int ABx = B.x - A.x;
		int ABy = B.y - A.y;
		int num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
		if (num < 0)
			num = -num;
		return num;
	}

	public void hullSet(Point A, Point B, ArrayList<Point> set, ArrayList<Point> hull)
	{
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0)
			return;
		if (set.size() == 1)
		{
			Point p = set.get(0);
			set.remove(p);
			hull.add(insertPosition, p);
			return;
		}
		int dist = Integer.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++)
		{
			Point p = set.get(i);
			int distance = distance(A, B, p);
			if (distance > dist)
			{
				dist = distance;
				furthestPoint = i;
			}
		}
		Point P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition, P);

		// Determine who's to the left of AP
		ArrayList<Point> leftSetAP = new ArrayList<Point>();
		for (int i = 0; i < set.size(); i++)
		{
			Point M = set.get(i);
			if (pointLocation(A, P, M) == 1)
			{
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Point> leftSetPB = new ArrayList<Point>();
		for (int i = 0; i < set.size(); i++)
		{
			Point M = set.get(i);
			if (pointLocation(P, B, M) == 1)
			{
				leftSetPB.add(M);
			}
		}
		hullSet(A, P, leftSetAP, hull);
		hullSet(P, B, leftSetPB, hull);

	}

	public int pointLocation(Point A, Point B, Point P)
	{
		int cp1 = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
		if (cp1 > 0)
			return 1;
		else if (cp1 == 0)
			return 0;
		else
			return -1;
	}
	
	public ArrayList<Point> getPoints()
	{
		ArrayList<Point> outPoints = new ArrayList<Point>();
		ArrayList<Vector2f> points = getVectors();

		for(int i = 0; i < points.size(); i++)
		{
			outPoints.add((points.get(i).toPoint()));
		}
		
		return outPoints;
	}

	public ArrayList<Vector2f> getVectors()
	{
		ArrayList<Vector2f> outPoints = new ArrayList<Vector2f>();

		for (CellEdge key : edges) 
		{
		    if(!outPoints.contains(key.start))
		    	outPoints.add(key.start);
		    if(!outPoints.contains(key.end))
		    	outPoints.add(key.end);
		}
		
		return outPoints;
	}

	public Vector2f getPosition()
	{
		return center;
	}
	
	public Polygon toPolygon()
	{
		ArrayList<Point> points = quickHull();

		int[] xPoints = new int[points.size()];
		int[] yPoints = new int[points.size()];
		for(int i = 0; i < xPoints.length; i++)
		{
			xPoints[i] = points.get(i).x;
			yPoints[i] = points.get(i).y;
		}
		
		return new Polygon(xPoints,yPoints, xPoints.length);
	}

	public void setColor(Color c)
	{
		this.color = c;
	}
	
	public void drawEdges(Graphics g, Matrix3x3f view)
	{
		for(CellEdge ce : edges)
		{
			ce.render(g,view);
		}
	}
	
	public void render(Graphics g, Matrix3x3f view)
	{
		g.setColor(color);
		ArrayList<Point> points = quickHull();
		ArrayList<Vector2f> vectors = new ArrayList<Vector2f>();
		for(int i = 0; i < points.size(); i++)
		{
			vectors.add(view.mul(new Vector2f(points.get(i))));
		}
		Utility.fillPolygon((Graphics2D)g, vectors);
		g.setColor(Color.BLACK);
		Utility.drawPolygon(g,vectors);
		//drawEdges(g,view);
	}
	
	public void render(Graphics g)
	{
		g.setColor(color);
		g.fillPolygon(toPolygon());
		drawEdges(g);
	}

	public void drawEdges(Graphics g)
	{
		for (CellEdge c : edges) 
		{
			g.setColor(Color.BLACK);
		    c.render(g);
		}
		
	}
	
	public void drawOutline(Graphics g, Color c)
	{
		g.setColor(c);
		g.drawPolygon(toPolygon());
	}
	
	public String toStringVerbose()
	{
		StringBuffer s = new StringBuffer(String.format("Cell%s{",center.toString()));
		for(CellEdge c : edges)
		{
			s.append(c.toString());
		}
		s.append("}");
		//s.append(String.format("} | %d Neighbors", getNeighbors().size()));
		return s.toString();
	}
	
	@Override
	public String toString()
	{
		/*
		BiomeType b = (BiomeType)getAttribute("BIOME");
		if(b != null)
		{
			return b.toString();
		}
		*/
		return toStringVerbose();
	}
	
	@Override
	public boolean equals(Object o)
	{
		return o instanceof Cell && ((Cell)o).center.equals(center);
	}
	
	@Override
	public int compare(Object otherCell, Object targetPoint) 
	{
		Point target = (Point)targetPoint;
		Cell cell = (Cell)otherCell;
		if(target == null || cell == null) throw new TypeMismatchException();
		
		if(this.equals(otherCell)) 
			return 0;	
		if(distFromCenterSq(target) > cell.distFromCenterSq(target))
			return 1;
		return -1;
	}

}
