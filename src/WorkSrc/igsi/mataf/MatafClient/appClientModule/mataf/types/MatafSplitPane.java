package mataf.types;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JSplitPane;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/09/2003 17:19:05).  
 */
public class MatafSplitPane extends JSplitPane 
{
	/**
	 * Constructor for MatafSplitPane.
	 * @param newOrientation
	 * @param newLeftComponent
	 * @param newRightComponent
	 */
	public MatafSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent) 
	{
		super(newOrientation, newLeftComponent, newRightComponent);
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setBackground(Color.white);
		setOneTouchExpandable(true);
		setContinuousLayout(true);
	}
}
