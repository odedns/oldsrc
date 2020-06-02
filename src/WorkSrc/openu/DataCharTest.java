
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

public class DataCharTest {


  public static void main(String argv[])
  {
    Connection conn=null;
    ResultSet rs = null;
    Statement st = null;
	 Properties props = new Properties();
      props.put("user", "sa");
        props.put("password", "telem480");
        props.put("server", "nt-dev2");
        props.put("db","new_opus");

    try {
          Driver myDriver = (Driver)Class.forName("weblogic.jdbc.mssqlserver4.Driver").newInstance();
          conn = myDriver.connect("jdbc:weblogic:mssqlserver4", props);



		  int count = 0;
          st = conn.createStatement();
          rs = st.executeQuery("select obj_id from data_text where data like '%ts.exe%'");
		  String s = null;
		  int obj_id;

          while(rs.next() ) {
		    ++count;
            obj_id = rs.getInt(1);
//            s = rs.getString(2);
            System.out.println("object id = " + obj_id);
//            System.out.println("old url = " + s);

          } // while

		  System.out.println("processed rows : " + count);
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