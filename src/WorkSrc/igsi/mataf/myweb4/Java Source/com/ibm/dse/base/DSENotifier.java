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
 * The DSENotifier class provides the capability to its objects to send and process event notifications. 
 *
 * If a class has to send event notifications, it can be defined as a subclass of Notifier or it can 
 * implement the Notifier interface. The Notifier constructor registers the new Notifier instance to the 
 * EventsManager so it can manage the handlers, which are registering for local or remote events, in a
 * transparent way for the application. 
 * @copyright(c) Copyright IBM Corporation 1998.
 */
public class DSENotifier implements com.ibm.dse.base.Notifier {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 1998 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	protected String name = "";
	protected Hashtable handlersList = new Hashtable();
/**
 * This constructor creates a <I>DSENotifier</I> object. 
 *
 * The constructor registers the new DSENotifier with the EventManager so it can manage 
 * the handlers, which are registering for local or remote events, in a transparent way for 
 * the application. 
 */
public DSENotifier () {
	Vector vect = new Vector();
	handlersList.put("allEvents",vect);
	try
	{EventManager.addNotifier(this);
	}catch(DSEInvalidArgumentException e){
		if( Trace.doTrace(Constants.COMPID,Trace.Medium,Trace.Information) )
			Trace.trace(Constants.COMPID,Trace.Medium,Trace.Information,Settings.getTID(),e.toString());
	}	
}
/**
 * This constructor creates a DSENotifier with a name. 
 *
 * This constructor creates a DSENotifier with name <I>aName</I> and register this new instance 
 * with the EventManager so it can manage the handlers, which are registering for local or remote 
 * events, in a transparent way for the application. 
 */
public DSENotifier (String aName) {
	if (aName !=null) name = aName;
	Vector vect = new Vector();
	handlersList.put("allEvents",vect);
	try
	{EventManager.addNotifier(this);
	}catch(DSEInvalidArgumentException e){
		if( Trace.doTrace(Constants.COMPID,Trace.Medium,Trace.Information) )
			Trace.trace(Constants.COMPID,Trace.Medium,Trace.Information,Settings.getTID(),e.toString());
	}	
}
/**
 * This constructor creates a DSENotifier with a name. 
 *
 * This constructor creates a DSENotifier with name <I>aName</I> for a specific operation 
 * started in a workstation, which is identified by aTID. It registers this new instance to 
 * the EventManager so it can manage the handlers, which are registering for local or remote
 * events, in a transparent way for the application. 
 */
public DSENotifier (String aName, String aTID) {
	if (aName !=null) name = aName;
	Vector vect = new Vector();
	handlersList.put("allEvents",vect);
	try {
		EventManager.addNotifier(this,aTID);
	} catch(DSEInvalidArgumentException e){
		if( Trace.doTrace(Constants.COMPID,Trace.Medium,Trace.Information) )
			Trace.trace(Constants.COMPID,Trace.Medium,Trace.Information,Settings.getTID(),e.toString());
	}
	
}
/**
 * This constructor creates a DSENotifier with mustRegister. 
 * @param mustRegister boolean
 */
public DSENotifier ( boolean mustRegister) {
	if (mustRegister) {
		try {	
			EventManager.addNotifier(this);
		} catch(DSEInvalidArgumentException e){
			if( Trace.doTrace(Constants.COMPID,Trace.Medium,Trace.Information) )
				Trace.trace(Constants.COMPID,Trace.Medium,Trace.Information,Settings.getTID(),e.toString());
		}
	}
	Vector vect = new Vector();
	handlersList.put("allEvents",vect);
}
/**
 * Adds an <I>aHandler</I> to the chain of handlers for the specific instance of DSENotifier. 
 *
 * The handler is located in the first position of the chain so it is the first handler 
 * to receive the event when signaled. 
 *
 * The handler, which is added to the chain of handlers using this operation, is registered 
 * for the event <I>anEventName</I> to be signaled by the DSENotifier. If it is interested in any event signaled
 * by this notifier, then the event name must be set to <I>allEvents</I>.
 * This method generates a <I>DSEInvalidArgumentException</I> when <I>aHandler</I> or <I>anEventName</I> are null.
 */


 
public void addHandler(Handler aHandler, String anEventName) throws DSEInvalidArgumentException {

	System.out.println("addHandler : " + aHandler.toString() + "\tevent: " + anEventName);	
	if ((aHandler != null) && (anEventName !=null))
	{
		if (anEventName.equals("allEvents"))
		{ 
			if (handlersList.size()!=0)
			{
				Vector vect = null;
				for (Enumeration e = handlersList.elements() ; e.hasMoreElements() ; )
				{
					vect = (Vector) e.nextElement();
					vect.insertElementAt(aHandler,0);
				}
			}
			else
			{
				Vector vect = new Vector();
				vect.addElement(aHandler);
				handlersList.put(anEventName,vect);
			}		
		}
		else 	
		{
			if ( handlersList.containsKey(anEventName) )
			{
				((Vector) handlersList.get(anEventName)).insertElementAt(aHandler,0);
			}
			else
			{
				Vector vect=new Vector();
				Vector vect2 = (Vector) (handlersList.get("allEvents"));
				if (vect2 != null) 
				 {
					for (Enumeration e=vect2.elements();e.hasMoreElements();) {
						Object element=e.nextElement();
						if (!element.equals(aHandler)) vect.addElement(element);
					}	
				 } 	 			
				vect.insertElementAt(aHandler,0);
				handlersList.put(anEventName,vect);
			}	
		}
		// signal HandlerAddedEvent		
	}
	else
		throw new DSEInvalidArgumentException(DSEException.harmless,"nullValue","Trying to add a null handler or a handler to a null events name");	
}
/**
 * Gets the handlersList attribute value.
 */
public Hashtable getHandlersList() {
	return handlersList;
}
/**
 * Gets the name attribute value.
 */
public String getName() {
	return name;
}
/**
 * Removes <I>aHandler</I> from the chain of handlers for that specific DSENotifier instance if the 
 * event it is interested in matches <I>anEventName</I>.
 *
 * If <I>anEventName</I> is <i>allEvents</I> all entries in the list for that specific handler are removed.
 * This method can generate a:
 * 
 * 	  -  <I>DSEInvalidArgumentException</I> if <I>aHandler</I> or <I>anEventName</I> are null.
 * 	  -  <I>DSEHandlerNotFoundException</I> if <I>aHandler</I> or <I>anEventName</I> are not found in the handlersList.
 * 
 */
public void removeHandler(Handler aHandler, String anEventName) throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	
	if ((aHandler != null) && (anEventName !=null))
	{
		if (anEventName.equals("allEvents"))
		{ 
			if (handlersList.size()!=0)
			{
				Vector vect = null;
				int i=0;
				for (Enumeration e = handlersList.elements() ; e.hasMoreElements() ; )
				{
					vect = (Vector) e.nextElement();
					if (vect.contains(aHandler))
					{
						vect.removeElement(aHandler);
						i++;
					}	
				}
				if (i==0) throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the  DSENotifier handlersList");
				else  ;//// HandlerRemovedEvent generation
			}	
		}
		else 	
		{
			if ( handlersList.containsKey(anEventName) )
			{
				if (((Vector) handlersList.get(anEventName)).removeElement(aHandler))
				{
					///// HandlerRemovedEvent generation
				}
				else
				{
					throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNoteFound","Handler not found in the  DSENotifier handlersList");
				}		
			}
			else 
			{
				if (((Vector) (handlersList.get("allEvents"))!=null) && ((Vector) (handlersList.get("allEvents"))).contains(aHandler))
				{
					Vector vect = (Vector) ((Vector) (handlersList.get("allEvents"))).clone();
					vect.removeElement(aHandler);
					handlersList.put(anEventName, vect);
					//HandlerRemovedEvent generation
				}
				else
					throw new DSEHandlerNotFoundException(DSEException.harmless,"EventNameFound","EventName not found in the  DSENotifier handlersList");
			}	
				
		}
	}
	else
		throw new DSEInvalidArgumentException(DSEException.harmless,"nullValue","Trying to remove a null handler or a handler from a null events name");	
}
/**
 * Sets the notifier name to <I>aName</I>
 */
