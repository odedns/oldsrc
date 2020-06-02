package mataf.slika.operations;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConfiscatedAccountCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String CONFISCATED_ACCOUNT_IND = "8";
	
	/**
	 * Constructor for ConfiscatedAccountCheckOpStep.
	 */
	public ConfiscatedAccountCheckOpStep() {
		super();
	}

	/**
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		
		setAccountProperty(CONFISCATED_ACCOUNT_IND);
		
		return RC_OK;
	}

}
