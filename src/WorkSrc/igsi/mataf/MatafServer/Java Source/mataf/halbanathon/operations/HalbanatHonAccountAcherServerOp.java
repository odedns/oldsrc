package mataf.halbanathon.operations;

import java.util.Hashtable;
import java.util.Map;

import mataf.data.VisualDataField;
import mataf.services.reftables.RefTables;
import mataf.validators.AccountnumberValidator;
import mataf.validators.MatafValidator;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/11/2003 16:55:00).  
 */
public class HalbanatHonAccountAcherServerOp extends DSEServerOperation 
{
	public void execute() throws Exception 
	{
		String screenAccountAcher = (String)getValueAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
		if(screenAccountAcher.equals("0"))
		{
			HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs", "TL015");
			((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.HA_CH_ACHER")).setShouldRequestFocus(Boolean.TRUE);
		}
		
		// Do MOD11 check.
		RefTables refTables = (RefTables) getService("refTablesService");
		Map paramsMap = new Hashtable();
		paramsMap.put(AccountnumberValidator.ACCOUNT_NUMBER_PARAM_NAME, screenAccountAcher);
		paramsMap.put(AccountnumberValidator.ACCOUNT_TYPE_PARAM_NAME, (String) getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER"));
		paramsMap.put(AccountnumberValidator.REF_TABLES_SRV_PARAM_NAME, refTables);
		MatafValidator validator = new AccountnumberValidator();
		if(!validator.isValid(paramsMap)) {
			IndexedCollection icoll = refTables.getByKey("GLST_SGIA", "GL_ZIHUY_HODAA", "GL262");
			String msg = (String) ((KeyedCollection) icoll.getElementAt(0)).getValueAt("GL_HODAA");
			VisualDataField vField = (VisualDataField) getElementAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
			vField.setErrorFromServer(msg);
			return;			
		}
		
		
		((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.TransmitButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.NextButton")).setIsEnabled(Boolean.TRUE);
		
		String screenCustomerType = (String)getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		if(screenCustomerType.equals("1"))
		{
			((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.NextButton")).setIsEnabled(Boolean.FALSE);
			((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.TransmitButton")).setShouldRequestFocus(Boolean.TRUE);
		}
		if(screenCustomerType.equals("2"))
		{
			((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.TransmitButton")).setIsEnabled(Boolean.FALSE);
			((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.NextButton")).setShouldRequestFocus(Boolean.TRUE);
		}
	}
}
