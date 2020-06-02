package tests;
import com.ibm.dse.base.*;
import com.ibm.dse.services.appltables.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ApptableTest {
	
	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/client/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	public static void main(String[] args) {
		
		try {
			init();
			//ApplicationTables applTab = (ApplicationTables)Service.readObject("appTablesService");
			ApplicationTables applTab = (ApplicationTables)Service.readObject("myapplTables");
			java.util.Vector v2 = applTab.getAllRecords("branches");
			System.out.println("v2 = " + v2.toString());
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
