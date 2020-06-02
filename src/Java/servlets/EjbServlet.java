import java.io.*;
import javax.ejb.*;
import javax.naming.*;
import java.rmi.RemoteException;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ban.*;

public class EjbServlet extends HttpServlet 
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

	  // String url       = "t3://localhost:8050";
	  PrintWriter out = servRes.getWriter();

	    out.println("\nBeginning .Client...\n");

	    try {
		    /*
		Hashtable h = new Hashtable();
		    h.put(Context.INITIAL_CONTEXT_FACTORY,
			"weblogic.jndi.WLInitialContextFactory");
		    h.put(Context.PROVIDER_URL, url);
		    */
		   // on the localserver no need to connect using T3.
	      Context ctx          = new InitialContext();
	      BanHome home = (BanHome) ctx.lookup("ban.BanHome");
	      BanRemote ban = (BanRemote) home.create(1,"Oded","Givataim");
	      out.println("data = " + ban.getData().toString());
	      ban = (BanRemote) home.create(2,"Adi","Tel-Aviv");
	      out.println("data = " + ban.getData().toString());
	      ban = (BanRemote) home.create(3,"Eric","Paris");
	      out.println("data = " + ban.getData().toString());
	      
	      BanData data = ban.getData();
	      data.setName("Balvu");
	      ban.setData(data);
	      out.println("data = " + ban.getData().toString());
	     } catch(Exception e) {
		     e.printStackTrace();
		     out.println("<p> Exception: " + e);
	     }
	      


	}
}

