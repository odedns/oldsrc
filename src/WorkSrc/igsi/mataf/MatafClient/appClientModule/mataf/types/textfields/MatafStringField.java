package mataf.types.textfields;

import java.awt.ComponentOrientation;

import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import mataf.types.MatafTextField;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/03/2004 12:50:29).  
 */
public class MatafStringField extends MatafTextField
{
	/**
	 * PENDING : Add a special formatter that transfers the focus when
	 * 				exceeding maxChars.
	 */
	public MatafStringField()
	{
		super();
		initComponent();
	}

	/**
	 * @param text
	 */
	public MatafStringField(String text)
	{
		super(text);
		initComponent();
	}
	
	private void initComponent()
	{
		setFormatterFactory(new DefaultFormatterFactory(new DefaultFormatter()));
		setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

}
