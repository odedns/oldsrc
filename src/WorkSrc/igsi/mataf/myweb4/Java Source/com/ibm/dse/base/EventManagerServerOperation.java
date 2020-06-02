package com.ibm.dse.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 1998
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.Enumeration;


/**
 * The client server mechanism creates the <I>EventManagerServerOperation</I> when registering or deregistering remote interest in a specific event from a specific Notifier.
 * The client server mechanism calls the operation <I>runInSession()</I> method to start the <I>EventManagerServerOperation</I> execution and when the execution ends it sends a <I>CSReplyEvent</I> to the client side.
 *
 * The <I>EventManagerServerOperation</I> has an operation context with three parameters in its KeyedCollection: 
 * 
 *  - <I>anEventName</I> is the name of the event to be handled. The value could be <I>allEvents</I>. 
 *  - <I>aNotiferName</I> is the name of the notifier that will signal the event to be handled. 
 *  - <I>operationType</I> is the type of operation to be performed. The value is <I>addHandler</I> when adding a handler or <I>removeHandler</I> when removing a handler. 
 * 
 * @copyright (c) Copyright IBM Corporation 1998.
 */
public class EventManagerServerOperation extends DSEServerOperation {
	private static final java.lang.String COPYRIGHT = "(c) Copyright IBM Corporation 1998. ";//$NON-NLS-1$
	/**
	* Keeps the identification of the session between the client and the server
	*/	
  protected String sessionId;
/**
 * This constructor creates an EventManagerServerOperation object. 
 */
public EventManagerServerOperation ( )  throws java.io.IOException, DSEInvalidRequestException {
	super();
	setCSReplyFormat((FormatElement) FormatElement.readObject("EventManagerCSFormat"));
	setContext(createOperationContext());
}
/**
 * Creates an empty keyed collection that will keep the events information.
 * @return com.ibm.dse.base.KeyedCollection 
 */
private static KeyedCollection createKeyedCollection()  {
	KeyedCollection kc = new KeyedCollection();
	kc.setName("eventPair");
	DataField eventName = new DataField();
	eventName.setName("eventName");
	kc.addElement(eventName);
	DataField notifierName = new DataField();
	notifierName.setName("notifierName");
	kc.addElement(notifierName);
	return kc;
}
/**
 * Creates and returns the context of the <I>EventManagerServerOperation</I> instance.
 *
 * This context will keep the data sent by the client <I>EventManagerClientOperation</I> instance.
 */
private static Context createOperationContext() throws DSEInvalidRequestException{
	KeyedCollection kc = new KeyedCollection();
	IndexedCollection eventList = new IndexedCollection();
	eventList.setName("eventList");
	eventList.addElement(createKeyedCollection());
	kc.addElement(eventList);
	DataField operationType = new DataField();
	operationType.setName("operationType");
	kc.addElement(operationType);
	return new Context("operationContext","operation", kc);
}
/**
 * Calls the EventManager addHandler() method using the parameters sent by the client. 
 */
public void execute() throws DSEHandlerNotFoundException, DSEException{


try 
{
	System.out.println("EventMnagerServerOperation.execute()");
	String operationType = ((String) getContext().getKeyedCollection().getValueAt("operationType"));
	if (operationType.equals("addHandler"))
		recursiveAddHandler();
	else
		if (operationType.equals("removeHandler"))
			recursiveRemoveHandler();
		else 
			if (operationType.equals("addExceptionHandler"))
				EventManager.addHandlerForException(sessionId);
			else
				EventManager.removeHandlerForException(sessionId);
}catch(DSEHandlerNotFoundException dsehnfe) {throw dsehnfe;}
 catch(DSEException dsee) {throw dsee;} 
	
	System.out.println("EventMnagerServerOperation.execute() - end");
}
/**
 * This method is an <I>Externalizable</I> interface method. It does not prevent the externalizer overwriting the attribute values already set by the class constructor.
 * @param aTag com.ibm.dse.base.Tag
 */
public Object initializeFrom(Tag aTag) throws java.io.IOException, DSEException{
	return this;
}
/**
 * Adds all the handlers in the eventList IndexedCollection data element that is defined in the operation context.
 */
private void recursiveAddHandler() throws DSEObjectNotFoundException, DSEInvalidArgumentException, DSEInvalidRequestException, DSEHandlerNotFoundException, DSEException{
	IndexedCollection aList = (IndexedCollection) getContext().getElementAt("eventList");
	System.out.println("EventMnagerServerOp.recursiveAddHandler()");
	for (Enumeration e = aList.getEnumeration(); e.hasMoreElements();)
		{	KeyedCollection kc = (KeyedCollection) e.nextElement();
			String eventName = ((String) kc.getValueAt("eventName"));
			String notifierName = ((String) kc.getValueAt("notifierName"));
			EventManager.addHandler(eventName, notifierName, sessionId);			
			System.out.println("adding evenName: " + eventName + "\tnotifier: " + notifierName);
		}	
}
/**
 * Removes all the handlers in the eventList IndexedCollection data element that is defined in the operation context.
 */
private void recursiveRemoveHandler() throws DSEHandlerNotFoundException, DSEObjectNotFoundException, DSEInvalidArgumentException, DSEInvalidRequestException{
	IndexedCollection aList = (IndexedCollection) getContext().getElementAt("eventList");
	for (Enumeration e = aList.getEnumeration(); e.hasMoreElements();)
		{	KeyedCollection kc = (KeyedCollection) e.nextElement();
			String eventName = ((String) kc.getValueAt("eventName"));
			String notifierName = ((String) kc.getValueAt("notifierName"));
			EventManager.removeHandler(eventName, notifierName, sessionId);
		}	
}
/**
 * Looks for the input session in the table of sessions (see the Context interface). Once it finds it, the method stores the identifier of the originating workstation
 * and calls the execute() method of the operation.
 * @param aSessionID java.lang.String - The identification of the session between the client and the server  
 */


public void runInSession(String aSessionID) throws DSEHandlerNotFoundException, DSEException{


	sessionId = aSessionID;
	try { setOriginWorkstation(Context.getTIDForSession(aSessionID));}
	catch (DSEException e) {throw e;};
	execute();
	return;
}
}
