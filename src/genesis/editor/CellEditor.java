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
import javagames.g2d.GridLines;

public class CellEditor extends EditorFramework 
{
	private final int I_CELLMANAGER= 0;
	public CellEditor()
	{
		super();
		objects.set(I_CELLMANAGER,new CellManager(rng,new ArrayList<Cell>()));
		addObject(new GridLines(this));
		appTitle = "Cell Editor";
	}

	@Override
	protected void initTools()
	{
		cursor = new CellCreateTool(this);
		tools.put("Create Cell", cursor);
		tools.put("Edit Cell", new CellSelectTool(this));
	}
	
	public CellManager getCellManager()
	{
		return (CellManager)objects.get(I_CELLMANAGER);
	}
	@Override
	public void reset()
	{
		
		getCellManager().reset();
	}
	
	@Override
	protected void render(Graphics g)
	{
		super.render(g);
		//cellManager.render(g,getViewportTransform());
	}
	
	public static void main(String[] args) 
	{
		params = args;
		launchApp(new CellEditor());
	}
}
