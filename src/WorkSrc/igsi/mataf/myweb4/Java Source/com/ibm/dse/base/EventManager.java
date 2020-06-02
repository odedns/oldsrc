package com.ibm.dse.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 1998, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
 import com.ibm.dse.clientserver.CSClientService;
 import com.ibm.dse.clientserver.CSServerService;
 import com.ibm.dse.clientserver.CSNotificationListener;
 import com.ibm.dse.clientserver.CSNotificationEvent;
 import java.util.Enumeration;


/**
 * This class manages the flow of events between the notifiers (producers of the events) and the handlers (consumers of the events), both when the event 
 * is produced locally or remotelly in a server machine. To make this possible, the server and the client should have only one instance of the EventManager existing
 * at any given moment. 
 *
 * To make the EventManager more easily addressable, most of its interface is based on static methods.
 * @copyright(c) Copyright IBM Corporation 1998, 2003.
 */
public class EventManager implements CSNotificationListener{
	private static final java.lang.String COPYRIGHT = "(c) Copyright IBM Corporation 1998, 2003. ";//$NON-NLS-1$
	/**
	 * Keeps the component identification that will be used by the traces tool. 	
	 */
	public static final String COMPID = "#EVMG";
	protected Hashtable handlersList = new Hashtable(1);
	protected Hashtable remoteHandlersList = new Hashtable(1);
	protected Vector exceptionHandlerList = new Vector(1); 
	protected static Vector notifierList = new Vector(1);
	protected static Operation operation;
	protected static EventManager uniqueInstance;
	protected static Hashtable workstationList = new Hashtable(1);
	protected static Hashtable mirrorNotifiers = new Hashtable(1);
	protected static boolean isCSNotificationListener = false;
	
/**
 * Adds a handler for the specified event signaled by a notifier coming from 
 * any of the workstations the machine has a session with.
 * @param aHandler com.ibm.dse.base.Handler
 * @param anEventName java.lang.String
 * @param aNotifier java.lang.String
 */
private void addHandler(Handler aHandler, String anEventName, String aNotifier) throws DSEInvalidArgumentException{
	for (Enumeration e = getHandlerList().keys(); e.hasMoreElements(); )
		{ String otherWorkstation = (String) e.nextElement();
		  addHandler(aHandler,anEventName,aNotifier, otherWorkstation);
		} 
}
/**
 * Registers the event Handler to the Notifier, whether the notifier exists on this machine or not. 
 * @param aHandler com.ibm.dse.base.Handler - The Handler that wants to register for an event
 * @param anEventName java.lang.String - The name of the event the Handler is interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will signal the event identified as anEventName
 * @param aContext com.ibm.dse.base.Context - The context where the EventManager will look for the notifier aNotifierName
 */
public static void addHandler(Handler aHandler, String anEventName, String aNotifierName, Context aContext) throws DSEInvalidArgumentException{
 Notifier aNotifier = null;
 if (aContext == null)
 		throw new DSEInvalidArgumentException(DSEException.harmless,"InvalidArgument","aContext can not be null");
 aNotifier = (Notifier) aContext.tryGetService(aNotifierName);
 System.out.println("addHandler notifierName = " + aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) aContext.getService(aNotifierName);}
 catch (DSEObjectNotFoundException snfe) 
 		{
 			aNotifier = (Notifier) aContext.tryGetNotifier(aNotifierName);
 			try {
 				if (aNotifier == null)
 					aNotifier = (Notifier) aContext.getNotifier(aNotifierName);}
				catch (DSEObjectNotFoundException nnfe)              
	 		          {aNotifier = getNotifier(aNotifierName);}
 		 }	 
 if (aNotifier == null) {
 		try
 		{
	    getUniqueInstance().addHandler(aHandler,anEventName, aNotifierName);
	    if (!isCSNotificationListener) {		    
			CSClientService csclient;     
		    csclient=aContext.getCSClient();
		    if (csclient != null) 
		    {
			 csclient.addCSNotificationListener((CSNotificationListener)getUniqueInstance());		    
		     isCSNotificationListener=true;
		    }
		    else
		    {
			 CSServerService csserver;   
			 csserver=aContext.getCSServer();
			 if (csserver != null)
			 {
			 csserver.addCSNotificationListener((CSNotificationListener)getUniqueInstance());
			 isCSNotificationListener=true;
			 }
			 else
			 {
				if (Trace.doTrace(EventManager.COMPID,Trace.Low,Trace.Error)) 
		       {
			       String aMessage = "No client/server service available in the context.";
			       System.out.println(aMessage);
			       Trace.trace(EventManager.COMPID, Trace.Low, Trace.Error, null, aMessage);
		       }
		       throw new DSEInvalidArgumentException("DSEException.critical", "", "No client/server service available in the context.");
			 }
		    }  		     	  
	     }
 		}catch (Exception e)
 		{throw new DSEInvalidArgumentException(DSEException.harmless,"InvalidArgument","Notifier "+aNotifierName+" cannot be locally found. Exception trying to register the Event Manager as a listener of the CSNotificationEvent from the CSClient:"+e.getMessage());} 
 	}	
 else aNotifier.addHandler(aHandler,anEventName);
}
/**
 * Registers the event Handler to the Notifier if the notifier exists on the specified server. 
 * @param aHandler com.ibm.dse.base.Handler - The Handler that wants to register for an event
 * @param anEventName java.lang.String - The name of the event the Handler is interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will signal the event identified as anEventName
 * @param aContext com.ibm.dse.base.Context
 * @param aServerTID java.lang.String - The TID of the server where the event will be produced
 */
public static void addHandler(Handler aHandler, String anEventName, String aNotifierName, Context aContext, String aServerTID) throws DSEInvalidArgumentException {
	 getUniqueInstance().addHandler(aHandler, anEventName, aNotifierName, aServerTID);
	 if (!isCSNotificationListener) {
		   		    
			CSClientService csclient;     
		    csclient=aContext.getCSClient();
		    if (csclient != null) 
		    {
			 csclient.addCSNotificationListener((CSNotificationListener)getUniqueInstance());		    
		     isCSNotificationListener=true;
		    }
		    else
		    {
			 CSServerService csserver;   
			 csserver=aContext.getCSServer();
			 if (csserver != null)
			 {
			 csserver.addCSNotificationListener((CSNotificationListener)getUniqueInstance());
			 isCSNotificationListener=true;
			 }
			 else
			 {
				if (Trace.doTrace(EventManager.COMPID,Trace.Low,Trace.Error)) 
		       {
			       String aMessage = "No client/server service available in the context.";
			       Trace.trace(EventManager.COMPID, Trace.Low, Trace.Error, null, aMessage);
		       }
		       throw new DSEInvalidArgumentException("DSEException.critical", "", "No client/server service available in the context.");
			 }
		    } 	     	  	     
	    }
}
/**
 * Adds a handler for the specified event signaled by the specified notifier coming from the  
 * specified workstation.
 * @param aHandler com.ibm.dse.base.Handler
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aServerName java.lang.String - The terminal identification of the workstation that is going to signal the event the application is interested in. 
 */
