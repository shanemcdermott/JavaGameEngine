package javagames.util.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

public class BoundingPoly extends BoundingShape {

	protected ArrayList<Vector2f> vertices;
	
	public BoundingPoly()
	{
		vertices = new ArrayList<Vector2f>();
		setPosition(new Vector2f());
	}
	
	public int numVertices()
	{
		return vertices.size();
	}
	
	public void addVertex(Vector2f vertex)
	{
		vertices.add(vertex);
		if(numVertices()>2)
			vertices = GeomUtility.quickHull(vertices);
	}
	
	@Override
	public BoundingPoly copy()
	{
		BoundingPoly p = new BoundingPoly();
		p.setPosition(getPosition());
		p.setChannel(getChannel());
		p.vertices = new ArrayList<Vector2f>(vertices);
		return p;
	}
	
	@Override
	public boolean intersects(BoundingShape otherShape) 
	{
		
		return false;
	}

	@Override
	public boolean contains(Vector2f point) {
		boolean inside = false;
		Vector2f start = vertices.get(numVertices()-1);
		boolean startAbove = start.y >= point.y;
		for (int i = 0; i < numVertices(); ++i) {
			Vector2f end = vertices.get(i);
			boolean endAbove = end.y >= point.y;
			if (startAbove != endAbove) {
				float m = (end.y - start.y) / (end.x - start.x);
				float x = start.x + (point.y - start.y) / m;
				if (x >= point.x) {
					inside = !inside;
				}
			}
			startAbove = endAbove;
			start = end;
		}
		return inside;
	}

	@Override
	public void render(Graphics g, Matrix3x3f view) 
	{
		if(numVertices()==0) return;
		
		ArrayList<Vector2f> vectors = new ArrayList<Vector2f>();
		for(Vector2f v : vertices)
			vectors.add(view.mul(getPosition().add(v)));
		
		if(fill)
			GeomUtility.fillPolygon((Graphics2D)g, vectors);
		else
			GeomUtility.drawPolygon((Graphics2D)g, vectors);

	}
}
