package com.mataf.operations;

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
 * This operation destroys the client context in the server side.
 * This is the client side. 
 * It is executed only once and the end of the working session.
 * @copyright (c)Copyright IBM Corporation 1998, 2000. 
 */
public class EndSessionClientOp extends com.ibm.dse.base.DSEClientOperation {
/**
 * EndSessionClientOp default constructor.
 */
public EndSessionClientOp() {
	super();
}
/**
 * EndSessionClientOp constructor.
 * @param anOperationName java.lang.String
 * @exception java.io.IOException.
 */
public EndSessionClientOp(String anOperationName) throws java.io.IOException {
	super(anOperationName);
}
/**
 * EndSessionClientOp constructor.
 * @param anOperationName java.lang.String
 * @param aParentContext java.lang.String
 * @exception java.io.IOException.
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 * @exception com.ibm.dse.base.DSEObjectNotFoundException.
 */
public EndSessionClientOp(String anOperationName, String aParentContext) throws java.io.IOException, com.ibm.dse.base.DSEInvalidRequestException, com.ibm.dse.base.DSEObjectNotFoundException {
	super(anOperationName, aParentContext);
}
/**
 * EndSessionClientOp constructor.
 * @param anOperationName java.lang.String
 * @param aParentContext com.ibm.dse.base.Context
 * @exception java.io.IOException.
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 */
public EndSessionClientOp(String anOperationName, com.ibm.dse.base.Context aParentContext) throws java.io.IOException, com.ibm.dse.base.DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * Execute method. Just sends the operation.
 * @exception java.lang.Exception.
 */
public void execute() throws Exception {
	((CSClientService)getService("CSClient")).sendAndWait(this,40000);//$NON-NLS-1$
}
}
