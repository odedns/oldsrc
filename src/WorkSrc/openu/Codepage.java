import java.sql.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class Codepage {

    public Codepage() {
    }


	public static void main(String argv[])
	{

		Connection conn=null;
        ResultSet rs = null;
        Statement st = null;
	    Properties props = new Properties();
        props.put("user", "sa");
        props.put("password", "enfopus");
        props.put("server", "nachal");
        props.put("db","opus");
		//props.put("weblogic.codeset","cp1255");

    try {
          Driver myDriver = (Driver)Class.forName("weblogic.jdbc.mssqlserver4.Driver").newInstance();
          conn = myDriver.connect("jdbc:weblogic:mssqlserver4", props);

		  DatabaseMetaData meta = conn.getMetaData();
		  System.out.println("meta = " + meta.toString());
          st = conn.createStatement();
          rs = st.executeQuery("select id, zone_name from milon where name='תסיסה'");
		   int obj_id;
		   String s;

          if(rs.next() ) {
		      obj_id = rs.getInt(1);
               s = rs.getString(2);
            System.out.println("object id = " + obj_id);
          System.out.println("zone = " + s);

          } // if


    } catch (Exception ex) {
          ex.printStackTrace();

    } finally {
	  try {
		    conn.close();
            rs.close();
            st.close();

      } catch(SQLException se) {
            se.printStackTrace();
      }
	} //finally

	} // main


}