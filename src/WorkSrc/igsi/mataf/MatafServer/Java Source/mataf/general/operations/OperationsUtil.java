package mataf.general.operations;

import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Operation;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

import mataf.services.reftables.RefTables;
import mataf.services.reftables.RefTablesService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OperationsUtil {
	
	public static final String PAGI_BANK_ID = "P";
	public static final String BENLEUMI_BANK_ID = "B";

	/**
	 * Constructor for SlikaUtil.
	 */
	public OperationsUtil() {
		super();
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
		
		IndexedCollection resultsInIcoll = refTablesSrv.getByKey("GLST_BANK", "GL_BANK", bankId);
		return (String) ((KeyedCollection) resultsInIcoll.getElementAt(0)).getValueAt("GL_SUG_BANK");
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
	public static void printFormat(Context ctx, FormatElement format) {
		try {
			String formatedCtx = format.format(ctx);
			System.out.println(formatedCtx);
		} catch(DSEInvalidArgumentException ex) {
			ex.printStackTrace();
		} catch(DSEInvalidRequestException ex) {
			ex.printStackTrace();
		} catch(DSEInvalidClassException ex) {
			ex.printStackTrace();
		}
	}
	
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
		if(bankType.equals(OperationsUtil.PAGI_BANK_ID) || bankType.equals(OperationsUtil.BENLEUMI_BANK_ID)) {
			aSearchCondition= " GL_IFYUN_SNIF LIKE '"+bankType+"%'";
		}
		Vector aDataVector = tableService.retrieveRecordsMatching(aSearchCondition.toString());
		IndexedCollection snifList = (IndexedCollection) ctx.getElementAt(ICollDataName);
		snifList.removeAll();
		String[] arrayOfFieldsNames1 = {"GL_SNIF","GL_SHEM_SNIF"};
		OperationsUtil.setTableData(snifList, aDataVector, KCollDataName, arrayOfFieldsNames1);
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
		OperationsUtil.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
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
		OperationsUtil.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
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
		OperationsUtil.setTableData(accountTypesList, accountTypesTable, KCollDataName, arrayOfFieldsNames);
	}
	
}
