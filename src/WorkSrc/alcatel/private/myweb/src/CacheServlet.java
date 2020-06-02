import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.websphere.cache.DistributedMap;

public class CacheServlet extends HttpServlet implements Servlet {
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CacheServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter pw = resp.getWriter();
		pw.println("<HTML><BODY>");
		pw.println("<p>CacheServlet");
		try {
			InitialContext ic = new InitialContext();
			DistributedMap dm = (DistributedMap) ic.lookup("services/mycache");
									
			dm.put("foo","bar");
			pw.println("<br>added cache Entry");
			String s = (String) dm.get("foo");
			pw.println("<br>got cache Entry for foo: " + s);
			System.out.println("Accessed dynamic cache : got cache Entry for foo: " + s);
		} catch(Exception e) {
			e.printStackTrace();
			pw.println("<br>Error: " + e);
		}
		
		
		
		pw.println("</BODY></HTML>");
		
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}