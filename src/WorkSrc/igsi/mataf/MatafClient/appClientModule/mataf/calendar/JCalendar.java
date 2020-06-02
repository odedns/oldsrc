package mataf.calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mataf.types.MatafTextField;
import mataf.types.textfields.MatafNumericField;

/**
 *  JCalendar is a bean for entering a date by choosing the year, month and day.
 *
 *@author     Eyal Ben Ze'ev
 *
 */
public class JCalendar extends JDialog implements PropertyChangeListener {
	
	private MatafTextField date;
	
	public JCalendar(MatafTextField date) {
		this(JMonthChooser.RIGHT_SPINNER, date);
	}
	/**
	 *  JCalendar constructor with month spinner parameter.
	 *
	 *@param  monthSpinner  Possible values are JMonthChooser.RIGHT_SPINNER,
	 *      JMonthChooser.LEFT_SPINNER, JMonthChooser.NO_SPINNER
	 */
	public JCalendar(int monthSpinner, MatafTextField date) {
		super(new Frame(), "\u05DC\u05D5\u05D7 \u05E9\u05E0\u05D4", true);
//        this.poalimCalendarButton = poalimCalendarButton;
        this.date = date;
		// needed for setFont() etc.
		dayChooser = null;
		monthChooser = null;
		yearChooser = null;

		locale = Locale.getDefault();
		calendar = Calendar.getInstance();

		getContentPane().setLayout(new BorderLayout());
		JPanel myPanel = new JPanel();
		myPanel.setBackground(Color.WHITE);
		myPanel.setLayout(new GridLayout(1, 3));
		monthChooser = new JMonthChooser(monthSpinner);
		yearChooser = new JYearChooser();
		monthChooser.setYearChooser(yearChooser);
		myPanel.add(monthChooser);
		myPanel.add(yearChooser);
		dayChooser = new JDayChooser();
		dayChooser.setBackground(Color.WHITE);
		dayChooser.addPropertyChangeListener(this);
		monthChooser.setDayChooser(dayChooser);
		monthChooser.addPropertyChangeListener(this);
		yearChooser.setDayChooser(dayChooser);
		yearChooser.addPropertyChangeListener(this);
		getContentPane().add(myPanel, BorderLayout.NORTH);
		getContentPane().add(dayChooser, BorderLayout.CENTER);
		this.setSize(200,200);
		this.setBackground(Color.WHITE);
		initialized = true;
		
	}


	/**
	 *  Sets the calendar attribute of the JCalendar object
	 *
	 *@param  c       The new calendar value
	 *@param  update  The new calendar value
	 */
	private void setCalendar(Calendar c, boolean update) {
		Calendar oldCalendar = calendar;
		calendar = c;
		if (update) {
			// Thanks to Jeff Ulmer for correcting a bug in the sequence :)
			yearChooser.setYear(c.get(Calendar.YEAR));
			monthChooser.setMonth(c.get(Calendar.MONTH));
			dayChooser.setDay(c.get(Calendar.DATE));
		}
		firePropertyChange("calendar", oldCalendar, calendar);
	}


	/**
	 *  Sets the calendar property. This is a bound property.
	 *
	 *@param  c  the new calendar
	 *@see       #getCalendar
	 */
	public void setCalendar(Calendar c) {
		setCalendar(c, true);
	}


	/**
	 *  Returns the calendar property.
	 *
	 *@return    the value of the calendar property.
	 *@see       #setCalendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}


	/**
	 *  Sets the locale property. This is a bound property.
	 *
	 *@param  l  The new locale value
	 *@see       #getLocale
	 */
	public void setLocale(Locale l) {
		if (!initialized) {
			super.setLocale(l);
		} else {
			Locale oldLocale = locale;
			locale = l;
			dayChooser.setLocale(locale);
			monthChooser.setLocale(locale);
			firePropertyChange("locale", oldLocale, locale);
		}
	}


	/**
	 *  Returns the locale.
	 *
	 *@return    the value of the locale property.
	 *@see       #setLocale
	 */
	public Locale getLocale() {
		return locale;
	}


	/**
	 *  Sets the font property.
	 *
	 *@param  font  the new font
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (dayChooser != null) {
			dayChooser.setFont(font);
			monthChooser.setFont(font);
			yearChooser.setFont(font);
		}
	}


	/**
	 *  Sets the foreground color.
	 *
	 *@param  fg  the new foreground
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (dayChooser != null) {
			dayChooser.setForeground(fg);
			monthChooser.setForeground(fg);
			yearChooser.setForeground(fg);
		}
	}


	/**
	 *  Sets the background color.
	 *
	 *@param  bg  the new background
	 */
	public void setBackground(Color bg) {
		super.setBackground(bg);
		if (dayChooser != null) {
			dayChooser.setBackground(bg);
		}
	}


	/**
	 *  JCalendar is a PropertyChangeListener, for its day, month and year chooser.
	 *
	 *@param  evt  Description of the Parameter
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (calendar != null) {
			Calendar c = (Calendar) calendar.clone();
			if (evt.getPropertyName().equals("day")) {
				c.set(Calendar.DAY_OF_MONTH,
						((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("month")) {
				c.set(Calendar.MONTH,
						((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			} else if (evt.getPropertyName().equals("year")) {
				c.set(Calendar.YEAR,
						((Integer) evt.getNewValue()).intValue());
				setCalendar(c, false);
			}
			DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, this.getLocale());
			date.setText(df.format(c.getTime()));
		}
	}


	/**
	 *  Returns "JCalendar".
	 *
	 *@return    The name value
	 */
	public String getName() {
		return "JCalendar";
	}


	/**
	 *  Enable or disable the JCalendar.
	 *
	 *@param  enabled  The new enabled value
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (dayChooser != null) {
			dayChooser.setEnabled(enabled);
			monthChooser.setEnabled(enabled);
			yearChooser.setEnabled(enabled);
		}
	}


	/**
	 *  Gets the dayChooser attribute of the JCalendar object
	 *
	 *@return    The dayChooser value
	 */
	public JDayChooser getDayChooser() {
		return dayChooser;
	}


	/**
	 *  Gets the monthChooser attribute of the JCalendar object
	 *
	 *@return    The monthChooser value
	 */
	public JMonthChooser getMonthChooser() {
		return monthChooser;
	}


	/**
	 *  Gets the yearChooser attribute of the JCalendar object
	 *
	 *@return    The yearChooser value
	 */
	public JYearChooser getYearChooser() {
		return yearChooser;
	}


	/**
	 *  Creates a JFrame with a JCalendar inside and can be used for testing.
	 *
	 *@param  s  The command line arguments
	 */
	public static void main(String[] s) {
		JFrame frame = new JFrame("JCalendar");
		frame.getContentPane().add(new JCalendar(new MatafNumericField()));
		frame.pack();

		frame.setVisible(true);
	}


	/**
	 *  the year chhoser
	 */
	protected JYearChooser yearChooser;

	/**
	 *  the month chooser
	 */
	protected JMonthChooser monthChooser;

	/**
	 *  the day chooser
	 */
	protected JDayChooser dayChooser;

	private Calendar calendar;
	private Locale locale;
	private boolean initialized = false;
}

