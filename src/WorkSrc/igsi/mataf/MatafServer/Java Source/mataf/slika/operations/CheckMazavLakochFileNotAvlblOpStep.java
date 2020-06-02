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
public class CheckMazavLakochFileNotAvlblOpStep extends MatafOperationStep {

	public static final int FILE_NOT_EVAILABLE = 3;
	
	/**
	 * Constructor for CheckMazavLakochFileNotAvlblOpStep.
	 */
	public CheckMazavLakochFileNotAvlblOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		int mazavLakoach = Integer.parseInt((String) getValueAt("AccountBalanceHostReplyData.GL_MAZAV_LAKOACH"));
				
		if(mazavLakoach == FILE_NOT_EVAILABLE) {
			setError();
		}
		
		return RC_OK;
	}

}
