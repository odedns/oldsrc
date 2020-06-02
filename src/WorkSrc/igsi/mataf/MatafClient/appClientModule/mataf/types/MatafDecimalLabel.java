/*
 * Created on 23/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.types;

import java.awt.ComponentOrientation;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.text.DefaultFormatterFactory;

import mataf.text.MatafNumberFormatter;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MatafDecimalLabel extends MatafLabel
{
	public MatafDecimalLabel()
	{
		super();
	}
	
	
	public MatafDecimalLabel(String text)
	{
		super(text, JLabel.RIGHT);
	}
	
	public MatafDecimalLabel(String text, int alignment)
	{
		super(text, alignment);
		//setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		//setDataDirection(Settings.OUTPUT_DIRECTION);
	}
		
	/* (non-Javadoc)
	 * @see javax.swing.JLabel#setText(java.lang.String)
	 */
	public void setText(String text)
	{
		// TODO Auto-generated method stub
		if(text.length()!=0)
		{
			DecimalFormat displayFormat = new DecimalFormat("###,###,###,###.00##");

			double dValue=0;
			try
			{
				dValue= Double.parseDouble(text);
			}
			catch(Exception e)
			{
				//We don't need any exception just continue
			}
			String sFormatedNum= displayFormat.format(dValue);
			super.setText(sFormatedNum);
		}
		else
			super.setText(text);
	}

}
