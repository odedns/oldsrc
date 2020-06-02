package composer.html;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2000, 2002 - All Rights Reserved
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
import com.ibm.dse.base.*;
import com.ibm.dse.cs.servlet.*;
import com.ibm.dse.cs.html.HtmlConstants;

/**
 * This class starts the HTML session and is invoked when the 
 * CSEstablishSessionServlet is invoked.
 *
 * @copyright(c) Copyright IBM Corporation 2000, 2002.
 */
public class StartHtmlSession extends DSEServerOperation
{
/**
 * StartupSessionOp constructor.
 */
public StartHtmlSession() {
	super();
}
/**
 * StartupSessionOp constructor.
 * @param aName java.lang.String
 * @exception java.io.IOException The exception description
 */
public StartHtmlSession(String aName) throws java.io.IOException {
	super(aName);
}
/**
 * StartupSessionOp constructor.
 * @param anOperationName java.lang.String
 * @param aParentContext java.lang.String
 * @exception java.io.IOException The exception description.
 * @exception com.ibm.dse.base.DSEObjectNotFoundException The exception description.
 * @exception com.ibm.dse.base.DSEInvalidRequestException The exception description.
 */
public StartHtmlSession(String anOperationName, String aParentContext) throws java.io.IOException, com.ibm.dse.base.DSEObjectNotFoundException, com.ibm.dse.base.DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * StartupSessionOp constructor.
 * @param anOperationName java.lang.String
 * @param aParentContext com.ibm.dse.base.Context
 * @exception java.io.IOException The exception description.
 * @exception com.ibm.dse.base.DSEInvalidRequestException The exception description.
 */
public StartHtmlSession(String anOperationName, com.ibm.dse.base.Context aParentContext) throws java.io.IOException, com.ibm.dse.base.DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * Creates the new session context and adds the new context to the sessions table.  * This method is invoked by the HTML Client framework when the 
 * CSEstablishSessionServlet is invoked
 */
public void execute ( ) throws Exception
{
	// Create the new session context for the user to be logged on
	Context sessionCtx = (Context) Context.readObject("htmlSessionCtx");

	// Chain the context to the root context. This is better than chaining to
	// a context by name. Obtaining an instance of a context by name may return
	// an invalid instance for our purposes. Keep in mind that you may have
	// more than one instance of context with the same name. The Context protocol
	// would return the first context found with that name, which may not be
	// the one we want.
	// For sample purposes we use the root context because it is the unique
	// context instance shared by all the contexts hierarchy.
	sessionCtx.chainTo(Context.getRoot());
	
	// Obtain the session id set by the infrastructure.
	String sessId = (String) getValueAt ( CSConstants.SESSIONIDKEY );
	
	// Add the session to the table of sessions managed by the Context 
	// class along with the start up context to be used as the session
	// context for the application.
	Context.addSession ( sessId, null, sessionCtx );
	
	// Add overall supported languages (for the purpose of the sample only two are used)
	/*	
	setValueAt("languages.0.name","English");
	setValueAt("languages.0.locale","en_US");			
	setValueAt("languages.1.name","Spanish");
	setValueAt("languages.1.locale","es_ES");
	*/
	
	
} // end execute ( )
} // end class StartHtmlSessionOperation
