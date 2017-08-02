package genesis.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import genesis.editor.tool.DrawPolygonTool;
import genesis.editor.tool.FlagBrushTool;
import genesis.editor.tool.HeightBrushTool;
import genesis.editor.tool.RoomEditorTool;
import genesis.noise.DiamondSquare;
import genesis.noise.NoiseFunction;
import genesis.noise.SeaLevel;
import genesis.noise.Smoothing;
import genesis.noise.WhiteNoise;
import javagames.game.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.util.geom.BoundingPoly;
import javagames.world.Dungeon;

public class WorldEditor extends EditorFramework 
{

	public Random randy;
	public Dungeon world;
	private NoiseFunction[] noise;

	private boolean bRenderHeight;
	
	public WorldEditor()
	{
		super();
		appTitle = "World Editor";
		randy = new Random();
	}

	@Override
	protected JMenu initFileMenu()
	{
		JMenu menu = super.initFileMenu();
		menu.add(initImport());
		menu.add(initExport());
		return menu;
	}
	
	protected JMenuItem initImport()
	{
		JMenuItem item = new JMenuItem(new AbstractAction("Import Heightmap"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				importHeightmap();
			}
			
		});
		
		return item;
	}
	
	protected JMenuItem initExport()
	{
		JMenuItem item = new JMenuItem(new AbstractAction("Export Heightmap"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				exportHeightmap();
			}
			
		});
		
		return item;
	}
	
	@Override
	protected JMenu initToolMenu()
	{
		JMenu menu = super.initToolMenu();
		
		
		menu.add(initNoiseMenu());
		return menu;
	}
	
	protected JMenu initNoiseMenu()
	{
		JMenu noiseMenu = new JMenu("Noise");
		
		Point p = world.getNumRooms();
		float[][] height = new float[p.x][p.y];
		
		world.getHeightmap(height);
		noise = new NoiseFunction[]{
				new WhiteNoise(randy, height),
				new DiamondSquare(randy,height),
				new Smoothing(randy,height),
				new SeaLevel(randy,height,world)
		};

		
		
		for(NoiseFunction nf : noise)
		{
			JMenuItem item = new JMenuItem(new AbstractAction(nf.getName()){

				@Override
				public void actionPerformed(ActionEvent arg0) 
				{
					Point p = world.getNumRooms();
					float[][] heightmap = new float[p.x][p.y];
					boolean[][] flags = world.getFlagmap(nf.getName());
					world.getHeightmap(heightmap);
					nf.setHeightmap(heightmap);
					nf.setIgnored(flags);
					nf.exec();
					world.setHeightMap(heightmap);
				}
			
			});
			noiseMenu.add(item);
		}
		return noiseMenu;
	}
	
	@Override
	protected JMenu initViewMenu()
	{
		JMenu menu = super.initViewMenu();
		JCheckBoxMenuItem item = new JCheckBoxMenuItem(new AbstractAction("Height Render") {
				public void actionPerformed(ActionEvent e) {
						JCheckBoxMenuItem i = (JCheckBoxMenuItem)e.getSource();
						bRenderHeight = !bRenderHeight;
						i.setSelected(bRenderHeight);
				}
			});
			menu.add(item);
		return menu;
	}
	
	@Override
	protected void initTools()
	{
		super.initTools();
		world = new Dungeon("World",appWorldWidth, 128,128);
		importHeightmap();
		//cursor = new EditorTool(this);
		//tools.put("Default", cursor);
		cursor = new RoomEditorTool(this);
		tools.put("Room Editor", cursor);
		tools.put("Height Editor", new HeightBrushTool(this));
		tools.put("Flag Editor", new FlagBrushTool(this));
		tools.put("Polygon Tool", new DrawPolygonTool(this));
		//world.resize(appWorldWidth, appWorldHeight);
		addObject(world);
		//cursor = new CellCreateTool(this);
		//tools.put("Create Cell", cursor);
		//tools.put("Edit Cell", new CellSelectTool(this));
	}
	
	public void importHeightmap()
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File("world.png"));
			world.setRoomCounts(img.getWidth(), img.getHeight());
			GameRoom[][] rooms = world.getRooms();
			for(int x = 0; x< rooms.length; x++)
			{
				for(int y = 0; y < rooms[x].length; y++)
				{
					float elevation = (img.getRGB(x, y) & 0xFF) / 255.f;
					rooms[x][y].setElevation(elevation);
				}
			}
			System.out.println("Heightmap imported.");
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.err.println("Failed to import.");
		}	
	}
	
	public void exportHeightmap()
	{
		try
		{
			GameRoom[][] rooms = world.getRooms();
			BufferedImage bi = new BufferedImage(rooms.length, rooms[0].length,BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x< rooms.length; x++)
			{
				for(int y = 0; y < rooms[x].length; y++)
				{
					bi.setRGB(x,y,rooms[x][y].getElevColor().getRGB());
				}
			}
			File outputfile = new File("world.png");
			ImageIO.write(bi, "png", outputfile);
			System.out.println("Heightmap exported.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace(System.err);
		}
	}
	
	@Override
	public void reset()
	{
		//cellManager.reset();
	}
	
	public void applyMask(String maskName, BoundingPoly maskPoly)
	{
		world.addMask(maskName, maskPoly);
	}
	
	@Override
	protected void renderObjects(Graphics2D g2d, Matrix3x3f view)
	{
		if(bRenderHeight)
		{
			world.renderHeight(g2d, view);
		}
		else
			super.renderObjects(g2d,view);
	}
	
	public static void main(String[] args)
	{
		params = args;
		launchApp(new WorldEditor());
	}
}
