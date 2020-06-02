package tests;

import com.ibm.dse.automaton.ext.DSEStep;
import com.ibm.dse.base.*;


/**
 * @author administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class TestStep3 extends DSEStep {
	
	
	/**
	 * @see com.ibm.dse.automaton.ext.DSEStep#executeStep()
	 */
	public void executeStep() throws Exception {
		// ***	STEP THREE	***
		System.out.println("***		Excuting Step 3		***");
		FlowCounter.getInstance().promoteCounter();
		System.out.println("Number Of states = "+FlowCounter.getInstance().getTheNumberOfStates());
	}

}