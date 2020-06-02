package mataf.halbanathon.operations;

import javax.swing.JTable;

import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (09/11/2003 11:38:17).  
 */
public class H16 extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		
		HalbanatHonUtil.handleOppositeAccount(getContext());
		
		Vector sugPeulaRows = HalbanatHonUtil.getSugPeulaRows(getContext());
		Hashtable sugPeulaRow = (Hashtable)sugPeulaRows.elementAt(0);
		String actionTypeFeedback = (String)sugPeulaRow.get("HA_PEULA_TEUR_SUG");
		
		setValueAt("HASS_LAKOACH_SUG.HA_SUG_PEULA_SCREEN_DESC",	actionTypeFeedback);
		
		String automaticAction = (String)sugPeulaRow.get("HA_PEULA_MEMUKENET");
		Hashtable record = HalbanatHonUtil.getDecisionTableRecord(getContext());
		String occasionalOnly = (String)record.get("HA_MIZDAMEN_BILVAD");
		
		if(automaticAction.equals("3"))
		{
			VisualDataField sugLakoachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
			sugLakoachBean.setIsEnabled(Boolean.FALSE);
		}		
		
		addToErrorListFromXML();
			
		VisualDataField nextButton = 
			(VisualDataField)getElementAt("HASS_LAKOACH_SUG.NextButton");
		
		if((automaticAction.equals("3")) || (occasionalOnly.equals("1")))
		{
			nextButton.setIsEnabled(Boolean.TRUE);
		}
		else
			nextButton.setIsEnabled(Boolean.FALSE);
			
		HalbanatHonUtil.handleOppositeAccount(getContext());
		
		return RC_OK;
	}

}
