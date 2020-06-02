package mataf.common.operationsteps;

import mataf.common.testcases.ServerOperationTestCase;
import mataf.operations.JournalServerOp;

import com.ibm.dse.base.DSEServerOperation;

import junit.framework.TestCase;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalServerOpTest extends ServerOperationTestCase {

	/**
	 * Constructor for JournalServerOpTest.
	 * @param arg0
	 */
	public JournalServerOpTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		JournalServerOp op = (JournalServerOp) DSEServerOperation.readObject("journalServerOp");
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testExecute() {
	}

}
