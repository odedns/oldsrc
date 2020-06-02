package mataf.general.operations;

import mataf.logger.GLogger;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.FormatElement;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FormatToHostTesterOpStep extends MatafOperationStep {

	/**
	 * Constructor for FormatToHostTesterOpStep.
	 */
	public FormatToHostTesterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		try {
			FormatElement format = (FormatElement) getFormat("hostTesterFmt");
			String formatedMessage = format.format(getContext());
			GLogger.debug("Formated Message to host: ");
			GLogger.debug(formatedMessage);
			return RC_OK;
		} catch(Exception ex) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), ex, null);
			return RC_ERROR;
		}
	}

}
