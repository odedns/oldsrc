package com.ibm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.wsif.WSIFMessage;

import sun.security.x509.OtherName;

import com.ibm.APIExerciser;
import com.ibm.AuditDataResult;
import com.ibm.bpe.api.*;
import com.ibm.bpe.database.EventTemplate;


/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

public class Utility {
	public static final int PIE 		= 1;
	public static final int AIE 		= 2;	
	public static final int VAR 		= 3;	
	public static final int CLE 		= 4;	
	public static final int PTE 		= 5;		
	public static final int UNKNOWN 	= 6;			

public static String formatValue(String theValue)
{
	return (theValue==null ? "-" : "'"+theValue+"'");
}
public static String formatValue(Object theValue)
{
	return (theValue==null ? "-" : "'"+theValue.toString()+"'");
}

	
public static void printProcessInstanceDetails( BusinessProcess processEJB, ProcessInstanceData theInstance) throws Exception{
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' hh:mm:ss SSS");	
	APIExerciser.println_s("   PIID                      "+formatValue(theInstance.getID()) );	
	APIExerciser.println_s("   Instance name             "+formatValue(theInstance.getName()) );
	APIExerciser.println_s("   Display  name             "+formatValue(theInstance.getDisplayName()) );	
	APIExerciser.println_s("   Description               "+formatValue(theInstance.getDescription()) );		
	APIExerciser.println_s("   Template name             "+formatValue(theInstance.getProcessTemplateName()) );	
	APIExerciser.println_s("   Input message type name   "+formatValue(theInstance.getInputMessageTypeName()) );	
	APIExerciser.println_s("   Output message type name  "+formatValue(theInstance.getOutputMessageTypeName()) );	
	Iterator theFaultTermNames = processEJB.getFaultTerminalNames(theInstance.getID()).iterator();
	if (!theFaultTermNames.hasNext())
		APIExerciser.println_s("   No fault terminals          ");					
	else {
		APIExerciser.println_s("   Fault terminal(s):          ");							
		while(theFaultTermNames.hasNext())
		{
			String theName = (String) theFaultTermNames.next();
			APIExerciser.println_s("      '"+theName+"'");	
			ClientObjectWrapper theFaultMessage = processEJB.getFaultMessage(theInstance.getID(), theName);
			if (theFaultMessage.getObject()!= null)
				WSDLHelper.printMessage((WSIFMessage)theFaultMessage.getObject());
			
		}
	}
	
	APIExerciser.println_s("   Category                  "+formatValue(theInstance.getCategory()) );	
	int[] theAvailableActions = theInstance.getAvailableActions();
	APIExerciser.println_s("   Available actions ");		
	if (theAvailableActions != null)		
		for (int i = 0; i < theAvailableActions.length; i++) {
			APIExerciser.println_s("      '"+getProcessAction(theAvailableActions[i])+"'");				
		}
	APIExerciser.println_s("   Starter                   "+formatValue(theInstance.getStarter()));	
	APIExerciser.println_s("   Created                   "+formatValue((theInstance.getCreationTime()==null ? " - " : formatter.format(theInstance.getCreationTime().getTime())) ));
	APIExerciser.println_s("   Started                   "+formatValue((theInstance.getStartTime()==null ? " - " : formatter.format(theInstance.getStartTime().getTime())) ));	
	APIExerciser.println_s("   Last modfication          "+formatValue((theInstance.getLastModificationTime()==null ? " - " : formatter.format(theInstance.getLastModificationTime().getTime())) ));		
	APIExerciser.println_s("   Last state change         "+formatValue(theInstance.getLastStateChangeTime()==null ? " - " : formatter.format(theInstance.getLastStateChangeTime().getTime())) );		
	APIExerciser.println_s("   Completion                "+formatValue((theInstance.getCompletionTime()==null ? " - " : formatter.format(theInstance.getCompletionTime().getTime())) ));			
	APIExerciser.println_s("   Exeuction state           "+formatValue(getProcessState(theInstance.getExecutionState()) ));			
}	

public static void printActivityInstanceDetails( BusinessProcess processEJB, ActivityInstanceData theInstance) throws Exception {
	SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd 'at' hh:mm:ss SSS");
	APIExerciser.println_s("   AIID                      "+formatValue(theInstance.getID()) );	
	APIExerciser.println_s("   Exeuction state           "+formatValue(getActivityState(theInstance.getExecutionState())  ));			
	APIExerciser.println_s("   Instance name             "+formatValue(theInstance.getName()) );
	APIExerciser.println_s("   Display  name             "+formatValue(theInstance.getDisplayName()) );	
	APIExerciser.println_s("   Description               "+formatValue(theInstance.getDescription()) );		
	APIExerciser.println_s("   Input message type name   "+formatValue(theInstance.getInputMessageTypeName()) );	
	APIExerciser.println_s("   Output message type name  "+formatValue(theInstance.getOutputMessageTypeName()) );		
//	Iterator theFaultTermNames = theInstance.getFaultTerminalNames().iterator();
	Iterator theFaultTermNames = processEJB.getFaultTerminalNames(theInstance.getID()).iterator();	
	if (!theFaultTermNames.hasNext())
		APIExerciser.println_s("   No faults defined          ");					
	else {
		APIExerciser.println_s("   Fault terminal(s):          ");							
		while(theFaultTermNames.hasNext())
		{
			String theName = (String) theFaultTermNames.next();
			APIExerciser.println_s("      '"+theName+"'");	
			ClientObjectWrapper theFaultMessage = processEJB.getFaultMessage(theInstance.getID(), theName);
			if (theFaultMessage.getObject()!= null)
				WSDLHelper.printMessage((WSIFMessage)theFaultMessage.getObject());
			
		}
	}
	APIExerciser.println_s("   Kind                       "+formatValue(getActivityKind(theInstance.getKind())));			

	int[] theAvailableActions = theInstance.getAvailableActions();
	APIExerciser.println_s("   Available actions: ");	
	if (theAvailableActions != null)	
		for (int i = 0; i < theAvailableActions.length; i++) {
			APIExerciser.println_s("      '"+getActivityAction(theAvailableActions[i])+"'");				
		}
	APIExerciser.println_s("   Owner                     "+formatValue(theInstance.getOwner()));	
	APIExerciser.println_s("   Activation time           "+formatValue((theInstance.getActivationTime()==null ? " - " : formatter.format(theInstance.getActivationTime().getTime())) ));
	APIExerciser.println_s("   Started                   "+formatValue((theInstance.getStartTime()==null ? " - " : formatter.format(theInstance.getStartTime().getTime())) ));	
	APIExerciser.println_s("   Last modfication          "+formatValue((theInstance.getLastModificationTime()==null ? " - " : formatter.format(theInstance.getLastModificationTime().getTime())) ));		
	APIExerciser.println_s("   Last state change         "+formatValue((theInstance.getLastStateChangeTime()==null ? " - " : formatter.format(theInstance.getLastStateChangeTime().getTime())) ));		
	APIExerciser.println_s("   Completion                "+formatValue((theInstance.getCompletionTime()==null ? " - " : formatter.format(theInstance.getCompletionTime().getTime()))) );			
}

/**
 * Method getActivityKind.
 * @param i
 * @return String
 */
public static String getActivityKind(int theKind) {
	switch (theKind) {
		case ActivityInstanceData.KIND_ELEMENTAL :
			return "ELEMENTAL";
		case ActivityInstanceData.KIND_EMPTY :
			return "EMPTY";
		case ActivityInstanceData.KIND_EVENT:
			return "EVENT";
		case ActivityInstanceData.KIND_FAULT :
			return "FAULT";
		case ActivityInstanceData.KIND_PERSON :
			return "PERSON";
		case ActivityInstanceData.KIND_PROCESS_BLOCK :
			return "PROCESS_BLOCK";
		case ActivityInstanceData.KIND_PROCESS_SUBPROCESS :
			return "PROCESS_SUBPROCESS";
		case ActivityInstanceData.KIND_SINK :
			return "SINK";
		case ActivityInstanceData.KIND_SOURCE :
			return "SOURCE";
		default :
			return "UNKNOWN";
	}
}


/**
 * Method getActivityAction.
 * @param i
 * @return String
 */
private static String getActivityAction(int theAction) {
		switch (theAction) {
			case ActivityInstanceActions.CANCELCLAIM :
				return "CANCELCLAIM";
			case ActivityInstanceActions.CLAIM :
				return "CLAIM";
			case ActivityInstanceActions.COMPLETE :
				return "COMPLETE";
			case ActivityInstanceActions.FORCECOMPLETE :
				return "FORCECOMPLETE";
			case ActivityInstanceActions.FORCERETRY :
				return "FORCERETRY";
			case ActivityInstanceActions.GETACTIVITYINSTANCE :
				return "GETACTIVITYINSTANCE";
			case ActivityInstanceActions.GETCUSTOMATTRIBUTE :
				return "GETCUSTOMATTRIBUTE";
			case ActivityInstanceActions.GETEVENTOUTPUTMESSAGE :
				return "GETEVENTOUTPUTMESSAGE";
			case ActivityInstanceActions.GETFAULTMESSAGE :
				return "GETFAULTMESSAGE";
			case ActivityInstanceActions.GETINPUTMESSAGE :
				return "GETINPUTMESSAGE";
			case ActivityInstanceActions.GETOUTPUTMESSAGE :
				return "GETOUTPUTMESSAGE";
			case ActivityInstanceActions.GETUISETTINGS :
				return "GETUISETTINGS";
			case ActivityInstanceActions.GETUSERINPUT :
				return "GETUSERINPUT";
			case ActivityInstanceActions.SENDEVENT :
				return "SENDEVENT";
			case ActivityInstanceActions.SETCUSTOMATTRIBUTE :
				return "SETCUSTOMATTRIBUTE";
			case ActivityInstanceActions.SETEVENTOUTPUTMESSAGE :
				return "SETEVENTOUTPUTMESSAGE";
			case ActivityInstanceActions.SETFAULTMESSAGE :
				return "SETFAULTMESSAGE";
			case ActivityInstanceActions.SETOUTPUTMESSAGE :
				return "SETOUTPUTMESSAGE";
			case ActivityInstanceActions.SETUSERINPUT :
				return "SETUSERINPUT";				
				
			default :
				return "UNKNOWN";
		}
}
	


