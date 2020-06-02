package mataf.personalmenu.operationsteps;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RemoveTrxFromPersonalMenuOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String condition = buildSqlCondition();
		
		JDBCTable tableService = (JDBCTable) getService("PERSONAL_MENU");
		
		tableService.deleteRecordsMatching(condition);
		
		return RC_OK;
	}
	
	private String buildSqlCondition() throws DSEObjectNotFoundException {
		String puId = (String) getValueAt("GLSG_GLBL.GL_PU_ID");
		String branchId = (String) getValueAt("GLSE_GLBL.GKSE_KEY.GL_SNIF");
		String clerkId = (String) getValueAt("GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID");
		String taskName = (String) getValueAt("personalMenuData.selectedTaskName");
		
		StringBuffer condition = new StringBuffer();
		condition.append("PU_ID='").append(puId);
		condition.append("' AND BRANCH_ID='").append(branchId);
		condition.append("' AND CLERK_ID='").append(clerkId);
		condition.append("' AND TRX_ID='").append(taskName).append("'");
		
		return condition.toString();
	}

}
