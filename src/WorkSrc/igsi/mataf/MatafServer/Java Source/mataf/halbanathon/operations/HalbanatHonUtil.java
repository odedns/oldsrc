package mataf.halbanathon.operations;

import java.awt.Color;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;
import mataf.general.operations.OperationsUtil;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (06/11/2003 13:49:47).  
 */
public class HalbanatHonUtil 
{
	
	public static Vector getSugPeulaRows(Context ctx) throws DSEException
	{
		String[] fieldsInContext = new String[]{"GLSX_K86P_PARAMS.GL_NOSE_PEULA","GLSX_K86P_PARAMS.HA_SUG_PEULA"};
		String[] fieldsInTable   = new String[]{"HA_PEULA_KOD_NOSE_EN","HA_PEULA_KOD_SUG"};
		return OperationsUtil.getRowsFromTableWithANDCondition(ctx, "HAST_SUG_PEULA",fieldsInContext, fieldsInTable);
	}
	
	public static boolean isExistsInMati(Context ctx) throws Exception
	{
		String accountType = (String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		IndexedCollection iColl = 
					refTables.getByKey("GLST_SCH","GL_SCH",accountType);
		if(iColl.size()>0)
		{
			KeyedCollection kColl = (KeyedCollection)iColl.getElementAt(0);
			String val = (String)kColl.getValueAt("GL_SW_SB_CH");
			if(val.equals("1"))
				return true;
			else
				return false;
		}
		else
		{
			// Should never happend - Indicates a table access error.
			throw new RuntimeException("Error Accessing Table");
		}
	}
	
	public static boolean isExistsInMatach(Context ctx) throws Exception
	{
		String accountType = (String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		RefTables refTables = (RefTables)ctx.getService("refTablesService");
		IndexedCollection iColl = 
					refTables.getByKey("MZST_SCH","GL_SCH",accountType);
		if(iColl.size()>0)
		{
			KeyedCollection kColl = (KeyedCollection)iColl.getElementAt(1);
			String val = (String)kColl.getValueAt("MZST_SCH.MZ_SW_BANKAI");
			if(val.equals("1"))
				return true;
			else
				return false;
		}
		else
		{
			// Should never happend - Indicates a table access error.
			throw new RuntimeException("Error Accessing Table");
		}
	}
	
	public static boolean isExistsInDecisionTable(Context ctx) throws Exception 
	{
		String[] fieldsInContext = new String[]{"GLSX_K86P_PARAMS.GL_NOSE_PEULA","GLSX_K86P_PARAMS.HA_SUG_PEULA"};
		String[] fieldsInTable   = new String[]{"HA_PEULA_KOD_NOSE_EN","HA_PEULA_KOD_SUG"};
		Vector sugPeulaRows = 
			OperationsUtil.getRowsFromTableWithANDCondition(ctx, "HAST_SUG_PEULA",fieldsInContext, fieldsInTable);
		if(sugPeulaRows.size()==0)
		{			
			ctx.setValueAt("HelpData.checkSucceeded","FAIL");
			return false;
		}
		
		StringBuffer SQLCondition = new StringBuffer();
		SQLCondition.append("HA_KOD_NOSE_TBL='");
		SQLCondition.append((String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE"));
		SQLCondition.append("' AND ");
		SQLCondition.append("HA_SUG_PEULA='");
		SQLCondition.append((String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_SUG_PEULA"));
		SQLCondition.append("' AND ");
		SQLCondition.append((String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_SCHUM")+">HA_SCHUM_ME");
		SQLCondition.append(" AND ");
		SQLCondition.append((String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_SCHUM")+"<HA_SCHUM_AD");
		
		SQLCondition.append(getSugLakoachSQLCondition(ctx));
		
		// Ensure we get the conditions to perform in the right order.
		SQLCondition.append(" ORDER BY ");
		SQLCondition.append("HA_INDEX");
		
		JDBCTable service = (JDBCTable)ctx.getService("HAST_TAVLAT_HACHLATA");
		Vector records = service.retrieveRecordsMatching(SQLCondition.toString());

		for(int i=0;i<records.size();i++)
		{
			Hashtable tableRow = (Hashtable)records.elementAt(i);
			String conditionNumber = (String)tableRow.get("HA_PEULA_KOD_TNAY");
			// One of the conditions passed.
			if(executeCondition(ctx, conditionNumber))
			{
				// Update that we have found a record.
				ctx.setValueAt("HelpData.actionNotInTable","0");
				
				// No Value Here
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK", tableRow.get("HA_PRATIM_MALE_CHELK"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM", tableRow.get("HA_SW_IMUT_MALAM"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_KOD_NOSE", tableRow.get("HA_PEULA_KOD_NOSE"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR", tableRow.get("HA_SW_LEFI_HOK_H_HON"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_KOD_MEVAZEA", tableRow.get("HA_PEULA_KOD_MEVAZEA"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA", tableRow.get("HA_DIVUACH_CHOVA"));
				
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA", "1");
				ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT", "1");
				ctx.setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA", tableRow.get("HA_SW_HAZRAMA_CHOVA"));
				
				// Update success flag.
				ctx.setValueAt("HelpData.checkSucceeded","SUCC");
				
				// Store record in server's context.
				ctx.setValueAt("HelpData.recordInDecisionTable",tableRow);
				
				return true;
			}
		}
		
		// None of the conditions passed.
		ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SUBYECTIVY_OBYECT", "2");
		
		if(((String)ctx.getValueAt("GLSX_K86P_PARAMS.HA_TLR_MCHZ_P_KVUIM")).equals("0"))
		{
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA","0");
			ctx.setValueAt("GLSX_K86P_RESULT.HA_SW_HAZRAMA_CHOVA","1");
			
			// Update success flag.
			ctx.setValueAt("HelpData.checkSucceeded","FAIL");
			
			return false;
		}
		else // getStringAt("HA_TLR_MCHZ_P_KVUIM").equals("1")
		{
			Hashtable sugPeulaRow = (Hashtable) sugPeulaRows.elementAt(0);
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM", sugPeulaRow.get("HA_SW_IMUT_MALAM"));
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET", sugPeulaRow.get("HA_PEULA_MEMUKENET"));
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_CASPIT", sugPeulaRow.get("HA_PEULA_CASPIT"));
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_PRATIM_MALE_CHELK", sugPeulaRow.get("HA_PRATIM_MALE_CHELK"));
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA","1");
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_KOD_MAKOR","1");
			ctx.setValueAt("HASX_PIRTEY_CHESHBON.HA_DIVUACH_CHOVA","0");
			
			// Update success flag.
			ctx.setValueAt("HelpData.checkSucceeded","SUCC");
			
			return false;
		}
	}
	
	private static String getSugLakoachSQLCondition(Context ctx) 
											throws DSEObjectNotFoundException
	{
		// Get selection from screen's context.
		String sugLakoach = (String)ctx.getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
	
		if(sugLakoach.equals("9"))
			return " AND HA_PEULA_KOD_MEVAZEA IN (1,2,3,4,5,9)";
		if(sugLakoach.equals("1"))
			return " AND HA_PEULA_KOD_MEVAZEA IN (1,3,4,5,9)";
		if(sugLakoach.equals("2"))
			return " AND HA_PEULA_KOD_MEVAZEA IN (2,9)";
		
		return "";
	}
	
	/** 
	 * Execute the condition according to the conditionNumber sent as parameter
	 * and return true if the condition is valid, otherwise return false.
	 */
	private static boolean executeCondition(Context ctx, String conditionNumber) throws Exception {
		String tnayOpName = "halbanatHonTnay" + conditionNumber + "ServerOp";
		HalbanatHonAbstractTnayServerOp tnayOp = 
				(HalbanatHonAbstractTnayServerOp) DSEOperation.readObject(tnayOpName);
		tnayOp.chainTo(ctx);
		tnayOp.execute();
		tnayOp.unchain();
		return tnayOp.isConditionValid();
	}
	
	/**
	 * H18
	 */
	public static void handleOppositeAccount(Context ctx)
											throws DSEException
	{
		Hashtable record = getDecisionTableRecord(ctx);
		String operatorCode = (String)record.get("HA_PEULA_KOD_MEVAZEA");
		
		if(operatorCode.equals("0"))
			return;
		
		if(operatorCode.equals("5"))
		{
			ctx.setValueAt("HASS_LAKOACH_SUG.HA_SNIF",
							ctx.getValueAt("GLSX_K86P_PARAMS.HA_SNIF"));
			ctx.setValueAt("HASS_LAKOACH_SUG.HA_SCH",
							ctx.getValueAt("GLSX_K86P_PARAMS.HA_SCH"));
			ctx.setValueAt("HASS_LAKOACH_SUG.HA_CH",
							ctx.getValueAt("GLSX_K86P_PARAMS.HA_CH"));
			
		}
		
		String customerType = (String)ctx.getValueAt("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		if(customerType.equals("1"))
		{
			if(operatorCode.equals("5"))
				return;
			if(operatorCode.equals("4"))
			{
				String oppositeAccount = (String)
					ctx.getValueAt("GLSX_K86P_PARAMS.HA_CH_NEG");
				if(oppositeAccount.equals("0"))
					return;
				else
				{
					// READ ? <-- PENDING
					return;
				}
			}
			
			if(operatorCode.equals("3"))
			{
				((VisualDataField)ctx.getElementAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER")).setIsEnabled(Boolean.TRUE);
				((VisualDataField)ctx.getElementAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER")).setShouldRequestFocus(Boolean.TRUE);
				((VisualDataField)ctx.getElementAt("HASS_LAKOACH_SUG.HA_SCH_ACHER")).setIsEnabled(Boolean.TRUE);
				((VisualDataField)ctx.getElementAt("HASS_LAKOACH_SUG.HA_CH_ACHER")).setIsEnabled(Boolean.TRUE);
				((VisualDataField)ctx.getElementAt("HASS_LAKOACH_SUG.NextButton")).setIsEnabled(Boolean.FALSE);
				return;
			}
		}
	}

	/**
	 * Convenient method for getting the record found in the 
	 * decision table.
	 * @return Hashtable - The record in the decision table.
	 */
	public static Hashtable getDecisionTableRecord(Context ctx)
											throws DSEObjectNotFoundException	
	{
		Object val = ctx.getValueAt("HelpData.recordInDecisionTable");
		return (val instanceof Hashtable) ? (Hashtable)val : null;
	}
	
	/**
	 * Returns true if HelpData.checkSucceeded = "SUCC"
	 */
	public static boolean isCheckSucceeded(Context ctx)
											throws DSEObjectNotFoundException
	{
		return ((String)ctx.getValueAt("HelpData.checkSucceeded")).equals("SUCC");
	}
	
	
	/**
	 * Adds an error to the businees messages panel.	 
	 * @param tableId The table from which to get the error ("runtimeMsgs" or "appMsgs")
	 * @param ctx The context of the operation.
	 * @param msgNumber The index of the message in the table
	 */
	public static void addErrorMsg(Context ctx, String tableId, String msgNumber) throws DSEException {
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		MessagesHandlerService msgHandler = (MessagesHandlerService) ctx.getService("msgsHandlerService");
		
		String errorMessage = null;
		try
		{
			errorMessage = msgHandler.getMsgFromTable(tableId, msgNumber, refTables);
		}
		catch(Exception e){e.printStackTrace();}
		
		IndexedCollection errorMessagesList = (IndexedCollection) ctx.getElementAt("BusinessMessagesList");
		VisualDataField vField = null;
		for(int counter=0 ; counter<errorMessagesList.size() ; counter++ ) {
			vField = (VisualDataField) ((KeyedCollection)errorMessagesList.getElementAt(counter)).getElementAt("BusinessMessage");
			if(vField.getValue().toString().trim().length()==0) {
				vField.setValue(errorMessage);
				vField.setForeground((Color)Settings.getValueAt("errorForegroundColor"));
				return;
			}
		}
	}
}
