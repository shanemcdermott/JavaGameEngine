package genesis.editor;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import genesis.editor.tool.RoomEditorTool;
import genesis.noise.DiamondSquare;
import genesis.noise.Erosion;
import genesis.noise.OceanEdges;
import javagames.game.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.world.Dungeon;

public class WorldEditor extends EditorFramework 
{
	
	public Random randy;
	public Dungeon<GameRoom> world;
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
		
		JMenuItem item = new JMenuItem(new AbstractAction("Export Heightmap"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
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
			
		});
		
		menu.add(item);
		return menu;
	}
	
	@Override
	protected JMenu initToolMenu()
	{
		JMenu menu = super.initToolMenu();
		
		JMenuItem item = new JMenuItem(new AbstractAction("White Noise"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Point p = world.getNumRooms();
				float[][] heightmap = new float[p.x][p.y];
				world.getHeightmap(heightmap);
				for(int x = 0; x < p.x;  x++)
				{
					for(int y = 0; y < p.y; y++)
					{
						heightmap[x][y] = randy.nextFloat();
					}
				}
				world.setHeightMap(heightmap);
			}
			
		});
		menu.add(item);
		
		item = new JMenuItem(new AbstractAction("Diamond-Square"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Point p = world.getNumRooms();
				float[][] heightmap = new float[p.x][p.y];
				world.getHeightmap(heightmap);
				DiamondSquare d = new DiamondSquare(heightmap);
				d.exec();
				world.setHeightMap(heightmap);
			}
			
		});
		menu.add(item);
		

		item = new JMenuItem(new AbstractAction("Erosion"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Point p = world.getNumRooms();
				float[][] heightmap = new float[p.x][p.y];
				world.getHeightmap(heightmap);
				Erosion d = new Erosion(heightmap);
				d.exec();
				world.setHeightMap(heightmap);
			}
			
		});
		menu.add(item);
		
		item = new JMenuItem(new AbstractAction("Ocean Border"){

			@Override
			public void actionPerformed(ActionEvent arg0) 
			{
				Point p = world.getNumRooms();
				float[][] heightmap = new float[p.x][p.y];
				world.getHeightmap(heightmap);
				OceanEdges d = new OceanEdges(heightmap);
				d.exec();
				world.setHeightMap(heightmap);
			}
			
		});
		menu.add(item);
		
		return menu;
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
		world = new Dungeon<GameRoom>("World",appWorldWidth, 64,64);
		//cursor = new EditorTool(this);
		//tools.put("Default", cursor);
		cursor = new RoomEditorTool(this);
		tools.put("Room Editor", cursor);
		//world.resize(appWorldWidth, appWorldHeight);
		addObject(world);
		//cursor = new CellCreateTool(this);
		//tools.put("Create Cell", cursor);
		//tools.put("Edit Cell", new CellSelectTool(this));
	}
	
	@Override
	public void reset()
	{
		//cellManager.reset();
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
