package javagames.util;

import java.awt.Point;

public class Vector2f {
	
	public float x;
	public float y;
	public float w;

	public Vector2f() {
		this(0.f);
	}

	public Vector2f(float v)
	{
		this(v,v);
	}
	
	public Vector2f(Vector2f v) {
		this.x = v.x;
		this.y = v.y;
		this.w = v.w;
	}

	public Vector2f(float x, float y) 
	{
		this(x,y,1.f);
	}

	public Vector2f(float x, float y, float w) {
		this.x = x;
		this.y = y;
		this.w = w;
	}

	public Vector2f(Point p)
	{
		this.x = p.x;
		this.y = p.y;
		this.w = 1.f;
	}
	
	public void translate(float tx, float ty) {
		x += tx;
		y += ty;
	}

	public void scale(float sx, float sy) {
		x *= sx;
		y *= sy;
	}

	public void rotate(float rad) {
		float tmp = (float) (x * Math.cos(rad) - y * Math.sin(rad));
		y = (float) (x * Math.sin(rad) + y * Math.cos(rad));
		x = tmp;
	}

	public void shear(float sx, float sy) {
		float tmp = x + sx * y;
		y = y + sy * x;
		x = tmp;
	}

	public Vector2f add(Vector2f v) {
		return new Vector2f(x + v.x, y + v.y);
	}

	public Vector2f sub(Vector2f v) {
		return new Vector2f(x - v.x, y - v.y);
	}

	public Vector2f mul(float scalar) {
		return new Vector2f(scalar * x, scalar * y);
	}

	public Vector2f div(float scalar) {
		return new Vector2f(x / scalar, y / scalar);
	}

	public Vector2f mid(Vector2f v)
	{
		return add(v).mul(0.5f);
	}
	
	public Vector2f inv() {
		return new Vector2f(-x, -y);
	}

	public Vector2f norm() {
		return div(len());
	}

	public float dot(Vector2f v) {
		return x * v.x + y * v.y;
	}

	public float len() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public float lenSqr() {
		return x * x + y * y;
	}

	public Vector2f perp() {
		return new Vector2f(-y, x);
	}

	public Vector2f abs()
	{
		return new Vector2f(Math.abs(x),Math.abs(y));
	}
	
	public float angle() {
		return (float) Math.atan2(y, x);
	}

	public static Vector2f polar(float angle, float radius) {
		return new Vector2f(radius * (float) Math.cos(angle), radius
				* (float) Math.sin(angle));
	}

	public Point toPoint()
	{
		return new Point((int)x,(int)y);
	}
	
	@Override
	public boolean equals(Object v)
	{
		Vector2f v2 = (Vector2f)v;
		if(v2 != null)
		{
			return x==v2.x && y == v2.y;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("(%.2f,%.2f)", x, y);
	}
}