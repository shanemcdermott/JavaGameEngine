package genesis.editor.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import genesis.cell.Cell;
import javagames.game.GameRoom;
import javagames.util.Vector2f;

public class CellDetailsPanel extends JPanel
{
	protected Cell object;
	
	protected TitledBorder title;
	protected JTextArea namePane;
	protected JTextArea locationPane;
	JList contentList;
	DefaultListModel<String> listModel;
	
	public CellDetailsPanel()
	{
		super();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		title = BorderFactory.createTitledBorder("Room Details");
		
		JLabel nameLabel = new JLabel("Name", JLabel.CENTER);
		
		namePane = new JTextArea();
		namePane.setText("Name");
		namePane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		nameLabel.setLabelFor(namePane);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				object.setAttribute("name",namePane.getText());
			}
		});
		
		JLabel locationLabel = new JLabel("Location", JLabel.CENTER);
				
		locationPane = new JTextArea();
		locationPane.setText(new Vector2f().toString());
		locationPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		locationPane.setEditable(false);
		locationLabel.setLabelFor(locationPane);
		
		JPanel actorPanel = new JPanel();
		actorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		actorPanel.add(nameLabel);
		actorPanel.add(namePane);
		
		setBorder(title);
		add(actorPanel);

		JPanel locPanel = new JPanel();
		locPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		locPanel.add(locationLabel);
		locPanel.add(locationPane);
		add(locPanel);
		
		add(saveButton);
		
		listModel = new DefaultListModel<String>();
		contentList = new JList<String>(listModel);
		contentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentList.setLayoutOrientation(JList.VERTICAL);
		contentList.setVisibleRowCount(5);
		//contentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		JPanel tagPanel = new JPanel();
		tagPanel.setLayout(new BoxLayout(tagPanel, BoxLayout.Y_AXIS));
		tagPanel.setBorder(BorderFactory.createTitledBorder("Contents"));
		tagPanel.add(contentList);
		JTextArea tagField = new JTextArea();
		tagField.setText("Add a tag");
		tagField.setBorder(BorderFactory.createEtchedBorder());
		tagPanel.add(tagField);
		
		JButton submitButton = new JButton("Add");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getCell().setAttribute(tagField.getText(),null);
				updateList();
				tagField.setText("Add a tag");
			}
		});
		tagPanel.add(submitButton);
		
		
		add(tagPanel);
		//add(locationLabel);
		//add(locationPane);
		

		
	}

	public Cell getCell()
	{
		return object;
	}
	
	public void setCell(Cell cell)
	{
		object = cell;
		
		if(cell == null) return;
		String n = (String)cell.getAttribute("name");
		
		if(n == null)
			n = "cell";
			
		namePane.setText(n);
		locationPane.setText(cell.getPosition().toString());
		
		updateList();
	}
	
	public void updateList()
	{
		listModel.clear();
		Set<String> tags = getCell().getAttributeNames();
		for(String s : tags)
		{
			listModel.addElement(s);
		}
	}
}
