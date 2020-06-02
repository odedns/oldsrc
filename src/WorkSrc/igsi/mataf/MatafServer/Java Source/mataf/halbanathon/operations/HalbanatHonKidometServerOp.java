package mataf.halbanathon.operations;

import mataf.data.VisualDataField;

import com.ibm.dse.base.DSEServerOperation;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/11/2003 15:27:54).  
 */
public class HalbanatHonKidometServerOp extends DSEServerOperation 
{
	public void execute() throws Exception 
	{
		String screenKidoment = (String)getValueAt("GLSX_K86P_RESULT.HA_PHONE_AREA1");
		if(!screenKidoment.equals("0"))
		{
			return;
		}
		else
		{
			String subOb = (String)getValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT");
			if(subOb.equals("2"))
			{
				return;
			}
			else
			{
				HalbanatHonUtil.addErrorMsg(getContext(), "runtimeMsgs", "OL167");
				((VisualDataField)getElementAt("GLSX_K86P_RESULT.HA_PHONE_NUM1")).setShouldRequestFocus(Boolean.TRUE);
			}
		}		
	}
}
