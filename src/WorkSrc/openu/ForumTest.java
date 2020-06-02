

import java.util.*;
import java.sql.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ForumTest {


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

    try {
          Driver myDriver = (Driver)Class.forName("weblogic.jdbc.mssqlserver4.Driver").newInstance();
          conn = myDriver.connect("jdbc:weblogic:mssqlserver4", props);



          st = conn.createStatement();
          rs = st.executeQuery("select message from forum where id='384'");
		  String msg = null;

          if(rs.next() ) {
            msg = rs.getString(1);
            System.out.println("msg = " + msg);

          } // while

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
