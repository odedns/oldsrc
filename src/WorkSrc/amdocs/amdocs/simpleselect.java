import java.sql.*;
import java.util.Properties;


public class simpleselect {
  
  public static void main(String argv[])
       throws Exception
  {
    Properties props = new Properties();
    props.put("user","ginshoouser");
    props.put("password","homer");
    //props.put("server","HOST3.CMAGIC");
    props.put("server","KPNONEW");

    Driver myDriver = (Driver) Class.forName("weblogic.jdbc.oci.Driver").newInstance();
      
    Connection conn = myDriver.connect("jdbc:weblogic:oracle", props);

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
  }
}

