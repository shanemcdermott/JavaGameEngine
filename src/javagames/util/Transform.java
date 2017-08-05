package javagames.util;

public class Transform 
{
	public Vector2f translation;
	public Vector2f scale;
	public float rotation;
	
	public Transform()
	{
		this(new Vector2f(),new Vector2f(1.f), 0.f);
	}
	
	public Transform(Vector2f translation, Vector2f scale, float rotation)
	{
		this.translation=translation;
		this.scale=scale;
		this.rotation=rotation;
	}
	
	public Matrix3x3f asMatrix()
	{		
		Matrix3x3f wTransform = Matrix3x3f.scale(scale);
		wTransform = wTransform.mul(Matrix3x3f.rotate(rotation));
		wTransform = wTransform.mul(Matrix3x3f.translate(translation));
		return wTransform;
	}
}