	public static String getProcessAction(int theAction) {
		switch (theAction) {
			case ProcessInstanceActions.DELETE :
				return "DELETE";
			case ProcessInstanceActions.FORCETERMINATE :
				return "FORCETERMINATE";
			case ProcessInstanceActions.GETCUSTOMATTRIBUTE :
				return "GETCUSTOMATTRIBUTE";
			case ProcessInstanceActions.GETFAULTMESSAGE :
				return "GETFAULTMESSAGE";
			case ProcessInstanceActions.GETINPUTMESSAGE :
				return "GETINPUTMESSAGE";
			case ProcessInstanceActions.GETOUTPUTMESSAGE :
				return "GETOUTPUTMESSAGE";
			case ProcessInstanceActions.GETPROCESSINSTANCE :
				return "GETPROCESSINSTANCE";
			case ProcessInstanceActions.GETUISETTINGS :
				return "GETUISETTINGS";
			case ProcessInstanceActions.GETVARIABLE :
				return "GETVARIABLE";
			case ProcessInstanceActions.SENDEVENT :
				return "SENDEVENT";
			case ProcessInstanceActions.SETCUSTOMATTRIBUTE:
				return "SETCUSTOMATTRIBUTE";
			default :
				return "UNKNOWN";
		}
	}
	public static String getProcessState(int theState) {
		switch (theState) {
			case ProcessInstanceData.STATE_READY :
				return "READY";
			case ProcessInstanceData.STATE_RUNNING :
				return "RUNNING";
			case ProcessInstanceData.STATE_FINISHED :
				return "FINISHED";
			case ProcessInstanceData.STATE_COMPENSATING :
				return "COMPENSATING";
			case ProcessInstanceData.STATE_FAILED :
				return "FAILED";
			case ProcessInstanceData.STATE_TERMINATED :
				return "TERMINATED";
			case ProcessInstanceData.STATE_COMPENSATED :
				return "COMPENSATED";
			case ProcessInstanceData.STATE_TERMINATING :
				return "TERMINATING";
			case ProcessInstanceData.STATE_FAILING :
				return "FAILING";
			default :
				return "UNKNOWN";
		}
	}
	public static String getActivityState(int theState) {
		switch (theState) {
			case ActivityInstanceData.STATE_READY :
				return "READY";
			case ActivityInstanceData.STATE_RUNNING :
				return "RUNNING";
			case ActivityInstanceData.STATE_FINISHED :
				return "FINISHED";
			case ActivityInstanceData.STATE_CLAIMED :
				return "CLAIMED";
			case ActivityInstanceData.STATE_FAILED :
				return "FAILED";
			case ActivityInstanceData.STATE_TERMINATED :
				return "TERMINATED";
			case ActivityInstanceData.STATE_EXPIRED :
				return "EXPIRED";
			case ActivityInstanceData.STATE_TERMINATING :
				return "TERMINATING";
			case ActivityInstanceData.STATE_FAILING :
				return "FAILING";
			case ActivityInstanceData.STATE_INACTIVE :
				return "INACTIVE";
			case ActivityInstanceData.STATE_SKIPPED :
				return "SKIPPED";
			case ActivityInstanceData.STATE_STOPPED :
				return "STOPPED";
			case ActivityInstanceData.STATE_WAITING :
				return "WAITING";
			default :
				return "UNKNOWN";
		}
	}
	public static String getProcessReason(int theReason) {
		switch (theReason) {
			case WorkItemData.REASON_ADMINISTRATOR :
				return "ADMINISTRATOR";
			case WorkItemData.REASON_EDITOR :
				return "EDITOR";
			case WorkItemData.REASON_MAX :
				return "MAX";
			case WorkItemData.REASON_OWNER :
				return "OWNER";
			case WorkItemData.REASON_POTENTIAL_OWNER :
				return "POTENTIAL_OWNER";
			case WorkItemData.REASON_POTENTIAL_SENDER :
				return "POTENTIAL_SENDER";
			case WorkItemData.REASON_POTENTIAL_STARTER :
				return "POTENTIAL_STARTER";
			case WorkItemData.REASON_READER :
				return "READER";
			case WorkItemData.REASON_STARTER :
				return "STARTER";
			default :
				return "UNKNOWN";
		}
	}
	
