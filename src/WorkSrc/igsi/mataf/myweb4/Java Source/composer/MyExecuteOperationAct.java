package composer;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.*;
import com.ibm.dse.automaton.*;

/**
 * This class provides the behavior for a sample DSEAction to create and execute
 * operations from the automaton. 
 * @copyright(c) Copyright IBM Corporation 2000.
 */

public class MyExecuteOperationAct extends com.ibm.dse.automaton.DSEAction implements OperationRepliedListener {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	protected com.ibm.dse.base.Semaphore operationRepliedSemaphore = new Semaphore();
	protected String exitEventName = null;
	public final String OK = "ok";
	public final String ERROR = "error";
	public final String DSEEXCEPTION = "dse_Exception";
	/**
	 * Name of the parameter in which the operation stores the event name
	 * that the automaton uses to go on with the flow.
	 * This parameter is passed within the operationRepliedEvent.
	 */
	public final static String EXIT_EVENT_NAME = "dse_exitEventName";
/**
 * Creates an object of type ExecuteOperationAct.
 */
public MyExecuteOperationAct() {
	super();
}
/**
 * Creates an object of type ExecuteOperationAct.
 * @param aName java.lang.String
 */
public MyExecuteOperationAct(String aName) {
	super(aName);
}
/**
 * Creates an object of type ExecuteOperationAct.
 * @param aName java.lang.String
 * @param aTID java.lang.String
 */
public MyExecuteOperationAct(String aName, String aTID) {
	super(aName, aTID);
}
/**
 * Creates an object of type ExecuteOperationAct.
 * @param mustRegister boolean
 */
public MyExecuteOperationAct(boolean mustRegister) {
	super(mustRegister);
}
/**
 * Verifies whether the operation has a context. If it does not, this method assigns the context 
 * identified the linkContextTo property. If the operation already has a context, this method chains 
 * the operation context to the linkContextTo context. If the method assigns the context, this method returns true. 
 *
 * @return boolean - True if the context is assigned
 * @param thisOp com.ibm.dse.base.Operation
 * @param contextName java.lang.String
 */
public boolean chainOrAssignContext(Operation thisOp, Context actionContext, String contextName)
{
	boolean assignedContext = true;	
	try
	{
		//if operation already has a context, then chain it to the processor context
		if (thisOp.getContext() !=null)
		{
			assignedContext = false;
			(thisOp.getContext()).chainTo(getProcessor().getContext());		
		}		
		// try to get the action context and assign it to the operation
	 	// otherwise assign the processor's context to the operation
		else
		{
			if (actionContext != null)
				thisOp.setContext(actionContext);
			else 
				thisOp.setContext(getProcessor().getContext());
		}
	}
	catch (DSEInvalidRequestException ire)
	{
		if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Debug)) 
			Trace.trace(Processor.COMPID,Trace.High,Trace.Debug,com.ibm.dse.base.Settings.getTID(), "Problem assigning or chaining the action for: " + thisOp);
	}
	return assignedContext;
}
/**
 * Instantiates and executes the operation indicated by the parameter name:<ol>
 *	<li>Creates the instance of the operation, using the operation externalizer</li>
 *	<li>Chains or assigns the operation context</li>
 *	<li>Copies the data from the action context to the operation context</li>
 *	<li>Executes the operation </li>
 *	<li>Waits for the "Operation Replied Event" from the operation, and then continues</li>
 *	<li>Copies data back from the operation context to the processor context</li>
 *	<li>Closes the operation and unchains its context if it was not assigned (which implies it was chained instead)</li>
 *      <li>Refreshes the active panel in the context's navigation controller IF the value for the 
 *			refreshActiveView attribute is true</li>
 * 	<li>Signals an "ok" event of completion if all of the above were executed without exception,
 *			otherwise an "error" event is fired</li></ol>
 *
 */
