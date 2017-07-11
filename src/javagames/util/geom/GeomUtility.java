package javagames.util.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javagames.util.Vector2f;

public class GeomUtility 
{
	
	public static void drawPolygon(Graphics g, Vector2f[] polygon) {
		Vector2f P;
		Vector2f S = polygon[polygon.length - 1];
		for (int i = 0; i < polygon.length; ++i) {
			P = polygon[i];
			g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
			S = P;
		}
	}

	public static void drawPolygon(Graphics g, List<Vector2f> polygon) {
		Vector2f S = polygon.get(polygon.size() - 1);
		for (Vector2f P : polygon) {
			g.drawLine((int) S.x, (int) S.y, (int) P.x, (int) P.y);
			S = P;
		}
	}

	public static void fillPolygon(Graphics2D g, Vector2f[] polygon) {
		Polygon p = new Polygon();
		for (Vector2f v : polygon) {
			p.addPoint((int) v.x, (int) v.y);
		}
		g.fill(p);
	}

	public static void fillPolygon(Graphics2D g, List<Vector2f> polygon) {
		Polygon p = new Polygon();
		for (Vector2f v : polygon) {
			p.addPoint((int) v.x, (int) v.y);
		}
		g.fill(p);
	}

	public static ArrayList<Vector2f> quickHull(ArrayList<Vector2f> points)
	{
		ArrayList<Vector2f> convexHull = new ArrayList<Vector2f>();
		if (points.size() < 3)
			return (ArrayList) points.clone();

		int minPoint = -1, maxPoint = -1;
		float minX = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE;
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
		Vector2f A = points.get(minPoint);
		Vector2f B = points.get(maxPoint);
		convexHull.add(A);
		convexHull.add(B);
		points.remove(A);
		points.remove(B);

		ArrayList<Vector2f> leftSet = new ArrayList<Vector2f>();
		ArrayList<Vector2f> rightSet = new ArrayList<Vector2f>();

		for (int i = 0; i < points.size(); i++)
		{
			Vector2f p = points.get(i);
			if (pointLocation(A, B, p) == -1)
				leftSet.add(p);
			else if (pointLocation(A, B, p) == 1)
				rightSet.add(p);
		}
		hullSet(A, B, rightSet, convexHull);
		hullSet(B, A, leftSet, convexHull);

		return convexHull;
	}
	
	private static void hullSet(Vector2f A, Vector2f B, ArrayList<Vector2f> set, ArrayList<Vector2f> hull)
	{
		int insertPosition = hull.indexOf(B);
		if (set.size() == 0)
			return;
		if (set.size() == 1)
		{
			Vector2f p = set.get(0);
			set.remove(p);
			hull.add(insertPosition, p);
			return;
		}
		float dist = Float.MIN_VALUE;
		int furthestPoint = -1;
		for (int i = 0; i < set.size(); i++)
		{
			Vector2f p = set.get(i);
			float distance = Vector2f.distance(A, B, p);
			if (distance > dist)
			{
				dist = distance;
				furthestPoint = i;
			}
		}
		Vector2f P = set.get(furthestPoint);
		set.remove(furthestPoint);
		hull.add(insertPosition, P);

		// Determine who's to the left of AP
		ArrayList<Vector2f> leftSetAP = new ArrayList<Vector2f>();
		for (int i = 0; i < set.size(); i++)
		{
			Vector2f M = set.get(i);
			if (pointLocation(A, P, M) == 1)
			{
				leftSetAP.add(M);
			}
		}

		// Determine who's to the left of PB
		ArrayList<Vector2f> leftSetPB = new ArrayList<Vector2f>();
		for (int i = 0; i < set.size(); i++)
		{
			Vector2f M = set.get(i);
			if (pointLocation(P, B, M) == 1)
			{
				leftSetPB.add(M);
			}
		}
		hullSet(A, P, leftSetAP, hull);
		hullSet(P, B, leftSetPB, hull);

	}
	
	public static int pointLocation(Vector2f A, Vector2f B, Vector2f P)
	{
		int cp1 = Math.round((B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x));
		if (cp1 > 0)
			return 1;
		else if (cp1 == 0)
			return 0;
		else
			return -1;
	}
	
}
