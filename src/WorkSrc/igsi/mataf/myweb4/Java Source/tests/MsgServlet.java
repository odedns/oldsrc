package tests;

import com.ibm.dse.base.*;
import composer.services.MQService;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MsgServlet extends HttpServlet {
	MQService m_service = null;	
	public void init(ServletConfig sc) 
	{
		try {

			Context.reset();
			Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
			m_service = new MQService();
			m_service.setUser("oded");
			System.out.println("MsgServlet after init()");
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}

	
	
   public void service(HttpServletRequest req, HttpServletResponse res)
       throws ServletException, IOException
  {
  	
  	String user = req.getParameter("user");
  	String msg = req.getParameter("msg");
  	try {
  		System.out.println("in service()");
  		PrintWriter out = res.getWriter();
  		if(user != null && msg != null) {
		  	m_service.send(user.getBytes(),msg);
  		}
  		out.print("<html><body><p>sending message<p> user: " + user +
  			"<p>msg = " + msg + "</body></html>");
  	} catch(Exception e) {
  		e.printStackTrace();
  		throw new ServletException(e.getMessage());	
  	}
  	
  }
}
