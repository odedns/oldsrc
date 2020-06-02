
import java.sql.*;
import java.util.Properties;

/**
 * This simple example shows how data and result set metadata
 * is selected with JDBC.
 * It assumes the table <tt>empdemo</tt> exists. This table is created
 * in the simplesql example.
 * <p>
 * This example is run on the command line and is output to
 * System.out.
 * <p><h3>Build the Example</h3>  
 * <ol>
 * <li> Open a new command shell. 
 * <p><li>Set up this development shell as described in 
 * <a href=../../examples.html#environment>Setting up Your Environment for 
 * Building and Running the Examples</a>.
 * <p>
 * <ul><li>There are two pathnames required in your environment
 * 1) the directory in which the WebLogic shared library (native interface) resides, and
 * 2) the directory in which the vendor-supplied libraries from Oracle
 * reside (<i>bin</i> for NT or <i>lib</i> for UNIX). 
 * Be sure to include these pathnames in your path.
 * <p>
 * <li> For complete information, see <a
 * href="http://e-docs.bea.com/wls/docs60/oracle/install_jdbc.html#environment">Setting Up the Environment for Using 
 * WebLogic jDriver for Oracle</a>
 * in <i>Installing and Using WebLogic jDriver for Oracle</i></a>. 
 * The following sections contain instructions for specific platforms:
 * <p>
 * <ul>
 * <li><a
 * href="http://e-docs.bea.com/wls/docs60/oracle/install_jdbc.html#nt">Windows NT</a>
 * <li><a
 * href="http://e-docs.bea.com/wls/docs60/oracle/install_jdbc.html#solaris">Solaris</a>
 * <li><a
 * href="http://e-docs.bea.com/wls/docs60/oracle/install_jdbc.html#hp">HP</a>
 * </ul>
 * <p></ul>
 * <li> Change connection parameters to correspond to your Oracle configuration.
 * If you need more help, check the section on connecting
 * to a database in the programming guide, <a
 * href="http://e-docs.bea.com/wls/docs60/oracle/API_joci.html">Using WebLogic jDriver for Oracle</a>.
 * <p>
 * <li>Compile the example by executing the following command or by executing the <a href=../../examples.html#buildScripts>build script</a> 
 * provided for this example in the <font face="Courier New" size = -1>samples/examples/jdbc/oracle</font> 
 * directory. The script will perform the following step:
 * <p> 
 * <ul>
 * <li>Compile the simpleselect class as shown in this example for <b>Windows NT/2000</b>:
 * <p>
 * <pre>  $ <b>javac -d %CLIENT_CLASSES% simpleselect.java</b></pre>
 * </ol>
 * <p><h3>Run the Example</h3>
 * <ul>
 * <li>Execute the following command in your development shell: 
 * 
 * <pre><b>$ java examples.jdbc.oracle.simpleselect</b></pre>
 * 
 * </ul>
 * <h3>There's More</h3>
 *
 * For more information about the WebLogic jDriver for Oracle, see   <a
 * href="http://e-docs.bea.com/wls/docs60/oracle/index.html">
 * <i>Installing and Using WebLogic jDriver for Oracle</i>. 
 * <p>
 * @author Copyright (c) 1996-2000 by BEA Systems, Inc.  All Rights Reserved.
 */

public class simpleselect {
  
  public static void main(String argv[])
       throws Exception
  {
    Properties props = new Properties();
    props.put("user","scott");
    props.put("password","tiger");
    props.put("server","DEMO");

    Driver myDriver = (Driver) Class.forName("COM.cloudscape.core.JDBCDriver").newInstance();
      
    Connection conn = myDriver.connect("jdbc:cloudscape:c:/bea/wlserver/samples/eval/cloudscape/data/demo", props);
    
    Statement stmt = conn.createStatement();
    
        stmt.execute("select * from empdemo");
    
        ResultSet rs = stmt.getResultSet();
    
    //    ResultSet rs = stmt.executeQuery("select * from empdemo");
    while (rs.next()) {
      System.out.println(rs.getString("empid") + " - " + 
                         rs.getString("name")  + " - " + 
                         rs.getString("dept"));
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
  }
}

