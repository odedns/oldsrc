package mataf.halbanathon.operations;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import mataf.data.VisualDataField;
import mataf.validators.IDNumberValidator;
import mataf.validators.MatafValidator;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.services.comms.DSECCException;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/11/2003 10:30:08).  
 */
public class HalbanatHonIDServerOp extends DSEServerOperation 
{
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
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
				return;
			}			
			
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
			return;
		}
		else
		{
			String tzEzer = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_TZ_EZER");
			if(tzEzer.equals("0"))
				searchForOperation();
			else
			{
				if(!tzEzer.equals(screenID))
				{
					
				}
			}
		}
	}
	
	private void searchForOperation() throws DSEException
	{
		Vector rows = HalbanatHonUtil.getSugPeulaRows(getContext());
		if(rows.size()==0)
		{
			HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","GL921");
			return;
		}
		Hashtable peulaRow = (Hashtable)rows.elementAt(0);

		String imutMalam = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		String tavlat = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_TAVLAT_HACHLATA");
		if(tavlat.equals("0"))
		{
			String screenType = (String)peulaRow.get("HA_PRATIM_MALE_CHELK");
		}
		
		String screenCountry = (String)getValueAt("HASS_LAK_PRATIM.HA_COUNTRY");
		if(!screenCountry.equals("212"))
		{
			setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM","0");
			((VisualDataField)getElementAt("HASS_LAK_PRATIM.HASG_TR_HANPAKA")).setIsEnabled(Boolean.FALSE);
		}
		
		String customerType = (String)getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		if( (customerType.equals("2")) && (imutMalam.equals("1")) )
		{
			return;
		}
		else
		{
			if(imutMalam.equals("0"))
			{
				callTransmition();
			}
		}
	}
	
	private void callTransmition() throws DSEException
	{
		String imutMalam = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		if(imutMalam.equals("1"))
		{
			return;
		}
		else
		{
			handleTransmition();
			setValueAt("HASX_PIRTEY_CHESHBON.HA_SEND_RECORD","1");
		}
	}
	
	private void handleTransmition()
	{
		// IMPLEMENT AS AN OPERATION STEP !
	}
}
