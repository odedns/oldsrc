package tests;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BTTest {

public static void init() throws Exception 
	{

		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		
		
	}
		
	static IndexedCollection getManagersList() throws Exception
	{
		IndexedCollection ic = (IndexedCollection) DataElement.readObject("managersList");
		for(int i=0; i < 5; ++i) {
			KeyedCollection kc = (KeyedCollection) DataElement.readObject("managerData");
			kc.trySetValueAt("managerName","name-" + i);
			kc.trySetValueAt("managerId","id-" + i);					
			ic.addElement(kc);
		}
		return(ic);
		
	}	
	
	public static void main(String[] args) {
		try {
			init();
			Context ctx = (Context) Context.readObject("overrideCtx");
			
			IndexedCollection ic = getManagersList();
			ic.setName("managersList");
			ctx.removeAt("managersList");
			System.out.println("removing ...");
			ctx.addElement(ic);
			ic = (IndexedCollection) ctx.getElementAt("managersList");
			System.out.println("ic = " + ic.toString());
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
