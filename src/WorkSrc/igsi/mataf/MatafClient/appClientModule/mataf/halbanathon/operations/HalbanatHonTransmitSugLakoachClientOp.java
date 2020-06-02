package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafClientOp;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (25/11/2003 11:08:20).  
 */
public class HalbanatHonTransmitSugLakoachClientOp extends MatafClientOp 
{
	/**
	 * @see mataf.general.operations.MatafClientOp#execute()
	 */
	public void execute() throws Exception 
	{
		// Inidicates that the transimition command invoked from 'sug lakoach' screen.
		setValueAt("HelpData.SW_SCREEN","0");		
		
		// Check if this is a "resend" situation
		try {
			String HA_DIVUACH_KAYAM = (String) getValueAt("HASR_PRATIM_SHIDUR.HA_DIVUACH_KAYAM");
			VisualDataField buttonField = null;
			if ((HA_DIVUACH_KAYAM != null) && (HA_DIVUACH_KAYAM.equals("1"))) {
				this.setServerOperation("dispatchSugLakoachAgain");
			}
		} catch (DSEObjectNotFoundException e) {
			e.printStackTrace();
		}
		super.execute();
	}
	
	/**
	 * @see mataf.general.operations.MatafClientOp#postExecute()
	 */
	public void postExecute() {
		try {
			String HA_DIVUACH_KAYAM = (String) getValueAt("HASR_PRATIM_SHIDUR.HA_DIVUACH_KAYAM");
			VisualDataField buttonField = null;
			if ((HA_DIVUACH_KAYAM != null) &&(HA_DIVUACH_KAYAM.equals("1"))) {
				buttonField = (VisualDataField)getElementAt("HASS_LAKOACH_SUG.TransmitButton");
				buttonField.setIsEnabled(Boolean.TRUE);
				
				buttonField = (VisualDataField)getElementAt("HASS_LAKOACH_SUG.ExitButton");
				buttonField.setIsEnabled(Boolean.FALSE);
			}
		} catch (DSEObjectNotFoundException e) {
			e.printStackTrace();
		}
	}


}
