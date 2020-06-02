package mataf.halbanathon.operationsteps;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SetIshurMenahel extends MatafOperationStep
{

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception
	{
		((VisualDataField) getElementAt("HASS_LAKOACH_SUG.ManagerButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("HASS_LAKOACH_SUG.RemoteManagerButton")).setIsEnabled(Boolean.TRUE);
		((VisualDataField) getElementAt("HASS_LAKOACH_SUG.TransmitButton")).setIsEnabled(Boolean.FALSE);
		
		if(((String)getValueAt("HelpData.SW_SCREEN"))=="0")
			((VisualDataField) getElementAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH")).setIsEnabled(Boolean.TRUE);
		
		//setValueAt("shouldRequestIshurMenahel", "true");
//		setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
//		setValueAt("trxORData.trxId","X410");
//		setValueAt("trxORData.trxName","дмбръ деп");		
//		setValueAt("trxORData.viewName","mataf.halbanathon.panels.HalbanathonConclusionPanel");
//		setValueAt("trxORData.ctxName","halbanathonCtx");	
		return RC_OK;
	}

}
