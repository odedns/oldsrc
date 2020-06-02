package mataf.common.operationsteps;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.KeyedCollection;

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
public class CompareAssertionAndConstantsTest extends TestCase {
	private CompareAssertionAndConstants compareAssertionAndConstants;
	/**
	 * Constructor for CompareAssertionAndConstantsTest.
	 * @param name
	 */
	public CompareAssertionAndConstantsTest(String name) {
		super(name);
	}

	public static void main(String[] args) {
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		compareAssertionAndConstants = new CompareAssertionAndConstants();
		KeyedCollection paramsKc = new KeyedCollection();
		paramsKc.setDynamic(true);
		paramsKc.setValueAt("dataElementA","testFieldA");
		paramsKc.setValueAt("dataElementB","testFieldB");
//		paramsKc.setValueAt("const","x");
		paramsKc.setValueAt("operator","equal");
		compareAssertionAndConstants.setParams(paramsKc);
		
		Context ctx = new Context();
		KeyedCollection testKC = new KeyedCollection();
		testKC.setDynamic(true);
		testKC.setValueAt("testFieldA", "Y");
		testKC.setValueAt("testFieldB", "y");
		ctx.setKeyedCollection(testKC);		
		
		DSEServerOperation serverOp = new DSEServerOperation();
		serverOp.setContext(ctx);
		
		compareAssertionAndConstants.setOperation(serverOp);
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testExcute() throws Exception{
		Assert.assertEquals(1, compareAssertionAndConstants.execute());
	}

}
