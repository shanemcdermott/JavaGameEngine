package javagames.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javagames.game.GameObject;
import javagames.sound.BlockingClip;
import javagames.sound.BlockingDataLine;
import javagames.sound.LoopEvent;
import javagames.sound.SoundCue;
import javagames.sound.SoundLooper;
import javagames.util.GameConstants;
import javagames.util.Matrix3x3f;
import javagames.util.ResourceLoader;
import javagames.util.Utility;

//TODO: XML/ GameObject Loading
public class LoadingState extends State 
{
	//private String backgroundFileName = "space_background_600x600.png";
	private String ambienceFileName = "AMBIENCE_alien.wav";
	
	//AttributeName, FileName
	private Map<String, String> soundCues;
	private Map<String, String> soundLoops;
	private Map<String, JSONObject> sprites;
	private Map<String, JSONObject> gameObjects;
	
	private ExecutorService threadPool;
	private List<Callable<Boolean>> loadTasks;
	private List<Future<Boolean>> loadResults;
	private int numberOfTasks;
	private float percent;
	private float wait;
	private Color textColor = Color.GREEN;
	private BufferedImage overlay;
	
	public LoadingState(String levelName)
	{
		soundCues = Collections.synchronizedMap(new HashMap<String, String>());
		soundLoops = Collections.synchronizedMap(new HashMap<String, String>());
		sprites = Collections.synchronizedMap(new HashMap<String,JSONObject>());
		gameObjects = Collections.synchronizedMap(new HashMap<String, JSONObject>());
		
		JSONParser parser = new JSONParser();
		try
		{
			JSONObject obj = (JSONObject) parser.parse(new FileReader(String.format("res/assets/json/%s.json",levelName)));
		/*
			backgroundFileName = (String)obj.get("background");
       
			ambienceFileName = (String)obj.get("ambience");
            JSONObject scues = (JSONObject) obj.get("sound cues");
            for(Object o : scues.keySet())
            {
            	soundCues.put((String)o, (String)scues.get(o));
            }
            
            JSONObject sloops = (JSONObject) obj.get("sound loops");
            for(Object o : sloops.keySet())
            {
            	soundLoops.put((String)o, (String)sloops.get(o));
            }
        */
            JSONObject spr = (JSONObject) obj.get("sprites");
            for(Object o : spr.keySet())
            	sprites.put((String)o, (JSONObject)spr.get(o));
           
            
            JSONObject gobj = (JSONObject) obj.get("game objects");
            for(Object o : gobj.keySet())
            {
            	gameObjects.put((String)o, (JSONObject)gobj.get(o));
            }
            
            textColor = Color.decode((String)obj.get("text color"));
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LoadingState()
	{
		//Add any Sound Cues here
		soundCues = Collections.synchronizedMap(new HashMap<String, String>());
		soundCues.put("explosion", "EXPLOSION_large_01.wav");
		soundCues.put("fire-clip", "WEAPON_scifi_fire_02.wav");
		
		//Add any looping sounds here
		soundLoops = Collections.synchronizedMap(new HashMap<String, String>());
		soundLoops.put("thruster", "DRONE9RE.WAV");
	}
	
	@Override
	public void enter()
	{
		threadPool = Executors.newCachedThreadPool();
		loadTasks = new ArrayList<Callable<Boolean>>();
		
		//Load Background Image
		/*
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception 
			{
			
				BufferedImage image = ResourceLoader.loadImage(LoadingState.class, backgroundFileName);
			
				Vector2f worldTopLeft = new Vector2f
				(
					-GameConstants.WORLD_WIDTH / 2.0f,
					GameConstants.WORLD_HEIGHT / 2.0f 
				);
				
				Vector2f worldBottomRight = new Vector2f
				(
					GameConstants.WORLD_WIDTH / 2.0f,
					-GameConstants.WORLD_HEIGHT / 2.0f 
				);
				
				Sprite sprite =	new Sprite( image, worldTopLeft, worldBottomRight );
				
				Viewport viewport =(Viewport)controller.getAttribute( "viewport" );
				
				sprite.scaleImage( viewport.asMatrix() );
				
				controller.setAttribute( "background", sprite );
				
				return Boolean.TRUE;
			}
		});
		*/
		// queueAmbience();
		
		//Load Sound FX
		for (Map.Entry<String, String> entry : soundCues.entrySet())
		{
			loadTasks.add(new Callable<Boolean>() 
			{
				@Override
				public Boolean call() throws Exception 
				{
					byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), entry.getValue());
					SoundCue restartClip =
						new SoundCue( new BlockingDataLine(soundBytes));
					restartClip.initialize();
					restartClip.open();
					controller.setAttribute( entry.getKey(), restartClip );
					return Boolean.TRUE;
				}
				
			});
		}
		
		//Load Sound FX
		for (Map.Entry<String, String> entry : soundLoops.entrySet())
		{
			loadTasks.add(new Callable<Boolean>() 
			{
				@Override
				public Boolean call() throws Exception 
				{
					byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), entry.getValue());
					SoundLooper clip =
							new SoundLooper( new BlockingDataLine( soundBytes ) );
						clip.initialize();
						clip.open();
						controller.setAttribute(entry.getKey(), clip );
						return Boolean.TRUE;
				}
			});
		}
		
		//Load Sprites
			for (Map.Entry<String, JSONObject> entry : sprites.entrySet())
			{
					loadTasks.add(new Callable<Boolean>() 
					{
						@Override
						public Boolean call() throws Exception 
						{
						
							controller.setAttribute( entry.getKey(), ResourceLoader.loadSprite(this.getClass(), entry.getValue()));
							
							return Boolean.TRUE;
						}
					});
			}
		
		//Load Objects
			for(Map.Entry<String,JSONObject> entry : gameObjects.entrySet())
			{
				loadTasks.add(new Callable<Boolean>() 
				{
					@Override
					public Boolean call() throws Exception 
					{
						GameObject g = ResourceLoader.loadObject(getClass(),entry.getValue());
						g.setName(entry.getKey());
						controller.setAttribute( entry.getKey(), g);
						System.out.println(entry.getKey() + " loaded.");
						return Boolean.TRUE;
					}
				});
			}
			
		loadResults = new ArrayList<Future<Boolean>>();
		for(Callable<Boolean> task : loadTasks)
		{
			loadResults.add(threadPool.submit(task));
		}
		
		numberOfTasks = loadResults.size();
		if(numberOfTasks == 0)
		{
			numberOfTasks = 1;
		}
	}

	protected void queueAmbience()
	{
		//Load Ambience
		loadTasks.add( new Callable<Boolean>() 
		{
			@Override
			public Boolean call() throws Exception {
				byte[] soundBytes = ResourceLoader.loadSound(this.getClass(), ambienceFileName);
				// Java 7.0
				LoopEvent loopEvent = new LoopEvent(
					new BlockingClip( soundBytes ) );
				// Java 6.0
						// LoopEvent loopEvent = new LoopEvent(
						// new BlockingDataLine(
						// soundBytes ) );
				loopEvent.initialize();
				controller.setAttribute( "ambience", loopEvent );
				return Boolean.TRUE;
			} 
		});
	}


	@Override
	public void updateObjects(float delta)
	{
		// remove finished tasks
		Iterator<Future<Boolean>> it = loadResults.iterator();
		while (it.hasNext()) 
		{
			Future<Boolean> next = it.next();
			if (next.isDone()) 
			{
				try 
				{
					if (next.get()) 
					{
						it.remove();
					}
				} 
				catch (Exception ex) 
				{
					ex.printStackTrace();
				}
			}
		}
		
		//update the progress bar
		percent = (numberOfTasks - loadResults.size()) / (float) numberOfTasks;
		if(percent >= 1.0f)
		{
			threadPool.shutdown();
			wait += delta;
		}
		//Finished Loading
		if(wait > 1.0f && threadPool.isShutdown())
		{
			//LoopEvent loop = (LoopEvent) controller.getAttribute("ambience");
			//loop.fire();
			getController().setState(new TitleMenuState());
		}
	}
	
	@Override
	public void render(Graphics2D g, Matrix3x3f view) 
	{
		super.render(g, view);
		
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setColor(textColor);
		Utility.drawCenteredString(g, app.getScreenWidth(),
				app.getScreenHeight() / 3, GameConstants.APP_TITLE);
		int vw = (int) (app.getScreenWidth() * .9f);
		int vh = (int) (app.getScreenWidth() * .05f);
		int vx = (app.getScreenWidth() - vw) / 2;
		int vy = (app.getScreenWidth() - vh) / 2;
		// fill in progress
		g.setColor(Color.GRAY);
		int width = (int) (vw * percent);
		g.fillRect(vx, vy, width, vh);
		// draw border
		g.setColor(Color.GREEN);
		g.drawRect(vx, vy, vw, vh);
	}
}
