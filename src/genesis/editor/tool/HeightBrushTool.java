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
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import genesis.editor.WorldEditor;
import genesis.editor.swing.RoomDetailsPanel;
import javagames.game.GameRoom;
import javagames.player.RelativeMouseInput;
import javagames.util.Matrix3x3f;
import javagames.util.geom.BoundingBox;
import javagames.world.Dungeon;

public class HeightBrushTool extends EditorTool {

	
	private Dungeon dungeon;
	private float changePerSecond;
	private GameRoom selectedRoom;

	public HeightBrushTool(WorldEditor editor) 
	{
		super(editor);
		dungeon = editor.world;
		changePerSecond = 0.1f;
		initToolPanel();
		// TODO Auto-generated constructor stub
	}

	
	private void initToolPanel()
	{
		toolPanel = new  RoomDetailsPanel();
		toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
		toolPanel.setBorder(BorderFactory.createTitledBorder("Room Details"));
		
		JTextField changeField = new JTextField(Float.toString(changePerSecond));
		changeField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changePerSecond = Math.min(1.f, Math.max(0.f, Float.parseFloat(changeField.getText())));
				
			}
		});
		toolPanel.add(changeField);
		
		JColorChooser colorChooser = new JColorChooser(getColor());
		colorChooser.getSelectionModel().addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(selectedRoom!=null)
					selectedRoom.setColor(colorChooser.getColor());
				
			}
			
		});
		toolPanel.add(colorChooser, BorderLayout.SOUTH);
			
	}
	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse,deltaTime);
		if(mouse.buttonDown(MouseEvent.BUTTON1))
		{
			selectRoom(dungeon.getRoomAt(getPosition()));
			increaseElevation(deltaTime);
			if(selectedRoom !=null)
				selectedRoom.setFlag("Sea Level", true);
		}
		if(mouse.buttonDown(MouseEvent.BUTTON3))
		{
			selectRoom(dungeon.getRoomAt(getPosition()));
			increaseElevation(-deltaTime);
		}
	}
	
	@Override
	public void deactivate()
	{
		deselectRoom();
	}
	
	protected void selectRoom(GameRoom room)
	{
		if(room != selectedRoom)
			deselectRoom();
		RoomDetailsPanel p = (RoomDetailsPanel)toolPanel;
		selectedRoom = room;
		p.setRoom(selectedRoom);
		
	}
	
	protected void deselectRoom()
	{
		selectedRoom=null;
	}
	
	protected void increaseElevation(float multiplier)
	{
		if(selectedRoom == null) return;
		
		float h = selectedRoom.getElevation() + changePerSecond*multiplier;
		if(h>1)
			h=1.f;
		else if (h<0)
			h=0.f;
		
		selectedRoom.setElevation(h);
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

