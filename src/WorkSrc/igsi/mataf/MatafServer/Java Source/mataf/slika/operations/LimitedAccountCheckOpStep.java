package mataf.slika.operations;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LimitedAccountCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String LIMITED_ACCOUNT_IND = "â";
	
	/**
	 * Constructor for LimitedAccountCheckOpStep.
	 */
	public LimitedAccountCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		setAccountProperty(LIMITED_ACCOUNT_IND);
		
		return RC_OK;
	}

}
