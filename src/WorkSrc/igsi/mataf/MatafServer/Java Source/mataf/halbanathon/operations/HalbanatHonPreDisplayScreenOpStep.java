package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;

import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/11/2003 12:52:26).  
 */
public class HalbanatHonPreDisplayScreenOpStep extends MatafOperationStep 
{
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		String branch = (String)getValueAt("GLSX_K86P_PARAMS.HA_SNIF");
		if(branch.equals("0"))
		{
			setValueAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER",
				getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF"));
		}
		else
		{
			setValueAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER",branch);
		}
		
		Vector vec = HalbanatHonUtil.getSugPeulaRows(getContext());
		if(vec.size()==0)
		{
			HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","GL499");
			setValueAt("HelpData.checkSucceeded","FAIL");
			return RC_OK;
		}
		Hashtable row = (Hashtable)vec.elementAt(0);
		String val = (String)row.get("HA_SW_MATBEA_CHUTZ");
		if(val.equals("0"))
		{
			// Load MATI
			OperationsUtil.loadMatiAccountTypesTableToContext(getContext(), 
											"halbanatHonAccountTypesList",
											"accountTypeData");
		}
		else
		{
			// Load MATACH
			OperationsUtil.loadMatachAccountTypesTableToContext(getContext(), 
											"halbanatHonAccountTypesList",
											"accountTypeData");
		}
		
		// We're acting 'as if' someone selected the default radio button
		// of customer type.
		setValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH","9");
		preOperateCustomerTypeOperation();
		
		return RC_OK;
	}
	
	private void preOperateCustomerTypeOperation() throws Exception
	{
		HalbanatHonSugLakoachServerOp tnayOp = 
				(HalbanatHonSugLakoachServerOp) DSEOperation.readObject("halbanatHonSugLakoachServerOp");
		
		// Set the operation to our context.
		tnayOp.unchain();
		tnayOp.setContext(getContext());
		tnayOp.execute();
	}

}
