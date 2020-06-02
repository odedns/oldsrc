package mataf.types.textfields;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.text.DefaultFormatterFactory;

import mataf.text.MatafDecimalFormatter;
import mataf.types.MatafTextField;

/**
 * This textfield is dedicated for displaying decimal values.<p>
 * It uses digits grouping and has control over the following properties :<p>
 * - Number of fraction digits.<p>
 * - Number of integer digits.<p>
 * - Maximum and minimum values.<p>
 * 
 * To display numerical-serial values(i.e. account number) use MatafNumericField.
 *
 * @author Nati Dykstein. Creation Date : (10/03/2004 12:51:46).  
 */
public class MatafDecimalField extends MatafTextField
{
	private static final int DEFAULT_NUMBER_OF_FRACTION_DIGITS = 2 ;
	
	
	public MatafDecimalField()
	{
		super();
		initComponent();
	}

	public MatafDecimalField(String text)
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
		ourFormat.setMaximumFractionDigits(DEFAULT_NUMBER_OF_FRACTION_DIGITS);
		
		// Create the formatter with our format.
		MatafDecimalFormatter mdf = new MatafDecimalFormatter(ourFormat);
		mdf.setAllowsInvalid(false);
		mdf.setOverwriteMode(false);
		mdf.setMinimum(new Double(0));
		mdf.setMaximum(new Double(1000000000));
		
		// Install the formatter in the factory.
		setFormatterFactory(new DefaultFormatterFactory(mdf));
	}

	/**
	 * Fills in the input in the textfield with leading chars of type 
	 * 'fillInChar' to the length of maxChar.
	 * This method will be activated only if the fillInChar and the
	 * maxChars properties have been set.
	 */
/*	public void applyDisplayPattern()
	{		
		getDecimalFormatter().setAllowsInvalid(true);
		//System.out.println("Applyings display Pattern=" + getDisplayPattern());
		getDecimalFormat().applyPattern(getDisplayPattern());
		setText(getUnFormattedText());
	}*/
	
	/**
	 * Applies the edit pattern to the textfield,
	 * and disallow invalid input.
	 */
/*	public void applyEditPattern()
	{
		getDecimalFormat().applyPattern(getEditPattern());
		getDecimalFormatter().setAllowsInvalid(false);
	}*/
	
	/**
	 * Overrides' MatafTextField's to return a value with no symbols.
	 * 
	 */
	public Object getDataValue()
	{
		return getUnFormattedText();
	}
	
	/**
	 * Override's MatafTextField to ignore symbols.
	 * 
	 * @return true - if the textfield currently holds
	 * the maximal number of chars.
	 */
	public boolean isMaxCharsReached()
	{
		if(getMaxChars()==Integer.MAX_VALUE)
			return false;
		return getUnFormattedText().length()==getMaxChars();
	}
	
	/**
	 * Returns the text without additional symbols.
	 * @return
	 */
	public String getUnFormattedText()
	{
		String text = getText();
	
		if(text.equals(""))
			return text;
		
		
		String unFormattedText = null;
		try
		{
			Object value = getDecimalFormatter().stringToValue(text);
			getDecimalFormat().setGroupingUsed(false);
			unFormattedText = getDecimalFormat().format(value);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		getDecimalFormat().setGroupingUsed(true);
	
		return unFormattedText;
	}
	
	/**
	 * @see mataf.types.textfields.MatafNumericField#setMaxChars(int)
	 */
	public void setMaxChars(int maxChars)
	{		
		super.setMaxChars(maxChars);
		
		getDecimalFormat().setMaximumIntegerDigits(maxChars);
	}
	
	/**
	 * Set the maximal value this decimal field may reach.
	 * @param maxValue
	 */
	public void setMaximum(double maxValue)
	{
		getDecimalFormatter().setMaximum(new Double(maxValue));
	}
	
	/**
	 * Returns the number formatter used by the textfield.
	 * 
	 * @return The number formatter used by the textfield.null if none is used.
	 */
	protected MatafDecimalFormatter getDecimalFormatter()
	{		
		return (MatafDecimalFormatter)getFormatterFactory().getFormatter(this);
	}
	
	/**
	 * Returns the decimal format used in the number formatter of the textfield.
	 * @return
	 */
	private DecimalFormat getDecimalFormat()
	{
		return (DecimalFormat)(getDecimalFormatter().getFormat());		
	}



	/**
	 * @return
	 */
	public int getNumberOfFractionDigits()
	{
		return getDecimalFormat().getMaximumFractionDigits();
	}

	/**
	 * @param n
	 */
	public void setNumberOfFractionDigits(int n)
	{
		getDecimalFormat().setMaximumFractionDigits(n);
	}

}
