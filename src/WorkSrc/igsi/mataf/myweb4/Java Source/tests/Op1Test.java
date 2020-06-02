package tests;

import com.ibm.dse.base.*;

import junit.framework.TestCase;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Op1Test extends TestCase {
	DSEOperation op = null;
	Context ctx = null;
	/**
	 * Constructor for Op1Test.
	 * @param arg0
	 */
	public Op1Test(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(Op1Test.class);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		op =  (DSEOperation) DSEOperation.readObject("op1");	
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		op.close();
	}

	public void testExecute() throws Exception {
		op.execute();
	}

}
