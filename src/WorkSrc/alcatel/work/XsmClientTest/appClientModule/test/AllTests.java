/*
 * Created on 25/12/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test");
		//$JUnit-BEGIN$
		suite.addTestSuite(LightsoftManagerTest.class);
		//$JUnit-END$
		return suite;
	}
	
	public static void main (String[] args) {
		System.out.println("running tests ");
		junit.textui.TestRunner.run (suite());
	}

}
