package com.ibm.dse.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 1998
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
 import com.ibm.dse.clientserver.CSClientService;
import com.ibm.dse.clientserver.CSReplyEvent;
 import com.ibm.dse.clientserver.DSECSInvalidRequestException;
 import com.ibm.dse.clientserver.DSECSTimeoutException;
 import com.ibm.dse.clientserver.DSECSRemoteOperationException;
 import java.io.IOException;


/**
 * The client side EventManager class creates the EventManagerClientOperation when registering or deregistering remote interest in a specific event from a specific
 * Notifier. 
 *
 * The EventManagerClientOperation has an operation context with three parameters in its KeyedCollection: 
 * 
 *  - <I>anEventName</I> is the name of the event to be handled. The value could be <I>allEvents</I>. 
 *  - <I>aNotiferName</I> is the name of the notifier that will signal the event to be handled. 
 *  - <I>operationType</I> is the type of operation to be performed. The value is <I>addHandler</I> when adding a handler or <I>removeHandler</I> when removing a handler. 
 * 
 * As a CSReplyListener implementor, the EventManagerClientOperation implements the handleCSReplyEvent() method. 
 * 
 * @copyright (c) Copyright IBM Corporation 1998.
 */
public class EventManagerClientOperation extends DSEClientOperation implements com.ibm.dse.clientserver.CSReplyListener {
	private static final java.lang.String COPYRIGHT = "(c) Copyright IBM Corporation 1998. ";//$NON-NLS-1$
	/**
	* Keeps the CSClientService instance that holds the session between the client and the server
	*/
	protected CSClientService csClient;
/**
 * The constructor for the <I>EventManagerClientOperation</I>.
 *
 * It creates an instance of <I>EventManagerClientOperation</I> with <I>aContext</I> as the operation context. The execute() method of the <I>EventManagerClientOperation</I> instance will
 * call the send() method of the <I>CSClientService</I> instance passed as an argument, which will start the server operation execution. 
 */
public EventManagerClientOperation (Context aContext, com.ibm.dse.clientserver.CSClientService aCSClient ) throws java.io.IOException{
	super();
	setName("eventManagerClientOperation");
	setServerOperation("eventManagerServerOperation");
	setCSRequestFormat((FormatElement) FormatElement.readObject("EventManagerCSFormat"));
	setContext(aContext);
	csClient = aCSClient;
}
/**
 * Calls the send() method of the <I>CSClientService</I> instance. The send() method asynchonously executed the remote <I>EventManagerServerOperation</I> with the
 * <I>EventManagerClientOperation</I> instance as the parameter. 
 */
public void execute() throws DSECSInvalidRequestException, DSECSTimeoutException, DSECSRemoteOperationException {
	
	try {
		System.out.println("EventManagerClientOp.execute()");
		csClient.sendAndWait(this,40000);
	}catch(DSECSInvalidRequestException dsecsire){
		dsecsire.printStackTrace();
		throw dsecsire;		
	}catch(DSECSTimeoutException dsecste) {
		dsecste.printStackTrace();
		throw dsecste; 		
	}catch(DSECSRemoteOperationException dsecsroe){
			dsecsroe.printStackTrace();		
			throw dsecsroe; 
	}
}
/**
 * Handles the CSReplyEvent.
 * @param aCSReplyEvent com.ibm.dse.clientserver.CSReplyEvent - The event that indicates that the server operation execution is completed and the response is available in the client 
 */
public void handleCSReplyEvent(com.ibm.dse.clientserver.CSReplyEvent aCSReplyEvent){
	
	Integer aRequestId = aCSReplyEvent.getRequestId();
	try 
	{
		System.out.println("EventManagerClientOp.handleCSReplyEvent():" + aCSReplyEvent.toString());
		csClient.receive(getContext(), aRequestId, 10000);
	}catch (DSECSInvalidRequestException dsecsire) { 
	}catch (DSECSTimeoutException dsecste) { 
	}catch (DSECSRemoteOperationException dsecsroe){ 		
	}	 
}
}