	/**
	 * Method printAuditData.
	 * @param theResult
	 */
	public static void printAuditData(AuditDataResult theResult) {
		for (int i = 0; i < theResult.rowCount(); i++) {
			Hashtable aRow = theResult.getRow(i);
			if (i==0) // only print process instance info once
			{
				APIExerciser.println_s("Process template information" );
				APIExerciser.println_s("  PROCESS_TEMPL_NAME : "+aRow.get("PROCESS_TEMPL_NAME"));	
				APIExerciser.println_s("  Process template ID: " + aRow.get("PTID"));				
				APIExerciser.println_s("  VALID_FROM         : "+aRow.get("VALID_FROM"));					
				APIExerciser.println_s("Process instance information" );				
				APIExerciser.println_s("  PIID               : "+aRow.get("PIID"));				
				APIExerciser.println_s("  PROCESS_INST_NAME  : "+aRow.get("PROCESS_INST_NAME"));	
				APIExerciser.println_s("  TOP_LEVEL_FI_NAME  : "+aRow.get("TOP_LEVEL_FI_NAME"));	
				APIExerciser.println_s("  TOP_LEVEL_PIID     : "+aRow.get("TOP_LEVEL_PIID"));	
				APIExerciser.println_s("  PARENT_PI_NAME     : "+aRow.get("PARENT_PI_NAME"));	
				APIExerciser.println_s("  PARENT_PIID        : "+aRow.get("PARENT_PIID"));	

			}
			APIExerciser.println_s("");
			APIExerciser.println_s("Audit event:");			
			APIExerciser.println_s("  Event              : " + Utility.getAuditEventType(((Integer) aRow.get("AUDIT_EVENT")).intValue()));			
			APIExerciser.println_s("  Event time         : " + aRow.get("EVENT_TIME"));


			switch (Utility.getAuditEventGroup(((Integer) aRow.get("AUDIT_EVENT")).intValue())) {
				case AIE :
					printActitityInstanceEvent(aRow);
					break;
				case PIE :
					printProcessInstanceEvent(aRow);
					break;
				case CLE : 
					printControlLinkEvent(aRow);
					break;
				case PTE :
					printProcessTemplateEvent(aRow);
					break;
				case VAR :
					printVariableEvent(aRow);
					break;
				default :
					APIExerciser.println_s("Unknown event");
			}
		}
	}

