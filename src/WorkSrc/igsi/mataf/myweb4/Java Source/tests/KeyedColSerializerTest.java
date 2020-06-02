package tests;

import com.ibm.dse.base.*;
import composer.utils.KeyedColSerializer;

import junit.framework.TestCase;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class KeyedColSerializerTest extends TestCase {

	/**
	 * Constructor for KeyedColSerializerTest.
	 * @param arg0
	 */
	public KeyedColSerializerTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(KeyedColSerializerTest.class);
	}

	
	public void testSerizlization() throws Exception
	{
		KeyedCollection kc = new KeyedCollection();
		kc.setDynamic(true);
		kc.setValueAt("id","100");
		kc.setValueAt("name","foo");
		
		String s = KeyedColSerializer.serialize(kc);
		kc = KeyedColSerializer.deserialize(s);
		System.out.println("kc =" + kc.toString());		
		
	}

}
