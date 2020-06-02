package composer;

import com.ibm.dse.base.OperationStep;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpStep2 extends OperationStep {

	/**
	 * Constructor for OpStep2.
	 */
	public OpStep2() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		System.out.println("OpStep2.execute()");
		return 0;
	}

}
