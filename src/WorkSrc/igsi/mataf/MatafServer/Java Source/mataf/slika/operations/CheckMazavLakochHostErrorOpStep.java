package mataf.slika.operations;

import mataf.general.operations.*;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckMazavLakochHostErrorOpStep extends MatafOperationStep {

	public static final int HOST_ERROR = 2;
	
	/**
	 * Constructor for CheckMazavLakochHostErrorOpStep.
	 */
	public CheckMazavLakochHostErrorOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		int mazavLakoach = Integer.parseInt((String) getValueAt("AccountBalanceHostReplyData.GL_MAZAV_LAKOACH"));
				
		if(mazavLakoach == HOST_ERROR) {
			setError();
			return RC_ERROR;
		}
		
		return RC_OK;
	}

}
