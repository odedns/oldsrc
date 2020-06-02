package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.clientserver.CSServerService;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (28/10/2003 14:41:47).  
 */
public class ExitFromHalbanatHonOpStep extends MatafOperationStep 
{	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		setValueAt("GLSX_K86P_RESULT.HA_SW_DIVUACH_BUTZA",
							getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA"));

		setValueAt("trxEnded","true");
		return RC_OK;
	}
}