private void addHandler(Handler aHandler, String anEventName, String aNotifierName,String aServerName) throws DSEInvalidArgumentException {
	 System.out.println("in EventManage.addHandler() handler =" + aHandler.toString()); 
	Hashtable aWorkstationHashtable = (Hashtable) getHandlerList().get(aServerName);
	if (aWorkstationHashtable == null)
			{ getHandlerList().put(aServerName, new Hashtable(1));
			  aWorkstationHashtable = (Hashtable) getHandlerList().get(aServerName);
			} 
	Notifier aMirrorNotifier = (Notifier) aWorkstationHashtable.get(aNotifierName);
	if (aMirrorNotifier == null)
			{ aWorkstationHashtable.put(aNotifierName, new MirrorNotifier(aNotifierName));
			  aMirrorNotifier = (Notifier) aWorkstationHashtable.get(aNotifierName);
			} 
	aMirrorNotifier.addHandler(aHandler, anEventName);
}
/**
 * Registers the client to the Notifier <I>aNotifierName</I> as interested in the specified event for the duration of the session.
 * This method is invoked during the execution of the EventManagerServerOperation operation in the server.
 * @param anEventName java.lang.String - The name of the event the client will be interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will produce the event <I>anEventName</I>
 * @param aSessionId java.lang.String - The identification of the session between client and server
 */
public static void addHandler(String anEventName, String aNotifierName, String aSessionId)throws DSEInvalidArgumentException, DSEInvalidRequestException, DSEObjectNotFoundException, DSEHandlerNotFoundException, DSEException {
 Notifier aNotifier = null;
 DSEException noNotifierException;
 CSServerService aCSServer = 	Context.getCSServer();

 System.out.println("in EventManage.addHandler()"); 
 aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).tryGetService(aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).getService(aNotifierName);}
 catch (DSEObjectNotFoundException snfe)
 		 {
 		 	aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).tryGetNotifier(aNotifierName);
 		 	try {
 		 		if (aNotifier == null)
	 		 		aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).getNotifier(aNotifierName);}
				catch (DSEObjectNotFoundException nnfe)              
	 		          {aNotifier = getNotifier(aNotifierName);}
 		 }	          
 if (aNotifier == null)
 		 { 

	 	   aNotifier = new MirrorNotifier(aNotifierName);
	 	   getUniqueInstance().addMirrorHandlerToMirrorNotifier(aNotifier,anEventName,aSessionId);
	     } 
 else getUniqueInstance().addMirrorHandler(aNotifier,anEventName,aSessionId);
 System.out.println("Notifier = " + aNotifier.toString());
}
/**
 * Registers the specified Handler for exceptions.
 * @param aHandler com.ibm.dse.base.Handler
 */
public static void addHandlerForException(Handler aHandler) throws DSEInvalidArgumentException {
	getUniqueInstance().addHandler(aHandler, "exception" , "exceptionNotifier");
}
/**
 * Registers the specified Handler for exceptions originating from the server identified by aServerTID.
 * @param aHandler com.ibm.dse.base.Handler
 * @param aServerTID java.lang.String - The TID of the server where the exception will be originated
 */
public static void addHandlerForException(Handler aHandler, String aServerTID) throws DSEInvalidArgumentException {
	getUniqueInstance().addHandler(aHandler, "exception" , "exceptionNotifier", aServerTID);
}
/**
 * Registers a Handler for exceptions originating from a server. This method is invoked during the execution of  
 * the EventManagerServerOperation operation in the server.
 * @param aSessionId java.lang.String - The identification of the session between the client that has the Handler and server
 */
protected static void addHandlerForException(String aSessionId) throws DSEInvalidRequestException, DSEObjectNotFoundException{
	getUniqueInstance().addMirrorHandlerForException(aSessionId);
}
/**
 * Adds a handler to a local notifier that will take care of sending the event 
 * to a remote workstation, connected to the client workstasion via the CSClientService instance passed as parameter.
 * This method must be only executed in a client workstation.
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param anEventName java.lang.String
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the connection between client and server
 */
private void addMirrorHandler(Notifier aNotifier, String anEventName, CSClientService aCSClient) throws DSEInvalidArgumentException {
	Hashtable aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aCSClient.getName());
	if (aWorkstationHashtable == null)
			{ getRemoteHandlerList().put(aCSClient.getName(), new Hashtable(1));
			  aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aCSClient.getName());
			} 
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());	
	if (aMirrorHandler == null)
			{ 	aWorkstationHashtable.put(aNotifier.getName(), new MirrorHandler(aCSClient, anEventName));
			  aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());
			} 
	else {aMirrorHandler.addEvent(anEventName);}
	aNotifier.addHandler(aMirrorHandler, anEventName);
}
/**
 * Adds the handler to a local notifier that will take care of sending the event 
 * to a remote workstation identified by the session identification passed as parameter.
 * This method must be only executed in a server workstation.
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param anEventName java.lang.String
 * @param aSessionId java.lang.String - The identification of the session between the client and the server
 */
private void addMirrorHandler(Notifier aNotifier, String anEventName, String aSessionId) throws DSEInvalidArgumentException,DSEInvalidRequestException,DSEObjectNotFoundException,DSEHandlerNotFoundException {
	String aClientWorkstation = Context.getTIDForSession(aSessionId);
   	Hashtable aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aClientWorkstation);
	if (aWorkstationHashtable == null)
			{ getRemoteHandlerList().put(aClientWorkstation, new Hashtable(1));
			  aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aClientWorkstation);
			} 
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());	
	if (aMirrorHandler == null)
			{ aWorkstationHashtable.put(aNotifier.getName(), new MirrorHandler(aSessionId, anEventName));
			  aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());
			} 
	else {
		   if (aMirrorHandler.getSessionId().equals(aSessionId))
		    
			   aMirrorHandler.addEvent(anEventName);
		   
		   else
		   {   aWorkstationHashtable.remove(aNotifier.getName());
			   try
			   {
		   	   aNotifier.removeHandler(aMirrorHandler, anEventName);
			   }catch(DSEHandlerNotFoundException e){
				  //Can only get here if the Notifier has been terminated because of session close.
			      //Do nothing; a new MirrorHandler will be created.	
			      }
		       aMirrorHandler =  new MirrorHandler(aSessionId, anEventName);  
		   	   aWorkstationHashtable.put(aNotifier.getName(),aMirrorHandler);  			   
		   }
	}
	aNotifier.addHandler(aMirrorHandler, anEventName);	
	}