public void setName(String aName) {
	name = aName;
}
/**
 * The operation must call the dispatch method of the first handler registered for this event, 
 * proceed according to the defined event propagation criteria, and follow the chain of handlers.
 * <I>anEventName</I> event contains, as attributes, as much information as is required to process it. 
 * The information is held in the params hashtable. Required notifier interface implementors must 
 * define themselves as a source of a standard Java event (that awakes all the listeners waiting for it). 
 * The signalEvent method can then be overwritten to add the statement that fires the event in the 
 * standard Java way. Additional methods to add and remove listeners must also be implemented as defined
 * in the Java development guide.
 * @param anEvent DSEEventObject
 */
public void signalEvent(DSEEventObject anEvent) {


	Handler handler1, handler2;
	Vector vect;
	System.out.println("in DSENotifier.signalEvent");
	if ( handlersList.containsKey(anEvent.getName()) )
	{
		vect = (Vector) handlersList.get(anEvent.getName());
		System.out.println("got handlers list: " + vect.toString());
		for (int i=0; i < vect.size(); i++) 
		{
			handler1 = (Handler) vect.elementAt(i);
			handler2 = (Handler) handler1.dispatchEvent(anEvent);
			System.out.println("dispatching event on handler: " + handler2.toString());

			if (handler2==null) i= (vect.size()) + 1;
		}	
	}
	else
	{
		vect = (Vector) handlersList.get("allEvents");
		
		for (int i=0; i < vect.size(); i++) 
		{
			handler1 = (Handler) vect.elementAt(i);
			handler2 = (Handler) handler1.dispatchEvent(anEvent);
			System.out.println("allEvents dispatching event on handler: " + handler2.toString());
	
			if (handler2==null) i= (vect.size()) + 1;
		}	
	}	


}
/**
 * Generates, by default, the event named <I>anEventName</I> as a specific event.
 *
 * This means that the operation must call the dispatch method of the first handler 
 * registered for this event, proceed according to the defined event propagation criteria, 
 * and follow the chain of handlers. <I>anEvent</I> contains, as attributes, the name and the 
 * source of this event. Required notifier interface implementors must define themselves 
 * as a source of a standard Java event (that awakes all the listeners waiting for it). 
 * The signalEvent method can then be overwritten to add the statement that fires the event 
 * in the standard Java way. Additional methods to add and remove listeners must be implemented 
 * as defined in the Java development guide. 
 *
 * This method throws a <I>DSEInvalidArgumentException</I> generated by the DSEEventObject constructor. 
 * @param anEventName java.lang.String
 */
