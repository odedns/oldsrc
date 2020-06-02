package mataf.slika.operations;

import java.awt.Color;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.gui.Settings;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AccountBalanceAmountsSetterOpStep extends MatafOperationStep {

	/**
	 * Constructor for AccountBalanceAmountsSetterOpStep.
	 */
	public AccountBalanceAmountsSetterOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		setItra("AccountBalanceHostReplyData.GL_ITRA_MESHICHA", "AccountBalance.ItraMeshicha");
		setItra("AccountBalanceHostReplyData.GL_ITRA_ADKANIT", "AccountBalance.ItraAdkanit");
		setItra("AccountBalanceHostReplyData.GL_ITRA_KLALIT", "AccountBalance.ItraKlalit");
		
		return RC_OK;
	}
	
	private void setItra(String itraFieldName2getFrom, String itraFieldName2set) 
									throws DSEObjectNotFoundException {
		
		String itraStr = ((String) getValueAt(itraFieldName2getFrom)).trim();
		float itra = 0;
		if(itraStr.length()>0)
			itra = Float.parseFloat((String) getValueAt(itraFieldName2getFrom));
			
		VisualDataField vField = (VisualDataField) getElementAt(itraFieldName2set);
		
		if(itra<=0) {
			vField.setForeground((Color) Settings.getValueAt("errorForegroundColor"));
		}
		
		vField.setValue(itra);
	}
	
}
