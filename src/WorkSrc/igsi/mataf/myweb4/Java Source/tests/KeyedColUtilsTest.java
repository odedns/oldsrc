package tests;
import com.ibm.dse.base.*;
import composer.utils.KeyedColUtils;

import junit.framework.TestCase;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KeyedColUtilsTest extends TestCase {
	KeyedCollection kc = null;
	/**
	 * Constructor for KeyedColUtilsTest.
	 * @param arg0
	 */
	public KeyedColUtilsTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(KeyedColUtilsTest.class);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {		
		super.setUp();
		kc = new KeyedCollection();
		kc.setDynamic(true);
		kc.setValueAt("myint", "10");
		kc.setValueAt("myfloat","1.1");
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {		
		super.tearDown();
		kc.removeAll();
	}

	public void testGetInt() throws Exception{
		int n=-1;
		n = KeyedColUtils.getInt(kc,"myint");
		assertEquals(n,10);
			
	}

	public void testGetFloat() {
		float f=-1;
		try {
			f = KeyedColUtils.getFloat(kc,"myfloat");
		} catch(Exception e) {
			fail("Exception in testGetInt");			
		}		
		assertEquals(f,(float)1.1,0);
	}

	public void testGetDate() {
	}

	public void testGetBoolean() {
	}

}
