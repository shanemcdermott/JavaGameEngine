package genesis.editor.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import genesis.cell.Cell;
import genesis.cell.CellManager;
import genesis.editor.CellEditor;
import genesis.editor.EditorFramework;
import genesis.editor.swing.CellDetailsPanel;
import genesis.editor.swing.RoomDetailsPanel;
import javagames.util.Matrix3x3f;
import javagames.player.RelativeMouseInput;
import javagames.room.Dungeon;
import javagames.world.InfluenceObject;

public class CellSelectTool extends EditorTool 
{

	private CellManager cellManager;
	public Cell selectedCell;
	public Cell hoveredCell;
	
	public Color hoverColor = new Color(0,255,0,100);
	public Color selectColor = Color.CYAN;
	public Color hoverColorOG = Color.WHITE;
	public Color selectColorOG = Color.WHITE;
	
	public CellSelectTool(CellEditor editor) 
	{
		super(editor);
		cellManager = editor.getCellManager();
		toolPanel = new CellDetailsPanel();
	}

	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse, deltaTime);
		startHover(cellManager.getCellAt(getPosition().toPoint()));
		
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			startSelection(hoveredCell);
			
			/*InfluenceObject i = new InfluenceObject();
			i.setPosition(getPosition());
			state.objects.add(i);
			*/
		}

	}

	public void startHover(Cell cell)
	{
		if(hoveredCell == cell || cell == selectedCell) return;
		
		endHover();
		hoveredCell = cell;
		if(hoveredCell != null)
		{
			hoverColorOG = hoveredCell.color;
			hoveredCell.setColor(hoverColor);
		}
	}
	
	public void endHover()
	{
		if(hoveredCell != null)
		{
			hoveredCell.setColor(hoverColorOG);
		}
		hoveredCell = null;
	}
	
	public void startSelection(Cell cell)
	{
		if(hoveredCell == cell)
			endHover();
		
		endSelection();
		selectedCell = cell;
		if(selectedCell != null)
		{
			selectColorOG = selectedCell.color;
			selectedCell.setColor(selectColor);
			updateEditorPanel();
		}
	}
	
	public void endSelection()
	{
		if(selectedCell != null)
		{
			selectedCell.setColor(selectColorOG);
		}
		selectedCell = null;
	}
	
	protected void updateEditorPanel()
	{
		((CellDetailsPanel)toolPanel).setCell(selectedCell);
	}

	@Override
	public void deactivate()
	{
		endSelection();
		endHover();
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport)
	{
		super.render(g, viewport);
		
	}
}
