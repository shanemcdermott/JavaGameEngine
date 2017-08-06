package javagames.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javagames.g2d.Animation;
import javagames.g2d.ImageUtility;
import javagames.g2d.Sprite;
import javagames.g2d.SpriteSheet;
import javagames.game.Construct;
import javagames.game.GameObject;
import javagames.player.PlayerController;
import javagames.state.LoadingState;
import javagames.world.GameMap;
import javagames.world.Ingredient;
import javagames.world.IngredientObject;

public class ResourceLoader {
	
	public static InputStream load(Class<?> clazz, String filePath)
	{
		InputStream in = null;
		if(!(filePath == null || filePath.isEmpty()))
		{
			in = clazz.getResourceAsStream(filePath);
		}
			
		if(in == null)
		{
			try 
			{
				in = new FileInputStream("res/assets" + filePath);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		return in;
	}
	
	public static Element loadXML(Class<?> clazz, String fileName) throws IOException, SAXException, ParserConfigurationException
	{
		InputStream model = load(clazz, "/xml/" + fileName);
		Document document = XMLUtility.parseDocument(model);
		return document.getDocumentElement();
	}
	
	public static BufferedImage loadImage(Class<?> clazz, String fileName) throws Exception
	{
		InputStream stream = ResourceLoader.load(clazz, "/images/" + fileName);
		return ImageIO.read( stream );
	}
	
	public static Sprite loadSprite(Class<?> clazz, JSONObject json) throws Exception
	{
		if(json.containsKey("Anims")) return loadSpriteSheet(clazz, json);
		
		BufferedImage image = ResourceLoader.loadImage(LoadingState.class, (String)json.get("file"));
		
		if(json.containsKey("x"))
		{
			int x = (int)(long)json.get("x");
			int y = (int)(long)json.get("y");
			int w = (int)(long)json.get("w");
			int h = (int)(long)json.get("h");
			image = image.getSubimage(x, y, w, h);
			
		}
		float halfWidth = 0.5f * (float)(double)json.get("width");
		float halfHeight = 0.5f * (float)(double)json.get("height");
		Vector2f worldTopLeft = new Vector2f(-halfWidth,halfHeight);
		Vector2f worldBottomRight = new Vector2f(halfWidth,-halfHeight);
		Sprite sprite =	new Sprite( image, worldTopLeft, worldBottomRight );
		return sprite;
	}
	
	
	public static SpriteSheet loadSpriteSheet(Class<?> clazz, JSONObject json) throws Exception
	{
		SpriteSheet sheet = null;
		Map<String, Animation> anims = new HashMap<String, Animation>();
		JSONObject animsjson = (JSONObject)json.get("Anims");
		BufferedImage source = ResourceLoader.loadImage(clazz, (String)json.get("file"));
		for(Object o : animsjson.keySet())
		{
			anims.put((String)o,ImageUtility.createAnim(source, (JSONObject)animsjson.get(o)));
		}
		
		float halfWidth = 0.5f * (float)(double)json.get("width");
		float halfHeight = 0.5f * (float)(double)json.get("height");
		Vector2f worldTopLeft = new Vector2f(-halfWidth,halfHeight);
		Vector2f worldBottomRight = new Vector2f(halfWidth,-halfHeight);
		sheet =	new SpriteSheet(source, worldTopLeft, worldBottomRight, anims);
		return sheet;
	}
	
	
	public static GameMap loadMap(Class<?> clazz, JSONObject json) throws Exception
	{
		JSONObject tileobj = (JSONObject)json.get("Tiles");
		Sprite[] tiles = new Sprite[tileobj.size()];
		
		int i = 0;
		for(Object o : tileobj.values())
		{
			tiles[i++] = ResourceLoader.loadSprite(clazz, (JSONObject)o);
		}
		GameMap map = new GameMap(tiles,10.f);
		
		return map;
	}
	
	public static GameObject loadObject(Class<?> clazz, JSONObject json) throws Exception
	{
			GameObject g = new GameObject();
			g.setSprite(ResourceLoader.loadSprite(clazz, (JSONObject)json.get("Sprite")));
			Vector2f pos = new Vector2f();
			JSONArray p = (JSONArray)json.get("Location");
			pos.x = (float)(double)p.get(0);
			pos.y = (float)(double)p.get(1);
			g.setPosition(pos);
			return g;
	}
	
	public static IngredientObject loadIngredients(Class<?> clazz, JSONObject json) throws Exception
	{
		IngredientObject resource = null;
		HashMap<Ingredient, Integer> res = new HashMap<Ingredient, Integer>();
		for(Object o : json.keySet())
		{
			Ingredient r = Ingredient.valueOf((String)o);
			res.put(r, (int)(long)json.get(o));
		}
		
		resource = new IngredientObject(res);
		return resource;
	}
	
	public static Construct loadConstruct(Class<?> clazz, JSONObject json) throws Exception
	{
		Construct g = new Construct(ResourceLoader.loadIngredients(clazz, (JSONObject)json.get("Ingredients")));
		g.setSprite(ResourceLoader.loadSprite(clazz, (JSONObject)json.get("Sprite")));
		Vector2f pos = new Vector2f();
		JSONArray p = (JSONArray)json.get("Location");
		pos.x = (float)(double)p.get(0);
		pos.y = (float)(double)p.get(1);
		g.setPosition(pos);
		return g;
	}
	
	public static PlayerController loadPlayer(Class<?> clazz, JSONObject json) throws Exception
	{
		PlayerController p = new PlayerController();
		p.setSprite(ResourceLoader.loadSprite(clazz, (JSONObject)json.get("Sprite")));
		return p;
	}
	
	public static byte[] loadSound(Class<?> clazz, String fileName)
	{
		InputStream in = load(clazz, "/sound/" + fileName);
		return readBytes(in);
	}
	
	private static byte[] readBytes(InputStream in) 
	{
		try 
		{
			BufferedInputStream buf = new BufferedInputStream(in);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int read;
			while ((read = buf.read()) != -1) {
				out.write(read);
			}
			in.close();
			return out.toByteArray();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
}