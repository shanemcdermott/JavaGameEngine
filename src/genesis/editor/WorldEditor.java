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
import java.util.List;

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
	public	  Dungeon dungeon;
	protected EditorTool cursor;
	
	protected static String[] params;
	protected JTextField tagField; 
	private JPanel mainPanel;
	private JPanel centerPanel;
	
	public WorldEditor()
	{
		super();
		objects = Collections.synchronizedList(new ArrayList<GameObject>());
		dungeon = new Dungeon("EditorDungeon");
		objects.add(dungeon);
		cursor = new EditorTool(this);
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
		JMenuItem item = new JMenuItem(new AbstractAction("Room Select") {
			public void actionPerformed(ActionEvent e) {
				WorldEditor.this.cursor = new RoomSelectTool(WorldEditor.this);
				WorldEditor.this.cursor.setColor(Color.BLUE);
				
			}
		});
		menu.add(item);
		ArrayList<JMenuItem> items = new ArrayList<JMenuItem>();
		//pointEditor.getCreateMenuItems(items);
		//cellEditor.getCreateMenuItems(items);
		for(JMenuItem jmi : items)
		{
			menu.add(jmi);
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
		JPanel p = new JPanel();
		p.add(new JLabel("Room Tag"));
		tagField = new JTextField(3);
		tagField.setHorizontalAlignment(JTextField.CENTER);
		tagField.setText("Tag");
		p.add(tagField);

		JButton button = new JButton("Add");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		p.add(button);
		getMainPanel().add(p, BorderLayout.SOUTH);
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
