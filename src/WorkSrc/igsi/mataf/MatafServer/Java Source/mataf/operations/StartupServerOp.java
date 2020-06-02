package mataf.operations;

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
import java.io.IOException;
import java.util.Arrays;

import mataf.logger.GLogger;
import mataf.services.reftables.RefTables;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInternalErrorException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;
import com.ibm.dse.services.jdbc.JournalService;
/**
 * This operation is invoked after initialization to create the
 * client workstation context in the server workstation. 
 * This is the server side.
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 */
public class StartupServerOp extends DSEServerOperation {
	/**
	 * This constructor creates a StartupServerOp object. 
	 */
	public StartupServerOp() {
		super();
	}
	/**
	 * This constructor creates a StartupServerOp object. 
	 * @param anOperationName java.lang.String
	 * @exception java.io.IOException.
	 */
	public StartupServerOp(String anOperationName) throws java.io.IOException {
		super(anOperationName);
	}
	/**
	 * This constructor creates a StartupServerOp object. 
	 * @param anOperationName java.lang.String
	 * @param aParentContext java.lang.String
	 * @exception java.io.IOException.
	 * @exception com.ibm.dse.base.DSEInvalidRequestException.
	 * @exception com.ibm.dse.base.DSEObjectNotFoundException.
	 */
	public StartupServerOp(String anOperationName, String aParentContext)
		throws
			java.io.IOException,
			DSEInvalidRequestException,
			DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
	}
	/**
	 * This constructor creates a StartupServerOp object. 
	 * @param anOperationName java.lang.String
	 * @param aParentContext com.ibm.dse.base.Context
	 * @exception java.io.IOException.
	 * @exception com.ibm.dse.base.DSEInvalidRequestException.
	 */
	public StartupServerOp(String anOperationName, Context aParentContext)
		throws java.io.IOException, DSEInvalidRequestException {
		super(anOperationName, aParentContext);
	}
	/**
	 * Creates the workstation context belonging to the client workstation.
	 * Following operations coming from the same client will be chained to the
	 * context created in this operation. Because this context will last for the
	 * complete session, it is the place to put data to be shared by some or all
	 * the operations.
	 * At the end of this process initializes the journal service.
	 *
	 * We are receiving in the operation context the following data:
	 *	<UL><LI>TID -> Client workstation Terminal IDentifier.
	 *	<LI>WKSContext -> Context to be created. This will be the generic workstation context 
	 *	defined in the XML context file (it must be unchained from any parent context).
	 *	<LI>WKSParentContext -> This is the context to which we have to chain the created
	 *	context. Usually it is the workstation type belonging to the workstation being connected.
	 * </UL>
	 *
	 * @exception java.io.Exception.
	 */
	public void execute() throws Exception {

		Context aContext, parentContext;		

		// Creates the context whose name is the value of the WKSContext data field.
		// This is the context that will be maintained during all the session.
		// All the operations coming from the same client will be, by default, chained to 
		// this context. This allows you to maintain data and services that can be shared by
		// all the operations coming from the same client.
		aContext = new Context((String) getValueAt("WKSContext")); //$NON-NLS-1$

		// In this sample we have decided to group clients according to their type (A, B, C, etc).
		// Context of clients belonging to the same type will be chained to a common parent context.
		// Now we check if the parent context for the workstation type we are dealing with already
		// exist or has to be created. The parent context for a given client type is created here 
		// when the first client of its type executes the startup operation (this method). 
		// The name of the parent context is the value of the WKSParentContext data field.  
		parentContext = Context.getContextNamed((String) getValueAt("WKSParentContext")); //$NON-NLS-1$
		if (parentContext == null)
			parentContext = (Context) Context.readObject((String) getValueAt("WKSParentContext")); //$NON-NLS-1$

		// The context created above is chained to its parent context.
		aContext.chainTo(parentContext);

		// Insert an entry in the sessions table. This is required for the internal behaviour of the
		// framework and indicates that, by default, any operation coming from the client we are dealing 
		// with has to chained to the context created above (aContext).
		Context.addSession(null, new Long(System.currentTimeMillis()), (String) getValueAt("sessionID"), (String) getValueAt("TID"), aContext); //$NON-NLS-2$//$NON-NLS-1$

		// Copy some values from the current operation context to the context that will exist during
		// all the session. This is because the current operation context will be destroyed at the end 
		// of this operation and we need these data for other operations.
		aContext.setValueAt("sessionID", (String) getValueAt("sessionID")); //$NON-NLS-2$//$NON-NLS-1$
		aContext.setValueAt("TID", (String) getValueAt("TID")); //$NON-NLS-2$//$NON-NLS-1$
		aContext.setValueAt("UserId", (String) getValueAt("UserId")); //$NON-NLS-2$//$NON-NLS-1$
	}
	
	/**
	 * The operation is not cacheable.
	 * @return boolean
	 */
	public boolean isCacheable() {
		return false;
	}
	/**
	 * Client/server mechanism invokes the operation from this method.
	 * @param aSession java.lang.String
	 * @exception java.io.Exception.
	 */
	public void runInSession(String aSession) throws Exception {
		setValueAt("sessionID", aSession); //$NON-NLS-1$
		execute();
	}
}
