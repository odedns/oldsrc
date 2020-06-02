package mataf.slika.operations;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationStep;
import com.ibm.dse.base.Service;
import com.ibm.dse.gui.Settings;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountAvailabilityCheckOpStep extends MatafOperationStep {

	public static final int AVAILABLE_ACCOUNT = 1;
	
	/**
	 * Constructor for AccountAvailabilityCheckOpStep.
	 */
	public AccountAvailabilityCheckOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String accountType = (String) getValueAt((String) getParams().getValueAt(ACCOUNT_TYPE_ATT_NAME));
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("GLST_SCH", "GL_SCH",accountType);
		KeyedCollection kcoll = (KeyedCollection) resultsInIcoll.getElementAt(0);
		boolean isAccountAvailble = Integer.parseInt((String)kcoll.getValueAt("GL_SW_SHIDUR_INQ")) == AVAILABLE_ACCOUNT;
		
		if(isAccountAvailble) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}

}