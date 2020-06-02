package composer.html;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 2002 - All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 * 
 * DISCLAIMER:
 * The following [enclosed] code is sample code created by IBM
 * Corporation. This sample code is not part of any standard IBM product
 * and is provided to you solely for the purpose of assisting you in the
 * development of your applications. The code is provided 'AS IS',
 * without warranty of any kind. IBM shall not be liable for any damages
 * arising out of your use of the sample code, even if they have been
 * advised of the possibility of such damages.
 */
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;
import com.ibm.dse.cs.servlet.*;
import com.ibm.dse.automaton.html.*;
import com.ibm.dse.automaton.ProcessorExternalizer;

/**
 * Sample servlet that starts the server in the web server
 * workstation. Creates an initial context in the server and
 * starts the client server service.
 * In a real implementation it has to perform any process required
 * by the server. Most probably it will also include the initial
 * context creation and the initialization of the client server
 * service. 
 * @copyright(c) Copyright IBM Corporation 1998, 2002.
 */
public class StartHtmlServerServlet extends composer.appl.StartServerServlet {
//	private static java.util.ResourceBundle reshtmlsample = java.util.ResourceBundle.getBundle("htmlsample");  //$NON-NLS-1$
	
/**
 * Constructor for class StartServerServlet. 
 */
public StartHtmlServerServlet ( )
{
	super ( );
	
} // end StartServerServlet ( ) constructor
/**
 * This method will be used for handle the events that the CSServer
 * will throw when an Html session expires.
 * @param event com.ibm.dse.cs.servlet.CSInactivityClientEvent
 */
public void handleCSInactivityClientEvent(CSInactivityClientEvent event) {

	String sessionId = null;

	try{
		sessionId = event.getSessionExpired();		
		if (!(Context.getSessionTable().getElements().containsKey(sessionId)))
		{
			if( Trace.doTrace(Constants.COMPID,Trace.High,Trace.Error) )
			{
				Trace.trace(Constants.COMPID,Trace.High,Trace.Error,Settings.getTID(),"A session inactivity timeout has occurred for a session not available in the WSBCC application sessions table. SessionId = "+sessionId);
			}				

			return;
		}

		Context	ctx=null;	
		java.util.Hashtable procTable = HtmlProcessorManager.getProcessorInfoTable(sessionId);
		java.util.Enumeration procKeyEnum = procTable.keys();
		while(procKeyEnum.hasMoreElements()){
			String processorId = (String)procKeyEnum.nextElement();
			String procName = ((HtmlProcessorInfo)procTable.get(processorId)).getProcessorName();

			// Remove the processor instance from the cache
			ProcessorExternalizer.getFromCache(procName);

			// Remove the processor instance from the processors registry
			HtmlProcessorManager.getUniqueHTMLInstance().removeProcessor(sessionId,processorId);
		}
		
		ctx=Context.getCurrentContextForSession(sessionId);

		// Remove the session from the sessions table
		Context.removeSession(sessionId);

		// Prune the session context and all its children contexts. This ensures the proper termination of
		// the services and correct contexts unchain processes
		ctx.prune();

	}catch(Exception e){
		e.printStackTrace();
		if( Trace.doTrace(Constants.COMPID,Trace.High,Trace.Error) )
		{
			Trace.trace(Constants.COMPID,Trace.High,Trace.Error,Settings.getTID(),"An exception has happened handling session inactivity timeout for session: "+sessionId);
			Trace.trace(Constants.COMPID,Trace.High,Trace.Error,Settings.getTID(),e.getMessage());
		}				
	}

}
/**
 * This method will be used for handle the events that the CSServer
 * will throw when any Html processor expires.
 * @param event com.ibm.dse.cs.servlet.CSProcessorInactivityEvent
 */
public void handleCSProcessorInactivityEvent(CSProcessorInactivityEvent event) {
	
	String processorId = event.getProcessorId();
	String sessionId = event.getSessionId();

	try{
		String procName = ((HtmlProcessorInfo)HtmlProcessorManager.getProcessorInfoTable(sessionId).get(processorId)).getProcessorName();
		HtmlProcessorManager.getUniqueHTMLInstance().removeProcessor(sessionId,processorId);
		//Removing one instance of processor from cache.
		ProcessorExternalizer.getFromCache(procName);
	}catch(Exception e){
		e.printStackTrace();
	}	
	

	
	}
/**
 * Shows a help message with the different arguments the servlet can handle 
 * from the input line.
 *
 * This arguments could be:
 *		-h				shows the help message
 *		-s <address>	IP address where the servlets server is running
 *		-p <path>		path to the server's dse.ini file
 */
 
private static void helpMessage() {
	System.out.println("Use__java_com.ibm.dse.samp"); //$NON-NLS-1$
	System.out.println();
	System.out.println("_-s_<server_address>_Serve"); //$NON-NLS-1$
	System.out.println("_-p_<dseIniPath>__Path_to_"); //$NON-NLS-1$
	System.out.println("_-h__________________This_"); //$NON-NLS-1$
	System.exit(0);
}
/**
 * Creates an initial context in the server and
 * starts the client server service.
 * @exception java.io.IOException.
 */
protected void initialize ( ) throws Exception
{
	String iniPath = getInitParameter("dseIniPath");//$NON-NLS-1$
	if (iniPath == null)
	{
		iniPath ="c:\\dse\\server\\dse.ini";
	}
	
	System.out.println("Initializing with dseIniPath = "+iniPath);
	initialize(iniPath);
} // end initialize ( )
/**
 * Creates an initial context in the server and starts the client server service.
 * This method is invoked when running the servlet from the command line.
 * @exception java.io.IOException.
 */
protected void initialize(String iniPath) throws Exception
{
	Context.reset();
	if (iniPath.toLowerCase().equals("fromjar"))
	{
		java.io.BufferedInputStream DSE_INI_stream = new java.io.BufferedInputStream (this.getClass().getResourceAsStream("/dse.ini"),200000);
		Settings.reset(DSE_INI_stream);
	}
	else
	{
		Settings.reset(iniPath);
	}
	Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);

