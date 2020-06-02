package mataf.utils;

import java.awt.Color;
import java.io.IOException;

import mataf.data.VisualDataField;
import mataf.logger.GLogger;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTables;
import mataf.services.reftables.RefTablesService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.gui.Settings;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafUtilities {
	
	public static final String PAGI_BANK_ID = "P";
	public static final String BENLEUMI_BANK_ID = "B";

	public static final String PEULAT_ZCHUT = "credit";
	public static final String PEULAT_HOVA = "debit";
	
	public static final double DOUBLE_ZERO_VALUE = 0.009;
	
	/**
	 * Returns:
	 * 		true if the action is a credit action.
	 */
	public static boolean isCreditTransaction(Context ctx) throws Exception {
		String transactionType = (String) ctx.getValueAt("taskAttributes.transactionType");
		return transactionType.equalsIgnoreCase(PEULAT_ZCHUT);
	}
	
	/**
	 * Method adds a message to the business messages panel by
	 * inserting the new message to the IndexedCollection.<p>
	 * The message color will be the default color as specified in the
	 * settings.properties file.
	 * 
	 * @param ctx - The context in which the IndexedCollection that we want
	 * 					to update is placed. 
	 * @param errorMessage - The message to display.
	 */
	public static void addBusinessMessage(Context ctx, String errorMessage) throws Exception 
	{
		// Get the error messages IColl from the context.
		IndexedCollection errorMessagesIColl = (IndexedCollection) ctx.getElementAt("BusinessMessagesList");

		// Get an empty template of the KColl
		KeyedCollection errorMessageKColl = (KeyedCollection)KeyedCollection.readObject("BusinessMessageRecord");
		
		// Create a new VisualDataField
		VisualDataField errorMessageField = 
					(VisualDataField)errorMessageKColl.getElementAt("BusinessMessage");
		
		// Insert the data to the VisualDataField
		errorMessageField.setValue(errorMessage);
		errorMessageField.setForeground((Color)Settings.getValueAt("errorForegroundColor"));
		
		// Add the KColl to the ICol
		errorMessagesIColl.addElement(errorMessageKColl);
	}
	
	public static void addBusinessMessageByMsgNo(Context ctx, String msgNumber) throws Exception {
		addBusinessMessageByMsgNo(ctx, null, msgNumber);
	}
	
	public static void addBusinessMessageByMsgNo(Context ctx, String tableId, String msgNumber) throws Exception {
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		MessagesHandlerService msgHandler = (MessagesHandlerService) ctx.getService("msgsHandlerService");
		
		String errorMessage = null;
		if(tableId==null) {
			errorMessage = msgHandler.getMsgFromTable(msgNumber, refTables);
		} else {
			errorMessage = msgHandler.getMsgFromTable(tableId, msgNumber, refTables);
		}
		
		addBusinessMessage(ctx, errorMessage);
	}
	
	/** 
	 * TODO:
	 * Nati - sometimes the class of the object in amountField value is String & sometimes is Double
	 * while we defining it as Double (in LoansList and ChecksList).
	 * You asked me to put it a side for the meantime...
	 * After handlling this problem remove this method and refer the amountField value as String OR as Double.
	 */
	public static boolean isAmountEmpty(DataField amountField) {
		
		double ammount = 0;
		
		Object amountObj = amountField.getValue();
		if((amountObj!=null) && (amountObj.toString().length()!=0)) {
			if(amountObj instanceof String) {
				ammount = Double.parseDouble((String) amountObj);
			} else if(amountObj instanceof Double) {
				ammount = ((Double) amountObj).doubleValue();
			}
		}
		
		return (ammount-0<DOUBLE_ZERO_VALUE);
	}
	
	public static void setErrorMessageInGLogger(Class callingClass,Context ctx, Exception ex, String message) {
		String stationNumber;
		String trxId;
		
		try {
			stationNumber = (String) ctx.getValueAt("GLSF_GLBL.GL_MISPAR_TAHANA");
			trxId = (String) ctx.getValueAt("taskAttributes.trxId");
			
			GLogger.error(stationNumber, trxId, callingClass ,null, message, ex, false);
		} catch(DSEObjectNotFoundException e) { }
	}
	
	public static boolean compareWithTable(RefTables refTables,
											 String tableName,
											 String keyColumnName,
											 String keyValue,
											 String columnName2cmpr,
											 String value2cmpr) throws Exception {
											 	
		IndexedCollection resultsInIcoll = refTables.getByKey(tableName, keyColumnName, keyValue);
		KeyedCollection recordInResult = (KeyedCollection) resultsInIcoll.getElementAt(0);
		return recordInResult.getValueAt(columnName2cmpr).equals(value2cmpr);
	}
	
	/**
	 * Return sug bank from table of banks by the bank id sent as parameter.
	 */
	public static String getSugBank(RefTablesService refTablesSrv, String bankId) throws Exception {
		
		IndexedCollection resultsIcoll = refTablesSrv.getByKey("GLST_BANK", "GL_BANK", bankId);
		if(resultsIcoll.size()>0) {
			return (String) ((KeyedCollection) resultsIcoll.getElementAt(0)).getValueAt("GL_SUG_BANK");
		} else {
			return null;
		}
	}
	
	public static void setTableData(IndexedCollection icoll2set, 
								 	  Vector tableVector,
								 	  String kcollName,
								 	  String[] arrayOfElementsNamesInKcoll) 
								 	  	throws DSEObjectNotFoundException, DSEInvalidArgumentException, IOException {
		
		for( int counter=0 ; counter<tableVector.size() ; counter++ ) {
			Hashtable hashtableFromTable = (Hashtable) tableVector.get(counter);
			KeyedCollection kcoll2add = (KeyedCollection) DataElement.readObject(kcollName);
			for( int arrayCount=0 ; arrayCount<arrayOfElementsNamesInKcoll.length ; arrayCount++ ) {
				String currentFieldName = arrayOfElementsNamesInKcoll[arrayCount]; 
				kcoll2add.setValueAt(currentFieldName, hashtableFromTable.get(currentFieldName));
			}
			icoll2set.addElement(kcoll2add);
		}
	}
	
	public static void setTableData(IndexedCollection icoll2set, 
								 	  IndexedCollection tableIcoll,
								 	  String kcollName,
								 	  String[] arrayOfElementsNamesInKcoll) 
								 	  	throws DSEObjectNotFoundException, DSEInvalidArgumentException, IOException {
		
		for( int counter=0 ; counter<tableIcoll.size() ; counter++ ) {
			KeyedCollection kcollFromTable = (KeyedCollection) tableIcoll.getElementAt(counter);
			KeyedCollection kcoll2add = (KeyedCollection) DataElement.readObject(kcollName);
			for( int arrayCount=0 ; arrayCount<arrayOfElementsNamesInKcoll.length ; arrayCount++ ) {
				String currentFieldName = arrayOfElementsNamesInKcoll[arrayCount]; 
				kcoll2add.setValueAt(currentFieldName, kcollFromTable.getValueAt(currentFieldName));
			}
			icoll2set.addElement(kcoll2add);
		}
	}
	
	// 4 testing only
//	public static void printFormat(Context ctx, FormatElement format) {
//		try {
//			String formatedCtx = format.format(ctx);
//			System.out.println(formatedCtx);
//		} catch(DSEInvalidArgumentException ex) {
//			ex.printStackTrace();
//		} catch(DSEInvalidRequestException ex) {
//			ex.printStackTrace();
//		} catch(DSEInvalidClassException ex) {
//			ex.printStackTrace();
//		}
//	}
	
		/**
	 * Performs the following SQL statement :
	 * SELECT * from 'serviceName' 
	 * WHERE fieldsInContextNames[0] = fieldsInTablesNames[0] conditions[0]
	 * 		 fieldsInContextNames[1] = fieldsInTablesNames[1] conditions[1]
	 * 				:							:					:
	 * 				:							:					:
	 * 		 fieldsInContextNames[n] = fieldsInTablesNames[n].
	 * 
	 * (conditions[n] = "AND","OR")
	 */
	public static Vector getRowsFromTable(Context ctx,
									String serviceName,
									String[] fieldsInContextNames,
									String[] fieldsInTableNames,
									String[] conditions) throws DSEException
	{
		JDBCTable service = (JDBCTable)ctx.getService(serviceName);
		String SQLCondition = "";
		for(int i=0;i<fieldsInContextNames.length;i++)
		{
			if(i>0)
				SQLCondition+=" " + conditions[i] + " ";
			SQLCondition+=fieldsInTableNames[i]+"='" + ctx.getValueAt(fieldsInContextNames[i])+"'";		
		}
		return service.retrieveRecordsMatching(SQLCondition);
	}
	
	/**
	 * Performs the following SQL statement :
	 * SELECT * from 'serviceName' 
	 * WHERE fieldsInContextNames[0] = fieldsInTablesNames[0] AND
	 * 		 fieldsInContextNames[1] = fieldsInTablesNames[1] AND
	 * 				:							:			   :	
	 * 				:							:			   :
	 * 		 fieldsInContextNames[n] = fieldsInTablesNames[n].
	 */
	public static Vector getRowsFromTableWithANDCondition(Context ctx,
													String serviceName,
													String[] fieldsInContextNames,
													String[] fieldsInTableNames) 
																throws DSEException
	{
		String[] conditions = new String[fieldsInContextNames.length];
		for(int i=0;i<conditions.length;i++)
			conditions[i] = "AND";
		return getRowsFromTable(ctx, serviceName, fieldsInContextNames, fieldsInTableNames, conditions);
			
	}
	
	public static void loadBranchTableToContext(Context ctx, 
													String ICollDataName, 
													String KCollDataName) 
																throws Exception
	{
		RefTables refTables = (RefTables) ctx.getService("refTables");
		
		// init snifim table
		String bankNumber = (String) ctx.getValueAt("GLSG_GLBL.GL_BANK");
		IndexedCollection sugBankIcoll = refTables.getByKey("GLST_BANK", "GL_BANK", bankNumber);
		String bankType = new String();
		if(sugBankIcoll.size()>0) {
			bankType = (String) ((KeyedCollection) sugBankIcoll.getElementAt(0)).getValueAt("GL_SUG_BANK");
			ctx.setValueAt("sugBank", bankType);
		}
		
		JDBCTable tableService = (JDBCTable) ctx.getService("snifin");
		String aSearchCondition = "";
		if(bankType.equals(MatafUtilities.PAGI_BANK_ID) || bankType.equals(MatafUtilities.BENLEUMI_BANK_ID)) {
			aSearchCondition= " GL_IFYUN_SNIF LIKE '"+bankType+"%'";
		}
		Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition.toString());
		IndexedCollection snifList = (IndexedCollection) ctx.getElementAt(ICollDataName);
		snifList.removeAll();
		String[] arrayOfFieldsNames1 = {"GL_SNIF","GL_SHEM_SNIF"};
		MatafUtilities.setTableData(snifList, aDataVector, KCollDataName, arrayOfFieldsNames1);
	}
	
	
	public static void loadMatiAccountTypesTableToContext(Context ctx, 
													String ICollDataName, 
													String KCollDataName) 
																throws Exception
	{
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		
		IndexedCollection accountTypesList = (IndexedCollection) ctx.getElementAt(ICollDataName);
		accountTypesList.removeAll();
		IndexedCollection accountTypesTable = refTables.getAll("GLST_SCH");
		String[] arrayOfFieldsNames = {"GL_SCH","GL_SHEM_SCH"};
		MatafUtilities.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
	}
	
	
	public static void loadMatachAccountTypesTableToContext(Context ctx, 
													String ICollDataName, 
													String KCollDataName) 
																throws Exception
	{
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		
		IndexedCollection accountTypesList = (IndexedCollection) ctx.getElementAt(ICollDataName);
		accountTypesList.removeAll();
		IndexedCollection accountTypesTable = refTables.getAll("MZST_SCH");
		String[] arrayOfFieldsNames = {"GL_SCH","GL_SHEM_SCH"};
		MatafUtilities.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
	}
	
	public static void loadCountriesTableToContext(Context ctx, 
													String ICollDataName, 
													String KCollDataName) 
																throws Exception
	{
		RefTables refTables = (RefTables) ctx.getService("refTablesService");
		
		IndexedCollection accountTypesList = (IndexedCollection) ctx.getElementAt(ICollDataName);
		accountTypesList.removeAll();
		IndexedCollection accountTypesTable = refTables.getAll("GLST_MEDINOT");
		String[] arrayOfFieldsNames = {"GL_KOD_MEDINA","GL_SHEM_MEDINA"};
		MatafUtilities.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
	}

}