/**
 * Adds a local handler for exceptions. It will take care of sending the exception to the remote workstation.
 * @param aSessionId java.lang.String
 */
private void addMirrorHandlerForException(String aSessionId) throws DSEInvalidRequestException, DSEObjectNotFoundException {
	getExceptionHandlerList().addElement(new MirrorHandler(aSessionId, "exception"));
}
/**
 * Adds a handler to a local mirror notifier that will take care of sending the event 
 * to a remote workstation identified by the session passed as parameter.
 * As soon as the notifier is instanciated in the server workstation, the Event Manager should
 * take care of adding the handlers waiting for the notifier events to the notifier handlers list.
 * This method must only be executed in a server workstation.
 * @param aNotifier com.ibm.dse.base.MirrorNotifier
 * @param anEventName java.lang.String
 * @param aSessionId java.lang.String  - The identification of the session between the client and the server
 */
private void addMirrorHandlerToMirrorNotifier(Notifier aNotifier, String anEventName, String aSessionId) throws DSEInvalidArgumentException,DSEInvalidRequestException,DSEObjectNotFoundException,DSEHandlerNotFoundException {
	String aClientWorkstation = Context.getTIDForSession(aSessionId);
   	Hashtable aWorkstationHashtable = (Hashtable) getMirrorNotifiers().get(aClientWorkstation);
	if (aWorkstationHashtable == null)
			{ getMirrorNotifiers().put(aClientWorkstation, new Hashtable(1));
			  aWorkstationHashtable = (Hashtable) getMirrorNotifiers().get(aClientWorkstation);
			} 
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());	
	if (aMirrorHandler == null)
			{ 	aWorkstationHashtable.put(aNotifier.getName(), new MirrorHandler(aSessionId, anEventName));
			  aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());
			} 
	else {
		   if (aMirrorHandler.getSessionId().equals(aSessionId))
		    
			   aMirrorHandler.addEvent(anEventName);
		   
		   else
		   {   aWorkstationHashtable.remove(aNotifier.getName());
			   try
			   {
		   	   aNotifier.removeHandler(aMirrorHandler, anEventName);
			   }catch(DSEHandlerNotFoundException e){
				  //Can only get here if the Notifier has been terminated because of session close.
			      //Do nothing; a new MirrorHandler will be created.	
			      }
		       aMirrorHandler =  new MirrorHandler(aSessionId, anEventName);  
		   	   aWorkstationHashtable.put(aNotifier.getName(),aMirrorHandler);  			   
		   };
	};
	aNotifier.addHandler(aMirrorHandler, anEventName);	
	}
/**
 * Adds a Notifier to the list of Notifiers. Checks if there are handlers already interested in events from this 
 * notifier. Each Notifier should run this method in its constructor DSENotifier(String aName). 
 * @param aNotifier com.ibm.dse.base.Notifier
 */
public synchronized static void addNotifier(Notifier aNotifier) throws DSEInvalidArgumentException{
	String aNotifierName = aNotifier.getName();
	if (aNotifierName.equals("")) return;
	else
	{
	for (Enumeration e = getNotifierList().elements(); e.hasMoreElements();)
		{ Notifier not = (Notifier) e.nextElement();
			if (not.getName().equals(aNotifierName))
				{ int notifierIndex = getNotifierList().indexOf(not);
				  getNotifierList().setElementAt(aNotifier,notifierIndex);
				  return;}
		}
	Hashtable mirrorNotifiersForAllWorkstations = (Hashtable)getMirrorNotifiers();
	if (mirrorNotifiersForAllWorkstations!= null)	
	  { Hashtable mirrorNotifiers;
		 for (Enumeration mhw = mirrorNotifiersForAllWorkstations.keys(); mhw.hasMoreElements();)
		{
		 mirrorNotifiers = (Hashtable)mirrorNotifiersForAllWorkstations.get(mhw.nextElement());	
		 MirrorHandler aMirrorHandler = (MirrorHandler)mirrorNotifiers.get(aNotifierName);
		 if (aMirrorHandler != null)
		 {
			for (Enumeration mhe = aMirrorHandler.getEventList().keys(); mhe.hasMoreElements();)
		      {
			      String event = (String) mhe.nextElement();			      	      
			      aNotifier.addHandler(aMirrorHandler,event);
			      
		       } 	 
	     }	  	
	   }
	  }		
	getNotifierList().addElement(aNotifier);	
	}
}
/**
 * Adds a Notifier to the list of Notifiers. Checks if there are handlers in the client identified by aTID already interested in events from this 
 * notifier. Each Notifier should run this method in its constructor DSENotifier(String aName, String aTID). 
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param aTID java.lang.String - The TID of the client, where there may be a Handler interested in an event produced by the notifier "aNotifier."
 */
public synchronized static void addNotifier(Notifier aNotifier, String aTID) throws DSEInvalidArgumentException{
	String aNotifierName = aNotifier.getName();
	if (aNotifierName.equals("")) return;
	else
	{
	for (Enumeration e = getNotifierList().elements(); e.hasMoreElements();)
		{ Notifier not = (Notifier) e.nextElement();
			if (not.getName().equals(aNotifierName))
				{ 
				  int notifierIndex = getNotifierList().indexOf(not);
				  getNotifierList().setElementAt(aNotifier,notifierIndex);
				 }
			else	  
			     {
				  getNotifierList().addElement(aNotifier);				  
			     } 
	     }
	//Check if there are handlers registered for events signaled by this notifier	
	Hashtable mirrorNotifiers = (Hashtable)getMirrorNotifiers().get(aTID);
	if (mirrorNotifiers	!= null)	
	  {
		MirrorHandler aMirrorHandler = (MirrorHandler)mirrorNotifiers.get(aNotifierName);
		if (aMirrorHandler != null)
		 {
			for (Enumeration mhe = aMirrorHandler.getEventList().keys(); mhe.hasMoreElements();)
		      {
			      String event = (String) mhe.nextElement();			      	      
			      aNotifier.addHandler(aMirrorHandler,event);
			      
		       } 	 
	     }	  	
	  }
	}  
}
/**
 * Adds the specified workstation to the workstationList. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aWorkstationTID java.lang.String
 */
