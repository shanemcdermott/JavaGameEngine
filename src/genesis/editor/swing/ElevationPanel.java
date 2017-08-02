package genesis.editor.swing;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javagames.room.GameRoom;

public class ElevationPanel extends JPanel 
{
	private JSlider elevSlider;
	private JCheckBox[] noiseMasks;
	private final int minSize = 0;
	private final int maxSize = 100;
	private float elevationScale;
	private GameRoom room;
	
	public ElevationPanel()
	{
		this(0.01f);
	}
	
	public ElevationPanel(float scale)
	{
		super();
		elevationScale = scale;
		
		elevSlider = new JSlider(JSlider.HORIZONTAL,minSize, maxSize, 0);
		elevSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				getRoom().setElevation(source.getValue() * elevationScale);
			}
		});
		
		int tickSpace = (maxSize-minSize)/10;
		elevSlider.setMajorTickSpacing(tickSpace);
		elevSlider.setMinorTickSpacing(tickSpace/2);
		elevSlider.setPaintTicks(true);
		elevSlider.setPaintLabels(true);
		
		setBorder(BorderFactory.createTitledBorder("Elevation"));
		add(elevSlider);
		
		noiseMasks = new JCheckBox[]{
				new JCheckBox("White Noise"),
				new JCheckBox("Diamond Square"),
				new JCheckBox("Sea Level")
		};
		for(JCheckBox cb : noiseMasks)
		{
			cb.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e)
				{
					JCheckBox source = (JCheckBox)e.getSource();
					getRoom().setFlag(source.getText(), source.isSelected());
				}
			});
			add(cb);
		}
		
	}
	
	public GameRoom getRoom()
	{
		return room;
	}
	
	public void setRoom(GameRoom room)
	{
		this.room = room;
		elevSlider.setValue(Math.round(room.getElevation()*maxSize));
		for(JCheckBox b : noiseMasks)
			b.setSelected(room.getFlag(b.getText()));
	}
}
