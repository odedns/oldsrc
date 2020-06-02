package mataf.halbanathon.operations;

import java.io.BufferedReader;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (28/10/2003 16:33:38).  
 */
public class CheckInDecisionTableOpStep extends MatafOperationStep 
{
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		return HalbanatHonUtil.isExistsInDecisionTable(getContext()) ? 
															RC_OK : RC_ERROR;
	}

}