protected static void addWorkstation(String anEventName,String aNotifierName,String aWorkstationTID) {
	Hashtable anEventDictionary = (Hashtable) getWorkstationList().get(anEventName);
	if (anEventDictionary == null)
			{ getWorkstationList().put(anEventName, new Hashtable(1));
			  anEventDictionary = (Hashtable) getWorkstationList().get(anEventName);
			} 
  Hashtable aNotifierDictionary = (Hashtable) anEventDictionary.get(aNotifierName);
  if (aNotifierDictionary == null)
  			{ anEventDictionary.put(aNotifierName, new Hashtable(1));
			  aNotifierDictionary.get(aNotifierName);
			} 
	if (aNotifierDictionary.get(aWorkstationTID) == null)
			{ aNotifierDictionary.put(aWorkstationTID, aWorkstationTID);}		
}
/**
 * Creates a collection with event name and notifier name.
 * @return com.ibm.dse.base.KeyedCollection
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 */
private static KeyedCollection createKeyedCollection(String anEventName, String aNotifierName) {
	KeyedCollection kc = new KeyedCollection();
	kc.setName("eventPair");
	DataField eventName = new DataField();
	eventName.setName("eventName");
	eventName.setValue(anEventName);
	kc.addElement(eventName);
	DataField notifierName = new DataField();
	notifierName.setName("notifierName");
	notifierName.setValue(aNotifierName);
	kc.addElement(notifierName);
	return kc;
}
/**
 * Creates the operation context of the EventManagerClientOperation with the list of events the client application will
 * be interested in (aList argument).
 * @return com.ibm.dse.base.Context
 * @param aList com.ibm.dse.base.Vector
 * @param aOperationType java.lang.String
 */
private static Context createOperationContext(Vector aList, String aOperationType) throws DSEInvalidRequestException{
	KeyedCollection kc = new KeyedCollection();
	IndexedCollection eventList = new IndexedCollection();
	eventList.setName("eventList");
	try 
		{for (Enumeration e = aList.elements(); e.hasMoreElements();)
			{ String anEventName = (String) e.nextElement();
		  		if (e.hasMoreElements())
		  			{ String aNotifierName = (String) e.nextElement();
		  				kc.addElement(createKeyedCollection(anEventName,aNotifierName));
		  			}	
		  		else 
		  			{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: (Vector parameter has invalid length)");
	  				}			
			}	
		}	
	catch (ClassCastException e)
			{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: (an element of the Vector is not a String)");
	  		}				
	DataField operationType = new DataField();
	operationType.setName("operationType");
	operationType.setValue(aOperationType);
	kc.addElement(operationType);
	return new Context("operationContext","operation",Context.currentContext(), kc);
}
/**
 * Creates the operation context of the EventManagerClientOperation, adding to the events list the event <I>anEventName</I>.
 * @return com.ibm.dse.base.Context
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aOperationType java.lang.String
 */
private static Context createOperationContext(String anEventName, String aNotifierName, String aOperationType) throws DSEInvalidRequestException{
	KeyedCollection kc = new KeyedCollection();
	IndexedCollection eventList = new IndexedCollection();
	eventList.setName("eventList");
	eventList.addElement(createKeyedCollection(anEventName,aNotifierName));
	kc.addElement(eventList);
	DataField operationType = new DataField();
	operationType.setName("operationType");
	operationType.setValue(aOperationType);
	kc.addElement(operationType);
	return new Context("operationContext","operation",Context.currentContext(), kc);
}
/**
 * Deregisters the client as no longer interested in certain events. This method is executed in the client to launch an operation that passes a list of events to the
 * EventManager in the server. As a result, the listed events are no longer sent to the client. 
 * @param aList com.ibm.dse.base.Vector
 */
public static void deregisterInterestInRemoteEvent(Vector aList) throws DSEInvalidRequestException,
																		java.io.IOException,
																	   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																	   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																	   com.ibm.dse.clientserver.DSECSTimeoutException,
																	   DSEObjectNotFoundException 
{
	deregisterInterestInRemoteEvent(aList,Context.getCSClient());
}
/**
 * Deregisters the specified client as no longer interested in certain events. This method is executed in the client to launch an operation that passes a list of events
 * to the EventManager in the server. As a result, the listed events are no longer sent to the specified client. 
 * @param aList com.ibm.dse.base.Vector
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session with a specific server machine
 */
