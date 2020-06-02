package mataf.general.operations;

import com.ibm.dse.base.DSEException;

import mataf.services.proxy.ProxyService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SaveContextInJournalClientOp extends MatafClientOp {

	public void postExecute() throws Exception {
		updateCountersInProxy();
	}
	
	private void updateCountersInProxy() throws Exception {
		try {
			ProxyService proxyService = (ProxyService) getService("proxyService");
			proxyService.setGlobalValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ", getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_CZ"));
		} catch(Exception ex) {
			addToErrorListFromXML();
			throw ex;
		}	
	}
}
