package genesis.editor.swing;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SwingConsole extends JPanel 
{
	private static DefaultListModel<String> messages = new DefaultListModel<String>();
	private static JList contentList = new JList(messages);
	
	public static void log(String message)
	{
		messages.addElement(message);
		System.out.println(message);
	}
	
	public SwingConsole(boolean isDoubleBuffered) 
	{
		super(isDoubleBuffered);
		init();
	}

	public SwingConsole(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		init();
	}

	public SwingConsole(LayoutManager layout) {
		super(layout);
		init();
	}

	public SwingConsole()
	{
		super();
		init();
	}
	
	public void init()
	{
		JScrollPane listScroller = new JScrollPane(contentList);
		listScroller.setPreferredSize(new Dimension(250, 80));
		contentList.setLayoutOrientation(JList.VERTICAL);
		contentList.setVisibleRowCount(3);
		listScroller.add(contentList);
		//add(contentList);
		add(listScroller);
		
	}
	
}
