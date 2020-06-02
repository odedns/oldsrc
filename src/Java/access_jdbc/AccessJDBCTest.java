import java.sql.*;
import javax.naming.*;
import java.util.Hashtable;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class AccessJDBCTest {

  public AccessJDBCTest() {
  }

  public static Connection getPoolConnection() throws Exception
  {

      Context ctx = null;

      // Put connection properties in to a hashtable.
      Hashtable ht = new Hashtable();
      ht.put(Context.INITIAL_CONTEXT_FACTORY,
             "weblogic.jndi.WLInitialContextFactory");
      ht.put(Context.PROVIDER_URL,
             "t3://localhost:7001");

      // Get a context for the JNDI look up
      ctx = new InitialContext(ht);
      javax.sql.DataSource ds
          = (javax.sql.DataSource) ctx.lookup ("jndi.accessPool");
      return(ds.getConnection());

  }


  public static Connection getConnection() throws Exception
  {
    // Try to load the JData2_0 driver

      Driver drv = (Driver) Class.forName("JData2_0.sql.$Driver").newInstance();
      // Change MyDSN, myUsername and myPassword to your specific DSN
      Connection c =java.sql.DriverManager.getConnection(
        "jdbc:JDataConnect://127.0.0.1/mysysdsn:oded:oded");
        return(c);
  }

  public static void main(String argv[])
  {

    try {



        Connection c =getConnection();
        PreparedStatement st = c.prepareStatement("insert into groups values (? , ? , ? , ?)");
        st.setInt(1,111);
        st.setString(2,"new2");
        st.setString(3,"desc");
        st.setDate(4,new Date(System.currentTimeMillis()));
        st.executeUpdate();
        st.close();

        PreparedStatement s = c.prepareStatement("SELECT * FROM GROUPS");
        ResultSet rs = s.executeQuery();
        while (rs.next()) {
              System.out.println("gid = " + rs.getInt("gid"));
              System.out.println("name = " + rs.getString("name"));
              System.out.println("desc = " + rs.getString("description"));
              System.out.println("update_ts = " + rs.getDate("update_ts").toString());
        }


      }  catch (Exception ex) {
         ex.printStackTrace();
      }




  }
}