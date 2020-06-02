package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckKodTipulKOpStep extends MatafOperationStep {

	/**
	 * Constructor for CheckKodTipulKOpStep.
	 */
	public CheckKodTipulKOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String kodTipul = (String) getValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
		
		if(kodTipul.equals("K")) {
			return RC_OK;
		} else {
			String fieldName2check = (String) getParams().getValueAt(FIELD_NAME_2_CHECK_PARAM_NAME);
			((VisualDataField) getElementAt(fieldName2check)).setErrorFromServer("");
			return RC_ERROR;
		}
	}

}
