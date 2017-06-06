package javagames.util.geom;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
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

}
