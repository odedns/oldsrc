package mataf.halbanathon.operations;

import java.util.Map;

import com.ibm.dse.base.Hashtable;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;
import mataf.validators.IDNumberValidator;
import mataf.validators.MatafValidator;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (27/11/2003 11:17:03).  
 */
public class HalbanatHonCheckIDOpStep extends MatafOperationStep 
{

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		String screenID = (String)getValueAt("HASS_LAK_PRATIM.HA_TZ");
		String screenCountry = (String)getValueAt("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
		
		if(screenCountry.equals("212"))
		{
			MatafValidator IDValidator = new IDNumberValidator();
			Map params = new Hashtable();
			params.put(IDNumberValidator.ID_CARD_NUMBER_PARAM_NAME,screenID);
			if(!IDValidator.isValid(params))
			{
				VisualDataField IDField = (VisualDataField)getElementAt("HASS_LAK_PRATIM.HA_TZ");
				IDField.setErrorFromServer("ספרת בקורת שגויה");
				return RC_ERROR;
			}
		}
		return RC_OK;
	}
}
