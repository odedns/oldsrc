import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class DBServlet extends HttpServlet
{

	/**
	 * @init
	 * initialize function.
	 * called once for the servlet.
	 */
	public void init(ServletConfig config)
		throws ServletException
	{
		super.init(config);
		System.out.println("DbServlet.init()");
	}
	/**
	 * @service
	 * the servlet service request.
	 * called once for each servlet request.
	 */
	public void service(HttpServletRequest servReq,
	                    HttpServletResponse servRes)
			    throws IOException
	{
		PrintWriter out = servRes.getWriter();
		System.out.println("DbServlet.service()");
	out.println("<p> This is DBServlet");	
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	try {
		
		conn = getConnection();
		System.out.println("testDb - gotConnection");
		st = conn.createStatement();
		String sql = "select * from mataf_override";
		rs = st.executeQuery(sql);
		if(rs.next()) {
			out.println("<p>rs = " + rs.toString());	
		}

		

	} catch(Exception e) {
		e.printStackTrace();
	}
	finally {
		try {
			
			if(null != conn) {
				conn.close();
			}
			if(null != st) {
				st.close();
			}
			if(null != rs) {
				rs.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
		}

	}
		


	}

 	static Connection getConnection() throws Exception
	{
	javax.naming.InitialContext context = null;
	context = new javax.naming.InitialContext();
	Hashtable hash = context.getEnvironment();
	System.out.println("context env = " + hash.toString());
	javax.sql.DataSource dataSource = (javax.sql.DataSource) context.lookup("jdbc/matafdb1");	
	

	System.out.println("got Datasource");
	//Connection conn = dataSource.getConnection("odedn","odedn01");
	
	
	Connection conn = dataSource.getConnection("mataf","mataf");
	return(conn);
}

}

