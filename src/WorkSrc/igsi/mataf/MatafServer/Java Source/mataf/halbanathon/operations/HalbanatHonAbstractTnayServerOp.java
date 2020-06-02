package mataf.halbanathon.operations;

import java.io.IOException;

import mataf.services.reftables.RefTables;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (28/10/2003 19:26:48).  
 */
public abstract class HalbanatHonAbstractTnayServerOp extends DSEServerOperation {
	
	private boolean conditionValid;
	
	/** Forces the invokationn of setTnayTakin(). */
	private boolean conditionWasSet;

	/**
	 * Constructor for HalbanatHonAbstractTnayServerOp.
	 */
	public HalbanatHonAbstractTnayServerOp() {
		super();
	}

	/**
	 * Constructor for HalbanatHonAbstractTnayServerOp.
	 * @param arg0
	 * @throws IOException
	 */
	public HalbanatHonAbstractTnayServerOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for HalbanatHonAbstractTnayServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public HalbanatHonAbstractTnayServerOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for HalbanatHonAbstractTnayServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public HalbanatHonAbstractTnayServerOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}

	/**
	 * If invoked before setting the property, exception is thrown.
	 * @return boolean
	 */
	public boolean isConditionValid() 
	{
		if(!conditionWasSet)
			throw new RuntimeException(this.getClass().getName() +
					" did not set the condition state. Must explicitly invoke" +
					" setConditionValid(boolean conditionValid) before returning"+
					" from the method !.");
		return conditionValid;
	}

	/**
	 * Sets the tnayTakin.
	 * @param tnayTakin The tnayTakin to set
	 */
	public void setConditionValid(boolean conditionValid) 
	{
		conditionWasSet = true;
		this.conditionValid = conditionValid;
	}
}
