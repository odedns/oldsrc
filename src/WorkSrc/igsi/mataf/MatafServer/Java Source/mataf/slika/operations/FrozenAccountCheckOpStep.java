package mataf.slika.operations;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FrozenAccountCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String FROZEN_ACCOUNT_IND_1 = "1";
	public static final String FROZEN_ACCOUNT_IND_2 = "2";
	
	/**
	 * Constructor for FrozenAccountCheckOpStep.
	 */
	public FrozenAccountCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		setAccountProperty(FROZEN_ACCOUNT_IND_1);
		setAccountProperty(FROZEN_ACCOUNT_IND_2);
		
		return RC_OK;
	}

}
