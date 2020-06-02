package mataf.general.operations;

import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.services.jdbc.JDBCTable;


/**
 * @author yossid
 *
 * This operation step adding a record to a table in the database.
 * To use this opStep you have to add to the operation definition 2 parameters:
 * 1.  service name - the JDBCTable service name defined in dsesrvce.xml, that is
 * 					  the table service that will add this record.
 * 2.  format name - the Hashtable format that the table service will use
 * 					 to add the record.
 * 
 * The next example show how this operation is being used to add a record
 * to PERSONAL_MENU table.
 * 
 * DSESRVCE.XML
 * <!-- Services definition sample file -->
 * <JDBCTable 	id="PERSONAL_MENU" autoCommit="true"
 *				autoConnect="true" databaseURL="jdbc:db2:MATAFDB" 
 *				user="mataf" password="mataf"
 *				table="PERSONAL_MENU"
 *				dataSourceName="jdbc/matafdb">
 *			<column id="PU_ID" dataName="GLSG_GLBL.GL_PU_ID" />	
 *			<column id="BRANCH_ID" dataName="GLSE_GLBL.GKSE_KEY.GL_SNIF" />
 *			<column id="CLERK_ID" dataName="GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID" />
 *			<column id="TRX_ID" dataName="personalMenuData.selectedTaskName" />			
 * </JDBCTable>
 * 
 * DSECTXT.XML
 * <!-- Contexts definitions -->
 * <context id="branchServer" parent="nil" type="branch">
 * 		<refService refId="PERSONAL_MENU" alias="PERSONAL_MENU" type="cs"/>
 * </context>
 * <context id="emptyCtxChainedToBranchCtx" parent="branchServer" type="op">
 *		<refKColl refId="emptyData" />
 * </context> 
 * 
 * DSEDATA.XML
 * <!-- Operation data definitions -->
 * <kColl id="GLSG_GLBL">
 * 		<field id="GL_PU_ID" />
 * </kColl>
 * <kColl id="GLSE_GLBL">
 * 		<kColl id="GLSE_GLBL">
 * 			<field id="GL_SNIF" />
 * 			<field id="GL_ZIHUI_PAKID" />
 * 		</kColl>
 * </kColl>
 * <kColl id="personalMenuData">
 * 		<field id="selectedTaskName" />
 * </kColl>
 * 
 * DSEFMTS.XML
 * <!-- Formats definitions --> 
 * <fmtDef id="personalMenuTableFmt">
 *     <hashtable>
 *	       <fObject dataName="GLSG_GLBL.GL_PU_ID"/>
 *	       <fObject dataName="GLSE_GLBL.GKSE_KEY.GL_SNIF"/>
 *	       <fObject dataName="GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID"/> 
 *	       <fObject dataName="personalMenuData.selectedTaskName"/>
 *	   </hashtable> 
 * </fmtDef>
 * 
 * DSEOPER.XML
 * <opStep id="addRecordToTableOpStep"
 * 			implClass="mataf.general.operations.AddRecordToTableOpStep" 
 * 			serviceName="PERSONAL_MENU"
 * 			formatName="personalMenuTableFmt" />
 */
public class AddRecordToTableOpStep extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// getting the params from the opStep definition
		String serviceNameParamValue = (String) getParams().getValueAt(SERVICE_NAME_PARAM_NAME);
		String formatParamValue = (String) getParams().getValueAt(FORMAT_NAME_PARAM_NAME);
		
		// getting the table service to add the record
		JDBCTable tableService = (JDBCTable) getService(serviceNameParamValue);
		
		// getting the context format to add the relevant data to the table
		HashtableFormat hashedTable = (HashtableFormat) FormatElement.readObject(formatParamValue);
		
		// adding the record to the table		
		tableService.addRecord(getContext(), hashedTable);
		
		return RC_OK;
	}

}
