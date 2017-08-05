package javagames.g2d;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ImageUtility 
{
	
	public static Animation createAnim(BufferedImage source, JSONObject json)
	{
		Animation anim = null;
		JSONArray xArr = (JSONArray)json.get("x");
		JSONArray yArr = (JSONArray)json.get("y");
		JSONArray framesArr = (JSONArray)json.get("frames");
		
		int[] x = new int[xArr.size()];
		int[] y = new int[yArr.size()];
		float[] frames = new float[framesArr.size()];
		for(int i = 0; i < frames.length; i++)
		{
			x[i] = (int)(long)xArr.get(i);
			y[i] = (int)(long)yArr.get(i);
			frames[i] = (float)(double)framesArr.get(i);
		}
		int w = (int)(long)json.get("w");
		int h = (int)(long)json.get("h");
		anim = new Animation(source, frames,x,y,w,h);
		
		return anim;
	}
	
	public static BufferedImage colorImage(BufferedImage source, Color color)
	{
		if(color == Color.WHITE)
		{
			return source;
		}
		BufferedImage image = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
		Graphics2D g2d = image.createGraphics();
		g2d.drawImage(source, 0, 0, image.getWidth(), image.getHeight(), null);
		g2d.setColor(color);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2d.dispose();
		return image;
	}
	
	public static BufferedImage scaleImage(BufferedImage toScale,
			int targetWidth, int targetHeight) {
		int width = toScale.getWidth();
		int height = toScale.getHeight();
		if (targetWidth < width || targetHeight < height) {
			return scaleDownImage(toScale, targetWidth, targetHeight);
		} else {
			return scaleUpImage(toScale, targetWidth, targetHeight);
		}
	}

	private static BufferedImage scaleUpImage(BufferedImage toScale,
			int targetWidth, int targetHeight) {
		BufferedImage image = 
			new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setRenderingHint(
			RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.drawImage(toScale, 0, 0, image.getWidth(), image.getHeight(), null);
		g2d.dispose();
		return image;
	}

	private static BufferedImage scaleDownImage(
		BufferedImage toScale, int targetWidth, int targetHeight) 
	{
		
		int w = toScale.getWidth();
		int h = toScale.getHeight();
		do {
			w = w / 2;
			if (w < targetWidth) {
				w = targetWidth;
			}
			h = h / 2;
			if (h < targetHeight) {
				h = targetHeight;
			}
			BufferedImage tmp = 
				new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = tmp.createGraphics();
			g2d.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.drawImage(toScale, 0, 0, w, h, null);
			g2d.dispose();
			toScale = tmp;
		} while (w != targetWidth || h != targetHeight);
		
		return toScale;
	}

}
