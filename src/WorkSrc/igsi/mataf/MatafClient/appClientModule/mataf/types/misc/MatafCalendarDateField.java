package mataf.types.misc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mataf.types.MatafTextField;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafCalendarDateField extends MatafTextField {
	private DateFormat dateFormatter;
	private Date date;
	
	public MatafCalendarDateField() {
		dateFormatter = new SimpleDateFormat();		
		date = new Date();
	}
	/**
	 * @see javax.swing.text.JTextComponent#setText(String)
	 */
	public void setText(String t) {
		try {
			date = dateFormatter.parse(t);
			super.setText(t);			
		} catch (Exception e) {
			System.out.println("Incopatible format pattern");
			System.out.println("dateFormatter pattern = " + dateFormatter);
			System.out.println("date pattern = " + t);
			System.out.println("set dateFormatter pattern or enter a valid date");
			//setInError(true);
		}		
	}


	/**
	 * Returns the date.
	 * @return Date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 * @param date The date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	
	
	public static void main(String[] args) {
		MatafCalendarDateField tester = new MatafCalendarDateField();
		DateFormat df = tester.getDateFormatter().getDateInstance(DateFormat.FULL, getDefaultLocale());
		Date d = new Date();
		System.out.println(df.format(d));
//		tester.setText( d.toString());
		tester.setDateFormatter(df);
		tester.setText( df.format(d));
	}

	/**
	 * Returns the dateFormatter.
	 * @return SimpleDateFormat
	 */
	public DateFormat getDateFormatter() {
		return dateFormatter;
	}

	/**
	 * Sets the dateFormatter.
	 * @param dateFormatter The dateFormatter to set
	 */
	public void setDateFormatter(DateFormat dateFormatter) {
		this.dateFormatter = dateFormatter;
	}

}
