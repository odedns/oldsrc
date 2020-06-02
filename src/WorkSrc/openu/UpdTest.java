

import java.util.*;
import java.sql.*;
import java.io.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class UpdTest {

  static String getUnicodeString(ResultSet rs, int colIndex) throws SQLException,IOException
  {

		 InputStream r = rs.getUnicodeStream(colIndex);
		 BufferedReader br = new BufferedReader(new InputStreamReader(r));
		 return(br.readLine());
  }

  public static void main(String argv[])
  {
		String hebStr = "opus data convertions מחרוזת בעברית";
    Connection conn=null;
    ResultSet rs = null;
    Statement st = null;
	 Properties props = new Properties();
      props.put("user", "sa");
        props.put("password", "sa");
        props.put("server", "yam");
        props.put("db","opus");
        props.put("weblogic.codeset","cp1255");

    try {
          Driver myDriver = (Driver)Class.forName("weblogic.jdbc.mssqlserver4.Driver").newInstance();
          conn = myDriver.connect("jdbc:weblogic:mssqlserver4", props);



		  int count = 0;
          st = conn.createStatement();
		  rs = st.executeQuery("select html from openu_htmlData where obj_id=20000");
		  String s=null;
		  if(rs.next() ) {
				 s = rs.getString(1); 
				// s = getUnicodeString(rs,1);
				 String sql = "insert into openu_htmlData values (20001,'" +
						  s + "' )" ;
		  		System.out.println(sql);
          		st.executeUpdate(sql);
		 }
		 rs.close();

    } catch (Exception ex) {
          ex.printStackTrace();

    } finally {
	  try {
		    conn.close();
            st.close();

      } catch(SQLException se) {
            se.printStackTrace();
      }
	} //finally

	} // main


}
