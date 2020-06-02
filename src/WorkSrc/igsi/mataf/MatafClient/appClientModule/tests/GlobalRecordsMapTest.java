package tests;

import junit.framework.TestCase;
import mataf.services.proxy.GlobalRecordsMap;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Settings;

/**
 * @author Oded Nissan 23/07/2003
 * A Test class for GlobalRecordsMap class.
 */
public class GlobalRecordsMapTest extends TestCase {

	/**
	 * Constructor for GlobalRecordsMapTest.
	 * @param arg0
	 */
	public GlobalRecordsMapTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		try {
			init();		
			junit.textui.TestRunner.run(GlobalRecordsMapTest.class);
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
	
	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}

	/**
	 * test the map and the reverse map.
	 */
	public void testMap() throws Exception {
//		init();
		GlobalRecordsMap map = GlobalRecordsMap.getInstance();
		String name = (String) map.getFieldName(1111);
		assertEquals(name,"GL_ZIHUI_PAKID");
		
		int code = map.getFieldCode("GL_ZIHUI_PAKID");
		assertEquals(code,1111);
	}

	
}