public static void deregisterInterestInRemoteEvent(Vector aList,CSClientService aCSClient) throws DSEInvalidRequestException,
																								  java.io.IOException,
																								   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																								   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																								   com.ibm.dse.clientserver.DSECSTimeoutException,
																								   DSEObjectNotFoundException 
{
	try 
		{ EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext(aList,"removeHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}
	catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: Cannot create EventManagerClientOperation");
	  } 
}
/**
 * Deregisters the client as no longer interested in the event <I>anEventName</I> originating from the specified Notifier. This method is executed in the client to launch an operation that
 * passes the name of the event to the EventManager in the server. As a result, the event is no longer sent to the client. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 */
public static void deregisterInterestInRemoteEvent(String anEventName, String aNotifierName) throws DSEInvalidRequestException,
																									java.io.IOException,
																						            com.ibm.dse.clientserver.DSECSInvalidRequestException,
																								   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																								   com.ibm.dse.clientserver.DSECSTimeoutException,
																								   DSEObjectNotFoundException 
{
	deregisterInterestInRemoteEvent(anEventName,aNotifierName,Context.getCSClient());
}
/**
 * Deregisters the specified client as no longer interested in an event "anEventName" originating from the specified Notifier. This method is executed in the client to launch an
 * operation that passes the name of the event to the EventManager in the server. As a result, the event is no longer sent to the specified client. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService which keeps the session with a specific server machine
 */
public static void deregisterInterestInRemoteEvent(String anEventName, String aNotifierName,CSClientService aCSClient) throws DSEInvalidRequestException,
																															  java.io.IOException,
																														   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																														   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																														   com.ibm.dse.clientserver.DSECSTimeoutException,
																														   DSEObjectNotFoundException 
{
	try 
		{ EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext(anEventName, aNotifierName,"removeHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}
	catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: Cannot create EventManagerClientOperation");
	  } 
}
/**
 * Deregisters the client as no longer interested in server-side exceptions. This method is executed in the client to launch an operation that removes the client from
 * the list of clients interested in server exceptions. As a result, the server exceptions are no longer sent to the client. 
 */
public static void deregisterInterestInRemoteException() throws DSEInvalidRequestException,
																java.io.IOException,
															   com.ibm.dse.clientserver.DSECSInvalidRequestException,
															   com.ibm.dse.clientserver.DSECSRemoteOperationException,
															   com.ibm.dse.clientserver.DSECSTimeoutException,
															   DSEObjectNotFoundException 
{
	deregisterInterestInRemoteException(Context.getCSClient());
}
/**  
 * Deregisters the specified client as no longer interested in server-side exceptions. This method is executed in the client to launch an operation that removes the
 * client from the list of clients interested in server exceptions. As a result, the server exceptions are no longer sent to the client. 
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session with a specific server machine
 */
public static  void deregisterInterestInRemoteException(CSClientService aCSClient) throws DSEInvalidRequestException,
																						  java.io.IOException,
																					      com.ibm.dse.clientserver.DSECSInvalidRequestException,
																					      com.ibm.dse.clientserver.DSECSRemoteOperationException,
 																						  com.ibm.dse.clientserver.DSECSTimeoutException,
																					      DSEObjectNotFoundException 
{
	try 
		{ EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext("exception", "EventManager","removeExceptionHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}
	catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteException",
																		  "InvalidRequest: (can not create EventManagerClientOperation)");
	  } 
}
/**
 * Deregisters the server as no longer interested in an event originating from the specified client. As a result, the event is no longer sent to the server.
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aContext com.ibm.dse.base.Context
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session between client and server
 */
public static void deregisterServerInterestInEvent(String anEventName, String aNotifierName, Context aContext, CSClientService aCSClient) throws DSEInvalidArgumentException,DSEHandlerNotFoundException {
 Notifier aNotifier = null;
 aNotifier = (Notifier) aContext.tryGetService(aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) aContext.getService(aNotifierName);}
 catch (DSEObjectNotFoundException e)
 		 {aNotifier = getNotifier(aNotifierName);}
 if (aNotifier == null)
 		 throw new DSEInvalidArgumentException(DSEException.harmless,"InvalidArgument","Notifier not found in this workstation");
 else getUniqueInstance().removeMirrorHandler(aNotifier,anEventName, aCSClient);
}
/**
 * Gets the exception handlers list.
 * @return com.ibm.dse.base.Vector
 */
private Vector getExceptionHandlerList() {
	return exceptionHandlerList;
}
/**
 * Returns the handlersList attribute value.
 * @return com.ibm.dse.base.Hashtable
 */
private Hashtable getHandlerList() {
	return handlersList;
}
/**
 * Returns the MirrorNotifier named aNotifierName. 
 * @return com.ibm.dse.base.MirrorNotifier
 * @param aNotifierName java.lang.String
 */
protected static MirrorNotifier getMirrorNotifier(String aNotifierName) {
	for (Enumeration e = getMirrorNotifiers().elements(); e.hasMoreElements();)
		{ MirrorNotifier not = (MirrorNotifier) e.nextElement();
			if (not.getName().equals(aNotifierName))
				{ return not;}
		}
	return null;	
}
/**
 * Returns the mirrorNotifiers attribute value.
 * @return com.ibm.dse.base.Hashtable
 */
protected static Hashtable getMirrorNotifiers() {
	return mirrorNotifiers;
}
/**
 * Returns the Notifier named aNotifierName.
 * @return com.ibm.dse.base.Notifier
 * @param aNotifierName java.lang.String
 */
protected synchronized static Notifier getNotifier(String aNotifierName) {
	for (Enumeration e = getNotifierList().elements(); e.hasMoreElements();)
		{ Notifier not = (Notifier) e.nextElement();
			if (not.getName().equals(aNotifierName))
				{ return not;}
		}
	return null;	
}
/**
 * Returns the notifierList attribute value.
 * @return com.ibm.dse.base.Vector
 */
protected static Vector getNotifierList() {
	return notifierList;
}
/**
 * Returns the operation attribute value.
 * @return com.ibm.dse.base.Operation
 */
protected static Operation getOperation() {
	return operation;
}
/**
 * Returns the remoteHandlersList attribute value.
 * @return com.ibm.dse.base.Hashtable
 */
private Hashtable getRemoteHandlerList() {
	return remoteHandlersList;
}
/**
 * Returns the unique instance of EventManager running on the application environment.
 * @return com.ibm.dse.base.EventManager
 */
protected static EventManager getUniqueInstance() {
	if (uniqueInstance == null)
		{ uniqueInstance = new EventManager();
		  uniqueInstance.getHandlerList().put("default", new Hashtable(1));}
	return uniqueInstance;
}
/**
 * Returns the workstationList attribute value.
 * @return com.ibm.dse.base.Hashtable
 */
protected static Hashtable getWorkstationList() {
	return workstationList;
}
/**
 * Calls the dispatch methods of the registered Handlers based on the information included in the event <I>CSNotificationEvent</I>. The information should contain the
 * <I>DSEEventObject</I> signaled in the remote workstation. This method is called when a <I>CSNotificationEvent</I> is signaled by the client/server mechanism. 
 * @param event com.ibm.dse.clientserver.CSNotificationEvent
 */
public void handleCSNotificationEvent(CSNotificationEvent event) {
	String tid = ((DSEEventObject) event.getFiredEvent()).getTID();
	DSEEventObject aDSEEvent = (DSEEventObject) event.getFiredEvent();
	if (tid == null) tid = "default";
	Hashtable aWorkstationHashtable = (Hashtable) getHandlerList().get(tid);
	if (aWorkstationHashtable == null)
			{ aWorkstationHashtable = (Hashtable) getHandlerList().get("default");
			} 
	MirrorNotifier aMirrorNotifier = (MirrorNotifier) aWorkstationHashtable.get(aDSEEvent.getSourceName());
	if (aMirrorNotifier != null)
			{ 	aMirrorNotifier.signalEvent(aDSEEvent);
			} 
}
/**
 * Returns true if the EventManager has a Handler registered for the specified remote event. 
 * @return boolean
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aTID java.lang.String
 */
protected static boolean hasHandlersInterestedIn(String anEventName, String aNotifierName, String aTID) {
	Hashtable aWorkstationHashtable = (Hashtable) getUniqueInstance().getHandlerList().get(aTID);
	if (aWorkstationHashtable == null)
		 return false;
	Notifier aMirrorNotifier = (Notifier) aWorkstationHashtable.get(aNotifierName);
	if (aMirrorNotifier == null)
		 return false;
	if (aMirrorNotifier.getHandlersList().containsKey(anEventName))
		 { if (((Vector) aMirrorNotifier.getHandlersList().get(anEventName)).size() != 0)
		 			return true;
		  }
  return false;
}
/**
 * Registers the client as interested in certain events. This method is executed in the client to launch an operation that passes a list of events to the EventManager
 * in the server. As a result, the listed events are now sent to the client. 
 * @param aList com.ibm.dse.base.Vector
 */
public static void registerInterestInRemoteEvent(Vector aList)throws DSEInvalidRequestException,
																	 java.io.IOException,
																     com.ibm.dse.clientserver.DSECSInvalidRequestException,
																     com.ibm.dse.clientserver.DSECSRemoteOperationException,
																     com.ibm.dse.clientserver.DSECSTimeoutException,
 																     DSEObjectNotFoundException 
{
	registerInterestInRemoteEvent(aList,Context.getCSClient());
}
/**
 * Registers the specified client as interested in certain events. This method is executed in the client to launch an operation that passes a list of events to the
 * EventManager in the server. As a result, the listed events are now sent to the specified client. 
 * @param aList com.ibm.dse.base.Vector
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session between client and server
 */
public static void registerInterestInRemoteEvent(Vector aList,CSClientService aCSClient) throws DSEInvalidRequestException,
																								java.io.IOException,
																							   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																							   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																							   com.ibm.dse.clientserver.DSECSTimeoutException,
																							   DSEObjectNotFoundException 
{
	try 
		{ EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext(aList,"addHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}
	catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: Cannot create EventManagerClientOperation)");
	  } 
}
/**
 * Registers the client as interested in the event <I>anEventName</I> originating from the specified Notifier. This method is executed in the client to launch an operation that passes the
 * name of the event to the EventManager in the server. As a result, the event is now sent to the client. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 */
public static void registerInterestInRemoteEvent(String anEventName,String aNotifierName) throws DSEInvalidRequestException,
																								 java.io.IOException,
 																							     com.ibm.dse.clientserver.DSECSInvalidRequestException,
																							     com.ibm.dse.clientserver.DSECSRemoteOperationException,
																							     com.ibm.dse.clientserver.DSECSTimeoutException,
																								 DSEObjectNotFoundException 
{
	registerInterestInRemoteEvent(anEventName,aNotifierName,Context.getCSClient());
}
/**
 * Registers the specified client as interested in the event <I>anEventName</I> originating from the specified Notifier. This method is executed in the client to launch an operation that
 * passes the name of the event to the EventManager in the server. As a result, the event is now sent to the specified client. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session between client and server
 */
public static void registerInterestInRemoteEvent(String anEventName,String aNotifierName,CSClientService aCSClient) throws DSEInvalidRequestException,
																														   java.io.IOException,
																														   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																														   com.ibm.dse.clientserver.DSECSRemoteOperationException,
																														   com.ibm.dse.clientserver.DSECSTimeoutException,
																														   DSEObjectNotFoundException 
{
	try 
		{ 
			System.out.println("registerInteresInRemoteEvent: " + anEventName );
			EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext(anEventName, aNotifierName,"addHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteEvent",
																		  "InvalidRequest: Cannot create EventManagerClientOperation)");
	    }
}
/**
 * Registers the client as interested in server-side exceptions. This method is executed in the client to launch an operation that adds the client to the list of clients
 * interested in server exceptions. As a result, the server exceptions are now sent to the client. 
 */
public static void registerInterestInRemoteException() throws DSEInvalidRequestException,
															  java.io.IOException,
 														      com.ibm.dse.clientserver.DSECSInvalidRequestException,
 														      com.ibm.dse.clientserver.DSECSRemoteOperationException,
														      com.ibm.dse.clientserver.DSECSTimeoutException,
														      DSEObjectNotFoundException 
{
	registerInterestInRemoteException(Context.getCSClient());
}
/**
 * Registers the specified client as interested in server-side exceptions. This method is executed in the client to launch an operation that adds the client to the list
 * of clients interested in server exceptions. As a result, the server exceptions are now sent to the client. 
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session between client and server
 */
public static void registerInterestInRemoteException(CSClientService aCSClient)throws DSEInvalidRequestException,
																					  java.io.IOException,
																					   com.ibm.dse.clientserver.DSECSInvalidRequestException,
																					   com.ibm.dse.clientserver.DSECSRemoteOperationException,
	  																			       com.ibm.dse.clientserver.DSECSTimeoutException,
																					   DSEObjectNotFoundException 
{
	try 
		{ EventManagerClientOperation anOperation = new EventManagerClientOperation (createOperationContext("exception", "EventManager","addExceptionHandler"), aCSClient);
		  anOperation.execute();
		  anOperation.close();
		}
	catch (DSEInvalidRequestException e)
		{ throw new DSEInvalidRequestException(DSEException.harmless,
																		  "registerInterestInRemoteException",
																		  "InvalidRequest: Cannot create EventManagerClientOperation)");
	  } 
}
/**
 * Registers the server as interested in an event originating from the specified client. As a result, the event is now sent to the server. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aContext com.ibm.dse.base.Context - The context where the EventManager will look for the notifier "aNotifierName"
 * @param aCSClient com.ibm.dse.clientserver.CSClientService - The CSClientService instance which keeps the session between client and server 
 */
public static void registerServerInterestInEvent(String anEventName, String aNotifierName, Context aContext, CSClientService aCSClient)throws DSEInvalidArgumentException {
 Notifier aNotifier = null;
 aNotifier = (Notifier) aContext.tryGetService(aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) aContext.getService(aNotifierName);}
 catch (DSEObjectNotFoundException e)
 		 { aNotifier = getNotifier(aNotifierName);}
 if (aNotifier == null)
 		 throw new DSEInvalidArgumentException(DSEException.harmless,"InvalidArgument","Notifier not found in this workstation");
 else getUniqueInstance().addMirrorHandler(aNotifier,anEventName, aCSClient);
}
/**
 * Removes a handler for the specified event signaled by a notifier coming from 
 * any of the workstations the machines have sessions with.
 * @param aHandler com.ibm.dse.base.Handler
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 */
private void removeHandler(Handler aHandler, String anEventName, String aNotifierName) throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	for (Enumeration e = getHandlerList().keys(); e.hasMoreElements(); )
		{ String otherWorkstation = (String) e.nextElement();
		  try { removeHandler(aHandler,anEventName,aNotifierName, otherWorkstation);
			  } catch (DSEHandlerNotFoundException exception) {} 
		} 
}
/**
 * Deregisters the event Handler from the notifier, whether the Notifier exists on this machine or not. 
 * @param aHandler com.ibm.dse.base.Handler - The Handler that wants to deregister for an event
 * @param anEventName java.lang.String - The name of the event the Handler is no longer interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will signal the event identified as "anEventName"
 * @param aContext com.ibm.dse.base.Context - The context where the EventManager will look for the notifier "aNotifierName"
 */