	// Creates the initial context in the server.
	Context context = (Context) Context.readObject("branchServer");//$NON-NLS-1$
	// Starts the client server session.
	((CSServerService)(context.getService("CSServer"))).initiateServer();//$NON-NLS-1$

	//Add this object as the listener for the session and processor expiration events
	((CSServerService)context.getService("CSServer")).addCSInactivityClientListener(this);
	((CSServerService)context.getService("CSServer")).addCSProcessorInactivityListener(this);
	
}
/**
 * Initializes the server.
 * Parses the command line arguments. See helpMessage().
 * @param args java.lang.String[]
 */
public static void main(String args[]) {
		
	try {
		String server = "127.0.0.1:80"; // Default address//$NON-NLS-1$
		String path = null;				// Default path

		int hIndex = -1, sIndex = -1, pIndex = -1, i = 0;

		String element;
		for (java.util.Enumeration enum = (new Vector(args)).elements(); enum.hasMoreElements(); i++)
		{
			element = (String) enum.nextElement();
			if ( element.toUpperCase().equals("-P") ) pIndex = i;//$NON-NLS-1$
			if ( element.toUpperCase().equals("-S") ) sIndex = i;	//$NON-NLS-1$
			if ( element.toUpperCase().equals("-H") ) hIndex = i;//$NON-NLS-1$
		}

		if (hIndex != -1) helpMessage();
		if (sIndex != -1) server = args[sIndex+1];
		if (pIndex != -1) path = getPath(args, pIndex);

		URL myURL = null;
		if (path != null)
			myURL=new URL("http://"+ server + "/servlet/com.ibm.dse.samples.html.StartHtmlServerServlet?" + path);//$NON-NLS-2$//$NON-NLS-1$
		else
			myURL=new URL("http://"+ server + "/servlet/com.ibm.dse.samples.html.StartHtmlServerServlet");//$NON-NLS-2$//$NON-NLS-1$
	
		HttpURLConnection urlConnection=(HttpURLConnection)(myURL.openConnection());
	 
		urlConnection.setRequestMethod("POST");//$NON-NLS-1$
		urlConnection.setDoInput(true);
	  	urlConnection.setDoOutput(true);
	  	urlConnection.setUseCaches(false);

	  	System.out.println("Connecting_with_the_server"); //$NON-NLS-1$
	  	urlConnection.connect();
	 
		java.io.DataInputStream in= new java.io.DataInputStream(urlConnection.getInputStream()); 	 
		int resultLength=in.readInt();

		byte responseBuffer[]=new byte[resultLength];
		in.readFully(responseBuffer);
		String result=new String(responseBuffer,"UTF8");//$NON-NLS-1$
		in.close();
		
		System.out.println(result);
			
	}		
	catch(Exception e) {
		System.out.println("Exception_in_main_"+e); //$NON-NLS-1$
	}
	return;
}
/**
 * Reads the parameter sent by the user and initializes the environment in function
 * of this paramether. This method is common for any client type (HTML, java, ...)
 * @return java.lang.String
 * @param req javax.servlet.http.HttpServletRequest
 * @param res javax.servlet.http.HttpServletResponse
 */
protected String processRequest(HttpServletRequest req, HttpServletResponse res) 
{
	String message="Initialization_OK"; //$NON-NLS-1$

	try
	{
		// Gets the path sent by the user
		String path = restoreSpacesToPath(req.getQueryString());

		if (path != null)	initialize(path);
		// If the user has not set the path, use the default (C:\\dse\\server\\dse.ini)
		else                initialize();
	} 
	catch (Throwable t) 
	{
		log("Exception_processing_reque"+t); //$NON-NLS-1$
		message="ERROR_in_Server__"+t.toString(); //$NON-NLS-1$
	}

	return message;
}
}
