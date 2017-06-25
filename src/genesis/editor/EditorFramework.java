package genesis.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import genesis.editor.tool.EditorTool;
import genesis.editor.swing.SwingConsole;
import genesis.editor.swing.TextAreaOutputStream;
import javagames.framework.SwingFramework;
import javagames.g2d.Drawable;
import javagames.g2d.GridLines;
import javagames.game.GameObject;
import javagames.util.Matrix3x3f;



public class EditorFramework extends SwingFramework
{

	private final int maxLines = 100;
	protected ArrayList<Drawable> objects;
	protected HashMap<String,EditorTool> tools;
	
	protected EditorTool cursor;
	
	protected static String[] params;
	protected JTextField tagField; 

	private JPanel editorPanel;
	private SwingConsole c;
	
	
	public EditorFramework()
	{
		super();
		appTitle = "Editor";
		objects = new ArrayList<Drawable>();
		objects.add(new GridLines(this));
		tools = new HashMap<String, EditorTool>();
	}

	public void addObject(Drawable drawable)
	{
		objects.add(drawable);
		objects.sort(null);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		initTools();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(initFileMenu());
		menuBar.add(initToolMenu());
		menuBar.add(initViewMenu());
		menuBar.add(initHelpMenu());
		setJMenuBar(menuBar);
		add(initEditorBar(), BorderLayout.EAST);
		add(initTopBar(), BorderLayout.NORTH);
	}


	protected void initTools()
	{
		cursor = new EditorTool(this);
	}
	
	protected JMenu initFileMenu()
	{
		JMenu menu = new JMenu("File");
		JMenuItem item = new JMenuItem(new AbstractAction("Exit") {
			public void actionPerformed(ActionEvent e) {
				EditorFramework.this.dispatchEvent(new WindowEvent(
						EditorFramework.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		menu.add(item);
		return menu;
	}
	
	protected JMenu initToolMenu()
	{
		JMenu menu = new JMenu("Tools");
		for(Map.Entry<String,EditorTool> entry : tools.entrySet())
		{
			JMenuItem item = new JMenuItem(new AbstractAction(entry.getKey()) {
				public void actionPerformed(ActionEvent e) {
						changeTool(entry.getKey(), entry.getValue());
				}
			});
			menu.add(item);
		}
		return menu;
	}
	
	protected JMenu initViewMenu()
	{
		JMenu menu = new JMenu("View");
		return menu;
	}
	
	protected JMenu initHelpMenu()
	{
		JMenu menu = new JMenu("Help");
		JMenuItem item = new JMenuItem(new AbstractAction("About") {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(EditorFramework.this,
						"Author: Shane McDermott\n2017\nAll Rights Reserved.", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		menu.add(item);
		return menu;
	}
	
	protected JPanel initTopBar()
	{
		JPanel topPanel = new JPanel();
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				reset();
			}
			
		});
		topPanel.add(clearButton);
		
		JButton createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				changeTool("Create Cell", tools.get("Create Cell"));
			}
			
		});
		topPanel.add(createButton);
		
		JTextArea gridX = new JTextArea("Grid X");
		JTextArea gridY = new JTextArea("Grid Y");
		JButton gridButton = new JButton("Resize Grid");
		gridButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				resizeGrid(Integer.parseInt(gridX.getText()), Integer.parseInt(gridY.getText()));
			}
			
		});
		topPanel.add(gridX);
		topPanel.add(gridY);
		topPanel.add(gridButton);
		
		return topPanel;
		
	}
	
	protected JPanel initEditorBar()
	{
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		
		CardLayout cl = new CardLayout();
		editorPanel = new JPanel(cl);
		
		for(Map.Entry<String,EditorTool> entry : tools.entrySet())
		{
			editorPanel.add(entry.getValue().toolPanel,entry.getKey());
		}
		JTextArea textArea = new JTextArea();
		PrintStream con=new PrintStream(new TextAreaOutputStream(textArea,maxLines));
		System.setOut(con);
		System.setErr(con);
		sidePanel.add(editorPanel);
		sidePanel.add(textArea);
		return sidePanel;
	}
	
	protected void resizeGrid(int x, int y)
	{
		GridLines grid = (GridLines)objects.get(objects.size()-1);
		grid.setNumLines(x, y);
	}
	
	public void reset()
	{
		//Implemented in children
	}
	
	public void changeTool(String toolName, EditorTool newTool)
	{
		cursor.deactivate();
		cursor = newTool;
		CardLayout cl = (CardLayout)(editorPanel.getLayout());
		cl.show(editorPanel, toolName);
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
		ArrayList<Drawable> copy = new ArrayList<Drawable>(objects);
		for(Drawable dr : copy)
		{
			if(dr instanceof GameObject)
			{
				GameObject go  = (GameObject)dr;
				go.update(deltaTime);
			}
		}
		
	}
	
	@Override
	protected void render(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		Matrix3x3f view = getViewportTransform();
		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON
			);
		renderObjects(g2d,view);
		cursor.render(g2d, view);
	}
	
	protected void renderObjects(Graphics2D g2d, Matrix3x3f view)
	{
		for(Drawable go : objects)
		{
			go.render(g2d, view);
		}
	}
}
