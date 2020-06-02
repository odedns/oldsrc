package mataf.slika.operations;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.general.operations.*;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.DSEObjectNotFoundException;
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
public class AccountIfyunSnifValidationOpStep extends MatafOperationStep {

	public static final int VALID_IFYUN_SNIF = 1;
	
	/**
	 * Constructor for AccountIfyunSnifValidationOpStep.
	 */
	public AccountIfyunSnifValidationOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String branchId = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		
		IndexedCollection resultsInIcoll = getRefTablesService().getByKey("GLST_SNIF", "GL_SNIF", branchId);
		KeyedCollection kcoll = (KeyedCollection) resultsInIcoll.getElementAt(0);
		int ifyunSnif = Integer.parseInt(((String)kcoll.getValueAt("GL_IFYUN_SNIF")).substring(1,2));
		
		if(ifyunSnif == VALID_IFYUN_SNIF) {
			return RC_OK;
		} else {
			setError();
			return RC_ERROR;
		}
	}

}