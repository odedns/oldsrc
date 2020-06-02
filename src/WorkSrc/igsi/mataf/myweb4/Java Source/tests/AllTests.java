package tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.extensions.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for tests");
		//$JUnit-BEGIN$
		suite.addTest(new TestSuite(MyTestCase.class));
		//$JUnit-END$
		 TestSetup wrapper = new TestSetup(suite) {

            protected void setUp() {
                System.out.println("in onetime setup");
            }

            protected void tearDown() {
                System.out.println("in onetime teardown ");
            }
        };
		
		return wrapper;
	}
	
	 public static void main(String args[]) {
        junit.textui.TestRunner.run(suite());
    }
}
