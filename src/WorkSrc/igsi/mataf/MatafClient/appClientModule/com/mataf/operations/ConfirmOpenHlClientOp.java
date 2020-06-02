package com.mataf.operations;

import java.util.Hashtable;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConfirmOpenHlClientOp extends DSEClientOperation {
	public void execute() throws Exception {
		System.out.println("in exe of ConfirmOpenHlClientOp");
		CSClientService csClientService = null;
		csClientService = (CSClientService) getService("CSClient"); //$NON-NLS-1$	
		csClientService.sendAndWait(this, 40000);

/*		String message =
			"ConfirmOpenHl Operation result:\n"
				+ "\n Reply Code: "
				+ getValueAt("returnCode")
				+ "\n HL Number: "
				+ getValueAt("misparHl")
				+ "\n Current Date: "
				+ getValueAt("taarichMatan");
		javax.swing.JOptionPane.showMessageDialog(
			null,
			message,
			"Client Operation",
			javax.swing.JOptionPane.INFORMATION_MESSAGE);*/

		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
	}

}
