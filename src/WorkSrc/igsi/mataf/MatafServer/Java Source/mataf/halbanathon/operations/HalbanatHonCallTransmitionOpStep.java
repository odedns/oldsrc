package mataf.halbanathon.operations;

import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (27/11/2003 11:31:27).  
 */
public class HalbanatHonCallTransmitionOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		String imutMalam = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		if(imutMalam.equals("1"))
		{
			return 2; // Return.
		}
		else
		{
			// Re-ordering allowed ?
			setValueAt("HASX_PIRTEY_CHESHBON.HA_SEND_RECORD","1");
			return RC_OK;
		}		
	}
}
