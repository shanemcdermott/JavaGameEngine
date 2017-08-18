package javagames.game;

import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.util.Matrix3x3f;
import javagames.util.Vector2f;

import java.awt.*;

public class SpriteObject extends GameObject
{
    protected Sprite sprite;

    public SpriteObject()
	{
		super();
	}

	public SpriteObject(Sprite spr)
	{
		super();
		setSprite(spr);
	}

    public SpriteObject(SpriteObject spr)
	{
		super(spr);
		setSprite(spr.getSprite());
	}


    @Override
    public void render(Graphics g, Matrix3x3f view)
    {
        renderSprite(g,view);
        super.render(g,view);
    }

    public void renderSprite(Graphics g, Matrix3x3f view)
	{
		if(sprite !=null)
		{
			sprite.render((Graphics2D)g, view, getPosition(), rotation);
		}

	}

	public void setSprite(Sprite sprite)
	{
		this.sprite = sprite;
	}

	public Sprite getSprite()
	{
		return sprite;
	}

	public void updateSprite(float deltaTime)
	{
		if(sprite != null && sprite instanceof SpriteSheet)
		{
			SpriteSheet spr = (SpriteSheet)sprite;
			if(!velocity.equals(new Vector2f()))
				spr.startAnimation(getDirection().getAnim());
			spr.update(deltaTime);
		}
	}


}
