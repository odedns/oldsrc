package tests;

import mataf.services.proxy.ProxyService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class GlobalFieldsRTSetterTest {

	/**
	 * Constructor for GlobalFieldsRTSetterTest.
	 */
	public GlobalFieldsRTSetterTest() {
		super();
	}

	public static void main(String[] args) {
		try {
			Context.reset();
			Settings.reset("http://localhost/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
			
			Context context = (Context) Context.readObject("workstationCtx");
				
			ProxyService proxyService = (ProxyService) context.getService("proxyService"); 
			proxyService.setGlobalValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ", "0004");
			proxyService.setGlobalValueAt("GLSF_GLBL.GL_MIS_BERUR_CZ", "0002");
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
