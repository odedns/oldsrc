package tests;

import mataf.utils.ContextUtils;

import junit.framework.TestCase;
import com.ibm.dse.base.*;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ContextUtilsTest extends TestCase {

	/**
	 * Constructor for ContextUtilsTest.
	 * @param arg0
	 */
	public ContextUtilsTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		try {
			init();			
			junit.textui.TestRunner.run(ContextUtilsTest.class);
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}

	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	public void testGetNamedContext() throws Exception {
		
		Context ctx = (Context) Context.readObject("slikaCtx");
		Context ctx2 = ContextUtils.getNamedContext(ctx,"branchServer");
		assert(ctx2 != null);	
		System.out.println("got ctx2 = " + ctx2.toString());	
		
	}

}
