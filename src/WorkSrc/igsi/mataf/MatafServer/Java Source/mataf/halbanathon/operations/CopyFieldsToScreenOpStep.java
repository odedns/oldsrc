package mataf.halbanathon.operations;

import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/11/2003 13:57:08).  
 */
public class CopyFieldsToScreenOpStep extends MatafOperationStep 
{
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		//setValueAt("HASS_LAKOACH_SUG.XXXX", // PENDING !
		//					getValueAt("GLSX_K86P_PARAMS.HA_SHEM_LAKOACH"));
		setValueAt("HASS_LAKOACH_SUG.HA_BANK",
							getValueAt("GLSX_K86P_PARAMS.HA_BANK"));
		setValueAt("HASS_LAKOACH_SUG.HA_SNIF",
							getValueAt("GLSX_K86P_PARAMS.HA_SNIF"));
		setValueAt("HASS_LAKOACH_SUG.HA_SCH",
							getValueAt("GLSX_K86P_PARAMS.HA_SCH"));
		setValueAt("HASS_LAKOACH_SUG.HA_CH",
							getValueAt("GLSX_K86P_PARAMS.HA_CH"));
		setValueAt("HASS_LAKOACH_SUG.HA_MATBEA",
							getValueAt("GLSX_K86P_PARAMS.HA_MATBEA"));
		setValueAt("HASS_LAKOACH_SUG.HA_SCHUM",
							getValueAt("GLSX_K86P_PARAMS.HA_SCHUM"));
		
		String coinType = getStringAt("GLSX_K86P_PARAMS.HA_MATBEA");
		if(!coinType.equals("0"))
		{
			setValueAt("HASS_LAKOACH_SUG.HA_NIS",
							getValueAt("GLSX_K86P_PARAMS.HA_SCHUM_NIS"));
		}
							
		return RC_OK;
	}

}
