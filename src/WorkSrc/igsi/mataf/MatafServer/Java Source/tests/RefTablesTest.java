package tests;

import junit.framework.TestCase;
import com.ibm.dse.base.*;
import mataf.services.reftables.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RefTablesTest extends TestCase {

	RefTables refTables = null;
	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	/**
	 * Constructor for RefTablesTest.
	 * @param arg0
	 */
	public RefTablesTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		try {
			init();			
			junit.textui.TestRunner.run(RefTablesTest.class);
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		refTables  = (RefTables) Service.readObject("refTables");
		refTables.setLoadAll(false);
		// refTables.addToCache("GLST_SNIF");
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetByKey() throws Exception {
		
		IndexedCollection ic = refTables.getByKey("GLST_SNIF","GL_SNIF",new Integer(1));
		assertTrue(ic != null);		
		System.out.println("testGetBykey");
		for(int i=0; i < ic.size(); ++i) {
			KeyedCollection kc = (KeyedCollection) ic.getElementAt(i);
			System.out.println("kc " + kc.toString());
		}

	}

	public void testGetByKeyEx() throws Exception {
		
		IndexedCollection ic = refTables.getByKeyEx("GLST_SNIF","GL_SNIF",new Integer(1));
		assertTrue(ic != null);		
		System.out.println("testGetBykeyEx");
		for(int i=0; i < ic.size(); ++i) {
			KeyedCollection kc = (KeyedCollection) ic.getElementAt(i);
			System.out.println("kc " + kc.toString());
		}

	}
	
	/*
	public void testGetAll() throws Exception {
		IndexedCollection ic = refTables.getAll("GLST_SNIF");
		assertTrue(ic != null);
		KeyedCollection kc = null;
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);
			System.out.println("kc " + kc.toString());
		}
		
	}
	*/
	
	

}
