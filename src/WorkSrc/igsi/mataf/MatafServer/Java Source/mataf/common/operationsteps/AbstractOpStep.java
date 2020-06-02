package mataf.common.operationsteps;

import com.ibm.dse.base.OperationStep;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class AbstractOpStep extends OperationStep {
	/**
	 * This method will be called automaticaly by the execute() method.
	 * Implement it when you need to do something before the execution of this step.
	 */
	public abstract void preExecute() throws Exception;
	
	/**
	 * This method will be called automaticaly by the execute() method.
	 * Implement it when you need to do something at the end of the execution of this step.
	 */
	public abstract void postExecute() throws Exception;
	
	/**
	 * This method will be called automaticaly by the execute() method
	 * after the calling postExecute
	 * Implement it to perform final termination activities
	 */
	public abstract void terminateTheStep() throws Exception;
}