	/**
	 * Method printVariableEvent.
	 * @param theResult
	 */
	private static void printVariableEvent(Hashtable aRow) {
	}


	/**
	 * Method printProcessTemplateEvent.
	 * @param theResult
	 */
	private static void printProcessTemplateEvent(Hashtable aRow) {
	}


	/**
	 * Method printControlLinkEvent.
	 * @param theResult
	 */
	private static void printControlLinkEvent(Hashtable aRow) {
		APIExerciser.println_s("  CONTROL_LINK_NAME:   "+aRow.get("CONTROL_LINK_NAME"));			
	}


	/**
	 * Method printProcessInstanceEvent.
	 * @param theResult
	 */
	private static void printProcessInstanceEvent(Hashtable aRow) {
		int eventType = ((Integer)aRow.get("AUDIT_EVENT")).intValue();
		
 		if (eventType==StateObserverEvent.PROCESS_COMPLETED ||		
			eventType==StateObserverEvent.PROCESS_STARTED	||
			eventType==StateObserverEvent.PROCESS_TERMINATED||
			eventType==StateObserverEvent.PROCESS_DELETED
			)			
			APIExerciser.println_s("  PRINCIPAL: "+aRow.get("PRINCIPAL"));
		
		if (eventType==StateObserverEvent.PROCESS_FAILED)				
			APIExerciser.println_s("  EXCEPTION_TEXT: "+aRow.get("EXCEPTION_TEXT"));	
	}


