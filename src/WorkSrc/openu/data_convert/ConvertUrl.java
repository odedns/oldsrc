
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Properties;
import java.sql.*;

/**
 * <p>Title: ConvertUrl</p>
 * <p>Description:
 * This program converts old style Enformia urls into new Enformia style urls.
 * In order to run this program you must use weblogic's SQL-SERVER JDBC driver.
 * This means that both weblogic.jar and license.bea license file must be in the CLASSPATH.
 * </p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Open University</p>
 * @author Oded Nissan
 * @version 1.0
 */

public  class ConvertUrl {

  static final String COMPONENT_PAGE="ComponentPage";
  static final String FOCUS_PAGE="FocusPage";
  static final String OPEN_WIN_STR ="openwin";
  HashMap m_map = null;
  Connection m_conn = null;
  boolean m_logEnabled = true;
  String m_currPage=null;
  boolean m_cont = true;



  /**
   * Constructor.
   * Initialize hash table with entries for translation of
   * zone class names.
   * Open a JDBC connection to the db.
   */
  public ConvertUrl()
  {


      m_map = new HashMap();
      m_map.put("/links","Links");
      m_map.put("/editor","Editor");
      m_map.put("/questioner","Questioner");
      m_map.put("/milon","Milon");
      m_map.put("/upload","Upload");
      m_map.put("/walla_forum", "Forum");
      m_map.put("/links","Links");
      m_map.put("/edit_news","Edit_news");


 }


  /**
   * close the JDBC connection.
   */
  void close()
  {
		ConnectionManager.close();
  }


  String getNameParam(String s)
  {
	int i = s.indexOf("name=");
	if(i < 0) {
		return(null);
	}
	log("getNameParam s = " +s);
	i += 5;
	StringBuffer sb = new StringBuffer();
	char c;
	while(i < s.length()) {
		c = s.charAt(i);
		++i;
		if(c == '\'' || c == '&' || c ==')') {
			break;
		}
		sb.append(c);
	}

	return(sb.toString());

  }
  /**
   * get the object id in case
   * we have only node id and tsstemplate
   */
  int getObjectId(int node, String s)
  {
	 int objId=-1;
     Statement stmt = null;
     ResultSet rs = null;
     Connection conn = ConnectionManager.getConnection();
	 String name = getNameParam(s);
	 String sql = "select id from data where node_id=" + node + " and name='" + name.trim() + "'";

     try {
        stmt = conn.createStatement();
		log("sql = " + sql);
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
          objId = rs.getInt(1);
       } // if
	   rs.close();
	   stmt.close();

     }  catch (SQLException ex) {
        ex.printStackTrace();

     }  finally {
         try {
            rs.close();
            stmt.close();
          } catch(SQLException se) {
            se.printStackTrace();
          }

	 } // finally

