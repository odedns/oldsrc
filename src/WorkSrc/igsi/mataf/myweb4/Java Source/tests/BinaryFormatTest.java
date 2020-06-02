package tests;

import com.ibm.dse.base.KeyedCollection;
import composer.utils.BinaryFormat;

import junit.framework.TestCase;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BinaryFormatTest extends TestCase {

	/**
	 * Constructor for BinaryFormatTest.
	 * @param arg0
	 */
	public BinaryFormatTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(BinaryFormatTest.class);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testBinaryFormat() throws Exception
	{
		BinaryFormat bf = new BinaryFormat();
		KeyedCollection kc = new KeyedCollection();
		kc.setDynamic(true);
		kc.setValueAt("id","100");
		kc.setValueAt("name","foo");
		String s = bf.format(kc);
		KeyedCollection kc1 = new KeyedCollection();
		kc1 = (KeyedCollection) bf.unformat(s, kc1);
		System.out.println("kc = " + kc1.toString());
		String id = (String) kc1.getValueAt("id");
		assertTrue(id.equals("100"));
	}
	
	public void testBinaryFormatCtx() throws Exception
	{
		BinaryFormat bf = new BinaryFormat();
		KeyedCollection kc = new KeyedCollection();
		kc.setDynamic(true);
		kc.setValueAt("id","100");
		kc.setValueAt("name","foo");
		String s = bf.format(kc);
		KeyedCollection kc1 = new KeyedCollection();
		kc1 = (KeyedCollection) bf.unformat(s, kc1);
		System.out.println("kc = " + kc1.toString());
		String id = (String) kc1.getValueAt("id");
		assertTrue(id.equals("100"));
	}

}