public void execute() throws Exception
{
	String contextName = null;
	Context processorContext = null;
	Context actionContext = null;
	Context opContext = null;
	Operation thisOp = null;
	boolean assignedContext = true;

	System.out.println("MyExecuteOperationAct.execute()");	
	try
	{
		try
		{
			thisOp = getOperation();
			actionContext = this.getContext();
		//	System.out.println("ctx = " + actionContext.toString());
		}
		catch(Exception e)
		{
			if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Debug)) 
				Trace.trace(Processor.COMPID,Trace.High,Trace.Debug,com.ibm.dse.base.Settings.getTID(), "Can not get a valid operation instance. Exception:"+e);
				
			e.printStackTrace();
			throw e;
		}
		
		if ((getParms().get("linkContextTo"))!= null)
			contextName=(String)getParms().get("linkContextTo");
		
		assignedContext=chainOrAssignContext(thisOp, actionContext, contextName);
		mapTheInput(actionContext, thisOp.getContext());
			
		thisOp.addOperationRepliedListener(this);

		try
		{
			thisOp.execute();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Debug)) 
			Trace.trace(Processor.COMPID,Trace.High,Trace.Debug,com.ibm.dse.base.Settings.getTID(),
			"An exception has been fired executing the operation "+thisOp+". Error: "+e+". Access the error information through the event parameter dse_Exception.");

			// Add the exception information to the event that is going to be raised
			Hashtable ht = new Hashtable();
			ht.put(DSEEXCEPTION, e);

			// Signal the error event passing to it the exception information
			signalEvent("error", ht);

			return;
		}
			
		//wait for OpReplied event
		operationRepliedSemaphore.waitOn();
		thisOp.removeOperationRepliedListener(this);

		processorContext= this.getProcessor().getContext();
		
		mapTheOutput(thisOp.getContext(), processorContext);

		signalEvent(exitEventName);
		System.out.println("execute: exitEventName = " + exitEventName);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		// Add the exception information to the event that is going to be raised
		Hashtable ht = new Hashtable();
		ht.put(DSEEXCEPTION, e);

		// Signal the error event passing to it the exception information
		signalEvent("error", ht);
		return;
	}
	finally
	{
		try
		{
			if (!assignedContext)
			{
				// If context is not assigned don't want to close because it would unchain the context,
				// and context not assigned means that the operation context is the processor context.
				thisOp.close();
			}
			else 
				thisOp.setContext(null);
		}
		catch (Exception e)
		{
			if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Error))
			Trace.trace(Processor.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(),
				"Unable to unchain the action context, this might cause further execution problems. Action:"+this.getName());
		}

	}
}
/**
 * Returns an operation instance obtained from the action's externalized definition.
 * 
 * @return com.ibm.dse.base.Operation
 */
protected Operation getOperation() throws Exception
{
	if (getOperationTag()==null)
	{
		StringBuffer message = new StringBuffer();
		message.append("The Action: ").append(getName()).append(" does not have an attribute named 'operationName', the operation to execute has not been defined.");
		if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Debug)) 
			Trace.trace(Processor.COMPID,Trace.High,Trace.Debug,com.ibm.dse.base.Settings.getTID(), message.toString());
		throw new DSEInvalidArgumentException(DSEException.critical, "NOOP", message.toString());
	}
	return (Operation) DSEOperation.getExternalizer().convertTagToObject(getOperationTag());
}
/**
 * Signals the semaphore when the state's activate
 * method is waiting for the operation end.
 * This method must get the name of the event that will 
 * be used to change state from the event received.
 * 
 * @param event com.ibm.dse.base.OperationRepliedEvent 
 */
public void handleOperationRepliedEvent(OperationRepliedEvent event)
{
	System.out.println("in handleOperationReplieEvent event");
	if (event.getParameters() == null)
	{
		exitEventName = OK;
	}
	else 
	{
		exitEventName = (String) event.getParameters().get(EXIT_EVENT_NAME);
		System.out.println("exitEventName = " + exitEventName);
		
	
		if ( exitEventName == null )
		{
			exitEventName = OK;
			String message = "The event received from the operation does not have a valid exitEventName. Set exitEventName to 'ok'.";
			if (Trace.doTrace(Processor.COMPID,Trace.High,Trace.Error))
			 	Trace.trace(Processor.COMPID,Trace.High,Trace.Error,com.ibm.dse.base.Settings.getTID(), message);
		}
	}

	operationRepliedSemaphore.signalOn();
}
}
