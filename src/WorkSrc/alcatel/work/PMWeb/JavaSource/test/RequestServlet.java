package test;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestServlet extends HttpServlet implements Servlet {
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RequestServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = (String) req.getParameter("action");		
		PrintWriter pw = resp.getWriter();
		pw.println("<HTML><BODY>");
		pw.println("<H1> RequestServlet</H1>");
		
		if(null != action) {
			pw.println("<p> Action is : " + action );
		}
		if(action.equals("select")) {
			SelectAction selAction = new SelectAction();
			selAction.doAction(pw);			
		} else {
			InsertAction insAction = new InsertAction();
			insAction.doAction(pw);
		}
		
		pw.println("</BODY></HTML>");
	}

}