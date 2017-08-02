package genesis.editor.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import genesis.editor.WorldEditor;
import genesis.editor.swing.RoomDetailsPanel;
import javagames.player.RelativeMouseInput;
import javagames.room.Dungeon;
import javagames.room.GameRoom;
import javagames.util.Matrix3x3f;
import javagames.util.geom.BoundingBox;

public class RoomEditorTool extends EditorTool 
{
	private Dungeon dungeon;
	private GameRoom selectedRoom;
	
	public RoomEditorTool(WorldEditor editor)
	{
		super(editor);
		dungeon = editor.world;
		initToolPanel();
	}

	private void initToolPanel()
	{
		toolPanel = new   RoomDetailsPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBorder(BorderFactory.createTitledBorder("Room Details"));
		JColorChooser colorChooser = new JColorChooser(getColor());
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(selectedRoom!=null)
					selectedRoom.setColor(colorChooser.getColor());
				
			}
			
		});
		toolPanel.add(colorChooser, BorderLayout.SOUTH);
		
		JButton extendButton = new JButton("Transform");
		extendButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				transformSelected();
			}});
		toolPanel.add(extendButton);
		
		
	}
	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse,deltaTime);
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			selectRoom(dungeon.getRoomAt(getPosition()));
		}
		if(mouse.buttonDownOnce(MouseEvent.BUTTON3))
		{
			transformSelected();
		}
	}
	
	@Override
	public void deactivate()
	{
		deselectRoom();
	}
	
	protected void selectRoom(GameRoom room)
	{
		deselectRoom();
		RoomDetailsPanel p = (RoomDetailsPanel)toolPanel;
		selectedRoom = room;
		p.setRoom(selectedRoom);
		
	}
	
	protected void deselectRoom()
	{
		selectedRoom=null;
	}
	
	public void transformSelected()
	{
		if(selectedRoom!=null)
			selectedRoom.transform(dungeon);
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f view)
	{
		super.render(g,view);
		if(selectedRoom!=null)
		{
			
			BoundingBox aabb = selectedRoom.getAABB();
			boolean bFill = aabb.fill;
			aabb.fill=false;
			aabb.render(g,view,Color.CYAN);
			aabb.fill= bFill;
		}
	}
}
