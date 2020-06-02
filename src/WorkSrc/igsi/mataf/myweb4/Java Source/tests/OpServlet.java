package tests;

import com.ibm.dse.base.*;
import java.io.*;
import javax.servlet.http.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpServlet extends HttpServlet {
	
	public void dseInit() throws Exception 
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);				
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
		try {
			dseInit();
			System.out.println("OpServlet.service()");
			out.println("<p> This is OpServlet");	
			Context ctx = (Context) Context.readObject("myCtx");	
			DSEOperation cop = (DSEOperation) DSEOperation.readObject("serverHandlerOp");
			cop.setContext(ctx);
			cop.execute();
			cop.close();

		} catch(Exception e) {
			e.printStackTrace();	
			out.println("Exception : " + e);
			
		}
		System.out.println("end OpTest");
	

	}

}
