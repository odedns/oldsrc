package tests;
import java.util.Enumeration; 
import java.sql.*;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

public class StoreTableManagement {
/**
* This class creates a store table that will later on be used by the
applications requesting the store services
*/

public static void main(String args[]) throws java.io.IOException,
DSEObjectNotFoundException 
{
    JDBCStoreSchemaGenerator ssg =null;
    // Setting the toolkit environment if not previously done
    try {
	    //Settings.reset();
    	// Settings.initializeExternalizers(Settings.MEMORY);

	    // 1) Instantiate JDBCStorechemaGenerator...

	    ssg = new JDBCStoreSchemaGenerator();

	    // 2) Connect to Database...

        Class.forName("COM.ibm.db2.jdbc.app.DB2Driver").newInstance();
        ssg.connect("jdbc:db2:WKSLOCAL","odedn","odedn01");
        System.out.println("connected ...");
    } catch (Exception e ) {
		e.printStackTrace();
		
	    return;
    }

    // 3) Create the store table
    String tableDefinition = "ACCOUNT_NUMBER CHAR(14), AMOUNT INTEGER, DESCRIPTION VARCHAR(50), THE_DATE DATE";
    String tableName = "StoreTable";
    try {
        // Generating Database Table...
        ssg.generateTable(tableName, tableDefinition);
        // Close the database connection
        ssg.disconnect();
    } // End try
    catch (Exception e ) {
        e.printStackTrace();

    }
    System.out.println("StoreTableManagement : done ...");
} // main

}
