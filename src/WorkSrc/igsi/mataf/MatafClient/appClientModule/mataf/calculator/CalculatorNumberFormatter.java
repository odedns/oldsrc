package mataf.calculator;

import java.text.NumberFormat;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (01/03/2004 16:49:53).  
 */
public class CalculatorNumberFormatter extends NumberFormatter
{
	private Calculator calc;

	
	/**
	 * @param format
	 */
	public CalculatorNumberFormatter(NumberFormat format, Calculator calc)
	{
		super(format);
		this.calc = calc;
	}
	
	/** 
	 * Supply a custom DocumentFilter.
	 */
	protected DocumentFilter getDocumentFilter()
	{
		return this.new CalculatorNumberFormatterDocumentFilter();
	}

	class CalculatorNumberFormatterDocumentFilter extends DocumentFilter
	{
		/**
		 * Allow removing of the last char in the textfield.
		 * 
		 * @see javax.swing.text.DocumentFilter#remove(javax.swing.text.DocumentFilter.FilterBypass, int, int)
		 */
		public void remove(FilterBypass fb, int offset, int length)
			throws BadLocationException
		{
			//System.out.println("REMOVING :");
			//System.out.println("offset = "+offset);
			//System.out.println("length=" + length);
			
			// Allow deleting the last char.
			if(offset==0 && length==1)
				fb.remove(offset, length);
			else
			if(offset==1 && length==1)
				fb.remove(offset, length);
				else			
					CalculatorNumberFormatter.super.getDocumentFilter().remove(fb, offset, length);
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
			if(text.equals(""))
			{
				fb.replace(offset, length, text, attrs);
				return;
			}
			
			if(Calculator.isAnOperator(text))
			{
				calc.processOperators(text);
				return;
			}
			
			if(Calculator.isDigitOrDecimalPoint(text))
			{
				calc.processDigits(text);
			}
					
			//CalculatorNumberFormatter.super.getDocumentFilter().replace(fb, offset, length, text, attrs);
			
		}

		/**
		 * Here is where all the string insertions occur.
		 * Special treatment is made when inserting a '-' or '.' chars, and
		 * when the number is represented exponentially.<p>
		 * (Special treatment is needed or else the formatter will not allow the
		 *  insertions of these chars).
		 * 
		 * @see javax.swing.text.DocumentFilter#insertString(javax.swing.text.DocumentFilter.FilterBypass, int, java.lang.String, javax.swing.text.AttributeSet)
		 */
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
																				throws BadLocationException
		{
			boolean valueRepresentedExponentially = string.indexOf('E')!=-1;
			if(string.equals("-") || string.equals(".") || valueRepresentedExponentially)
				fb.insertString(offset, string, attr);
			else
				CalculatorNumberFormatter.super.getDocumentFilter().insertString(fb, offset, string, attr);
		}
	}


}
