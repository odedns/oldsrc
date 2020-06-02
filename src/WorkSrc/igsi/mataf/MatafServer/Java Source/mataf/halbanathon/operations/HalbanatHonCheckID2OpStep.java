package mataf.halbanathon.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (27/11/2003 11:20:15).  
 */
public class HalbanatHonCheckID2OpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		String screenCountry = (String)getValueAt("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
		String screenID = (String)getValueAt("HASS_LAK_PRATIM.HA_TZ");
		
		if(screenCountry.equals("212"))
		{
			setValueAt("HASX_PIRTEY_CHESHBON.HA_SEND_RECORD","0");
		}
		
		String imutMalam = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		
		if( (!screenCountry.equals("212")) || (imutMalam.equals("0")) )
		{			
		 // NOTHING.
		}
		else
		{
			((VisualDataField)getElementAt("HASS_LAK_PRATIM.HASG_TR_LEDA")).setIsEnabled(Boolean.TRUE);
			((VisualDataField)getElementAt("HASS_LAK_PRATIM.HASG_TR_HANPAKA")).setIsEnabled(Boolean.TRUE);
		}
		
		if(screenID.equals("0"))
		{
			HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs","GL159");
			return RC_ERROR;
		}
		else
		{
			String tzEzer = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_TZ_EZER");
			if(tzEzer.equals("0"))
			{
				return RC_OK;//searchForOperation();
			}
			else
			{
				if(!tzEzer.equals(screenID))
				{
					// Clear fields in the screen ?
					return 2; // Handle Transmition.
				}
				else
				{
					return RC_OK; // searchForOperation();
				}
			}
		}
		
		
	}

}
