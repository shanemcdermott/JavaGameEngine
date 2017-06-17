package genesis.editor;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import genesis.cell.Cell;
import genesis.cell.CellManager;
import genesis.editor.tool.CellCreateTool;
import genesis.editor.tool.CellSelectTool;
import javagames.world.Dungeon;

public class WorldEditor extends EditorFramework 
{
	public Dungeon world;
	
	public WorldEditor()
	{
		super();
		world = new Dungeon("World");
		appTitle = "World Editor";
		addObject(world);
	}

	@Override
	protected void initTools()
	{
		super.initTools();
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
