package mataf.halbanathon.operations;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Hashtable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (11/11/2003 13:16:50).  
 */
public class HalbanatHonSugLakoachServerOp extends DSEServerOperation
{
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
	{
		((VisualDataField) getElementAt(
			"HASS_LAKOACH_SUG.NextButton")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt(
			"HASS_LAKOACH_SUG.TransmitButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt(
			"HASS_LAKOACH_SUG.HA_SNIF_ACHER")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt(
			"HASS_LAKOACH_SUG.HA_CH_ACHER")).setIsEnabled(Boolean.FALSE);
		((VisualDataField) getElementAt(
			"HASS_LAKOACH_SUG.HA_SCH_ACHER")).setIsEnabled(Boolean.FALSE);
		
		HalbanatHonUtil.isExistsInDecisionTable(getContext());
		
		Hashtable record = HalbanatHonUtil.getDecisionTableRecord(getContext());
		int lefiChok;
		VisualDataField reportReasonBean = 
				(VisualDataField) getElementAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");
		String subOb = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT");
		
		String flag = (String)getValueAt("HelpData.checkSucceeded");
		if(!flag.equals("SUCC"))
		{
			HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs", "GL141");
			setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA","0");
			return;
		}
		else
		{
			setValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",subOb);
			lefiChok = Integer.parseInt(((String)record.get("HA_SW_LEFI_HOK_H_HON")));
			if((lefiChok>6) && (subOb.equals("1")))
			{
				reportReasonBean.setIsEnabled(Boolean.TRUE);
			}
			
			if(lefiChok<=6)
			{
				reportReasonBean.setIsEnabled(Boolean.FALSE);
			}
		
			if(subOb.equals("2"))
			{
				reportReasonBean.setIsEnabled(Boolean.FALSE);
				setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM","0");
			}
		}
		
		String actionNotInTable = (String)getValueAt("HelpData.actionNotInTable");
		
		// Used to simulate the TGL 'goto code flow'.
		boolean skip = false;
		
		if(actionNotInTable.equals("1"))
		{
			skip = true;
		}
		
		if(!skip)
		{
			String dealSubjectCode = (String)getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE_ISKA");
			String mandatoryReport = (String)record.get("HA_DIVUACH_CHOVA");
			if( (!dealSubjectCode.equals("1")) || (lefiChok>6) || (!mandatoryReport.equals("0")) )
			{
				skip = true;
			}
		}
		
		if(!skip)
		{
			setValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH",subOb);
			reportReasonBean.setIsEnabled(Boolean.TRUE);
		}
		
		// Check if account in bank type of customer type.
		String operatorCode = (String)record.get("HA_PEULA_KOD_MEVAZEA");
		if(operatorCode.equals("9"))
		{
			checkCustomerType();
			return;
		}
		else
		{
			String bankai = (String)getValueAt("HASX_PIRTEY_CHESHBON.GL_SW_BANKAI");
			if(bankai.equals("0"))
			{
				HalbanatHonUtil.handleOppositeAccount(getContext());
				checkCustomerType();
				return;
			}
			else
			{
				String customerTypeFromScreen = (String)getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
				if(subOb.equals("1"))
				{
					if( (!customerTypeFromScreen.equals("1")) || (operatorCode.equals("3")) ||
																 (operatorCode.equals("4")) ||
																 (operatorCode.equals("5")) )
					{
						HalbanatHonUtil.handleOppositeAccount(getContext());
					}
					else
					{
						HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs", "GL163");
						
						VisualDataField customerTypeBean = 
							(VisualDataField) getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
						customerTypeBean.setShouldRequestFocus(Boolean.TRUE);
					}
				}
				else
				{
					if( (subOb.equals("2")) && (customerTypeFromScreen.equals("1")) )
					{
						HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs", "GL163");
				
						VisualDataField customerTypeBean = 
							(VisualDataField) getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
						customerTypeBean.setShouldRequestFocus(Boolean.TRUE);
					}
					else
					{
						HalbanatHonUtil.handleOppositeAccount(getContext());
					}
				}
			}
		}
	}
	
	private void checkCustomerType() throws DSEException
	{
		VisualDataField transmitBean = 
			(VisualDataField) getElementAt("HASS_LAKOACH_SUG.TransmitButton");
		transmitBean.setIsEnabled(Boolean.TRUE);
		
		VisualDataField nextBean = 
			(VisualDataField) getElementAt("HASS_LAKOACH_SUG.NextButton");
		nextBean.setIsEnabled(Boolean.TRUE);
		
		String customerTypeFromScreen = (String)getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		if(customerTypeFromScreen.equals("1"))
		{
			nextBean.setIsEnabled(Boolean.FALSE);
		}
		
		if(customerTypeFromScreen.equals("2"))
		{
			transmitBean.setIsEnabled(Boolean.FALSE);
		}
	}
}
