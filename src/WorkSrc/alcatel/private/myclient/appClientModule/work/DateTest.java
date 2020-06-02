/**
 * Date: 16/05/2007
 * File: DateTest.java
 * Package: work
 */
package work;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author a73552
 *
 */
public class DateTest {

	/**
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String t = "17:30:00";
		
		try {
			Date d = sdf.parse(t);
			System.out.println("date = " + d.toString());
			Date d2 = new Date();
			d2.setHours(d.getHours());
			d2.setMinutes(d.getMinutes());
			d2.setSeconds(d.getSeconds());
			System.out.println("date = " + d2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
