package mataf.slika.operations;


/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MoneyLaunderyCheckOpStep extends AccountBalanceCheckOpStep {

	public static final String MONEY_LAUNDERY_IND = "ì";
	
	/**
	 * Constructor for MoneyLaunderyCheckOpStep.
	 */
	public MoneyLaunderyCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		boolean isIshurMenahelNeeded = setAccountProperty(MONEY_LAUNDERY_IND);
		
		// set Ishur menahel if needed
		if(isIshurMenahelNeeded) {
			setValueAt("shouldRequestIshurMenahel", "true");
		}	
		
		return RC_OK;
	}
	
}
