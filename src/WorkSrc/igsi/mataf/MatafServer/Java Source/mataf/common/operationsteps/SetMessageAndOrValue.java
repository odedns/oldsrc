package mataf.common.operationsteps;

import mataf.general.operations.MatafOperationStep;

import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;
import com.ibm.dse.base.Trace;

/**
 * @author Eyal Ben Ze'ev
 *
 * This opstep objectives are to set an error message into the clients' message panel,
 * to set a value (dataValue) into a dataElement ("dataElementName").
 * The opstep will set the data according to given values in the xml.
 * If no data given for one of the objective ir will not prevent it from accomplishing the other,
 * but if a dataElement is given and not found in the context, the opstep will return error.
 */
public class SetMessageAndOrValue extends MatafOperationStep {
	/**
	 * @see com.ibm.dse.base.CompareAssertion#execute()
	 */
	public int executeOp() throws Exception {
		KeyedCollection kc = getParams();
		String dataElementName = null;
		String dataValue = null;
		try {
			addToErrorListFromXML();
		} catch (Exception e) {
		}
		try {
			dataElementName = (String) kc.getValueAt("dataElementName");
			dataValue = (String) kc.getValueAt("dataValue");
			setValueAt(dataElementName, dataValue);
		} catch (Exception e) {
			if (Trace.doTrace("#CORE", 256, 8))
				Trace.trace("#CORE", 256, 8, Settings.getTID(), "CompareAssertionAndConstants.execute: " + e);
			return RC_ERROR;
		}
		return RC_OK;
	}

}
