package genesis.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javagames.game.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.util.RelativeMouseInput;
import javagames.world.Dungeon;
import javagames.world.InfluenceObject;

public class RoomSelectTool extends EditorTool 
{

	private Dungeon dungeon;
	public GameRoom selectedRoom;
	public GameRoom hoveredRoom;
	
	public Color hoverColor = new Color(0,255,0,100);
	public Color selectColor = Color.CYAN;
	
	public RoomSelectTool(WorldEditor editor) 
	{
		super(editor);
		dungeon = editor.dungeon;
		toolPanel = new RoomDetailsPanel();
	}

	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse, deltaTime);
		startHover(dungeon.getRoomAt(getPosition()));
		
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			startSelection(hoveredRoom);
			
			/*InfluenceObject i = new InfluenceObject();
			i.setPosition(getPosition());
			state.objects.add(i);
			*/
		}
		else if(mouse.buttonDownOnce(MouseEvent.BUTTON2))
		{
			selectedRoom.setComponent("Hello", "Goodbye");
			
		}
	}

	public void startHover(GameRoom room)
	{
		if(hoveredRoom == room || room == selectedRoom) return;
		
		endHover();
		hoveredRoom = room;
		if(hoveredRoom != null)
		{
			hoveredRoom.setColor(hoverColor);
			hoveredRoom.getBounds().fill=true;
		}
	}
	
	public void endHover()
	{
		if(hoveredRoom != null)
		{
			hoveredRoom.setColor(Color.WHITE);
			hoveredRoom.getBounds().fill=false;
		}
		hoveredRoom = null;
	}
	
	public void startSelection(GameRoom room)
	{
		if(hoveredRoom == room)
			endHover();
		
		endSelection();
		selectedRoom = room;
		if(selectedRoom != null)
		{
			selectedRoom.setColor(selectColor);
			selectedRoom.getBounds().fill=true;
			((RoomDetailsPanel)toolPanel).setRoom(selectedRoom);
		}
	}
	
	public void endSelection()
	{
		if(selectedRoom != null)
		{
			selectedRoom.setColor(Color.WHITE);
			selectedRoom.getBounds().fill=false;
		}
		selectedRoom = null;
	}
	
	protected void updateEditorPanel()
	{
		
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport)
	{
		super.render(g, viewport);
		
	}
}
