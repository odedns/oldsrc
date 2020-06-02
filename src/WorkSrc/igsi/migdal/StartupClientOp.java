package composer;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 2000 - All Rights Reserved
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
import com.ibm.dse.clientserver.*;
/**
 * This operation is invoked after initialization to create the
 * client workstation context in the server workstation. 
 * This is the client side.
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 */
public class StartupClientOp extends DSEClientOperation {
/**
 * StartupClientOp default constructor.
 */
public StartupClientOp() {
	super();
}
/**
 * Constructor for the class StartupClientOp.
 * @param anOperationName java.lang.String
 * @exception java.io.IOException.
 */
public StartupClientOp(String anOperationName) throws java.io.IOException {
	super(anOperationName);
}
/**
 * Constructor for the class StartupClientOp.
 * @param anOperationName java.lang.String
 * @param aParentContext java.lang.String
 * @exception java.io.IOException.
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 * @exception com.ibm.dse.base.DSEObjectNotFoundException.
 */
public StartupClientOp(String anOperationName, String aParentContext) throws java.io.IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
	super(anOperationName, aParentContext);
}
/**
 * Constructor for the class StartupClientOp.
 * @param anOperationName java.lang.String
 * @param aParentContext com.ibm.dse.base.Context
 * @exception java.io.IOException.
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 */
public StartupClientOp(String anOperationName, Context aParentContext) throws java.io.IOException, DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * Executes the operation.
 * Initializes the terminal identification, gets the instance of the
 * client-server service and invokes the server operation using a
 * synchronous call.
 * @exception java.io.Exception.
 */
public void execute() throws Exception {
	CSClientService csClientService=null;
	setValueAt("TID",Settings.getTID());//$NON-NLS-1$
	csClientService	=((CSClientService)getService("CSClient"));//$NON-NLS-1$
	csClientService.sendAndWait(this,40000);
}
}
