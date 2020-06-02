package mataf.services.electronicjournal;

import java.util.Enumeration;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JournalSchemaManagement {
	
	public static final String SQL_SERVER_DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	public static final String SQL_SERVER_URL = "jdbc:microsoft:sqlserver://10.11.103.216:1433";
	
//	4 Emanuel...
//	public static final String SQL_SERVER_URL = "jdbc:microsoft:sqlserver://10.11.103.1:1433;databaseName=matafdb";
	
	public static final String SQL_SERVER_MATAF_DB_USER = "mataf";
	public static final String SQL_SERVER_MATAF_DB_PASS = "mataf";
	
	public static final String DB2_URL = "jdbc:db2:BTF";
	public static final String DB2_MATAF_DB_USER = "db2admin";
	public static final String DB2_MATAF_DB_PASS = "db2admin";
	
	public Connection connection = null;
	public JDBCJournalSchemaGenerator jsg = null;
	
	private void initJournalSchemaGenerator(String driver, String url, String user, String pass) 
						throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
								DSEInternalErrorException, DSEInvalidRequestException, DSESQLException {
									
		// 1) Load the DBMS driver 
		Class.forName(driver).newInstance();

		// 2) Instantiate JDBCJournalSchemaGenerator...
		System.out.println(">>> Instantiating JDBCJournalGenerator...");
		jsg = new JDBCJournalSchemaGenerator();

		// 3) Request a database connection
		jsg.connect(url, user, pass);
		
		// 4) Generate all Tables and schema...

		/* When the DBMS is Oracle or SQLServer 2000, 
		 * always set this attribute to false, 
		 * as the schemas are automatically created
		 * when a new database user is defined and 
		 * no other schemas can be created.*/
		 jsg.setCreateSchema(false);
		 jsg.setUserDefaultSchema(true);
	}
	
	public void generateSchema(String journalTableName, String schemaFileName, String[] propertiesNames) 
								throws FileNotFoundException, IOException, DSEInvalidArgumentException, DSEInvalidRequestException, DSEInternalErrorException, DSESQLException {
									
		Vector entities = new Vector();
		entities.addElement(journalTableName);
		Properties tablesSchema = new Properties();
		
		tablesSchema.load(new FileInputStream(schemaFileName));
		
		StringBuffer tableDefinition = new StringBuffer();
		for( int counter=0 ; counter<propertiesNames.length ; counter++) {
			tableDefinition.append(tablesSchema.getProperty(propertiesNames[counter]));
		}
		
		System.out.println(">>> Generating Database Schema...");
		
		jsg.generateSchema(entities, 3, tableDefinition.toString());
		
		System.out.println(">>> Disconnecting from the database...");
		
		jsg.disconnect();		
	}
	
	
	
	
	/**
	* This method creates the journal tables using the JDBCJournalSchemaGenerator class
	*/
	public static void main(String args[]) {

		try {
			JournalSchemaManagement jsm = new JournalSchemaManagement();
			jsm.initJournalSchemaGenerator(JournalSchemaManagement.SQL_SERVER_DRIVER,
											JournalSchemaManagement.SQL_SERVER_URL,
											JournalSchemaManagement.SQL_SERVER_MATAF_DB_USER,
											JournalSchemaManagement.SQL_SERVER_MATAF_DB_PASS);
			
			String[] propertiesNames = {"GKSJ_HDR_SCHEMA", "CZSJ_MUTAV_SCHEMA", "CZSJ_CHOTEM_SCHEMA", "CONTEXT_SCHEMA"};
			jsm.generateSchema("SLIKA_JOURNAL", "EJ tables def.txt", propertiesNames);			
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
	}
}