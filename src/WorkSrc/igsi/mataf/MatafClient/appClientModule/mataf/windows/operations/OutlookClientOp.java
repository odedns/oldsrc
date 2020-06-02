package mataf.windows.operations;

import mataf.outlook.NewAppointmentView;

import com.ibm.dse.base.DSEClientOperation;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OutlookClientOp extends DSEClientOperation {
	/**
	 * @see com.ibm.dse.base.DSEClientOperation#execute()
	 */
	public void execute() throws Exception {
		NewAppointmentView aView = new NewAppointmentView();
	}


}
