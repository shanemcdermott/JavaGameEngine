package genesis.editor.tool;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import genesis.editor.EditorFramework;
import genesis.editor.WorldEditor;
import javagames.player.RelativeMouseInput;
import javagames.util.Matrix3x3f;
import javagames.util.geom.BoundingPoly;

public class DrawPolygonTool extends EditorTool 
{
	public BoundingPoly poly;
	
	public DrawPolygonTool(WorldEditor editor) 
	{
		super(editor);
		poly = new BoundingPoly();
		poly.fill = true;
	}
	
	@Override
	public void processInput(RelativeMouseInput mouse, float deltaTime) 
	{
		super.processInput(mouse,deltaTime);
		if(mouse.buttonDownOnce(MouseEvent.BUTTON1))
		{
			poly.addVertex(getPosition());
		}
		if(mouse.buttonDownOnce(MouseEvent.BUTTON2))
		{
			WorldEditor e = (WorldEditor)editor;
			String maskName = "Sea Level";
			e.applyMask(maskName, poly);
			System.out.printf("%s applied.\n", maskName);
		}
		if(mouse.buttonDown(MouseEvent.BUTTON3))
		{
			poly.setPosition(getPosition());
		}
	}
	
	@Override
	public void render(Graphics g, Matrix3x3f viewport)
	{
		super.render(g, viewport);
		poly.render(g, viewport);
	}
}
