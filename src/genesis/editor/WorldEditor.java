package genesis.editor;

import java.awt.Graphics;

import genesis.editor.tool.EditorTool;
import genesis.editor.tool.RoomEditorTool;
import javagames.world.Dungeon;

public class WorldEditor extends EditorFramework 
{
	public Dungeon world;
	
	public WorldEditor()
	{
		super();
		appTitle = "World Editor";
		
	}

	@Override
	protected void initTools()
	{
		super.initTools();
		world = new Dungeon("World",appWorldWidth, 64,64);
		cursor = new EditorTool(this);
		tools.put("Default", cursor);
		tools.put("Room Editor", new RoomEditorTool(this));
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
	protected void render(Graphics g)
	{
		super.render(g);
		
	}
	
	public static void main(String[] args)
	{
		params = args;
		launchApp(new WorldEditor());
	}
}
