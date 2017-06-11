package genesis.editor.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import genesis.cell.Cell;
import genesis.cell.CellEdge;
import genesis.cell.CellFactory;
import genesis.cell.CellManager;
import genesis.editor.WorldEditor;
import genesis.editor.swing.DetailsPanel;
import genesis.editor.swing.SizePanel;
import javagames.util.Matrix3x3f;
import javagames.player.RelativeMouseInput;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;


public class CellCreateTool extends EditorTool 
{
	private CellManager cellManager;
	private Cell createdCell;
	
	private Vector2f notchWeight = new Vector2f(-1.1f);
	
	public CellCreateTool(WorldEditor editor) 
	{
		super(editor);
		
		
		cellManager = editor.cellManager;	
		createdCell = null;
		
		int boxSize = 20;
		bounds = new BoundingBox(getPosition(), new Vector2f(boxSize,boxSize));
		bounds.fill=true;
		
		initToolPanel();
	}

	private void initToolPanel()
	{
		toolPanel = new SizePanel((BoundingBox)bounds);
		JColorChooser colorChooser = new JColorChooser(getColor());
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				setColor(colorChooser.getColor());
				
			}
			
		});
		toolPanel.add(colorChooser, BorderLayout.SOUTH);
	}

	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse, deltaTime);
				
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			createdCell = CellFactory.makeCell(getPosition(), aabb().getSize());
			System.out.printf("Created cell at %s.\n%s\n",getPosition().toString(), createdCell.toString());
			createdCell.setColor(getColor());
			cellManager.addCell(createdCell);
		}
		if(mouse.getNotches()!=0)
		{
			int notches = mouse.getNotches();
			Vector2f newSize = aabb().getSize();
			newSize = newSize.add(notchWeight.mul(notches));
			getPanel().resizeBox(newSize);
		}
		if(mouse.buttonDownOnce(MouseEvent.BUTTON3))
		{
			createNeighbor();
		}

	}
	
	public void createNeighbor()
	{
		if(createdCell == null) return;
		
		CellEdge nEdge = createdCell.getClosestEdge(getPosition());
		if(nEdge.isBorder())
		{
			createdCell = CellFactory.makeNeighborCell(aabb().getSize(), nEdge, createdCell);
			System.out.printf("Created cell at %s.\n%s\n",createdCell.getPosition().toString(), createdCell.toString());
			createdCell.setColor(getColor());
			cellManager.addCell(createdCell);
		}
	}
	
	public SizePanel getPanel()
	{
		return (SizePanel)toolPanel;
	}
	
	public BoundingBox aabb()
	{
		return (BoundingBox)bounds;
	}
	
}
