package mataf.types.textfields;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import mataf.types.MatafTextField;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/03/2004 12:51:03).  
 */
public class MatafDateField extends MatafTextField
{

	private String dateFormatString = "HH:mm";
	
	/**
	 * 
	 */
	public MatafDateField()
	{
		super();
		initComponent();
	}

	/**
	 * @param text
	 */
	public MatafDateField(String text)
	{
		super(text);
		initComponent();
	}
	
	
	/**
	 * Use maskFormatter to format the editing.
	 * The mask is created by convertDateFormatStringToMask() .
	 */
	private void initComponent()
	{
		MaskFormatter maskFormatter = null;
		
		String mask = convertDateFormatStringToMask(dateFormatString);
		try
		{
			maskFormatter = new MaskFormatter(mask);
			maskFormatter.setPlaceholderCharacter('0');
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		} 
		
	
		//setFormatterFactory(new DefaultFormatterFactory(dateFormatter));
		setFormatterFactory(new DefaultFormatterFactory(maskFormatter));		
		
		setInputVerifier(new DateFormattedTextFieldVerifier());
	}
	
	/**
	 * Used to convert the format string to the appropriate mask formatter.
	 * For example : dd/MM/YYYY will turn to the mask ##/##/####.
	 *  
	 * @param stringFormat
	 * @return
	 */
	private static String convertDateFormatStringToMask(String stringFormat)
	{
		StringBuffer mask = new StringBuffer(stringFormat.length());
		for(int i=0;i<stringFormat.length();i++)
		{
			if(Character.isLetter(stringFormat.charAt(i)))
				mask.append('#');
			else
				mask.append(stringFormat.charAt(i));
		}
		
		System.out.println("stringFormat=" + stringFormat);
		System.out.println("mask=" + mask);
		
		return mask.toString();
	}
	
	/**
	 * @return String - The format in which the date in the textfield is displayed.
	 */
	public String getDateFormatString()
	{
		return dateFormatString;
	}

	/**
	 * Sets the format in which to display the date in the textfield.
	 * 
	 * @see java.text.SimpleDateFormat
	 * 
	 * @param string - The format according to SimpleDateFormat rules.
	 */
	public void setDateFormatString(String dateFormat)
	{
		try
		{
			getMaskFormatter().setMask(convertDateFormatStringToMask(dateFormat));
			
			// Needed to refresh the textfield contents.
			getMaskFormatter().install(this);			
			this.dateFormatString = dateFormat;
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the number formatter used by the textfield.
	 * 
	 * @return The number formatter used by the textfield.null if none is used.
	 */
	private MaskFormatter getMaskFormatter()
	{		
		return (MaskFormatter)getFormatterFactory().getFormatter(this);
	}
	
	/**
	 * Allows leaving the field only on a valid input.
	 *
	 * @author Nati Dykstein. Creation Date : (16/03/2004 16:21:07).
	 */
	static class DateFormattedTextFieldVerifier extends InputVerifier
	{
		static private SimpleDateFormat sdf;
		static private DateFormatter dateFormatter;
	
		static
		{
			sdf = new SimpleDateFormat();
			dateFormatter = new DateFormatter(sdf);
		}
	
		public boolean verify(JComponent input)
		{
			if (input instanceof JFormattedTextField)
			{
				MatafDateField mdf = (MatafDateField)input;
				sdf.applyPattern(mdf.getDateFormatString());
			
				String text = mdf.getText();
				//System.out.println("Trying to format : "+mdf.getText());
				try
				{
					//System.out.println("Formatted To : "+dateFormatter.stringToValue(text));
					mdf.setText(sdf.format(dateFormatter.stringToValue(text)));
					return true;
				}
				catch (ParseException pe)
				{
					mdf.setErrorMessage("ערך לא חוקי עבור "+mdf.getDateFormatString());
					if(!"".equals(mdf.getPreviousText()))
						mdf.setText(mdf.getPreviousText());
					return false;
				}
			
			}
			return true;
		}
		public boolean shouldYieldFocus(JComponent input)
		{
			return verify(input);
		}
	}
}