	 log("objId = " + objId);
	 m_currPage=COMPONENT_PAGE;
	 return(objId);

  }

  /**
   * Get the zone class name.
   * Perform a lookup according to the node id.
   */
  String getZoneClass(int node)
  {
    /**
     * get the zoneClass
     */
     Statement stmt = null;
     ResultSet rs = null;
     Connection conn = ConnectionManager.getConnection();

     String zoneClass = null;
     try {
        stmt = conn.createStatement();
        rs = stmt.executeQuery("select tmplt_path from ts_index where id=" + node);
        if(rs.next()) {
          String zc = rs.getString(1);
          if(null != zc) {
            return((String)m_map.get(zc.trim()));

          } // if


       } // if
	   rs.close();
	   stmt.close();

     }  catch (SQLException ex) {
        ex.printStackTrace();

     }  finally {
         try {
            rs.close();
            stmt.close();
          } catch(SQLException se) {
            se.printStackTrace();
          }
    } //finally


     return(zoneClass);

  }


  /**
   * get the index of the old url in the string s.
   * @param s the string to process.
   * @return int the index of the old url string to
   * convert in the input string.
   */
  int getUrlIndex(String s)
  {
    String urls[] = { "http://telem9.openu.ac.il/tsol/ts.exe?",
                      "http://telem5.openu.ac.il/tsol/ts.exe?",
                      "http://camun.openu.ac.il/tsol/ts.exe?",
                      "ts.exe?"
                      };

     int index = -1;

     s = s.toLowerCase();

     for(int i=0; i < urls.length; ++i) {
      index = s.indexOf(urls[i]);
      if(index >= 0) {
		// if there is an openWin javascript line
		// we have to use component page.
		if(s.indexOf(OPEN_WIN_STR) > 0 ) {
			m_currPage=COMPONENT_PAGE;
		}
         break;
      }
    } //for

     return(index);

  }

  /**
   * Convert all occurances of the old telesite 3 url to the
   * new Enformia url.
   * @param s the string containing the old url.
   * @return String a the input String with new url replacing
   * the old urls.
   */
  String convertAllUrl(String s)
  {
	int index;
	String newStr = s;
	    while(0 < (index = getUrlIndex(newStr))) {
		    newStr = convertUrl(newStr);
			if(null == newStr) {
				break;
			}
	    }
		if(null== newStr) {
			return(s);
		}
		return(newStr);
  }

  String tmpCvt(String s)
  {
		s= s.replaceFirst("ts.exe","ts.cvt_exe");
		StringBuffer sb = new StringBuffer(s);
		sb = handleCommas(sb);
		return(sb.toString());
  }

  /**
   * Convert the old telesite 3 url to the
   * new Enformia url.
   * @param s the string containing the old url.
   * @return String a the input String with new url replacing
   * the old urls.
   */
  String convertUrl(String s)
  {
    int prefix=0;
    String zoneClass = null;
    int node=-1;
    int objectId=-1;


	m_currPage= FOCUS_PAGE;
	int i= getUrlIndex(s);


    if (i < 0) {
      return(tmpCvt(s));
    }
    String before = s.substring(0,i);
    String after = "";

    log("s = " + s);
    // find end of url
    int j = s.indexOf("=",i);
    if( j <0) {
      after = "";
    } else {
      int c;
      ++j;
      for(; j < s.length(); ++j) {
         c = s.charAt(j);
         if(  !Character.isDigit((char)c) && !(c == '.')  ) {
          break;
        }

      } // while

      after = s.substring(j);
    }


   if(i < 0 || j < 0) {
    return(tmpCvt(s));
   }
    String url = s.substring(i,j);

   log("before = " + before);
   log("after = " + after);
   log("url = " + url);
   System.out.println("url = " + url);


    /**
     * find the begining of the url.
     */
    int index = url.indexOf('=');
      if(index < 0) {
        return(tmpCvt(s));
      }
      String s2 = url.substring(++index);


      /**
       * parse the string tokens into:
       * prefix, node and object id.
       * 0.213.2222
       */
      StringTokenizer st = new StringTokenizer(s2,".");

      int cnt = st.countTokens();
      if (cnt < 2) {
        return(tmpCvt(s));
      }

      String token = st.nextToken().trim();
      try {
        prefix =  Integer.parseInt(token);
      	token = st.nextToken().trim();
        node = Integer.parseInt(token);
		log("node = " + node);
        if(cnt >=  3 ) {
        	token = st.nextToken().trim();
        	objectId = Integer.parseInt(token);
        }
		/*
		if(s.indexOf("tsstmplt=search_object") > 0) {
			log("calling getObjectId");
		    objectId = getObjectId(node,s);
			if(objectId < 0) {
					return(null);
			}
		}
		*/


      }     catch (NumberFormatException ex) {
        System.out.println("token = " + token);
        System.out.println("s2 = " + s2);
        ex.printStackTrace();
        return(tmpCvt(s));
      }


    zoneClass = getZoneClass(node);
	/*
	System.out.println("zoneClass=" + zoneClass + " prefix = " + prefix +
					" node = " + node + " objectId = " + objectId);
					*/
    if (null == zoneClass) {
      return(tmpCvt(s));
    }

    String newUrl = buildUrl(zoneClass,prefix, node,objectId);
    StringBuffer sb = new StringBuffer(200);
    sb.append(before);
    sb.append(newUrl);
    sb.append(after);
	after = before = newUrl = null;
    StringBuffer sb2 = handleCommas(sb);
	sb = null;
    return(sb2.toString());

  }


  String handleCommas(String s)
  {
		  StringBuffer sb = new StringBuffer(s);
		  sb = handleCommas(sb);
		  return(sb.toString());
  }

  /**
   * Convert single commas to double commas, so that sql can handle
   * the input string.
   *
   */
  StringBuffer handleCommas(StringBuffer sb)
  {
    char c;
    StringBuffer sb2 = new StringBuffer(sb.length());
    for(int i=0; i < sb.length(); ++i) {
      c = sb.charAt(i);
      if(c == '\'') {
        sb2.append('\'');
      }
      sb2.append(c);
    } // for
    return(sb2);

  }


  /**
   * Bulld the new url.
   * @param zoneClass the zone class name.
   * @param prefix an integer the prefix of the url address ( 0.213.0 -  0 is the prefix).
   * @param node the node id in the url address ( 0.213.0 213 is the node 0.213.0).
   * @param object id in the url address  ( 0.213.1600.0 1600 is the object id).
   * @return String the new converted url string.
   */
  String buildUrl(String zoneClass, int prefix,int node, int objectId)
  {
    StringBuffer sb = new StringBuffer(100);
	String enDisplay="add";

    if(prefix == 0) {
		enDisplay="view";
    }

    sb.append("en.jsp?enPage=");
	sb.append(m_currPage);
	sb.append("&enDisplay=" + enDisplay + "&enZone=" + zoneClass+node);

    if(objectId > 0 ) {
      sb.append("&enDispWhat=object&enDispWho=" +
        zoneClass + "%5El" + objectId);
    } else {
      sb.append("&enDispWhat=zone&enDispWho=" + zoneClass + node);
    }


    if(prefix > 0 ) {
      System.out.println("WARNING PREFIX IS : " + prefix);
    }
    return(sb.toString());

  }

  /**
   * Process  all data tables, read the data and convert old urls
   * to new urls.
   */
  void process()
  {
     String data_char = "select * from data_char where data like'%ts.exe%'";
     String data_char_upd = "update data_char set data='";
     String data_text = "select * from data_text where data like'%ts.exe%'";
     String data_text_upd = "update data_text set data='";
     //String openu_htmlData = "select * from openu_htmlData where html like'%ts.exe%' ";
     String editor = "select * from Editor where htmldata like'%ts.exe%' ";
     //String openu_htmlData_upd = "update openu_htmlData set html='";
     String editor_upd = "update Editor set htmldata='";

	 /*
     process("data_char",data_char,data_char_upd);
     process("data_text",data_text, data_text_upd);
	 */

   /*
	* we need to process this table until all entries
	* are replaced. We cannot perform the replacements using
	* the convertAllUrl method because of memory limitations.
	*/
   do {
	     process("Editor",editor,editor_upd);
	} while(m_cont);
  }

  /**
   * Process a specific table using appropriate sql statements.
   * @param table the name of the table to process.
   * @param sql the sql query used to retrieve the data.
   * @param update the sql update statement used to update
   * the data.
   */
  void process(String table,String sql, String update)
  {

    ResultSet rs = null;
    Statement st = null;
    Statement updSt = null;
    int obj_id = -1;
    Connection conn = ConnectionManager.getConnection();
    System.out.println("converting table : " + table);
    int count = 0;
	int propId;
	int propInst;
	String upd=null;
	String newUrl;

    try {
      st = conn.createStatement();

      updSt = conn.createStatement();
      rs = st.executeQuery(sql);
      if(null == rs) {
		m_cont =false;
        return;
      }
      String s = null;
      int i = 0;
	  boolean hasMore = rs.next();
	  if(!hasMore) {
		  m_cont = false;
	  }
      while(hasMore ) {
        ++i;
        obj_id = rs.getInt(1);

		if(!table.equals("Editor") && !table.equals("openu_htlmData") ) {
			propId= rs.getInt(2);
			propInst = rs.getInt(3);
        	s = rs.getString(4);
	       	newUrl = convertUrl(s);
	       	//newUrl = convertAllUrl(s);
            upd = update + newUrl + "' where obj_id=" + obj_id +
				  " and prop_id=" + propId + " and prop_inst=" + propInst;
		} else {
        	s = rs.getString(2);
        	newUrl = convertUrl(s);
            upd = update + newUrl + "' where id=" + obj_id;
		}

        log("object id = " + obj_id);
        log("old url = " + s);


        if (null != newUrl) {
          ++count;
          log("new url = " + newUrl);
          log("---------------------------");

          log("upd = " + upd);
		  System.out.println("updated object id: " + obj_id);
          updSt.executeUpdate(upd);
		  upd=null;
        }
		s=null;
		newUrl=null;


	  	hasMore = rs.next();
      } // while

    } catch (SQLException ex) {
          ex.printStackTrace();
		  System.out.println("upd : " + upd);

    } finally {
         try {
            rs.close();
            st.close();
            updSt.close();
          } catch(SQLException se) {
            se.printStackTrace();
          }
    } //finally
    System.out.println("=====================================================");
    System.out.println("Table: " + table + " total updated rows : " + count);
    System.out.println("=====================================================");

  } // process



  /**
   * log a message.
   */
  void log(String msg)
  {
    if(m_logEnabled) {
      System.out.println(msg);
    }
  }

  void cleanUp()
  {
	ResultSet rs = null;
    Statement st = null;
    Statement updSt = null;
    int obj_id = -1;
    Connection conn = ConnectionManager.getConnection();
    System.out.println("cleaning up editor ");
	String upd=null;
	String newData;

    try {
      st = conn.createStatement();

      updSt = conn.createStatement();
      rs = st.executeQuery("select id, htmldata from editor where htmldata like '%ts.cvt_exe%'");
      if(null == rs) {
		m_cont =false;
        return;
      }
      String s = null;
      int i = 0;
      while(rs.next() ) {
        ++i;
        obj_id = rs.getInt(1);
       	s = rs.getString(2);
	    newData = handleCommas(s.replaceAll("ts.cvt_exe","ts.exe"));
            upd = "update editor set htmldata = '" + newData +"' where id=" + obj_id;

          updSt.executeUpdate(upd);
        }


    } catch (SQLException ex) {
          ex.printStackTrace();
		  System.out.println("upd : " + upd);

    } finally {
         try {
            rs.close();
            st.close();
            updSt.close();
          } catch(SQLException se) {
            se.printStackTrace();
          }
    } //finally

  }

  /**
   * main conversion program.
   */
  public static void main(String argv[])
  {


	System.out.println("Running Conversion ....)");
        ConvertUrl cv = new ConvertUrl();
		/**
		 * change to true in order to print
		 * debugging messages.
		 */
		cv.m_logEnabled = false;
        cv.process();
		cv.cleanUp();
        cv.close();



  }


}
