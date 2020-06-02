package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import java.io.IOException;

/**
 * The RetrieveSchemaInfoClientOperation is used to retrieve information
 * in a schema linked to a given journal service.
 *
 * @see	 com.ibm.dse.base.DSEServerOperation
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class RetrieveSchemaInfoClientOperation extends DSEClientOperation {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
/**
 * This constructor creates a RetrieveSchemaInfoClientOperation object.
 */
public RetrieveSchemaInfoClientOperation() {
	super();
}
/**
 * This constructor creates a RetrieveSchemaInfoClientOperation object.
 *
 * @param anOperationName String - an operation name
 * @exception IOException thrown when there is a problem accessing XML files
 * @see	java.io.IOException
 */
public RetrieveSchemaInfoClientOperation(String anOperationName) throws IOException {
	super(anOperationName);
}
/**
 * This constructor creates a RetrieveSchemaInfoClientOperation object.
 *
 * @param anOperationName String - an operation name
 * @param aParentContext String - a parent context name
 * @exception IOException thrown when there is a problem accessing XML files
 * @exception DSEInvalidRequestException thrown if the request is invalid
 * @exception DSEObjectNotFoundException thrown when the parent context is not found
 * @see	java.io.IOException
 * @see	com.ibm.dse.base.DSEInvalidRequestException
 * @see	com.ibm.dse.base.DSEObjectNotFoundException
 * @see	com.ibm.dse.base.Context
 *
 */
public RetrieveSchemaInfoClientOperation(String anOperationName, String aParentContext) throws IOException, DSEObjectNotFoundException, DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
/**
 * This constructor creates a RetrieveSchemaInfoClientOperation object.
 *
 * @param anOperationName String - an operation name
 * @param aParentContext Context - a parent context
 * @exception IOException thrown when there is a problem accessing XML files
 * @exception DSEInvalidRequestException thrown if the request is invalid
 * @see	java.io.IOException
 * @see	com.ibm.dse.base.DSEInvalidRequestException
 * @see	com.ibm.dse.base.Context
 *
 */
public RetrieveSchemaInfoClientOperation(String anOperationName, Context aParentContext) throws IOException, DSEInvalidRequestException {
	super(anOperationName, aParentContext);
}
}
