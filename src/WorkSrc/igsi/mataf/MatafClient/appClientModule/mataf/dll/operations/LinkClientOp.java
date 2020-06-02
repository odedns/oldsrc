package mataf.dll.operations;

import mataf.desktop.dll.ApplicationProxy;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.desktop.NavigationController;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LinkClientOp extends DSEClientOperation {
	private NavigationController nc;

	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {
		System.out.println(ApplicationProxy.getProxy().canRunTrx("001"));
	}
}
