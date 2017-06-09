package genesis.editor.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javagames.game.GameObject;
import javagames.util.Vector2f;
import javagames.util.geom.BoundingBox;

public class SizePanel extends JPanel
{
	
	static final int minSize = 1;
	static final int maxSize = 100;
	
	BoundingBox sourceObject;
	
	JTextArea widthPane;
	JSlider widthSlider;
	JTextArea heightPane;
	JSlider heightSlider;
	
	public SizePanel(BoundingBox sourceObject)
	{
		this.sourceObject=sourceObject;
		int w = Math.round(sourceObject.getWidth());
		int h = Math.round(sourceObject.getHeight());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Cell Details"));
		
		JLabel widthLabel = new JLabel("Width", JLabel.CENTER);
		widthPane = new JTextArea();
		widthPane.setText(Float.toString(w));
		widthPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		widthPane.addInputMethodListener(new InputMethodListener(){
			public void inputMethodTextChanged(InputMethodEvent e){
				JTextArea pane = (JTextArea)e.getSource();
				changeWidth(Float.parseFloat(pane.getText()));
			}
			
			public void caretPositionChanged(InputMethodEvent e) {}
		});
		
		widthLabel.setLabelFor(widthPane);
		
		widthSlider = new JSlider(JSlider.HORIZONTAL,minSize, maxSize, w);
		widthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				changeWidth(source.getValue());
			}
		});
		
		int tickSpace = (maxSize-minSize)/10;
		widthSlider.setMajorTickSpacing(tickSpace);
		widthSlider.setMinorTickSpacing(tickSpace/2);
		widthSlider.setPaintTicks(true);
		widthSlider.setPaintLabels(true);
		
		
		JLabel heightLabel = new JLabel("Height", JLabel.CENTER);
		
		heightPane = new JTextArea();
		heightPane.setText(Float.toString(h));
		heightPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		heightPane.addInputMethodListener(new InputMethodListener(){
			public void inputMethodTextChanged(InputMethodEvent e){
				JTextArea pane = (JTextArea)e.getSource();
				changeHeight(Float.parseFloat(pane.getText()));
			}
			
			public void caretPositionChanged(InputMethodEvent e) {}
		});
		
		heightLabel.setLabelFor(heightPane);
		
		heightSlider = new JSlider(JSlider.HORIZONTAL,minSize, maxSize, h);
		heightSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				changeHeight(source.getValue());
			}
		});
		
		heightSlider.setMajorTickSpacing(tickSpace);
		heightSlider.setMinorTickSpacing(tickSpace/2);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		
		
		add(widthLabel);
		add(widthSlider);
		add(widthPane);
		
		
		add(heightLabel);
		add(heightSlider);
		add(heightPane);
		
		
	}
	
	public void changeWidth(float width)
	{
		resizeBox(width, sourceObject.getHeight());
	}
	
	public void changeHeight(float height)
	{
		resizeBox(sourceObject.getWidth(), height);
	}
	
	public void resizeBox(Vector2f newSize)
	{
		resizeBox(newSize.x,newSize.y);
	}
	
	public void resizeBox(float width, float height)
	{
		sourceObject.resize(width, height);
		widthSlider.setValue((int)width);
		widthPane.setText(Float.toString(width));
		heightSlider.setValue((int)height);
		heightPane.setText(Float.toString(height));
	}
}