	/**
	 * Method printActitityInstanceEvent.
	 * @param theResult
	 */
	private static void printActitityInstanceEvent(Hashtable aRow ) {
		int eventType = ((Integer)aRow.get("AUDIT_EVENT")).intValue();
		
		APIExerciser.println_s("  ACTIVITY NAME: "+aRow.get("ACTIVITY NAME"));	
		int activityKind = ((Integer)aRow.get("ACTIVITY_KIND")).intValue();
		APIExerciser.println_s("  ACTIVITY_KIND: "+getActivityKind(activityKind));
		if (activityKind==ActivityInstanceData.KIND_ELEMENTAL)	
			APIExerciser.println_s("  IMPL_NAME: "+aRow.get("IMPL_NAME"));			
		
		APIExerciser.println_s("  ACTIVITY_STATE: "+getActivityState(((Integer)aRow.get("ACTIVITY_STATE")).intValue()));			
 		if (eventType==StateObserverEvent.ACTIVITY_COMPLETED ||		
			eventType==StateObserverEvent.ACTIVITY_CLAIMED	 || 		
			eventType==StateObserverEvent.ACTIVITY_FAILED	 ||
			eventType==StateObserverEvent.ACTIVITY_FAULT_MESSAGE_SET ||
			eventType==StateObserverEvent.ACTIVITY_OUTPUT_MESSAGE_SET ||
			eventType==StateObserverEvent.ACTIVITY_USERINPUT_SET
			)			
			APIExerciser.println_s("  PRINCIPAL: "+aRow.get("PRINCIPAL"));
		
		if (eventType==StateObserverEvent.ACTIVITY_FAULT_MESSAGE_SET)	
			APIExerciser.println_s("  TERMINAL_NAME: "+aRow.get("TERMINAL_NAME"));	
		
		if (eventType==StateObserverEvent.ACTIVITY_FAILED)				
			APIExerciser.println_s("  EXCEPTION_TEXT: "+aRow.get("EXCEPTION_TEXT"));	
	
	}




