package genesis.editor.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import genesis.editor.WorldEditor;
import javagames.game.GameCursor;
import javagames.game.GameObject;
import javagames.util.MouseControls;
import javagames.util.RelativeMouseInput;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingCircle;
import javagames.world.InfluenceObject;

public class EditorTool extends GameCursor
{
	public WorldEditor editor;
	public JPanel	toolPanel;
	
	public EditorTool(WorldEditor editor)
	{
		super();
		bounds=new BoundingCircle(new Vector2f(), 0.1f);
		color = new Color(0,255,0,100);
		this.editor = editor;
		toolPanel = new JPanel();
	}

	public void deactivate()
	{
		
	}
	
}
