
import javax.servlet.*;
import javax.servlet.http.*;

import onjlib.db.WASTranactionFactory;

import java.io.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

public class UserServlet extends HttpServlet {

        public UserServlet() {
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

		/*
		servRes.setContentType("text/html");
		servRes.sendError(-1,"Some fucking error");
		servRes.addHeader("status", "fucking status");
		*/
		
		ServletOutputStream out = servRes.getOutputStream();

		out.println("<p>in UserServlet  !!");
		WASTranactionFactory.foo();
		out.println("<p> after calling foo()");
	}
}