	/**
	 * Method getEventType.
	 * @param i
	 * @return String
	 */
	private static String getAuditEventType(int eventType) {
		switch (eventType) {
			case StateObserverEvent.ACTIVITY_CLAIM_CANCELED :
				return "ACTIVITY_CLAIM_CANCELED";
			case StateObserverEvent.ACTIVITY_CLAIMED :
				return "ACTIVITY_CLAIMED";
			case StateObserverEvent.ACTIVITY_COMPLETED :
				return "ACTIVITY_COMPLETED";
			case StateObserverEvent.ACTIVITY_EXPIRED :
				return "ACTIVITY_EXPIRED";
			case StateObserverEvent.ACTIVITY_FAILED :
				return "ACTIVITY_FAILED";
			case StateObserverEvent.ACTIVITY_FAILING :
				return "ACTIVITY_FAILING";
			case StateObserverEvent.ACTIVITY_FAULT_MESSAGE_SET :
				return "ACTIVITY_FAULT_MESSAGE_SET";
			case StateObserverEvent.ACTIVITY_LOOPED :
				return "ACTIVITY_LOOPED";
			case StateObserverEvent.ACTIVITY_OUTPUT_MESSAGE_SET :
				return "ACTIVITY_OUTPUT_MESSAGE_SET";
			case StateObserverEvent.ACTIVITY_READY :
				return "ACTIVITY_READY";
			case StateObserverEvent.ACTIVITY_RESTARTED_EXIT_CONDITION_FALSE :
				return "ACTIVITY_RESTARTED_EXIT_CONDITION_FALSE";
			case StateObserverEvent.ACTIVITY_SKIPPED :
				return "ACTIVITY_SKIPPED";
			case StateObserverEvent.ACTIVITY_STARTED :
				return "ACTIVITY_STARTED";
			case StateObserverEvent.ACTIVITY_STOPPED :
				return "ACTIVITY_STOPPED";
			case StateObserverEvent.ACTIVITY_TERMINATED :
				return "ACTIVITY_TERMINATED";
			case StateObserverEvent.ACTIVITY_TERMINATING :
				return "ACTIVITY_TERMINATING";
			case StateObserverEvent.ACTIVITY_USERINPUT_SET :
				return "ACTIVITY_USERINPUT_SET";
			case StateObserverEvent.CONTROL_LINK_EVALUATED_TO_FALSE :
				return "CONTROL_LINK_EVALUATED_TO_FALSE";
			case StateObserverEvent.CONTROL_LINK_EVALUATED_TO_TRUE :
				return "CONTROL_LINK_EVALUATED_TO_TRUE";
			case StateObserverEvent.PROCESS_COMPENSATED :
				return "PROCESS_COMPENSATED";
			case StateObserverEvent.PROCESS_COMPENSATING :
				return "PROCESS_COMPENSATING";
			case StateObserverEvent.PROCESS_COMPLETED :
				return "PROCESS_COMPLETED";
			case StateObserverEvent.PROCESS_DELETED :
				return "PROCESS_DELETED";
			case StateObserverEvent.PROCESS_FAILED :
				return "PROCESS_FAILED";
			case StateObserverEvent.PROCESS_FAILING :
				return "PROCESS_FAILING";
			case StateObserverEvent.PROCESS_INSTALLED :
				return "PROCESS_INSTALLED";
			case StateObserverEvent.PROCESS_RESUMED :
				return "PROCESS_RESUMED";
			case StateObserverEvent.PROCESS_STARTED :
				return "PROCESS_STARTED";
			case StateObserverEvent.PROCESS_SUSPENDED :
				return "PROCESS_SUSPENDED";
			case StateObserverEvent.PROCESS_TERMINATED :
				return "PROCESS_TERMINATED";
			case StateObserverEvent.PROCESS_TERMINATING :
				return "PROCESS_TERMINATING";
			case StateObserverEvent.PROCESS_UNINSTALLED :
				return "PROCESS_UNINSTALLED";
			case StateObserverEvent.VARIABLE_UPDATED:
				return "VARIABLE_UPDATED";
				

			default :
				return "UNKNOWN";
		}
	}

