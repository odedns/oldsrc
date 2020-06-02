/*
 * Created on 19/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.querylist.operationsteps;

import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.general.operations.MatafOperationStep;

/**
 * @author ronenk
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AddQueryToQueryList extends MatafOperationStep
{

	/* (non-Javadoc)
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception
	{
		JDBCTable userQueryTable = (JDBCTable) getService("USER_QUERIES");
		Hashtable queryListFormat = (Hashtable) FormatElement.readObject("");
		//userQueryTable.addRecord(getContext(), queryListFormat);
		return RC_OK;
	}

}
