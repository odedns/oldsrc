import java.sql.*;
import java.util.*;

/**
 * <p>Title: ConvertStaticAttachments</p>
 * <p>Description:
 * This program converts old style Enformia urls into new Enformia style urls.
 * In order to run this program you must use weblogic's SQL-SERVER JDBC driver.
 * This means that both weblogic.jar and license.bea license file must be in the CLASSPATH.
 * </p>
 */
public class ConvertStaticAttachments {


	   Connection m_conn = null;
   static final String orig[] = {"http://telem5.openu.ac.il/tsol/openu_help",
		   				"Images/icon",
						"Images/url.gif",
						"http://telem5.openu.ac.il/tsol/Images/icon", 
		   				"http://telem5.openu.ac.il/tsol/Images" 
						 };

	static final String reps[] = { "/opus/Static/openu_help",
								"/opus/Static/Images/file_types/icon",
								"/opus/Static/Images/url.gif",
								"/opus/Static/Images/file_types/icon",
								"/opus/Static/Images"
						};
								

		/**
		 * close the JDBC connection.
		 */
		 void close()
		 {
			 ConnectionManager.close();
		  }

		/**
		 * Convert single commas to double commas, so that sql can handle
	     * the input string.
	     *
		 */
		 String handleCommas(String s)
  		{
		    char c;
    		StringBuffer sb = new StringBuffer(s.length());
		    for(int i=0; i < s.length(); ++i) {
      			c = s.charAt(i);
		    	if(c == '\'') {
        			sb.append('\'');
				}
	        	sb.append(c);
    		} // for
		    return(sb.toString());
  		}

		  /**
		   * convert the attachments.
		   */
		   void convert()
		   {
			  Connection conn = ConnectionManager.getConnection();
			  Statement st = null;
			  Statement updSt = null;
			  ResultSet rs = null;
			  String data = null;
			  boolean update=false;
				int cnt=0;
				int id=0;
			String sql = "select id, htmldata from Editor where htmldata like '%" + orig[0] + "%' or htmldata like '%" + orig[1] + 
					"%' or htmldata like '%" + orig[2] +
					"%' or htmldata like '%" + orig[3] + 
					"%' or htmldata like '%" + orig[4]+ "%'";

			  try {
				st = conn.createStatement();
				updSt = conn.createStatement();
				rs = st.executeQuery(sql);
				while(rs.next() ) {
				  id = rs.getInt(1);
				  data = rs.getString(2);
				  for(int i=0; i < orig.length; ++i) {
						  data = data.replaceAll(orig[i],reps[i]);
				  }
				  /* 
				   * replace single commas with double commas
				   * for SQL syntax.
				   */
				  data=handleCommas(data);
				  String upd = "update Editor set htmldata='" + data +
						  "' where id=" + id;
				  updSt.executeUpdate(upd);
				  ++cnt;
				  System.out.println("replacing id : " + id);

				} // while
				System.out.println("replaced entries: " + cnt);

			  }  catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("\n------------------------------\n");
				System.out.println("obj_id = " + id);
			  } finally {
			        try {
						System.out.println("closing ...");
						System.out.println("replaced entries: " + cnt);

					    if(rs != null) {
						   rs.close();

						}

						st.close();
						updSt.close();


					  } catch(SQLException se) {
						se.printStackTrace();
					  }
			  } //finally




		   }


		   /* main */
		public static void main(String argv[])
		{
		  ConvertStaticAttachments cva = new ConvertStaticAttachments();
		  System.out.println("ConvertStaticAttachments connected ..");
		  cva.convert();
		  cva.close();
		  System.out.println("done ....");

		}



} // ConvertAttachments
