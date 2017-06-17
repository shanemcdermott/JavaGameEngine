package genesis.editor.tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import genesis.editor.EditorFramework;
import javagames.game.GameObject;
import javagames.player.GameCursor;
import javagames.player.MouseControls;
import javagames.player.RelativeMouseInput;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingCircle;
import javagames.world.InfluenceObject;

public class EditorTool extends GameCursor
{
	public EditorFramework editor;
	public JPanel	toolPanel;
	
	public EditorTool(EditorFramework editor)
	{
		super();
		bounds=new BoundingCircle(new Vector2f(), 0.1f);
		setColor(new Color(0,255,0,100));
		this.editor = editor;
		toolPanel = new JPanel();
	}

	public void deactivate()
	{
		
	}
	
}
