package javagames.util;

public enum Direction 
{
	UP(new Vector2f(0.f,1.f), "Move_Up"),
	DOWN(new Vector2f(0.f, -1.f), "Move_Down"),
	LEFT(new Vector2f(-1.f,0.f), "Move_Left"),
	RIGHT(new Vector2f(1.f,0.f), "Move_Right");
	
	private Vector2f v;
	private String anim;
	
	public Vector2f getV() {
		return v;
	}

	public String getAnim() {
		return anim;
	}

	Direction(Vector2f v, String anim)
	{
		this.v=v;
		this.anim=anim;
	}

}
