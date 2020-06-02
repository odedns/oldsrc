package mataf.slika.operations;

import java.awt.Color;
import java.util.StringTokenizer;

import mataf.data.VisualDataField;
import mataf.format.VisualFieldFormat;
import mataf.general.operations.*;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
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
public class AccountBalanceCheckOpStep extends MatafOperationStep {

	/**
	 * Constructor for AccountBalanceCheckOpStep.
	 */
	public AccountBalanceCheckOpStep() {
		super();
	}
	
	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// here should notify that this method should b implemented
		// by the class that inherit from this class
		throw new Exception("Should implement the method: 'public int executeOp() throws Exception'"+
							 " 4 the class "+this.getClass().getName());
	}

	/**
	 * Return true find the property2check in the reply from the host
	 */
	protected boolean setAccountProperty(String property2check) throws Exception {
		
		String kodIfyun = (String) getValueAt("AccountBalanceHostReplyData.GKSG_IFYUN");
		IndexedCollection sqlQueryResult = null;
		if(kodIfyun.indexOf(property2check) != -1) {
			sqlQueryResult = getRefTablesService().getByKey("GLST_IFYUN", "GL_IFYUN", property2check);
			if(sqlQueryResult.size()>0) {
				setError();
				return true;
			}
		}
		return false;
	}
	
}
