package mataf.text;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

import mataf.types.MatafTextField;
import mataf.types.textfields.MatafDecimalField;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (01/03/2004 16:49:53).  
 */
public class MatafDecimalFormatter extends NumberFormatter
{
	
	/**
	 * 
	 */
	public MatafDecimalFormatter()
	{
		super();		
	}

	/**
	 * @param format
	 */
	public MatafDecimalFormatter(NumberFormat format)
	{
		super(format);
	}

	/** 
	 * Supply a custom DocumentFilter.
	 */
	protected DocumentFilter getDocumentFilter()
	{
		return this.new MatafNumberFormatterDocumentFilter();
	}

	class MatafNumberFormatterDocumentFilter extends DocumentFilter
	{
		/**
		 * Allow removing of the last char in the textfield.
		 * 
		 * @see javax.swing.text.DocumentFilter#remove(javax.swing.text.DocumentFilter.FilterBypass, int, int)
		 */
		public void remove(FilterBypass fb, int offset, int length)
			throws BadLocationException
		{
			// Allow deleting the last char.
			if(offset==0 && length==1)
				fb.remove(offset, length);
			else
			if(offset==1 && length==1)
				fb.remove(offset, length);
				else			
					MatafDecimalFormatter.super.getDocumentFilter().remove(fb, offset, length);
		}
	
		/**
		 * Used as a delegator to the methods in Calculator class.
		 * 
		 * Replace actually occurs only when setting an empty string, on all other
		 * cases the call is eventually delegated to the insertString() method.
		 * 
		 * @see javax.swing.text.DocumentFilter#replace(javax.swing.text.DocumentFilter.FilterBypass, int, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void replace(FilterBypass fb, int offset, int length, String text,	AttributeSet attrs)
														throws BadLocationException
		{
			// Allow a state of an empty field.
			if(text.equals(""))
			{
				fb.replace(offset, length, text, attrs);
				return;
			}
			
			// Fixes a problem with the insertion point of the decimal point.
			// There is a weird cursor behavior in the textfield.
			if(text.equals("."))
			{
				// Allow insertion of only a single '.' not at the begining.
				if(getFormattedTextField().getText().indexOf('.')==-1 && offset!=0)
					fb.replace(offset, length, text, attrs);
				return;
			}
			
			if(allowReplace(offset, text))
				MatafDecimalFormatter.super.getDocumentFilter().replace(fb, offset, length, text, attrs);

			if(shouldTransferFocus(offset, text))
				((MatafTextField)getFormattedTextField()).activateFocusTraversal(false, true);
		}

		/**
		 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
																				throws BadLocationException
		{
			MatafDecimalFormatter.super.getDocumentFilter().insertString(fb, offset, string, attr);
		}
		
		/**
		 * Returns true if insertion of char is currently permitted.
		 * 
		 * @param offset
		 * @return
		 */
		private boolean allowReplace(int offset, String text)
		{
			MatafDecimalField mtf = (MatafDecimalField)getFormattedTextField();
				
			// When max chars reached allow typing only if we're replacing a 
			// selected text or we're in overwrite mode.
			boolean hasTextSelected = mtf.getSelectedText()!=null;
			
			if(mtf.isMaxCharsReached() && !hasTextSelected)
			{
				if(isInOverwriteMode())
				{
					// Allow only to overwrite exisiting chars.
					if(offset==mtf.getText().length())
						return false;
				}
				else
					return false;
			}
			
			if(exceedingFractionDigits(offset))
				return false;
			
			return true;
		}
		
		/**
		 * Returns true if a we're trying to exceed to number of
		 * allowed fraction digits.
		 * 
		 * @param offset - The position of inserted text.
		 * @param text - The text inserted.
		 * @return
		 */
		private boolean exceedingFractionDigits(int offset)
		{
			// Get our textfield.
			MatafDecimalField mtf = (MatafDecimalField)getFormattedTextField();
	
			// Get it's text.
			String currentText = mtf.getText();
			
			// Find the decimal point.
			int indexOfPoint = currentText.indexOf('.');
			if(indexOfPoint==-1)
				 return false;
	
			// Find how many fraction digits we already have.
			int fractionDigitsNumber = 
							currentText.substring(indexOfPoint+1).length();
					
			// Get the maximum number of fraction digits allowed.
			int maxNumberOfFractionDigits = 
							((DecimalFormat)getFormat()).getMaximumFractionDigits();
	
			// Compare them.
			if(fractionDigitsNumber==maxNumberOfFractionDigits)
			{
				// Check that we're inserting at the fraction part.
				if(offset>=indexOfPoint)
					return true;
			}
	 
			return false;
		}
	
		/**
		 * Return the textfield overwrite state.
		 * 
		 * @return
		 */
		private boolean isInOverwriteMode()
		{
			MatafDecimalField mtf = (MatafDecimalField)getFormattedTextField();
			
			return ((DefaultFormatter)mtf.getFormatter()).getOverwriteMode();
		}
		
		/**
		 * Returns true if conditions yields a focus trasfer.
		 * 
		 * @param offset
		 * @return
		 */
		private boolean shouldTransferFocus(int offset,String text)
		{
			// Prevent invokations of setText() from transferring the focus.
			// Automatic transfer of the focus should be allowed only
			// when manually inserting the text.
			if(text.length()>1)
				return false;
			
			MatafDecimalField mtf = (MatafDecimalField)getFormattedTextField();
			
			// Transfer focus if maxChars is reached.
			if(mtf.isMaxCharsReached())
			{
				// Check if the textfield in overwrite mode.
				if(isInOverwriteMode())
				{
					// Here we are after the invokation of replace(),
					// so the offset needs to be incremented to reflect the real
					// cursor position.
					// The offset is legal if it's on the last char.
					if((offset+1==mtf.getText().length()))
						return true;
				}
				else
					return true;					
			}
			
			return false;
		}
	}
}
