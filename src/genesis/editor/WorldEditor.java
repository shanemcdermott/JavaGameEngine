package genesis.editor;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;


import javagames.framework.SwingFramework;
import javagames.game.GameObject;
import javagames.state.LoadingState;
import javagames.state.StateController;
import javagames.util.Matrix3x3f;
import javagames.world.Dungeon;


public class WorldEditor extends SwingFramework
{

	protected List<GameObject> objects;
	protected HashMap<String,EditorTool> tools;
	
	
	public	  Dungeon dungeon;
	protected EditorTool cursor;
	
	protected static String[] params;
	protected JTextField tagField; 
	private JPanel mainPanel;
	private JPanel centerPanel;
	private JPanel toolPanel;
	
	public WorldEditor()
	{
		super();
		objects = Collections.synchronizedList(new ArrayList<GameObject>());
		dungeon = new Dungeon("EditorDungeon");
		objects.add(dungeon);
		cursor = new RoomSelectTool(this);
		tools = new HashMap<String, EditorTool>();
		tools.put("Room Select", cursor);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		processParams();
		initEditorBar();
	}
	
	public static void main(String[] args) 
	{
		params = args;
		launchApp(new WorldEditor());
	}

	protected void processParams()
	{
		if(params.length>0)
		{
			System.out.println(params[0]);
		}
		else
		{
			JMenuBar menuBar = new JMenuBar();
			menuBar.add(initFileMenu());
			menuBar.add(initModeMenu());
			menuBar.add(initHelpMenu());
			setJMenuBar(menuBar);
		}
	}

	protected JMenu initFileMenu()
	{
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent e) {
				WorldEditor.this.dispatchEvent(new WindowEvent(
						WorldEditor.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		menu.add(item);
		return menu;
	}
	
	protected JMenu initModeMenu()
	{
		JMenu menu = new JMenu("Mode");
		for(Map.Entry<String,EditorTool> entry : tools.entrySet())
		{
			JMenuItem item = new JMenuItem(new AbstractAction(entry.getKey()) {
				public void actionPerformed(ActionEvent e) {
					WorldEditor.this.cursor = entry.getValue();
					getMainPanel().remove(WorldEditor.this.toolPanel);
					WorldEditor.this.toolPanel = entry.getValue().toolPanel;
					getMainPanel().add(WorldEditor.this.toolPanel, BorderLayout.EAST);
					
				}
			});
			menu.add(item);
		}
		return menu;
	}
	
	protected JMenu initHelpMenu()
	{
		JMenu menu = new JMenu("Help");
		JMenuItem item = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(WorldEditor.this,
						"About this app!!!", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(item);
		return menu;
	}
	
	protected void initEditorBar()
	{
		toolPanel = cursor.toolPanel;
		getMainPanel().add(toolPanel, BorderLayout.EAST);
	}
	
	
	public void shutDownGame()
	{
		shutDown();
	}
	
	@Override
	protected void processInput(float deltaTime)
	{
		super.processInput(deltaTime);
		mouse.setWorldPosition(getWorldMousePosition());
			cursor.processInput(mouse,deltaTime);
	}
	
	@Override
	protected void updateObjects(float deltaTime)
	{
		ArrayList<GameObject> copy = new ArrayList<GameObject>(objects);
		for(GameObject go : copy)
			go.update(deltaTime);
		
	}

	@Override
	protected void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		Matrix3x3f view = getViewportTransform();
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
			);
		cursor.render(g2d, view);
		for(GameObject go : objects)
		{
			go.render(g, view);
		}
	}
	
}