public static void removeHandler(Handler aHandler, String anEventName,String aNotifierName, Context aContext) throws DSEInvalidArgumentException,DSEHandlerNotFoundException {
 Notifier aNotifier = null;
 aNotifier = (Notifier) aContext.tryGetService(aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) aContext.getService(aNotifierName);}
 catch (DSEObjectNotFoundException e) 
 		{
 			aNotifier = (Notifier) aContext.tryGetNotifier(aNotifierName); 			
 			try {
 				if (aNotifier == null)
 					aNotifier = (Notifier) aContext.getNotifier(aNotifierName);}
				catch (DSEObjectNotFoundException nnfe)              
	 		          {aNotifier = getNotifier(aNotifierName);}
 		 }	 
 if (aNotifier == null) {
 		getUniqueInstance().removeHandler(aHandler,anEventName, aNotifierName);
 		if (getUniqueInstance().getHandlerList().isEmpty())
 		{
 		aContext.getCSClient().removeCSNotificationListener((CSNotificationListener)getUniqueInstance());
 		isCSNotificationListener=false;
 		}
 	}	
 else aNotifier.removeHandler(aHandler,anEventName);
}
/**
 * Deregisters the event Handler from the notifier if the Notifier exists on the specified server. 
 * @param aHandler com.ibm.dse.base.Handler - The Handler that wants to deregister for an event
 * @param anEventName java.lang.String - The name of the event the Handler is no more interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will signal the event identified as anEventName
 * @param aContext com.ibm.dse.base.Context
 * @param aTID java.lang.String - The TID of the server
 */
