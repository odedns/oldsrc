package mataf.personalmenu.operationsteps;

import mataf.general.operations.MatafOperationStep;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author yossid
 *
 * This OperationStep add to the context the transactions id's that should be add
 * to the personal menu from the database.
 */
public class InitPersonalMenuOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		JDBCTable personalMenuTableService = (JDBCTable) getService("PERSONAL_MENU");
		String sqlCondition = buildSqlQueryForPersonalMenu();
		Vector queryResults = personalMenuTableService.retrieveRecordsMatching(sqlCondition);
		
		IndexedCollection icoll = (IndexedCollection) getContext().getElementAt("personalMenuData.personalMenuTrxList");
		icoll.removeAll();
		
		for(int counter=0 ; counter<queryResults.size() ; counter++) {
			KeyedCollection currentKcoll = (KeyedCollection) DataElement.readObject("personalMenuTrxRecord");
			currentKcoll.setValueAt("trxId", ((Hashtable) queryResults.get(counter)).get("personalMenuData.selectedTaskName"));
			icoll.addElement(currentKcoll);
		}		
		
		return RC_OK;
	}
	
	/**
	 * Build a sql query to get the transactions id's of the clerk personal menu
	 * 
	 * @param
	 * 		ctx  the context to get from the data to create the sql
	 * @return
	 * 		the sql query to to get the  transaction id's of the clerk personal menu
	 * @throws
	 * 		DSEObjectNotFoundException  if a specific data could'nt be found in the context
	 */
	private String buildSqlQueryForPersonalMenu() throws DSEObjectNotFoundException {
		String puId = (String) getValueAt("GLSG_GLBL.GL_PU_ID");
		String branchId = (String) getValueAt("GLSE_GLBL.GKSE_KEY.GL_SNIF");
		String clerkId = (String) getValueAt("GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID");
		
		StringBuffer condition = new StringBuffer();
		condition.append("PU_ID='").append(puId);
		condition.append("' AND BRANCH_ID='").append(branchId);
		condition.append("' AND CLERK_ID='").append(clerkId).append("'");
		
		return condition.toString();
	}

}
