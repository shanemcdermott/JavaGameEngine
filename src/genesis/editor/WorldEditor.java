package genesis.editor;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import genesis.editor.tool.EditorTool;
import genesis.editor.tool.RoomEditorTool;
import javagames.world.Dungeon;

public class WorldEditor extends EditorFramework 
{
	public Dungeon world;
	private boolean bRenderHeight;
	
	public WorldEditor()
	{
		super();
		appTitle = "World Editor";
		
	}

	@Override
	protected JMenu initModeMenu()
	{
		JMenu menu = super.initModeMenu();
		JMenuItem item = new JMenuItem(new AbstractAction("Toggle Height Render") {
				public void actionPerformed(ActionEvent e) {
						bRenderHeight = !bRenderHeight;
				}
			});
			menu.add(item);
		return menu;
	}
	
	@Override
	protected void initTools()
	{
		super.initTools();
		world = new Dungeon("World",appWorldWidth, 64,64);
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
	protected void render(Graphics g)
	{
		super.render(g);
		if(bRenderHeight)
			world.renderHeight(g, getViewportTransform());
	}
	
	public static void main(String[] args)
	{
		params = args;
		launchApp(new WorldEditor());
	}
}
