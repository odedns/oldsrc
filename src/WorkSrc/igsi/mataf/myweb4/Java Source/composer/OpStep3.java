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
public class OpStep3 extends OperationStep {

	/**
	 * Constructor for OpStep3.
	 */
	public OpStep3() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		System.out.println("OpStep3.execute()");
		return 0;
	}

}
