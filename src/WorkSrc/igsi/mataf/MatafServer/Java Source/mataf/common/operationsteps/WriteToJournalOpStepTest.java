package mataf.common.operationsteps;

import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;

import mataf.common.testcases.ServerOperationTestCase;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WriteToJournalOpStepTest extends ServerOperationTestCase {
	private WriteToJournalOpStep step;
	/**
	 * Constructor for WriteToJournalOpStepTest.
	 * @param arg0
	 */
	public WriteToJournalOpStepTest(String arg0) {
		super(arg0);
	}

	public void testPreExecute() {
		try {
			step.preExecute();
			Assert.assertEquals((String) step.getValueAt("GL_BANK"), "12");
			Assert.assertEquals((String) step.getValueAt("GL_SNIF"), "100");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testInitJournal() {
		try {
			step.initJournal();
			Assert.assertEquals(step.getJournal().isConnected(),true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * @see mataf.common.testcases.ServerOperationTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		DSEServerOperation serverop = (DSEServerOperation) DSEServerOperation.readObject("journalWithStepsServerOp");

		step = new WriteToJournalOpStep();
		step.setOperation(serverop);
	}

}
