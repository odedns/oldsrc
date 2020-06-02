package mataf.halbanathon.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.OperationsUtil;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/11/2003 18:30:19).  
 */
public class HalbanatHonDetailsServerOp extends DSEServerOperation 
{
	public void execute() throws Exception 
	{
		((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.HA_SUG_LAKOACH")).setIsEnabled(Boolean.TRUE);
		String screenReportReason = 
							(String)getValueAt("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");
		if(screenReportReason.equals("2"))
		{
			setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM","0");
		}
		
		String screenCustomerType = 
							(String)getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		if(screenCustomerType.equals("1"))
		{
			// TRANSMIT TO HOST OPERATION ! <-- PENDING
			
			return;
		}
		if(screenCustomerType.equals("2"))
		{
			HalbanatHonUtil.isExistsInDecisionTable(getContext());
			String checkSucceeded = (String) getValueAt("HelpData.checkSucceeded");
			if(!checkSucceeded.equals("SUCC"))
			{
				HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs","GL141");
				setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA","0");
				
				// Exiting.
				exit();
			}
			else
			{
				if(screenReportReason.equals("2"))
				{
					setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM","0");
					
					// Make fields mandatory in Full Details Screen <-- PENDING
				}
					
				Hashtable record = 
					(Hashtable)HalbanatHonUtil.getDecisionTableRecord(getContext());
				String messageCode = (String)record.get("HA_KOD_HODAA");
				if(messageCode.equals("1"))
				{
					HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs","GL508");
				}
					
				String fullOrPartial = (String)record.get("HA_PRATIM_MALE_CHELK");
					
				setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_SUG_MASACH", fullOrPartial);
				// Update that we are opening the partial details screen.
				// (The values in SW_SCREEN are the opposite of the values in HA_PRATIM_MALE_CHELK)
				setValueAt("HelpData.SW_SCREEN",fullOrPartial.equals("1") ? "2" : "1");
					
				// Load the states table.
				OperationsUtil.loadCountriesTableToContext(getContext(), 
								"countriesList",
								"countriesData");
					
				if(fullOrPartial.equals("2")) // Display partial details.
				{						
					// Maybe not needed ? could assign the dataName from
					// the previous screen (HASS_SUG_LAKOACH) to the fields
					// in this screen. <-- PENDING 
					//doAssignment(); 
						
					Vector rows = HalbanatHonUtil.getSugPeulaRows(getContext());
					Hashtable actionType = (Hashtable)rows.elementAt(0);
					setValueAt("HASS_LAKOACH_SUG.HA_SUG_PEULA_SCREEN_DESC",
								actionType.get("HA_PEULA_TEUR_SUG"));
					//setValueAt("HASS_LAK_PRATIM.HA_SHEM_LAKOACH",
					//			getValueAt("GLSX_K86P_PARAMS.HA_SHEM_LAKOACH"));
					
					HalbanatHonUtil.isExistsInDecisionTable(getContext());
						
					if(!HalbanatHonUtil.isCheckSucceeded(getContext()))
					{
						HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","GL141");
						setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA","0");
					}
					else
					{
						((VisualDataField) getElementAt(
							"HASS_LAK_PRATIM.HA_TZ")).setShouldRequestFocus(Boolean.TRUE);
					}
				}
				else // DISPLAY FULL DETAILS
				{
					showFullDetailsComponents();
						
					String MLMConfirmation = (String)record.get("HA_SW_IMUT_MALAM");
					if(MLMConfirmation.equals("1"))
					{
						((VisualDataField) getElementAt(
							"HASS_LAK_PRATIM.HASG_TR_LEDA")).setIsEnabled(Boolean.TRUE);
						((VisualDataField) getElementAt(
							"HASS_LAK_PRATIM.HASG_TR_HANPAKA")).setIsEnabled(Boolean.TRUE);
							
						Vector rows = HalbanatHonUtil.getSugPeulaRows(getContext());
						Hashtable actionType = (Hashtable)rows.elementAt(0);
						
						setValueAt("HASS_LAK_PRATIM.HA_SUG_PEULA_SCREEN_DESC",
								actionType.get("HA_PEULA_TEUR_SUG"));
						setValueAt("HASS_LAK_PRATIM.HA_SHEM_LAKOACH",
								getValueAt("GLSX_K86P_PARAMS.HA_SHEM_LAKOACH"));
								
						((VisualDataField) getElementAt(
							"HASS_LAK_PRATIM.TransmitButton")).setIsEnabled(Boolean.FALSE);
						((VisualDataField) getElementAt(
							"HASS_LAK_PRATIM.HA_TZ")).setShouldRequestFocus(Boolean.TRUE);
					}						
				}
			}
		}
	}
	
	/**
	 * Show all the components that are relevant to Full Details screen.
	 */
	private void showFullDetailsComponents() throws DSEObjectNotFoundException
	{
		show("HASS_LAK_PRATIM.HASG_TR_LEDA_LABEL");
		show("HASS_LAK_PRATIM.HASG_TR_LEDA");
		show("HASS_LAK_PRATIM.HASG_TR_HANPAKA_LABEL");
		show("HASS_LAK_PRATIM.HASG_TR_HANPAKA");
		show("HASS_LAK_PRATIM.HA_HOME_STREET_LABEL");
		show("HASS_LAK_PRATIM.HA_HOME_STREET");
		show("HASS_LAK_PRATIM.HA_HOME_ST_NUM_LABEL");
		show("HASS_LAK_PRATIM.HA_HOME_ST_NUM");
		show("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM");
		show("HASS_LAK_PRATIM.HA_HOME_APT_LABEL");
		show("HASS_LAK_PRATIM.HA_HOME_APT");
		show("HASS_LAK_PRATIM.HA_HOME_CITY_LABEL");
		show("HASS_LAK_PRATIM.HA_HOME_CITY");
		show("HASS_LAK_PRATIM.HA_HOME_ZIP_LABEL");
		show("HASS_LAK_PRATIM.HA_HOME_ZIP");
		show("HASS_LAK_PRATIM.HA_PHONE_NUM2_LABEL");
		show("HASS_LAK_PRATIM.HA_PHONE_NUM2");
		show("HASS_LAK_PRATIM.HA_PHONE_NUM2_SEPERATOR_LABEL");
		show("HASS_LAK_PRATIM.HA_PHONE_AREA2");
		show("HASS_LAK_PRATIM.HA_SEX_LABEL");
		show("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM_LABEL");
		show("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM");
		show("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD_LABEL");
		show("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD");
	}
	
	/**
	 * Set the visible property of the specified bean's data name
	 * to true.
	 */
	private void show(String beanDataName) throws DSEObjectNotFoundException
	{
		((VisualDataField) getElementAt(beanDataName)).setIsVisible(Boolean.TRUE);
	}
	
	
	/**
	 * Exit from HalbanatHon.
	 */
	private void exit() throws DSEException
	{
		setValueAt("GLSX_K86P_RESULT.HA_SW_DIVUACH_BUTZA","HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA");
		((VisualDataField) getElementAt(
				"HASS_LAKOACH_SUG.ExitButton")).setIsEnabled(Boolean.FALSE);
	}
	
	/**
	 * Assignment HASS_CHESHBON_CHEL . (Not Needed ?)
	 */
	private void doAssignment() throws DSEException
	{
		setValueAt("HASS_LAK_PRATIM.HA_BANK","GLSX_K86P_PARAMS.HA_BANK");
		setValueAt("HASS_LAK_PRATIM.HA_SNIF","GLSX_K86P_PARAMS.HA_SNIF");
		setValueAt("HASS_LAK_PRATIM.HA_SCH","GLSX_K86P_PARAMS.HA_SCH");
		setValueAt("HASS_LAK_PRATIM.HA_CH","GLSX_K86P_PARAMS.HA_CH");
		setValueAt("HASS_LAK_PRATIM.HA_KOD_NOSE","GLSX_K86P_PARAMS.HA_KOD_NOSE_SCREEN");
		setValueAt("HASS_LAK_PRATIM.HA_SUG_PEULA","GLSX_K86P_PARAMS.HA_SUG_PEULA_SCREEN");
		setValueAt("HASS_LAK_PRATIM.HA_MATBEA","GLSX_K86P_PARAMS.HA_MATBEA");		
	}

}