	private static int getAuditEventGroup(int eventType) {
		switch (eventType) {
			case StateObserverEvent.ACTIVITY_CLAIM_CANCELED :
			case StateObserverEvent.ACTIVITY_CLAIMED :
			case StateObserverEvent.ACTIVITY_COMPLETED :
			case StateObserverEvent.ACTIVITY_EXPIRED :
			case StateObserverEvent.ACTIVITY_FAILED :
			case StateObserverEvent.ACTIVITY_FAILING :
			case StateObserverEvent.ACTIVITY_FAULT_MESSAGE_SET :
			case StateObserverEvent.ACTIVITY_LOOPED :
			case StateObserverEvent.ACTIVITY_OUTPUT_MESSAGE_SET :
			case StateObserverEvent.ACTIVITY_READY :
			case StateObserverEvent.ACTIVITY_RESTARTED_EXIT_CONDITION_FALSE :
			case StateObserverEvent.ACTIVITY_SKIPPED :
			case StateObserverEvent.ACTIVITY_STARTED :
			case StateObserverEvent.ACTIVITY_STOPPED :
			case StateObserverEvent.ACTIVITY_TERMINATED :
			case StateObserverEvent.ACTIVITY_TERMINATING :
			case StateObserverEvent.ACTIVITY_USERINPUT_SET :
				return AIE;
				
			case StateObserverEvent.CONTROL_LINK_EVALUATED_TO_FALSE :
			case StateObserverEvent.CONTROL_LINK_EVALUATED_TO_TRUE :
				return CLE;
				
			case StateObserverEvent.PROCESS_COMPENSATED :
			case StateObserverEvent.PROCESS_COMPENSATING :
			case StateObserverEvent.PROCESS_COMPLETED :
			case StateObserverEvent.PROCESS_DELETED :
			case StateObserverEvent.PROCESS_FAILED :
			case StateObserverEvent.PROCESS_FAILING :
			case StateObserverEvent.PROCESS_RESUMED :
			case StateObserverEvent.PROCESS_STARTED :
			case StateObserverEvent.PROCESS_SUSPENDED :
			case StateObserverEvent.PROCESS_TERMINATED :
			case StateObserverEvent.PROCESS_TERMINATING :
				return PIE;
				
			case StateObserverEvent.PROCESS_INSTALLED :
			case StateObserverEvent.PROCESS_UNINSTALLED :
				return PTE;
				
			case StateObserverEvent.VARIABLE_UPDATED:
				return VAR;
			default: 
				return UNKNOWN;
		}
	}
}
