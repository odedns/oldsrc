package mataf.halbanathon.operations;

import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (06/11/2003 15:17:13).  
 */
public class H15 extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		// Get the pre-fetched record.
		Hashtable record = HalbanatHonUtil.getDecisionTableRecord(getContext());
		String lefiChok = (String)record.get("HA_SW_LEFI_HOK_H_HON");
		
		int lefiChokInt = Integer.parseInt(lefiChok);
		String actionNotInTable = getStringAt("HelpData.actionNotInTable");
		
		if( (actionNotInTable.equals("0")) && (lefiChokInt<=6) )
		{
				VisualDataField sibatDivuachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");

				sibatDivuachBean.setIsEnabled(Boolean.FALSE);
		}
		else
		{
			if(actionNotInTable.equals("1"))
			{
				setValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",
						getValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT"));
				return RC_OK;
			}
			
			
			if(!record.get("HA_MIZDAMEN_BILVAD").equals("0"))
			{
				VisualDataField sibatDivuachBean = 
				(VisualDataField)getElementAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");

				sibatDivuachBean.setIsEnabled(Boolean.FALSE);
				
				setValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",
						getValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT"));
			}
		}
		
		return RC_OK;
	}

}
