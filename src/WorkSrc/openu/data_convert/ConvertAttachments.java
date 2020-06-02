import java.sql.*;
import java.util.*;

/**
 * <p>Title: ConvertUrl</p>
 * <p>Description:
 * This program converts old style Enformia urls into new Enformia style urls.
 * In order to run this program you must use weblogic's SQL-SERVER JDBC driver.
 * This means that both weblogic.jar and license.bea license file must be in the CLASSPATH.
 * </p>

/**
 * utility class to replace strings.
 */
abstract class StringUtils {
		static boolean m_replaced=false;


	/**
	 * indicate if string was replaced.
	 */
	static boolean getReplaced()
	{
		return(m_replaced);
	}
  /**
   * Convert the old telesite 3 url to the
   * new Enformia url.
   * @param s the string containing the old url.
   * @return String a the input String with new url replacing
   * the old urls.
   */
	static String replaceStr(String s, String oldPat, String newPat)
    {
	    int currIndex = 0;
	    int index;
	    int oldPatLen = oldPat.length();
		m_replaced=false;
	    StringBuffer sb = new StringBuffer(s.length());
	    String before=null;
		/* save some mem */
		if(0> s.indexOf(oldPat,currIndex)) {
			return(s);
		}
	    while(-1 < (index = s.indexOf(oldPat,currIndex))) {
			m_replaced=true;
		    before=s.substring(currIndex,index);
		    sb.append(before);
		    sb.append(newPat);
		    currIndex=index+ oldPatLen;
	    }
	    String after=s.substring(currIndex);
	    sb.append(after);
		after=before=null;

		return(sb.toString());

    }




} // class StringUtils
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ConvertAttachments {


	   Connection m_conn = null;
	   static final String urlPrefix = "http://telem5.openu.ac.il/tsol/";
	   static final String newPrefix = "/opus/Static/Binaries/editor/";



	   public ConvertAttachments()
	   {

		}


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
		  * correct the data field in the data_bin table.
		  * change '\' to '/'
		  */
		 void correctTable()
		 {

			  Connection conn = ConnectionManager.getConnection();
			  Statement st = null;
			  int rows=0;
			  try {
				st = conn.createStatement();
				rows=st.executeUpdate(
							"update data_bin set data=REPLACE(data ,'\\','/')");
				st.close();
				System.out.println("correctTable: updated rows : " + rows);
			 } catch(SQLException ex) {
					 ex.printStackTrace();
			}

		} //correctTable

		  /**
		   * convert the attachments.
		   */
		   void convert()
		   {
			  Connection conn = ConnectionManager.getConnection();
			  Statement st = null;
			  Statement st2 = null;
			  Statement updSt = null;
			  ResultSet rs = null;
			  ResultSet rs2 = null;
			  String new_data=null;
			  boolean update=false;
				int i=0;
				int id=0;

			  try {
				st = conn.createStatement();
				updSt = conn.createStatement();
				rs = st.executeQuery("select obj_id, html from openu_htmlData");
				st2 = conn.createStatement();
				while(rs.next() ) {
				  id = rs.getInt(1);
				  new_data = rs.getString(2);
				  /* 
				   * replace single commas with double commas
				   * for SQL syntax.
				   */
				  new_data=handleCommas(new_data);
				  

				   rs2 = st2.executeQuery("select a.data, b.data from data_bin a,  data_bin_old b where a.obj_id=" + id + 
				  " and a.obj_id=b.obj_id and a.prop_id=b.prop_id and a.prop_inst=b.prop_inst");
				   freeMem();
				  while(rs2.next()) {
				  	  String data = rs2.getString(1).trim();;
					  String old_data = rs2.getString(2).trim();
				  	  // convert ' to ''
					  data= newPrefix + data;
				      // now replace the strings.
				      System.out.println("trying to replace: " + urlPrefix+old_data);
					  /*
				      new_data = StringUtils.replaceStr(html_data,urlPrefix+old_data, data);
				      update= StringUtils.getReplaced();
				      new_data=StringUtils.replaceStr(new_data,old_data,data);
				      if(!update) {
						update=StringUtils.getReplaced();
				      }
					  */

					  /**
					   * Use jdk 1.4 regular expression package
					   * in order to save memory.
					   */
					  new_data=new_data.replaceAll(urlPrefix+old_data,data);
					  new_data=new_data.replaceAll(old_data,data);

					  old_data = data = null;
					} // while on data_bin_old - data_bin
					rs2.close();
					rs2=null;
				  	++i;
					update=true;
				    if(update ) {
				  	     System.out.println("entry(" + i + ") id: " + id );
				  	     updSt.executeUpdate("update openu_htmlData set html='" + new_data+ "'" + " where obj_id=" + id);
				    } 
					new_data=null;


				} // while
				System.out.println("replaced entries: " + i);

			  }  catch (SQLException ex) {
				ex.printStackTrace();
				System.out.println("\n------------------------------\n");
				System.out.println("obj_id = " + id);
			  } finally {
			        try {
						System.out.println("closing ...");
						System.out.println("replaced entries: " + i);

					    if(rs != null) {
						   rs.close();

						}
						if(rs2 != null) {
							rs2.close();
						}

						st.close();
						st2.close();
						updSt.close();


					  } catch(SQLException se) {
						se.printStackTrace();
					  }
			  } //finally




		   }

		   void freeMem()
		   {
				long free = Runtime.getRuntime().freeMemory();
				//System.out.println("freeMem: " + free/1024);
    			long oldfree;
    			do {
			      oldfree = free;
      			  Runtime.getRuntime().gc();
     			  free = Runtime.getRuntime().freeMemory();
    			} while (free > oldfree);
  
  			}
				   

		   /* main */
		public static void main(String argv[])
		{
		  ConvertAttachments cva = new ConvertAttachments();
		  System.out.println("ConvertAttachments connected ..");
	 	  cva.correctTable();
		  System.out.println("CorrectTable ..");
		  cva.convert();
		  cva.close();
		  System.out.println("done ....");

		}



} // ConvertAttachments
