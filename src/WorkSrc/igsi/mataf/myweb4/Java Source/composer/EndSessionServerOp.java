package composer;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 2003 - All Rights Reserved
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
import com.ibm.dse.base.*;
import com.ibm.dse.services.jdbc.*;
/**
 * This operation is invoked to remove the client workstation context 
 * in the server workstation. To start working again, the client will 
 * have to invoke the StartupClientOp first in order to create the client 
 * context hierarchy in the server. 
 * @copyright (c)Copyright IBM Corporation 1998, 2003. 
 */
public class EndSessionServerOp extends DSEServerOperation {
	
/**
 * EndSessionServerOp default constructor.
 */
public EndSessionServerOp() {
	super();
}
/**
 * EndSessionServerOp constructor with operation name.
 * @param anOperationName java.lang.String
 * @exception java.io.IOException
 */
public EndSessionServerOp(String anOperationName) throws java.io.IOException {
	super(anOperationName);
}
/**
 * EndSessionServerOp constructor with operation name and parent context name.
 * @param anOperationName java.lang.String
 * @param aParentContext java.lang.String
 * @exception java.io.IOException
 * @exception com.ibm.dse.base.DSEObjectNotFoundException
 * @exception com.ibm.dse.base.DSEInvalidRequestException
 */
public EndSessionServerOp(String anOperationName, String aParentContext) throws java.io.IOException, DSEObjectNotFoundException, DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * EndSessionServerOp constructor with operation name and parent context.
 * @param anOperationName java.lang.String
 * @param aParentContext com.ibm.dse.base.Context
 * @exception java.io.IOException
 * @exception com.ibm.dse.base.DSEInvalidRequestException
 */
public EndSessionServerOp(String anOperationName, Context aParentContext) throws java.io.IOException, DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}

/**
 * Removes the session entry in the
 * sessions table and the context hierarchy created in the initial 
 * operation (StartupServerOp).
 * The operation context of this operation will be automatically
 * removed by the client server mechanism during the close() process.
 * @exception java.io.IOException
 */
public void execute() throws Exception {
	// Removes the session entry in the sessions table	
	Context.removeSession((String)getValueAt("sessionID"));//$NON-NLS-1$
	// Removes the context and its parent (the parent session context)
	getParent().prune();
}
}
