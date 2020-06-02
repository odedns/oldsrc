package mataf.halbanathon.views;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafClientOp;
import mataf.halbanathon.panels.MevatseaHapeulaComplete;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.OperationRepliedEvent;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/11/2003 18:15:02).
 * revised by (Maestro, aka Chimpy) Eyal Ben Ze'ev
 * Changed:
 * functionality added to support client activities when it get errors from CICS 
 */
public class HalbanatHonDetailsView extends HalbanatHonAbstractView 
{
	private MevatseaHapeulaComplete mainPanel;
	
	public HalbanatHonDetailsView() throws DSEInvalidRequestException 
	{
		setViewName("halbanatHonDetailsView");
		setContextName("halbanatHonDetailsCtx");
		setInstanceContext(true);
		setOperationName("halbanatHonDetailsClientOp");
		setExecuteWhenOpen(true);
		initialize();
	}

	private void initialize() 
	{
		mainPanel = new MevatseaHapeulaComplete();
		setActivePanel(mainPanel);
	}
	
	/**
	 * @see mataf.desktop.views.MatafDSEPanel#handleOperationRepliedEvent(OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent e) {
		super.handleOperationRepliedEvent(e);
		try 
		{
			if (e.getSource() instanceof MatafClientOp)//HalbanatHonTransmitDetailsClientOp) 
			{
				String replyCode = (String) getValueAt("HASR_MIZDAMEN_PRATIM.HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
				
				if(replyCode==null)
					return;
				
				if ((replyCode.equalsIgnoreCase("s")) || 
					(replyCode.equalsIgnoreCase("r")) ||
					(replyCode.equalsIgnoreCase("q")) ||					
					(replyCode.equalsIgnoreCase("n")) ||
					(replyCode.equalsIgnoreCase("e")) ||
					(replyCode.equalsIgnoreCase("d")) ||
					(replyCode.equalsIgnoreCase("k")) ||
					(replyCode.equalsIgnoreCase("h")))
				{
					// Close transmit button.
					VisualDataField transmitButton = 
						(VisualDataField)getContext().getElementAt("HASS_LAK_PRATIM.TransmitButton");
					transmitButton.setIsEnabled(Boolean.FALSE);
					String imutMalam = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
					if(imutMalam.equals("0"))
					{
						// Open the following fields :
						
						// Last Name
						getVisualDataField("GLSX_K86P_RESULT.HA_NAME_LAST").setIsVisible(Boolean.TRUE);
						
						// First Name
						getVisualDataField("GLSX_K86P_RESULT.HA_NAME_FIRST").setIsVisible(Boolean.TRUE);
						
						// Addresses
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_STREET").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_STREET_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_NUM").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_NUM_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_APT").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_APT_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_CITY").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_CITY_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ZIP").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ZIP_LABEL").setIsVisible(Boolean.TRUE);
						
						// Phones
						getVisualDataField("GLSX_K86P_RESULT.HA_PHONE_AREA1").setIsVisible(Boolean.TRUE);
						getVisualDataField("GLSX_K86P_RESULT.HA_PHONE_NUM1").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_AREA2").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2_SEPERATOR_LABEL").setIsVisible(Boolean.TRUE);
						
						// Gender
						getVisualDataField("HASS_LAK_PRATIM.HA_SEX").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_SEX_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_SEX_DESC").setIsVisible(Boolean.TRUE);
						
						// Date of Birth.
						getVisualDataField("HASS_LAK_PRATIM.HASG_TR_LEDA").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HASG_TR_LEDA_LABEL").setIsVisible(Boolean.TRUE);
					}
					else
					{
						// Open the following fields :
						
						// Phones
						getVisualDataField("GLSX_K86P_RESULT.HA_PHONE_AREA1").setIsVisible(Boolean.TRUE);
						getVisualDataField("GLSX_K86P_RESULT.HA_PHONE_NUM1").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_AREA2").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_PHONE_NUM2_SEPERATOR_LABEL").setIsVisible(Boolean.TRUE);
						
						// Addresses
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_STREET").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_STREET_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_NUM").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_NUM_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_APT").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_APT_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_CITY").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_CITY_LABEL").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ZIP").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HA_HOME_ZIP_LABEL").setIsVisible(Boolean.TRUE);

						// Date of Birth.
						getVisualDataField("HASS_LAK_PRATIM.HASG_TR_LEDA").setIsVisible(Boolean.TRUE);
						getVisualDataField("HASS_LAK_PRATIM.HASG_TR_LEDA_LABEL").setIsVisible(Boolean.TRUE);
					}
					
				}				
			}

		} 
		catch (Exception ex) {ex.printStackTrace();}
	}
	
	/** 
	 * @see mataf.halbanathon.views.HalbanatHonAbstractView#afterChildClose(java.lang.Object)
	 */
	public void afterChildClose(Object param)
	{
		try
		{
			boolean managerApproved = Boolean.valueOf((String)getValueAt("trxORData.overrideResult")).booleanValue();
			if(managerApproved)
			{
				setValueAt("HASI_PRATIM_SHIDUR.GKSI_HDR_CONT.GL_ZIHUY_MEASHER",getValueAt("trxORData.mgrUserId"));
				setValueAt("HASI_PRATIM_SHIDUR.GKSI_HDR_CONT1.GL_SUG_ISHUR_MENAEL","1");
				setValueAt("HASI_PRATIM_SHIDUR.GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET","trxORData.samchutMeasheret");
			}
			else
			{
				setValueAt("HASI_PRATIM_SHIDUR.GKSI_HDR_CONT1.GL_SUG_ISHUR_MENAEL","0");
			}
			
		}
		catch(DSEException e)
		{
			e.printStackTrace();
		}
		// Click the transmit button.
		mainPanel.getMatafButton5().doClick();
	}

}