public static void removeHandler(Handler aHandler, String anEventName,String aNotifierName, Context aContext, String aTID) throws DSEHandlerNotFoundException,DSEInvalidArgumentException {
 		getUniqueInstance().removeHandler(aHandler,anEventName, aNotifierName, aTID);
}
/**
 * Removes a handler for the specified event signaled by the specified notifier coming from the  
 * specified workstation.
 * @param aHandler com.ibm.dse.base.Handler
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aServerName java.lang.String - The terminal identification of the workstation that is going to signal the event the application was interested in  
 */
private void removeHandler(Handler aHandler, String anEventName, String aNotifierName, String aServerName) throws DSEInvalidArgumentException, DSEHandlerNotFoundException{
	Hashtable aWorkstationHashtable = (Hashtable) getHandlerList().get(aServerName);
	if (aWorkstationHashtable == null)
			{ getHandlerList().put(aServerName, ((Hashtable) getHandlerList().get("default")).clone());
			  aWorkstationHashtable = (Hashtable) getHandlerList().get(aServerName);
			} 
	Notifier aMirrorNotifier = (Notifier) aWorkstationHashtable.get(aNotifierName);
	if (aMirrorNotifier == null)
			{ throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the  Event Manager handlersList");} 
	aMirrorNotifier.removeHandler(aHandler, anEventName);
}
/**
 * Deregisters the client to the Notifier "aNotifierName" as interested in the specified event for the duration of the session. 
 * This method is invoked during the execution of the EventManagerServerOperation operation in the server.
 * @param anEventName java.lang.String - The name of the event the client is no longer interested in
 * @param aNotifierName java.lang.String - The name of the Notifier that will produce the event "anEventName"
 * @param aSessionId java.lang.String - The identification of the session between client and server
 */
public static void removeHandler(String anEventName, String aNotifierName, String aSessionId) throws DSEHandlerNotFoundException,DSEInvalidArgumentException, DSEInvalidRequestException, DSEObjectNotFoundException {
 Notifier aNotifier = null;
 aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).tryGetService(aNotifierName);
 try {
 	if (aNotifier == null)
 		aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).getService(aNotifierName);}
 catch (DSEObjectNotFoundException e)
 		{
 			aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).tryGetNotifier(aNotifierName);
 			try {
 				if (aNotifier == null)
 					aNotifier = (Notifier) Context.getCurrentContextForSession(aSessionId).getNotifier(aNotifierName);}
				catch (DSEObjectNotFoundException nnfe)              
	 		          {aNotifier = getNotifier(aNotifierName);}
 		 }	 
 if (aNotifier == null)
 		 { //Although the notifier has been terminated, it can be a mirror notifier instanciated for that handler   
			 try
			 { getUniqueInstance().removeMirrorHandler(aNotifierName,anEventName, Context.getTIDForSession(aSessionId));
			 }catch(DSEHandlerNotFoundException dsehnf){	 		 
	 		 throw new DSEInvalidArgumentException(DSEException.harmless,"InvalidArgument","No registration for this notifier events have been previously done");}	 	
 		 }   
 else getUniqueInstance().removeMirrorHandler(aNotifier,anEventName, Context.getTIDForSession(aSessionId));
}
/**
 * Deregisters the specified Handler for exceptions.
 * @param aHandler com.ibm.dse.base.Handler
 */
public static void removeHandlerForException(Handler aHandler) throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	getUniqueInstance().removeHandler(aHandler, "exception" , "exceptionNotifier");
}
/**
 * Deregisters the specified Handler for exceptions originating from the server. This method is invoked during the execution of  
 * the "EventManagerServerOperation" operation in the server.
 * @param aSessionId java.lang.String - The identification of the session between the client that has the Handler and server 
 */
