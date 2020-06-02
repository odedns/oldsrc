package mataf.types.textfields;

import java.text.DecimalFormat;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import mataf.text.MatafNumberDisplayFormatter;
import mataf.text.MatafNumberFormatter;
import mataf.types.MatafTextField;

/**
 * This textfield is dedicated for displaying numerical serial numbers.<p>
 * By default it fills-in the input with a specified char(fillInChar) until
 * the length reaches the maxChars property.
 * 
 * To display decimal values(i.e. amounts) use MatafDecimalField.
 *
 * @author Nati Dykstein. Creation Date : (10/03/2004 12:51:21).  
 */
public class MatafNumericField extends MatafTextField
{
	/** The char used to 'fillIn' empty spaces. */
	private char		fillInChar;
	/** Indicates whether we are 'filling in' the input. */
	private boolean 	useFillInChar = true;
	
	
	public MatafNumericField()
	{
		super();
		initComponent();		
	}

	public MatafNumericField(String text)
	{
		super(text);
		initComponent();
	}
	
	/**
	 * Initializes the textfield with the appropriate formatter.
	 */
	private void initComponent()
	{
		// Create and configure the format.
		DecimalFormat ourFormat = (DecimalFormat)DecimalFormat.getNumberInstance();
		ourFormat.setParseIntegerOnly(true);
		ourFormat.setGroupingUsed(false);		
		
		// Create the formatter with our format.
		MatafNumberFormatter mnf = new MatafNumberFormatter(ourFormat);		
		mnf.setAllowsInvalid(false);
		mnf.setOverwriteMode(false);
		mnf.setMinimum(new Double(0));
		
		// Install the formatter in the factory.
		setFormatterFactory(new DefaultFormatterFactory(mnf));
	}
	
	/**
	 * Fills in the input in the textfield with leading chars of type 
	 * 'fillInChar' to the length of maxChar.
	 * This method will be activated only if the fillInChar and the
	 * maxChars properties have been set.
	 */
/*	public void applyDisplayPattern()
	{
		if(useFillInChar)
		{
			// System.out.println("Applyings display Pattern=" + getDisplayPattern());
			getNumberFormatter().setAllowsInvalid(true);
			System.out.println("Before applyingDisplayPattern : text = "+getText());
			getDecimalFormat().applyPattern(getDisplayPattern());
			System.out.println("After applyingDisplayPattern : text = "+getText());
			try
			{
				((DefaultFormatterFactory)getFormatterFactory()).getDisplayFormatter().valueToString(new Long(getText()));
			}
			catch (NumberFormatException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (ParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//setText(getUnFormattedText());
		}
	}*/
	
	/**
	 * Applies the edit pattern to the textfield,
	 * and disallow invalid input.
	 */
/*	public void applyEditPattern()
	{
		getDecimalFormat().applyPattern(getEditPattern());
		getNumberFormatter().setAllowsInvalid(false);
	}*/
	
	/**
	 * @return
	 */
	public boolean isGroupingUsed()
	{		
		return getDecimalFormat().isGroupingUsed();
	}

	/**
	 * @see java.text.NumberFormat.setGroupingUsed(boolean b)
	 * @param groupingUsed
	 */
	public void setGroupingUsed(boolean groupingUsed)
	{
		getDecimalFormat().setGroupingUsed(groupingUsed);
	}

	/**
	 * Updates the display pattern according to the max chars property.
	 */
	public void setMaxChars(int maxChars)
	{
		super.setMaxChars(maxChars);
		
		// Use '0' as the default filler.
		if(fillInChar==Character.UNASSIGNED)
			setFillInChar('0');
		
		if(maxChars==Integer.MAX_VALUE)
			useFillInChar = false;
		
		installDisplayFormatter();
	}
	
	/**
	 * Installs/Uninstalls the display formatter according to the
	 * value of useFillInChar property.
	 */
	private void installDisplayFormatter()
	{
		if(useFillInChar)
		{
			StringBuffer pattern = new StringBuffer(getMaxChars());						
			for(int i=0;i<getMaxChars();i++)
				pattern.insert(0,fillInChar);		
		
			// Create a display formatter.
			DecimalFormat displayFormat = new DecimalFormat(pattern.toString());
			NumberFormatter numberDisplayFormatter = 
					new MatafNumberDisplayFormatter(displayFormat);
			
			// Install the display formatter.
			DefaultFormatterFactory dff = (DefaultFormatterFactory)getFormatterFactory();
			dff.setDisplayFormatter(numberDisplayFormatter);
		}
		else
		{
			DefaultFormatterFactory dff = (DefaultFormatterFactory)getFormatterFactory();
			dff.setDisplayFormatter(null);
		}
	}
	
	/**
	 * Returns the number formatter used by the textfield.
	 * 
	 * @return The number formatter used by the textfield.null if none is used.
	 */
	private MatafNumberFormatter getNumberFormatter()
	{		
		return (MatafNumberFormatter)getFormatterFactory().getFormatter(this);
	}

	/**
	 * Returns the decimal format used in the number formatter of the textfield.
	 * @return
	 */
	private DecimalFormat getDecimalFormat()
	{
		return (DecimalFormat)(getNumberFormatter().getFormat());		
	}
	
	/**
	 * Returns the fillInChar used to fill in the input.
	 * @return char
	 */
	public char getFillInChar() {
		return fillInChar;
	}

	/**
	 * Sets the fillInChar.
	 * Will be used only if the maxChars property was set.<p>
	 * 
	 * @param fillInChar The fillInChar to set
	 */
	public void setFillInChar(char fillInChar) 
	{
		this.fillInChar = fillInChar;
	}

	/**
	 * @return true - If we are using the display pattern to fill in the input.
	 */
	public boolean isUseFillInChar()
	{
		return useFillInChar;
	}

	/**
	 * Set to false to prevent the display pattern from filling in the input.
	 * @param useFillInChar
	 */
	public void setUseFillInChar(boolean useFillInChar)
	{
		if(this.useFillInChar!=useFillInChar)
		{
			this.useFillInChar = useFillInChar;
			
			// Reset display formatter. 
			installDisplayFormatter();
		}
	}
}
