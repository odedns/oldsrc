package tests;

import mataf.services.UuidService;

import junit.framework.TestCase;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UuidTest extends TestCase {

	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	/**
	 * Constructor for UuidTest.
	 * @param arg0
	 */
	public UuidTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		try {
			init();
			junit.textui.TestRunner.run(UuidTest.class);
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
	
	public void testGetUuid() throws Exception
	{
		UuidService uuidSrv = (UuidService) Service.readObject("uuidService");
		int uuid = uuidSrv.getUuid("DEFAULT");
		assertTrue(uuid > 0);
		System.out.println("got uuid = " + uuid);
	}

}
