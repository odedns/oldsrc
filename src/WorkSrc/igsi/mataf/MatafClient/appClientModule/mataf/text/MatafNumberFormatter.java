package mataf.text;

import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

import mataf.types.MatafTextField;
import mataf.types.textfields.MatafNumericField;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (01/03/2004 16:49:53).  
 */
public class MatafNumberFormatter extends NumberFormatter
{
	
	/**
	 * 
	 */
	public MatafNumberFormatter()
	{
		super();		
	}

	/**
	 * @param format
	 */
	public MatafNumberFormatter(NumberFormat format)
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
			/*// Allow deleting the last char.
			if(offset==0 && length==1)
				fb.remove(offset, length);
			else
			if(offset==1 && length==1)
				fb.remove(offset, length);
				else			
					MatafNumberFormatter.super.getDocumentFilter().remove(fb, offset, length);*/
			fb.remove(offset, length);
		}
	
		/**
		 *
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
			
			if(allowReplace(offset, text))
			{
				// Allow insertion of leading zeros.
				if(Character.isDigit(text.charAt(0)))
					fb.replace(offset, length, text, attrs);
				else // Invoke the replace() method.
					MatafNumberFormatter.super.getDocumentFilter().replace(fb, offset, length, text, attrs);
			}
			
			if(shouldTransferFocus(offset, text))
			{
				//System.out.println("Transferring focus, text = "+text);
				((MatafTextField)getFormattedTextField()).activateFocusTraversal(false, true);
			}
		}
		
		private boolean insertingLeadingZeros(int offset)
		{
			String t = getFormattedTextField().getText();
			for(int i=0;i<offset;i++)
				if(t.charAt(i)!='0')
					return false;
			return true;
		}
		
		/**
		 * Returns true if insertion of char is currently permitted.
		 * 
		 * @param offset
		 * @return
		 */
		private boolean allowReplace(int offset, String text)
		{
			MatafNumericField mtf = (MatafNumericField)getFormattedTextField();
				
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
			
			return true;
		}
		
		/**
		 * Return the textfield overwrite state.
		 * 
		 * @return - true if we're in overwrite state.
		 */
		private boolean isInOverwriteMode()
		{
			MatafNumericField mtf = (MatafNumericField)getFormattedTextField();
			
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
			MatafNumericField mtf = (MatafNumericField)getFormattedTextField();
			
			// If only one char is allowed, we surely can transfer the focus.
			if(mtf.getMaxChars()==1)
				return true;
			
			// Ignore identical values.
			if(text.equals(mtf.getText()))
				return false;
			
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
					if((offset+1==mtf.getMaxChars()))
						return true;
				}
				else
					return true;					
			}
			
			return false;
		}
		
		/**
		 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
																				throws BadLocationException
		{
			MatafNumberFormatter.super.getDocumentFilter().insertString(fb, offset, string, attr);
		}
	}
}
