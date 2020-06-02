package mataf.halbanathon.operations;

import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (06/11/2003 14:51:45).  
 */
public class H14 extends MatafOperationStep 
{
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		setValueAt("HASS_LAKOACH_SUG.HA_KOD_NOSE_SCREEN",
							getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE"));
		setValueAt("HASS_LAKOACH_SUG.HA_SUG_PEULA_SCREEN",
							getValueAt("GLSX_K86P_PARAMS.HA_SUG_PEULA"));
		
		HalbanatHonUtil.isExistsInDecisionTable(getContext());
		
		if(!(getValueAt("HelpData.checkSucceeded").equals("SUCC")))
			return RC_ERROR;
		else
			return RC_OK;
	}

}
