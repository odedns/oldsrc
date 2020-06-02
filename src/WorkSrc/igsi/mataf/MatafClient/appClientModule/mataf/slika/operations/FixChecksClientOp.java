package mataf.slika.operations;

import java.io.IOException;

import mataf.data.VisualDataField;
import mataf.general.operations.*;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.OperationRepliedEvent;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (14/10/2003 10:35:34).  
 */
public class FixChecksClientOp extends MatafClientOp {
	
	/**
	 * @see mataf.general.operations.MatafClientOp#executeOp()
	 */
	public void executeOp() throws Exception {
		((VisualDataField) getElementAt("VadeNetunimButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("KlotHamchaotButton")).setIsEnabled(Boolean.FALSE);
	}

}
