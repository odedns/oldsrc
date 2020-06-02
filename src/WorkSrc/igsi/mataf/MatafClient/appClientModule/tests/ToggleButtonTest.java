package tests;

import javax.swing.JPanel;

import mataf.types.MatafToggleButton;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (19/01/2004 14:05:19).  
 */
public class ToggleButtonTest extends JPanel 
{
	private MatafToggleButton toggleButton;
	
	public ToggleButtonTest()
	{
		toggleButton = new MatafToggleButton();
		toggleButton.setText("Toggle Me!");
		add(toggleButton);
		setSize(300,200);
	}
}
