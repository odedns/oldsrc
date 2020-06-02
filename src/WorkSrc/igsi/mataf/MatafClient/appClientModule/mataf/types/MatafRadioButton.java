package mataf.types;

import java.awt.ComponentOrientation;
import java.awt.Font;

import mataf.utils.FontFactory;

import com.ibm.dse.gui.SpRadioButton;

/**
 * @author Nati Dykstein
 *
 */

public class MatafRadioButton extends SpRadioButton 
{
	private static final Font FONT = FontFactory.getDefaultFont();

	public MatafRadioButton()
	{
		this("כפתור", false);
	}
	
	public MatafRadioButton(String text) 
	{
		this(text, false);
	}
	
	public MatafRadioButton(String text, boolean selected) 
	{
		super(text, selected);
		setText(text); // Override's stupid code in SpRadioButton.
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		setFont(FONT);
		setOpaque(false);
		setRolloverEnabled(true);
		setSize(40,16);
	}
	
	
}
