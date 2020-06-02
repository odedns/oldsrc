package tests;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Tester {
	
	public Tester() {
	}

	public static void main(String[] args) {
		Tester test = new Tester();
		test.run();
	}
	
	public void run() {
		Calendar cal =  new GregorianCalendar(new SimpleTimeZone(2, "Jer"));
		String[] str = TimeZone.getAvailableIDs();
		System.out.println(cal.getTime());
	}
}