public void signalEvent(String anEventName) throws DSEInvalidArgumentException {


	
	DSEEventObject dseeo = new DSEEventObject(anEventName,this);
	
	signalEvent(dseeo);
	


}
/**
 * Generates, by default, the event named <I>anEventName</I> as a specific event.
 *
 * The operation must call the dispatch method of the first handler registered for 
 * this event, proceed according to the defined event propagation criteria, and follow
 * the chain of handlers. <I>anEventName</I> event contains, as attributes, as much 
 * information as is required to process it. The information is held in the <I>params</I>
 * hashtable. Required notifier interface implementors must define themselves as a source
 * of a standard Java event (that awakes all the listeners waiting for it). The signalEvent
 * method can then be overwritten to add the statement that fires the event in the standard
 * Java way. Additional methods to add and remove listeners must be implemented as defined 
 * in the Java development guide.
 *
 * This method throws a <I>DSEInvalidArgumentException</I> generated by the DSEEventObject constructor.
 */
public void signalEvent(String anEventName, Hashtable params) throws DSEInvalidArgumentException {


	
	DSEEventObject dseeo = new DSEEventObject(anEventName, this, params);
	
	signalEvent(dseeo);


}
/**
 * Provides a housekeeping process for the notifier instance. This is called either from 
 * the application or from the context when it is destroyed or unchained. Subclasses should 
 * overwrite this implementation to perform the actions required to release the resources 
 * acquired by the notifier. 
 */
public void terminate() throws DSEException {
	EventManager.getNotifierList().removeElement(this);
	return;
}
}
