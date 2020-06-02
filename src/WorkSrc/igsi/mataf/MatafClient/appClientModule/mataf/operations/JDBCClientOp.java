package mataf.operations;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JDBCClientOp extends DSEClientOperation {
	
	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {		
		CSClientService csClientService = (CSClientService) getService("CSClient");
		csClientService.sendAndWait(this, 40000000);		
	}
}
