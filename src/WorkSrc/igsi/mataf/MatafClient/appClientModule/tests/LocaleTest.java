package tests;

import java.util.Locale;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LocaleTest {
	
	public static void main(String[] args) {
		LocaleTest test = new LocaleTest();
		test.run();
	}

	public void run() {
		Locale[] availbleLocales = Locale.getAvailableLocales();
		for (int i = 0; i < availbleLocales.length; i++) {
			System.out.println(availbleLocales[i]);
			System.out.println("Locale.getDefault()=" + Locale.getDefault());
		}
	}

}
