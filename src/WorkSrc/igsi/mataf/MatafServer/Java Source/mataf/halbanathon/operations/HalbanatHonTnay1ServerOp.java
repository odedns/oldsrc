package mataf.halbanathon.operations;

import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (10/11/2003 18:33:21).  
 */
public class HalbanatHonTnay1ServerOp extends HalbanatHonAbstractTnayServerOp 
{
	public void execute() throws Exception 
	{
		String operationBank = (String)getValueAt("GLSX_K86P_PARAMS.HA_BANK");
		String systemBank = (String)getValueAt("GLSG_GLBL.GL_BANK");
		
		if(operationBank.equals(systemBank))
		{
			setConditionValid(true);
			return;
		}
		
		String systemBranchType = (String)getValueAt("GLSG_GLBL.GL_SUG_SNIF");
		JDBCTable service = (JDBCTable)getService("GLST_SNIF");
		String SQLCondition = "HA_SNIF='" + systemBranchType +"'";
		Vector aDataVector = service.retrieveRecordsMatching(SQLCondition.toString());
		Hashtable row = (Hashtable)aDataVector.elementAt(0);
		String branchDescription = (String)row.get("GL_IFYUN_SNIF");
		if(branchDescription.charAt(0)!=systemBranchType.charAt(0))
			setConditionValid(true);
		else
			setConditionValid(false);
	}

}
