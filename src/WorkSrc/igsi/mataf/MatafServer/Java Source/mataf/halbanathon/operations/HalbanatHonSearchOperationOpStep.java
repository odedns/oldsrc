package mataf.halbanathon.operations;

import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (27/11/2003 11:28:24).  
 */
public class HalbanatHonSearchOperationOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		Vector rows = HalbanatHonUtil.getSugPeulaRows(getContext());
		if(rows.size()==0)
		{
			HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","GL921");
			return RC_ERROR;
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
			return RC_OK;
		}
		else
		{
			if(imutMalam.equals("0"))
			{
				return 2; //callTransmition();
			}
		}
		return 0;
	}

}
