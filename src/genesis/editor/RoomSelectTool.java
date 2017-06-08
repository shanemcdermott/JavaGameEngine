package genesis.editor;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javagames.game.GameRoom;
import javagames.util.RelativeMouseInput;
import javagames.world.Dungeon;
import javagames.world.InfluenceObject;

public class RoomSelectTool extends EditorTool 
{

	private Dungeon dungeon;
	public GameRoom selectedRoom;
	
	public RoomSelectTool(WorldEditor editor) 
	{
		super(editor);
		dungeon = editor.dungeon;
	}

	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse, deltaTime);
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			startSelection(dungeon.getRoomAt(getPosition()));
			
			/*InfluenceObject i = new InfluenceObject();
			i.setPosition(getPosition());
			state.objects.add(i);
			*/
		}
	}

	
	public void startSelection(GameRoom room)
	{
		endSelection();
		selectedRoom = room;
		if(selectedRoom != null)
		{
			selectedRoom.setColor(Color.CYAN);
		}
	}
	
	public void endSelection()
	{
		if(selectedRoom != null)
			selectedRoom.setColor(Color.WHITE);
	}
}
