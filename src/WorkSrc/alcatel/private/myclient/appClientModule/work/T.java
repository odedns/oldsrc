/*
 * Created on 16/01/2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package work;

import java.util.Date;
import java.util.*;



/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class T {

	
	public static void main(String[] args) {
		int n = 0x01020304;
		short hi = (short) (n >>> 16);
		short low = (short) n;
		System.out.println("hi =" + Integer.toHexString(hi) + "\tlo=" + Integer.toHexString(low));
		int n3 = (low << 16) | hi ;
		System.out.println(Integer.toHexString(n3));
		int n2 = 0x0102;
		System.out.println("n2 =" + (n2 >>> 8));
		int id = Thread.currentThread().hashCode();
		System.out.println("timezone= " + TimeZone.getDefault());		
		System.out.println("timezone= " + TimeZone.getTimeZone("GMT+2"));
		System.out.println("timezone =" + System.getProperty("user.timezone"));
		System.out.println("date = " + new Date());
	}
}
