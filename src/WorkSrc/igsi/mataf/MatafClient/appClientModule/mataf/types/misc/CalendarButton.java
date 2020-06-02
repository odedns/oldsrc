package mataf.types.misc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Popup;

import mataf.calendar.JCalendar;
import mataf.types.MatafTextField;

/**
 * @author Eyal Ben Ze'e v
 * 
 */

public class CalendarButton extends JButton implements ActionListener {

//	private MatafTextField date;
	/** Used for pre-loading of the calender's GUI. */
	private JCalendar jc;
	private Popup calendarPopup;
	private MatafTextField dateField;

	public CalendarButton(MatafTextField dateField) {
		super();
		this.dateField = dateField;
		// Get button's image.
		ImageIcon icon = new ImageIcon("JCalendarColor16.gif");
		this.setIcon(icon);
		this.setSize(20, 20);
		this.setPreferredSize(new Dimension(20, 20));
		this.addActionListener(this);
	}

	public void setDate(MatafTextField dateField) {
		this.dateField = dateField;
		// Set default date to current date.
		((javax.swing.text.JTextComponent)dateField).setText(getCurrentDate());

		// Pre-load the calendar for faster pop-up.
		jc = new JCalendar(dateField);

	}

	public MatafTextField getDateField() {
		return dateField;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (jc == null)
			jc = new JCalendar(dateField);

		//		jc.pack();
		jc.setLocation(dateField.getX() + 2, dateField.getY() + 40);
		jc.setVisible(true);
	}

	/** 
	 * Method returns the current date as a String object in the 
	 * format of "dd/MM/yyyy".
	 *
	 */
	private String getCurrentDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(Calendar.getInstance().getTime());
	}
}
