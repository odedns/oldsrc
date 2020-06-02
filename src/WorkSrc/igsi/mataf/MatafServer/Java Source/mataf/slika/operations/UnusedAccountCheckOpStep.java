package mataf.slika.operations;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UnusedAccountCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String SLEEPY_ACCOUNT_IND = "ø";
	
	/**
	 * Constructor for SleepyAccountCheckOpStep.
	 */
	public UnusedAccountCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String peulotZchutHova = (String) getValueAt("SlikaGeneralData.PEULOT_ZCHUT_HOVA");
		
		if(peulotZchutHova.equals(PEULAT_HOVA)) {
			setAccountProperty(SLEEPY_ACCOUNT_IND);
		}
		
		return RC_OK;
	}

}
