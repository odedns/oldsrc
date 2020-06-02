import java.sql.*;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import weblogic.db.jdbc.*;
import weblogic.common.*;


public class wlpool {
  
  public static void main(String argv[])
       throws Exception
  {
	  /*
	Hashtable env = new Hashtable();
	env.put(Context.INITIAL_CONTEXT_FACTORY,
			"weblogic.jndi.WLInitialContextFactory");
	env.put(Context.PROVIDER_URL, "t3://localhost:8050"); 
	env.put(Context.SECURITY_PRINCIPAL, "system");
	env.put(Context.SECURITY_CREDENTIALS, "adminadmin");
	Context ctx = new InitialContext(env);
	weblogic.jdbc.common.JdbcServices jdbc = 
(weblogic.jdbc.common.JdbcServices) ctx.lookup("weblogic.jdbc.JdbcServices");
	weblogic.jdbc.common.Pool pool = null;  
	try {    
		Properties poolProps = new Properties(); 
		poolProps.put("poolName",        "eng2"); 
		poolProps.put("url", "jdbc:weblogic:oracle");
		poolProps.put("driver","weblogic.jdbc.oci.Driver"); 
		poolProps.put("initialCapacity", "1");
		poolProps.put("maxCapacity",     "10");
		poolProps.put("props","user=ginshouser;password=homer;server=HOST3.CMAGIC"); 
		poolProps.put("aclName",         "dynapool"); 
		jdbc.createPool(poolProps);  
	}   catch (Exception e) {      
		System.out.println("Error creating connection pool eng2.");  
	}  finally { 
		ctx.close();   
	} 

	*/
	/******/
	T3Client t3 = new T3Client("t3://localhost:8050");
	t3.connect();
	Properties t3props = new Properties();
	t3props.put("weblogic.t3", t3);
	t3props.put("weblogic.t3.connectionPoolID", "ginshooPool");
	Class.forName("weblogic.jdbc.t3.Driver").newInstance();
	Connection conn=DriverManager.getConnection("jdbc:weblogic:t3",t3props);

    /**************/

    Statement stmt = conn.createStatement();
    
        stmt.execute("select * from user_tables");
    
        ResultSet rs = stmt.getResultSet();
    
    while (rs.next()) {
      System.out.println("table_name : " + rs.getString("table_name"));
      System.out.println("tablespace_name: " + rs.getString("tablespace_name"));
    }
    
    ResultSetMetaData rsmd = rs.getMetaData();
    
    System.out.println("Number of Columns: " + rsmd.getColumnCount());
    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
      System.out.println("Column Name: "          + rsmd.getColumnName(i));
      System.out.println("Nullable: "             + rsmd.isNullable(i));
      System.out.println("Precision: "            + rsmd.getPrecision(i));
      System.out.println("Scale: "                + rsmd.getScale(i));
      System.out.println("Size: "                 + rsmd.getColumnDisplaySize(i));
      System.out.println("Column Type: "          + rsmd.getColumnType(i));
      System.out.println("Column Type Name: "     + rsmd.getColumnTypeName(i));
      System.out.println("");
    }
    
    stmt.close();
    conn.close();
	t3.disconnect();
  }
}

