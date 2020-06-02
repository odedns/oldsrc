package tests;
import java.util.Enumeration;
import java.sql.*;
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;

public class StoreApplication {
public static void main(String args[]) throws java.io.IOException,
                DSEObjectNotFoundException {
    Context storeContext;
    HashtableFormat storeFormat= null;
    StoreService store = null;

    try { 
    	Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
        storeContext = (Context)Context.readObject("myStoreContext");
        System.out.println(">>> Creating a Store Instance...");
        store = (StoreService)storeContext.getService("store");

        // Connect to the database 

        store.loadDriver();
        store.connect("jdbc:db2:WKSLOCAL",
                "odedn","odedn01");
        if (store.isActive() == false) {
            store.open();
        }
        int nbrOfRecords = 3;
        
        // Filling the Store Table with 3 records...
        for (int i = 1; i <= nbrOfRecords ; i++) {
            storeContext.setValueAt("account_number", "0007000" + i);
            storeContext.setValueAt("amount",new Integer(200000 + i));
            storeContext.setValueAt("date", new java.sql.Date(2003,1,1));
            storeContext.setValueAt("DESCRIPTION", "Adding record " + i + 
            	"in Store instance 1..."); 
            storeFormat = (HashtableFormat) FormatElement.readObject("storeFormatName");

            // Call the format method with argument the operation context
            Hashtable dataTable = new Hashtable();
            dataTable = (Hashtable) 
            storeFormat.format(storeContext);

            // Adding a record to the Store...");
            store.addRecord(dataTable); 
        } // End for 

        // Application committing Database changes...");
        store.commit();

        // Disconnecting JDBCStore instance from Database
        store.disconnect();
        store.close();
    } // End try
    catch (Exception e ) {System.out.println(e.getMessage());
        try {
            store.disconnect(); store.close();
        } catch (Exception ex){System.out.println(ex.getMessage());} 
            return;
        }
    }
}


