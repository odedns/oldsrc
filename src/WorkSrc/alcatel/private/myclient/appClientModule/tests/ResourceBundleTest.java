/**
 * Date: 12/06/2007
 * File: ResourceBundleTest.java
 * Package: tests
 */
package tests;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Oded 
 *
 */
public class ResourceBundleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Locale mylocale = new Locale("heb");
		
		ResourceBundle bundle = ResourceBundle.getBundle("messages",mylocale);
		String msg =bundle.getString("foo");
		System.out.println(msg);
		
		
	}

}
