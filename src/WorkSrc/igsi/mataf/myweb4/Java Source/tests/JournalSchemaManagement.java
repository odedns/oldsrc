package tests;

import java.util.Enumeration;
import java.sql.*;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;

public class JournalSchemaManagement { 

public static Connection connection = null;
public static JDBCJournalSchemaGenerator jsg = null;

/**
* This method creates the journal tables using the JDBCJournalSchemaGenerator class
*/
public static void main(String args[]) { 

try { 

  // 1) Load the JDBC DB2 driver 
  Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();

  // 2) Instantiate JDBCJournalSchemaGenerator...
  System.out.println(">>> Instantiating JDBCJournalGenerator...");
  jsg = new JDBCJournalSchemaGenerator();
  // 3) Request a database connection
  jsg.connect("jdbc:db2:WKSLOCAL", "odedn", "odedn01");

} catch (Exception e ) {
	e.printStackTrace();
	return;
} 

// 3) Generate all Tables...
//String myJournalSchemaName="myschema";
String myJournalSchemaName="DSESCHEM";
Vector entities = new Vector();
entities.addElement( new String ("UserA"));
entities.addElement( new String ("UserB"));
//jsg.setUserDefaultSchema(true);
jsg.setCreateSchema(false);
jsg.setUserDefaultSchema(true);
String tableDefinition = "BRANCHNUMBER CHAR(4), AGREEMENTNUMBER INTEGER, DUEDATE DATE";
try { 
  System.out.println(">>> Generating Database Schema...");
  jsg.generateSchema(entities, 3, tableDefinition, myJournalSchemaName);
  System.out.println(">>> Disconnecting from the database...");
  jsg.disconnect();
} catch (DSEInvalidArgumentException e) { 
  System.out.println(e.getMessage());
  return;
} catch (DSEInvalidRequestException e) { 
  System.out.println(e.getMessage());
} catch (DSESQLException e) { 
  System.out.println(e.getMessage());
  return;
} catch (DSEInternalErrorException e) { 
  System.out.println(e.getMessage());
  return;
}
}

}