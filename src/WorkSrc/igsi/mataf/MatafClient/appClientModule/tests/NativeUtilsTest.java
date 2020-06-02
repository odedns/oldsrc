package tests;
import mataf.utils.NativeUtils;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NativeUtilsTest {

	public static void main(String[] args) {
		
		System.out.println("beeping ...");
		System.out.println("path = " + (String) System.getProperty("java.library.path"));
		NativeUtils.beep(500,2000);		
	}
}
