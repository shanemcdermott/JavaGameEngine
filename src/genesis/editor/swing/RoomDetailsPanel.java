package genesis.editor.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javagames.game.GameRoom;
import javagames.util.Vector2f;

public class RoomDetailsPanel extends DetailsPanel
{
	JList contentList;
	DefaultListModel<String> listModel;
	

	ElevationPanel elevPanel;
	
	public RoomDetailsPanel()
	{
		super();
		
		
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
				//getRoom().setComponent(tagField.getText(),null);
				updateList();
				tagField.setText("Add a tag");
			}
		});
		tagPanel.add(submitButton);
		
		
		add(tagPanel);
		//add(locationLabel);
		//add(locationPane);
		
		elevPanel = new ElevationPanel(0.01f);
		add(elevPanel);
		
	}

	public GameRoom getRoom()
	{
		return (GameRoom)object;
	}
	
	public void setRoom(GameRoom room)
	{
		object = room;
		
		if(room == null) return;
		namePane.setText(room.getName());
		locationPane.setText(room.getPosition().toString());
		elevPanel.setRoom(room);
		updateList();
	}
	
	public void updateList()
	{
		listModel.clear();
		listModel.addElement(((GameRoom)object).getEcosystem().toString());
		/*Set<String> tags = getRoom().getComponentNames();
		for(String s : tags)
		{
			listModel.addElement(s);
		}
		*/
	}
}
