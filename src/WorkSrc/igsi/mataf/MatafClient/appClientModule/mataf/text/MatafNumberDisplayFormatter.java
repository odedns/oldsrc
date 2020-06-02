package mataf.text;

import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

import mataf.types.MatafTextField;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (01/03/2004 16:49:53).  
 */
public class MatafNumberDisplayFormatter extends NumberFormatter
{
	
	/**
	 * 
	 */
	public MatafNumberDisplayFormatter()
	{
		super();		
	}

	/**
	 * @param format
	 */
	public MatafNumberDisplayFormatter(NumberFormat format)
	{
		super(format);
	}

	/** 
	 * Supply a custom DocumentFilter.
	 */
	protected DocumentFilter getDocumentFilter()
	{
		return this.new MatafNumberDisplayFormatterDocumentFilter();
	}

	class MatafNumberDisplayFormatterDocumentFilter extends DocumentFilter
	{
		/**
		 * Allow removing of the last char in the textfield.
		 * 
		 * @see javax.swing.text.DocumentFilter#remove(javax.swing.text.DocumentFilter.FilterBypass, int, int)
		 */
		public void remove(FilterBypass fb, int offset, int length)
			throws BadLocationException
		{
			MatafNumberDisplayFormatter.super.getDocumentFilter().remove(fb, offset, length);
		}
	
		/**
		 * Allows the display formatter to show only digits.
		 * 
		 * @see javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void replace(FilterBypass fb, int offset, int length, String text,	AttributeSet attrs)
																						throws BadLocationException
		{
			MatafTextField mtf = (MatafTextField)getFormattedTextField();
			
			if(mtf.isMaxCharsReached())
				return;			

			if(!"".equals(text) && Character.isDigit(text.charAt(0)))		
				MatafNumberDisplayFormatter.super.getDocumentFilter().replace(fb, offset, length, text, attrs);
		}
		
				
		/**
		 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
																				throws BadLocationException
		{
			MatafNumberDisplayFormatter.super.getDocumentFilter().insertString(fb, offset, string, attr);
		}
	}
}
