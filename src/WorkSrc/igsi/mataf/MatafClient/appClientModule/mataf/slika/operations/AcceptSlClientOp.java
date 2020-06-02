package mataf.slika.operations;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;

import mataf.general.operations.MatafClientOp;
import mataf.services.MessagesHandlerService;
import mataf.services.proxy.ProxyService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AcceptSlClientOp extends MatafClientOp {
		
	public void postExecute() {
		updateCountersInProxy();			
		printReport();
	}
	
	private void updateCountersInProxy() {
		try {
			ProxyService proxyService = (ProxyService) getService("proxyService");
			proxyService.setGlobalValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ", getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ"));
			proxyService.setGlobalValueAt("GLSF_GLBL.GL_MIS_BERUR_CZ", getValueAt("GLSF_GLBL.GL_MIS_BERUR_CZ"));
		} catch(Exception ex) {
			ex.printStackTrace();
			try {
				addToErrorList(MessagesHandlerService.APP_MSG_SERVICE_NAME, "6003");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void printReport() {
		try {
			DSEClientOperation printOperation = (DSEClientOperation) DSEOperation.readObject("printChecksClientOp");
			printOperation.setContext(getContext());
			printOperation.execute();
		} catch(Exception ex) {
			ex.printStackTrace();
			try {
				addToErrorList(MessagesHandlerService.APP_MSG_SERVICE_NAME, "6004");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
