package genesis.editor.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import javagames.game.GameObject;
import javagames.util.Vector2f;

public class DetailsPanel extends JPanel
{
	protected GameObject object;
	
	protected TitledBorder title;
	protected JTextArea namePane;
	protected JTextArea locationPane;
	
	public DetailsPanel()
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
				object.setName(namePane.getText());
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
	}
	
	public void setObject(GameObject o)
	{
		this.object = o;
	}
}
