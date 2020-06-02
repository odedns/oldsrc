package tests;

import junit.framework.TestCase;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyTestCase extends TestCase {
	int n = 0;
	/**
	 * Constructor for MyTestCase.
	 * @param arg0
	 */
	public MyTestCase(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("in setup ...");
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("in teardown ...");
	}
	
	
	public void test1()
	{
			System.out.println("in test1 n=" + n);		
			++n;
	}
	
	public void test2()
	{
		System.out.println("in test2 n=" + n);	
		++n;
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(MyTestCase.class);
	}
	
	

}
