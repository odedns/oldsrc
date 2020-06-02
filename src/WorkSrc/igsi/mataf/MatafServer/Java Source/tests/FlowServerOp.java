package tests;

import com.ibm.dse.automaton.DSEProcessor;
import com.ibm.dse.automaton.ext.DSEOperationProcessor;
import com.ibm.dse.base.DSEServerOperation;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FlowServerOp extends DSEServerOperation {
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		DSEOperationProcessor proc = (DSEOperationProcessor) DSEOperationProcessor.readObject("flowProcServerOp");
		proc.setContext(getContext());
		proc.setFormats(getFormats());
		proc.execute();
		
	}


}
