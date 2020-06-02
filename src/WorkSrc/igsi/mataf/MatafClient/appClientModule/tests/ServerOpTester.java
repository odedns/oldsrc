package tests;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Settings;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ServerOpTester {

	public static void main(String[] args) {
		ServerOpTester tester = new ServerOpTester();
		try {
			tester.run();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			System.exit(0);
		}		
	}
	public void run() throws Exception{
		Context.reset();
		Settings.reset("http://127.0.0.1:80/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(Settings.MEMORY);				
		
		DSEServerOperation oper = (DSEServerOperation) DSEServerOperation.readObject("JDBCTableServerOp");
		oper.execute();
	}	
}
