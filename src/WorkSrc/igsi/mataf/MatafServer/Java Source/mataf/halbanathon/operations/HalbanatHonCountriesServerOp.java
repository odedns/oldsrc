package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Hashtable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/11/2003 15:11:21).  
 */
public class HalbanatHonCountriesServerOp extends DSEServerOperation
{
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
	{
		String countryCode = (String) getValueAt("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
		if(countryCode.equals("212"))
		{
			setValueAt("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD","תושב הארץ");
		}
		else
		{
			HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs","GL151");
			setValueAt("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD","תושב חוץ");
		}
		
		
		String fullPartialDetails = (String)getValueAt("HelpData.SW_SCREEN");
		if(fullPartialDetails.equals("1"))
		{
			setValueAt("HASS_LAK_PRATIM.HA_TZ","");
			setValueAt("GLSX_K86P_RESULT.HA_PHONE_AREA1","");
			setValueAt("GLSX_K86P_RESULT.HA_PHONE_NUM1","");
			setValueAt("HASS_LAK_PRATIM.HA_PHONE_AREA2","");
			setValueAt("HASS_LAK_PRATIM.HA_PHONE_NUM2","");
			setValueAt("HASS_LAK_PRATIM.HA_HOME_STREET","");
			setValueAt("HASS_LAK_PRATIM.HA_HOME_ST_NUM","");
			setValueAt("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM","");
			setValueAt("HASS_LAK_PRATIM.HA_HOME_ZIP","");
			setValueAt("HASS_LAK_PRATIM.HASG_TR_LEDA","");
			setValueAt("HASS_LAK_PRATIM.HASG_TR_HANPAKA","");
			setValueAt("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM","");
		}
		
		if(fullPartialDetails.equals("2"))
		{
			setValueAt("HASS_LAK_PRATIM.HA_TZ","");
			setValueAt("GLSX_K86P_RESULT.HA_PHONE_AREA1","");
			setValueAt("GLSX_K86P_RESULT.HA_PHONE_NUM1","");
			setValueAt("GLSX_K86P_RESULT.HA_NAME_LAST","");
			setValueAt("GLSX_K86P_RESULT.HA_NAME_FIRST","");
		}
	}
}
