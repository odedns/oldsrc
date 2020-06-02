package mataf.slika.operations;

import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RejectedChequeCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String REJECTED_CHEQUE_IND = "9";
	
	/**
	 * Constructor for UnrespectedChequeCheckOpStep.
	 */
	public RejectedChequeCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		setAccountProperty(REJECTED_CHEQUE_IND);
		
		return RC_OK;
	}

}