protected static void removeHandlerForException(String aSessionId) throws DSEInvalidRequestException, DSEObjectNotFoundException{
	getUniqueInstance().removeMirrorHandlerForException(aSessionId);
}
/**
 * Removes the handler from the local notifier that is taking care of sending the event 
 * to a remote workstation, connected to the client workstation via the CSClientService instance passed as a parameter.
 * This method must be only executed in a client workstation.
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param anEventName java.lang.String
 * @param aCSClient com.ibm.dse.clientserver.CSClientService
 */
private void removeMirrorHandler(Notifier aNotifier, String anEventName, CSClientService aCSClient) throws DSEInvalidArgumentException,DSEHandlerNotFoundException {
	Hashtable aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aCSClient.getName());
	if (aWorkstationHashtable == null)
			{  aWorkstationHashtable = (Hashtable) getMirrorNotifiers().get(aCSClient.getName());
			   if (aWorkstationHashtable == null) 	 
				throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");
			}	
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());	
	if (aMirrorHandler == null)
			{ throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");} 
	aNotifier.removeHandler(aMirrorHandler, anEventName);
	aMirrorHandler.removeEvent(anEventName);
	if (!aMirrorHandler.hasEvents())
			{ aWorkstationHashtable.remove(aNotifier.getName());}
}
/**
 * Removes the handler from the local notifier that is taking care of sending the event 
 * to a remote workstation identified by the TID passed as a parameter.
 * This method must only be executed in a server workstation.
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param anEventName java.lang.String
 * @param aTID java.lang.String
 */
private void removeMirrorHandler(Notifier aNotifier, String anEventName, String aTID) throws DSEInvalidArgumentException,DSEHandlerNotFoundException {
	Hashtable aWorkstationHashtable = (Hashtable) getRemoteHandlerList().get(aTID);
	if (aWorkstationHashtable == null)
			{  aWorkstationHashtable = (Hashtable) getMirrorNotifiers().get(aTID);
			   if (aWorkstationHashtable == null) 	 
				throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");
			} 
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifier.getName());	
	if (aMirrorHandler == null)
			{ throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");} 
	aNotifier.removeHandler(aMirrorHandler, anEventName);
	aMirrorHandler.removeEvent(anEventName);
	if (!aMirrorHandler.hasEvents())
			{ aWorkstationHashtable.remove(aNotifier.getName());}
}
/**
 * Removes the handler from the local mirror notifier, that has been instanciated to keep information about the  
 * handlers from a remote workstation, waiting for events to be signaled by a notifier not yet instanciated in the 
 * server. This method must only be executed in a server workstation.
 * @param aNotifier com.ibm.dse.base.Notifier
 * @param anEventName java.lang.String
 * @param aTID java.lang.String
 */
private void removeMirrorHandler(String aNotifierName, String anEventName, String aTID) throws DSEInvalidArgumentException,DSEHandlerNotFoundException {
	Hashtable aWorkstationHashtable = (Hashtable) getMirrorNotifiers().get(aTID);
	if (aWorkstationHashtable == null)
		throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");
			 
	MirrorHandler aMirrorHandler = (MirrorHandler) aWorkstationHashtable.get(aNotifierName);	
	if (aMirrorHandler == null)
			{ throw new DSEHandlerNotFoundException(DSEException.harmless,"HandlerNotFound","Handler not found in the Event Manager handlers list");} 
	aMirrorHandler.removeEvent(anEventName);
	if (!aMirrorHandler.hasEvents())
			{ aWorkstationHashtable.remove(aNotifierName);}
}
/**
 * Removes a local handler for exceptions. The exceptions will no longer be sent to that handler.
 * @param aSessionId java.lang.String
 */
private void  removeMirrorHandlerForException(String aSessionId)throws DSEInvalidRequestException, DSEObjectNotFoundException {
	String aTID = Context.getTIDForSession(aSessionId);
	MirrorHandler handlerToRemove = null;
	for (Enumeration e = getExceptionHandlerList().elements(); e.hasMoreElements(); )
		{ MirrorHandler aHandler = (MirrorHandler) e.nextElement();
			if (aHandler.getTID().equals(aTID))
				{handlerToRemove = aHandler;}
		}
	if (handlerToRemove != null)
		{getExceptionHandlerList().removeElement(handlerToRemove);}
}
/**
 * Deregisters the specified Handler for exceptions originating from the server identified by <I>aServerTID</I>. 
 * @param aHandler com.ibm.dse.base.Handler
 * @param aServerTID java.lang.String - The TID of the server where the exception will be originated
 */
public static void removesHandlerForException(Handler aHandler, String aServerTID) throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	getUniqueInstance().removeHandler(aHandler, "exception" , "exceptionNotifier", aServerTID);
}
/**
 * Removes the specifed workstation from the workstationList. 
 * @param anEventName java.lang.String
 * @param aNotifierName java.lang.String
 * @param aClientWorkstation java.lang.String
 */
protected static void removeWorkstation(String anEventName, String aNotifierName, String aClientWorkstation) {
	Hashtable anEventDictionary = (Hashtable) getWorkstationList().get(anEventName);
	if (anEventDictionary != null)
		{  Hashtable aNotifierDictionary = (Hashtable) anEventDictionary.get(aNotifierName);
  			 if (aNotifierDictionary != null)
			 		{ aNotifierDictionary.remove(aClientWorkstation);
			 		}		
		}
}
/**
 * Initializes this class. 
 */
public synchronized static void reset() {
	notifierList = new Vector(1);
	setOperation(null);
	setUniqueInstance(null);
	workstationList = new Hashtable(1);
}
/**
 * Sends an exception.
 * @param anException com.ibm.dse.base.DSEException
 */
public static void sendException(DSEException anException) {
	CSServerService aCSServer =	Context.getCSServer();
	
	DSEExceptionEvent anEvent = new DSEExceptionEvent(anException);
	for (Enumeration e = getUniqueInstance().getExceptionHandlerList().elements(); e.hasMoreElements();)
		{ MirrorHandler aHandler = (MirrorHandler) e.nextElement();
		  aCSServer.sendEvent(aHandler.getTID(),anEvent);
		}	
}
/**
 * Sets the value of operation.
 * @param anOperation com.ibm.dse.base.Operation
 */
protected static void setOperation(Operation anOperation) {
	operation = anOperation;
}
/**
 * Sets the <I>uniqueInstance</I> attribute value. This should be the only instance of <I>EventManager</I> in the application environment (either in a client or in a server). 
 * @param anEventManager com.ibm.dse.base.EventManager
 */
protected static void setUniqueInstance(EventManager anEventManager) {
	uniqueInstance = anEventManager;
}
}
