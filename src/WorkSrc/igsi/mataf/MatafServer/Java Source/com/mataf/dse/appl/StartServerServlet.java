package com.mataf.dse.appl;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1999, 2002 - All Rights Reserved
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
/**
 * This class is a sample servlet that starts the server in the Web server workstation. 
 * it creates an initial context in the server and starts the client server service.
 * In a real implementation, this class would have to perform any process required by the 
 * server such as the initial context creation and the 
 * initialization of the client server service.
 * <p>This servlet supports command line arguments. See helpMessage().</p>
 * @copyright(c) Copyright IBM Corporation 1999, 2002.
 */
public class StartServerServlet extends javax.servlet.http.HttpServlet implements CSInactivityClientListener, CSProcessorInactivityListener {
	private static java.util.ResourceBundle resResources = java.util.ResourceBundle.getBundle("sampleapplserver"); //$NON-NLS-1$

	/**
	 * This constructor creates a StartServerServlet object. 
	 */
	public StartServerServlet() {
		super();
	}
	/**
	 * Initializes the environment. 
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @exception java.io.IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		log(resResources.getString("Begin_of_doGet")); //$NON-NLS-1$

		String message = processRequest(req, res);

		// Get an OutputStream to send back a response
		ServletOutputStream o = res.getOutputStream();

		// Sends the response
		o.println("<HEAD><H1><B>StartServerServlet</B></H1><HR></HEAD>"); //$NON-NLS-1$
		o.println("<BODY>"); //$NON-NLS-1$
		o.println(message + "<BR><HR>"); //$NON-NLS-1$
		o.println("</BODY></HTML>"); //$NON-NLS-1$
		o.close();

		log(resResources.getString("End_of_doGet")); //$NON-NLS-1$
	}
	/**
	 * Initializes the environment. 
	 * @param req HttpServletRequest
	 * @param res HttpServletResponse
	 * @exception java.io.IOException
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
		log(resResources.getString("Begin_of_doPost")); //$NON-NLS-1$

		String message = processRequest(req, res);

		// Get an output stream to send the response back
		java.io.DataOutputStream out = new java.io.DataOutputStream(res.getOutputStream());

		// The response is the "message" String converted to UTF8
		byte[] messageBytes = message.getBytes("UTF8"); //$NON-NLS-1$
		out.writeInt(messageBytes.length);
		out.write(messageBytes, 0, messageBytes.length);

		out.close();

		log(resResources.getString("End_of_doPost")); //$NON-NLS-1$
	}
	/**
	 * Converts all blanks in the String to a key instead of an
	 * space. The key is "dse_Blank". This method is used if the path passed as a parameter 
	 * in the main method (-p parameter) has spaces so the paths must not contain the key.
	 * @return java.lang.String - The path with blanks replaced by the key
	 * @param array String[] - The command line arguments
	 * @param int index - The index to the -p argument
	 */
	protected static String getPath(String[] array, int index) {
		int i = index, ind1 = 0, ind2 = 0;
		String temp = "";

		i++;
		while (ind2 > -1) {
			ind2 = array[1].indexOf(" ", ind2); //$NON-NLS-1$
			if (ind2 == -1) {
				temp = temp + array[i].substring(ind1, array[i].length());
			} else {
				temp = temp + array[i].substring(ind1, ind2);
				ind2++;
				ind1 = ind2;
				temp = temp + "dse_Blank"; //$NON-NLS-1$
			}
		}

		return temp;
	}
	/**
	 * Handles the events that the CSServer
	 * will throw when an HTML session expires.
	 * @param event com.ibm.dse.cs.servlet.CSInactivityClientEvent
	 */
	public void handleCSInactivityClientEvent(CSInactivityClientEvent event) {

		try {
			String sessionId = event.getSessionExpired();
			java.util.Hashtable procTable = HtmlProcessorManager.getProcessorInfoTable(sessionId);
			java.util.Enumeration procKeyEnum = procTable.keys();
			while (procKeyEnum.hasMoreElements()) {
				String processorId = (String) procKeyEnum.nextElement();
				com.ibm.dse.automaton.ProcessorExternalizer.getFromCache(processorId);
			}

			Context.removeSession(event.getSessionExpired());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Handles the events that the CSServer
	 * will throw when any HTML processor expires.
	 * @param event com.ibm.dse.cs.servlet.CSProcessorInactivityEvent
	 */
	public void handleCSProcessorInactivityEvent(CSProcessorInactivityEvent event) {

		String processorId = event.getProcessorId();
		String sessionId = event.getSessionId();

		try {
			String procName = ((HtmlProcessorInfo) HtmlProcessorManager.getProcessorInfoTable(sessionId).get(processorId)).getProcessorName();
			HtmlProcessorManager.getUniqueHTMLInstance().removeProcessor(sessionId, processorId);
			//Removing one instance of processor from cache.
			com.ibm.dse.automaton.ProcessorExternalizer.getFromCache(procName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Shows a help message with the different arguments the servlet can handle 
	 * from the input line. The arguments can be:<br>
	 *		-h				shows the help message<br>
	 *		-s <address>	IP address where the servlets server is running<br>
	 *		-p <path>		path to the server's dse.ini file
	 */

	private static void helpMessage() {
		System.out.println(resResources.getString("Use__java_com.ibm.dse.samp")); //$NON-NLS-1$
		System.out.println();
		System.out.println(resResources.getString("_-s_<server_address>_Serve")); //$NON-NLS-1$
		System.out.println(resResources.getString("_-p_<dseIniPath>__Path_to_")); //$NON-NLS-1$
		System.out.println(resResources.getString("_-h__________________This_")); //$NON-NLS-1$
		System.exit(0);
	}
	/**
	 * Initializes the servlet. To avoid the framework initialization when the Application Server is initiated, set the
	 * initStart parameter to false.
	 * @param sc ServletConfig
	 */
	public void init(ServletConfig sc) {

		try {
			super.init(sc);

			String initStart = getInitParameter("initStart"); //$NON-NLS-1$
			//		
			//		if (initStart!=null && initStart.equals("false"))//$NON-NLS-1$
			//		{
			//			// Do nothing: the user doesn't want to initialize the environment in the Application Server's startup
			//		}
			//		else
			//		{
			//			// Get the path of the server's dse.ini file
			//			String path=sc.getInitParameter("dseIniPath");//$NON-NLS-1$
			//			
			//			if (path != null)	initialize(path);
			//			// If the user has not set the path, use the default (C:\\dse\\server\\dse.ini)
			//			else                initialize();
			//		}
		} catch (Exception e) {
			log(resResources.getString("Exception_in_init__") + e); //$NON-NLS-1$
		}
		log(resResources.getString("End_of_init")); //$NON-NLS-1$
	}
	/**
	 * Creates an initial context in the server and starts the client server service.
	 * This method is invoked while running the servlet from WebSphere or from the command line 
	 * without specifying a path to the configuration file.
	 * @exception java.io.IOException.
	 */
	protected void initialize() throws Exception {
//		if (Context.getRoot() == null) {
			Context.reset();
//		}

		String iniPath = getInitParameter("dseIniPath"); //$NON-NLS-1$
		if ((iniPath != null) && (iniPath.toLowerCase().equals("fromjar"))) {
			java.io.BufferedInputStream DSE_INI_stream = new java.io.BufferedInputStream(this.getClass().getResourceAsStream("/dse.ini"), 200000);
			Settings.reset(DSE_INI_stream);
		} else {
			if (Context.getRoot() == null) {
				if (iniPath != null)
					Settings.reset(iniPath);
				else
					Settings.reset("c:\\dse\\server\\dse.ini"); //$NON-NLS-1$
			}
		}

		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);

		// Creates the initial context in the server.
		Context context = new com.ibm.dse.base.Context("branchServer"); //$NON-NLS-1$
		// Starts the client server session.
		try {
			((CSServerService) (context.getService("CSServer"))).stopServer();
		} catch (Exception e) {}
		 ((CSServerService) (context.getService("CSServer"))).initiateServer(); //$NON-NLS-1$

		//Add this object as the listener for the session and processor expiration events
		 ((CSServer) context.getService("CSServer")).addCSInactivityClientListener(this);
		((CSServer) context.getService("CSServer")).addCSProcessorInactivityListener(this);
		
//		Service.readObject("Monitor");
	}
	/**
	 * Creates an initial context in the server and starts the client server service.
	 * This method is invoked when running the servlet from the command line.
	 * @exception java.io.IOException.
	 */
	protected void initialize(String iniPath) throws Exception {
		if (Context.getRoot() == null) {
			Context.reset();

			if ((iniPath != null) && (iniPath.toLowerCase().equals("fromjar"))) {
				java.io.BufferedInputStream DSE_INI_stream = new java.io.BufferedInputStream(this.getClass().getResourceAsStream("/dse.ini"), 200000);
				Settings.reset(DSE_INI_stream);
			} else {
				Settings.reset(iniPath);
			}
		}
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);

		// Creates the initial context in the server.
		Context context = new com.ibm.dse.base.Context("branchServer"); //$NON-NLS-1$
		// Starts the client server session.
		try {
			((CSServerService) (context.getService("CSServer"))).stopServer();
		} catch (Exception e) {}
		 ((CSServerService) (context.getService("CSServer"))).initiateServer(); //$NON-NLS-1$

		//Add this object as the listener for the session and processor expiration events
		 ((CSServer) context.getService("CSServer")).addCSInactivityClientListener(this);
		((CSServer) context.getService("CSServer")).addCSProcessorInactivityListener(this);
	}
	/**
	 * Initializes the server using the command line arguments. See helpMessage().
	 * @param args java.lang.String[]
	 */
	public static void main(String args[]) {

		try {
			String server = "127.0.0.1:80"; // Default address//$NON-NLS-1$
			String path = null; // Default path

			int hIndex = -1, sIndex = -1, pIndex = -1, i = 0;

			String element;
			for (java.util.Enumeration enum = (new Vector(args)).elements(); enum.hasMoreElements(); i++) {
				element = (String) enum.nextElement();
				if (element.toUpperCase().equals("-P"))
					pIndex = i; //$NON-NLS-1$
				if (element.toUpperCase().equals("-S"))
					sIndex = i; //$NON-NLS-1$
				if (element.toUpperCase().equals("-H"))
					hIndex = i; //$NON-NLS-1$
			}

			if (hIndex != -1)
				helpMessage();
			if (sIndex != -1)
				server = args[sIndex + 1];
			if (pIndex != -1)
				path = getPath(args, pIndex);

			URL myURL = null;
			if (path != null)
				myURL = new URL("http://" + server + "/servlet/StartServerServlet?" + path); //$NON-NLS-2$//$NON-NLS-1$
			else
				myURL = new URL("http://" + server + "/MatafServer/servlet/StartServerServlet"); //$NON-NLS-2$//$NON-NLS-1$

			HttpURLConnection urlConnection = (HttpURLConnection) (myURL.openConnection());

			urlConnection.setRequestMethod("POST"); //$NON-NLS-1$
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);

			System.out.println(resResources.getString("Connecting_with_the_server1")); //$NON-NLS-1$
			urlConnection.connect();

			java.io.DataInputStream in = new java.io.DataInputStream(urlConnection.getInputStream());
			int resultLength = in.readInt();

			byte responseBuffer[] = new byte[resultLength];
			in.readFully(responseBuffer);
			String result = new String(responseBuffer, "UTF8"); //$NON-NLS-1$
			in.close();

			System.out.println(result);

		} catch (Exception e) {
			System.out.println(resResources.getString("Exception_in_main__") + e); //$NON-NLS-1$
		}
		return;
	}
	/**
	 * Reads the parameter sent by the user and initializes the environment based
	 * on this paramether. This method is common for any client type (HTML, java, ...)
	 * @return java.lang.String
	 * @param req javax.servlet.http.HttpServletRequest
	 * @param res javax.servlet.http.HttpServletResponse
	 */
	protected String processRequest(HttpServletRequest req, HttpServletResponse res) {
		String message = "Hey Hey ! Initialization is OK ...(Run OpenDesktop Already !)";//resResources.getString("Initialization_OK"); //$NON-NLS-1$

		try {
			// Gets the path sent by the user
			String path = restoreSpacesToPath(req.getQueryString());

			if (path != null)
				initialize(path);
			// If the user has not set the path, use the default (C:\\dse\\server\\dse.ini)
			else
				initialize();
		} catch (Throwable t) {
			log(resResources.getString("Exception_processing_reque") + t); //$NON-NLS-1$
			message = resResources.getString("ERROR_in_Server___") + t.toString(); //$NON-NLS-1$
		}

		return message;
	}
	/**
	 * Utility method that looks in the mainString all the occurrences of the 
	 * oldString and replaces each of them with the newString.
	 *
	 * @return java.lang.String[]
	 */
	protected static String replace(String mainString, String oldString, String newString) {
		StringBuffer result = new StringBuffer();

		int i = 0, j;

		for (j = mainString.indexOf(oldString); j != -1; j = mainString.substring(i).indexOf(oldString)) 
		{
			result.append(mainString.substring(i, j));
			result.append(newString);
			i = j + oldString.length();
		}
		result.append(mainString.substring(i));

		return result.toString();
	}
	/**
	 * Utility method that replaces each occurrence of the key "dse_Blank" with a space.
	 * @return java.lang.String
	 */
	protected String restoreSpacesToPath(String path) {
		if (path == null)
			return null;

		StringBuffer spacesPath = new StringBuffer();

		int i = 0, j;

		for (j = path.indexOf("dse_Blank"); j != -1; j = path.substring(i).indexOf("dse_Blank")) //$NON-NLS-2$//$NON-NLS-1$
			{
			j = i + j;
			spacesPath.append(path.substring(i, j));
			spacesPath.append(" "); //$NON-NLS-1$
			i = j + 9;
		}
		spacesPath.append(path.substring(i));

		return spacesPath.toString();
	}
}
