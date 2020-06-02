package com.ibm;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.util.*;

import javax.ejb.RemoveException;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.wsdl.Message;
import javax.wsdl.Part;
import org.apache.wsif.WSIFMessage;
import org.apache.wsif.WSIFMessageFactory;
import org.apache.wsif.base.WSIFDefaultMessage;
import com.ibm.bpe.api.*;
import com.ibm.bpe.database.ActivityMessageInstance;
import com.ibm.bpe.database.ProcessInstance;

import com.ibm.mq.MQEnvironment;
import com.ibm.utils.*;
import com.ibm.utils.WSDLHelper;
/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class APIExerciser implements MenuServices{	
	// Time to wait for replys on receive queue (in milliseconds)	
	static final long JMS_RECEIVE_WAIT_TIME = 3000;  
	boolean disableAutomaticLogin 			= false;

	BusinessProcess theProcessEJB 			= null;
	BusinessProcessJMS theProcessJMS 		= null;
	
	String theJMSUser						= "wasadmin";
	String theJMSPassword					= "wasadmin";
	
	String theEJBUser						= "wasadmin";
	String theEJBPassword					= "wasadmin";

	final static String INDENTATION = "     ";

	final static MenuCommand[] EJBPROCESSMENU =
		{
			new MenuCommand("List  Templates", "listTemplates"),
			new MenuCommand("Start (call)", "callProcessEJB"),
			new MenuCommand("Start (initiate)", "initiateProcessEJB"),
			new MenuCommand("Start (callWithReplyContext)", "callWithReplyContextProcessEJB"),			
			new MenuCommand("List instances ", "listProcessInstances"),			
			new MenuCommand("List details", "listProcessInstanceDetails"),						
			new MenuCommand("List input", "listProcessInput"),									
			new MenuCommand("List output", "listProcessOutput"),			
			new MenuCommand("List faults", "listProcessFaults"),								
			new MenuCommand("List variable", "listProcessVariable"),									
			new MenuCommand("Terminate", "terminateProcessEJB"),
			new MenuCommand("Delete", "deleteProcessEJB"),
			new MenuCommand("List worklist names", "listWorkListNames"),
			};	


	final static MenuCommand[] EJBACTIVITYMENU =
		{
			new MenuCommand("Claim staff activity", "claimActivity"),			
			new MenuCommand("Cancel claimed staff activity", "cancelClaim"),						
			new MenuCommand("Complete claimed staff activity", "completeClaim"),			
			new MenuCommand("List activity instances", "listActivityInstances"),
			new MenuCommand("List details", "listActivityInstanceDetails"),									
			new MenuCommand("Send event", "sendEvent"),	
			new MenuCommand("Retry stopped activity", "forceRetry"),	
			new MenuCommand("List input", "listActivityInput"),									
			new MenuCommand("List output", "listActivityOutput"),			
			
		};	
			
	final static MenuCommand[] EJBMENU =
		{
			new MenuSubCommand("Process", EJBPROCESSMENU),
			new MenuSubCommand("Activity", EJBACTIVITYMENU),
//			new MenuCommand("Set User", "setUser", "isUserAuthenticated"),
		};	
					
			
	final static MenuCommand[] JMSMENU =
		{
			new MenuCommand("Start process (call)", "callProcessJMS"),
			new MenuCommand("Start process (initiate)", "initiateProcessJMS"),			
			new MenuCommand("Send event", "sendEventJMS"),						
			new MenuCommand("Terminate process", "terminateProcessJMS"),			
		};

	final static MenuCommand[] MAINMENU =
		{
			new MenuCommand("Disable/enable program login", "disableEnableLogin"),			
			new MenuSubCommand("EJB", EJBMENU),
			new MenuSubCommand("JMS", JMSMENU),
			new MenuCommand("List process audit ", "listProcessAudit"),						
		};

	Object getSelection(Object[] theList, String theFormatter) {
		if ((theList == null) || (theList.length == 0))
			return null;
		if (theList.length == 1) // if there is one item in list use it.
			return theList[0];
		Class[] theTypes = new Class[1];
		theTypes[0] = Object.class;
		Object[] theArgs = new Object[1];
		while (true)
			try {
				for (int i = 0; i < theList.length; i++) {
					theArgs[0] = theList[i];
					String theText;
					try {
						theText = (String) this.getClass().getDeclaredMethod(theFormatter, theTypes).invoke(this, theArgs);
						println(i + ((i<10)&&(theList.length>9)?" ":"")+ "): " + theText);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
				String theSelection = this.prompt("Enter Selection");
				 // make 0 default selection				
				if (theSelection.equals("")) theSelection="0";
				return theList[Integer.parseInt(theSelection)];
			} catch (Exception exp) {
			}
	}
	public String formatTemplateData(Object theTemplate)
	{
		return ((ProcessTemplateData)theTemplate).getName();
	}
	
	public void disableEnableLogin() {
		boolean currentLogin = disableAutomaticLogin;
		println("Do you want to login manually?");
		disableAutomaticLogin = areYouSure(true);
		
		// null the processEJB to force new lookup
		if (currentLogin != disableAutomaticLogin)
		{
			try {
				if (theProcessEJB != null)
					theProcessEJB.remove();
			} catch (Exception e) {
				e.printStackTrace();
			}
			theProcessEJB=null;
			if (!disableAutomaticLogin)
			{
				theEJBUser     = prompt("Userid: ");
				theEJBPassword = prompt("Password: ");				
			}
		}
		
		return;
	}
	
	public void callProcessEJB() {
		ProcessTemplateData[] theTemplates = this.getSyncTemplates();
		ProcessTemplateData theSelectedTemplate = (ProcessTemplateData) this.getSelection(theTemplates, "formatTemplateData");
		if (theSelectedTemplate != null) {
			String theTemplate = theSelectedTemplate.getName();
			println("Using template: " + formatTemplateData(theSelectedTemplate));
			if (!areYouSure(true))
				return;
			try {
				// Create Input message
				ClientObjectWrapper aInput = WSDLHelper.createInputMessage(getProcessEJB(), theTemplate);
				Object inputObject = aInput.getObject();
				if (inputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) inputObject);
				} else {
					println("Unable to process input message"); 
					return;
				}
				// Call the process
				ClientObjectWrapper theOutput = getProcessEJB().call(theTemplate, aInput);
				println(" ---------------------- Result from process");
				// List output parts
				Object theObject = theOutput.getObject();
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process templates available");
	}
	public void callWithReplyContextProcessEJB() {
		ProcessTemplateData[] theTemplates = this.getAllTemplates();
		ProcessTemplateData theSelectedTemplate = (ProcessTemplateData) this.getSelection(theTemplates, "formatTemplateData");
		if (theSelectedTemplate != null) {
			String theTemplate = theSelectedTemplate.getName();
			println("Using template: " + formatTemplateData(theSelectedTemplate));
			if (!areYouSure(true))
				return;
			String theProcessName = this.prompt("Enter process name (optional)");
			try {
				// Create Input message
				ClientObjectWrapper aInput = WSDLHelper.createInputMessage(getProcessEJB(), theTemplate);
				Object inputObject = aInput.getObject();
				if (inputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) inputObject);
				} else {
					println("Unable to process input message");
					return;
				}
				ReplyContext replyContext = new ReplyFunction();
				// Call the process
				println("Starting process with reply function: "+replyContext.getClass());
				if (theProcessName.equals(""))
					getProcessEJB().callWithReplyContext(theTemplate, aInput, new ReplyContextWrapper(replyContext));
				else
					getProcessEJB().callWithReplyContext(theTemplate, theProcessName, aInput, new ReplyContextWrapper(replyContext));
				println(" ---------------------- Result from process");
				BusinessProcessJMS theProcessJMS = this.getProcessJMS();
				theProcessJMS.getTheConnection().start();
				QueueSession queueSession = theProcessJMS.getTheConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueReceiver queueReceiver = queueSession.createReceiver(theProcessJMS.getTheReplyQueue());				

				// Receive output message 
				println("Waiting for reply within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");				
				javax.jms.Message reply = queueReceiver.receive(JMS_RECEIVE_WAIT_TIME);
				if (reply==null)
				{
					println("No reply received within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");
					return;
				}
				if (reply instanceof ObjectMessage) {
					ObjectMessage objectReply = (ObjectMessage) reply;
					String[] theResults =(String[]) objectReply.getObject();
					println("Received result: ");
					for (int i = 0; i < theResults.length; i++) {
						println(theResults[i]);
					}
					
				} else {
					println("Object message not received ");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally
			{
				//Stop the connection
				try {
					theProcessJMS.getTheConnection().stop();
				} catch (JMSException e) {
					e.printStackTrace();
				}
								
			}			
		} else
			println("No process templates available");
	}

	
	public void callProcessJMS() {
		ProcessTemplateData[] theTemplates = this.getSyncTemplates();
		ProcessTemplateData theSelectedTemplate = (ProcessTemplateData) this.getSelection(theTemplates, "formatTemplateData");
		if (theSelectedTemplate != null) {
			String theTemplate = theSelectedTemplate.getName();
			println("Using template: " + formatTemplateData(theSelectedTemplate));
			if (!areYouSure(true))
				return;
			
			try {
				// Create Input message
				ClientObjectWrapper aInput = WSDLHelper.createInputMessage(getProcessEJB(), theTemplate);
				Object inputObject = aInput.getObject();
				if (inputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) inputObject);
				} else {
					println("Unable to process input message");
					return;
				}
				// Call the process
				BusinessProcessJMS theProcessJMS = this.getProcessJMS();
				QueueSession queueSession = theProcessJMS.getTheConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueSender queueSender = queueSession.createSender(theProcessJMS.getTheQueue());
				// Start sending and receiving messages
				theProcessJMS.getTheConnection().start();
				// Create message
				ObjectMessage message = queueSession.createObjectMessage();
				message.setJMSReplyTo(theProcessJMS.getTheReplyQueue());
				message.setStringProperty("wf$verb", "call");
				message.setStringProperty("wf$processTemplateName", theTemplate);
				message.setObject(aInput);
				// Send message			
				queueSender.send(message);
				// Receive message and analyze reply
				QueueReceiver queueReceiver = queueSession.createReceiver(theProcessJMS.getTheReplyQueue(), "JMSCorrelationID='"+message.getJMSMessageID()+"'");				
				println("Waiting for reply within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");				
				javax.jms.Message reply = queueReceiver.receive(JMS_RECEIVE_WAIT_TIME);
				if (reply==null)
				{
					println("No reply received within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");
					return;
				}
				ClientObjectWrapper theOutput = null;
				if (reply instanceof ObjectMessage) {
					ObjectMessage objectReply = (ObjectMessage) reply;
					theOutput = (ClientObjectWrapper) objectReply.getObject();
				} else {
					println("Object message not received from Process container");
					return;
				}
				println(" ---------------------- Result from process");
				// List output parts
				Object theObject = theOutput.getObject();
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally
			{
				//Stop the connection to the free resources. 
				try {
					theProcessJMS.getTheConnection().stop();
				} catch (JMSException e) {
					e.printStackTrace();
				}
								
			}
		} else
			println("No process templates available");
	}

	public void initiateProcessJMS() {
		ProcessTemplateData[] theTemplates = this.getAsyncTemplates();
		ProcessTemplateData theSelectedTemplate = (ProcessTemplateData) this.getSelection(theTemplates, "formatTemplateData");
		if (theSelectedTemplate != null) {
			String theTemplate = theSelectedTemplate.getName();
			println("Using template: " + formatTemplateData(theSelectedTemplate));
			if (!areYouSure(true))
				return;
				
			String theProcessName = this.prompt("Enter process name (optional)");
			try {
				// Create Input message
				ClientObjectWrapper aInput = WSDLHelper.createInputMessage(getProcessEJB(), theTemplate);
				Object inputObject = aInput.getObject();
				if (inputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) inputObject);
				} else {
					println("Unable to process input message");
					return;
				}
				// Call the process
				BusinessProcessJMS theProcessJMS = this.getProcessJMS();
				QueueSession queueSession = theProcessJMS.getTheConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueSender queueSender = queueSession.createSender(theProcessJMS.getTheQueue());

				// Start sending and receiving messages
				theProcessJMS.getTheConnection().start();
				// Create message
				ObjectMessage message = queueSession.createObjectMessage();
				message.setJMSReplyTo(theProcessJMS.getTheReplyQueue());
				message.setStringProperty("wf$verb", "initiate");
				message.setStringProperty("wf$processTemplateName", theTemplate);
				if (!theProcessName.equals(""))
					message.setStringProperty("wf$processInstanceName", theProcessName);
				message.setObject(aInput);
				// Send message			
				queueSender.send(message);
				// Receive message and analyze reply
				QueueReceiver queueReceiver = queueSession.createReceiver(theProcessJMS.getTheReplyQueue(), "JMSCorrelationID='"+message.getJMSMessageID()+"'");												
				println("Waiting for reply within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");				
				javax.jms.Message reply = queueReceiver.receive(JMS_RECEIVE_WAIT_TIME);
				if (reply==null)
				{
					println("No reply received within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");
					return;
				}
				
				String thePiid = reply.getStringProperty("wf$piid");
				
				println(" ---------------------- Result from process");
				println("Process started with piid: " + thePiid.toString());
				// List output parts
			} catch (Exception e) {
				e.printStackTrace();
			} finally
			{
				//Stop the connection to the free resources. 
				try {
					theProcessJMS.getTheConnection().stop();
				} catch (JMSException e) {
					e.printStackTrace();
				}
								
			}
		} else
			println("No process templates available");
	}
	
	public String formatFields(String[][] theFields)
	{
		final String PADDING = "                                                                          ";
		String theResult = "";
		
		for (int i = 0; i < theFields.length; i++) {
			int theLength = Integer.parseInt(theFields[i][2]); 
			String theValue="";
			if (theFields[i][1] == null)
				theValue="-"+PADDING.substring(0,theLength-1);
			else if (theFields[i][1].length()>=theLength)
				theValue = theFields[i][1].substring(0,theLength);
			else
				theValue = theFields[i][1]+PADDING.substring(0,theLength-theFields[i][1].length());
			
			theResult+=theFields[i][0]+": "+theValue+" ";
		}
		
		return theResult;
	}
	
	public String formatActivityData(Object theObject) throws Exception{
		ActivityInstanceData theActivity = (ActivityInstanceData) theObject;
		String processInstanceName = getProcessEJB().getProcessInstance(theActivity.getProcessInstanceID()).getName();
		String theAdministrators = "";
		String[] admins;
		try {
			if (theActivity.getProcessAdministrators() != null) {
				admins = theActivity.getProcessAdministrators().getUserIDs();
				for (int i = 0; i < admins.length; i++) {
					theAdministrators += admins[i] + " ";
				}
			} else
				theAdministrators += "-";
		} catch (WorkItemManagerException e) {
			e.printStackTrace();
		}
		String[][] theFields ={	
						{"Name", theActivity.getName()	, "15"},								
						{"Kind", Utility.getActivityKind(theActivity.getKind())	, "13"},
						{"State", Utility.getActivityState(theActivity.getExecutionState())	, "10"},								
						{"Process Instance Name", processInstanceName	, "15"},														
						{"Process template", getProcessEJB().getProcessTemplate(theActivity.getProcessTemplateID()).getName()	, "15"},
						{"Owner", theActivity.getOwner(), "10"},
						{"AIID", theActivity.getID().toString()					, "33"},						
						{"Administrators", theAdministrators, "50"},								
		};
		
		return formatFields(theFields);
	}
	public String formatEventData(Object theObject) {
		EventInstanceData theEvent = (EventInstanceData)theObject;
		String eiid = theEvent.getTheEIID().toString();
		String aiid = theEvent.getTheAIID().toString();		
		String piid = theEvent.getThePIID().toString();		
		String ein = theEvent.getTheName();
		
		String[][] theFields ={	
						{"EIID", eiid	, "33"},
						{"Name", ein	, "15"},
						{"AIID", aiid	, "33"},
						{"PIID", piid	, "33"},												
		};

		return formatFields(theFields);
	}
	
	public void claimActivity() throws Exception {
		ActivityInstanceData[] theActivities = this.getClaimableActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theActivities, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using Activity: " + formatActivityData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// Claim the instance
				ClientObjectWrapper theInput = getProcessEJB().claim(theSelectedInstance.getID());
				Object theObject = theInput.getObject();
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process activity input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No claimable instances found");
	}	
	public void sendEvent() {
		EventInstanceData[] theEvents = this.getEventInstances();
		EventInstanceData theSelectedInstance = (EventInstanceData) this.getSelection(theEvents, "formatEventData");
		if (theSelectedInstance != null) {
			println("Using Event: " + formatEventData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// Create event input message
				ProcessInstanceData processInstance = getProcessEJB().getProcessInstance(theSelectedInstance.getThePIID());
				String theEventType = processInstance.getEventMessageTypeName(theSelectedInstance.getTheName());

				ClientObjectWrapper theInput = WSDLHelper.createMessage(theProcessEJB, processInstance.getID(), theEventType);				
		
				WSDLHelper.promptForParts((WSIFMessage)theInput.getObject());
				
				getProcessEJB().sendEvent(theSelectedInstance.getThePIID(), theSelectedInstance.getTheName(), theInput);
				println("Event sent successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No activities waiting for events found");
	}

	public void cancelClaim()  throws Exception{
		ActivityInstanceData[] theActivities = this.getClaimedActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theActivities, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using Activity: " + formatActivityData(theSelectedInstance));	
			if (!areYouSure(true))
				return;
					
			try {
				// Claim the instance
				getProcessEJB().cancelClaim(theSelectedInstance.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No claimed instances found");
	}
	public void completeClaim()  throws Exception {
		ActivityInstanceData[] theActivities = this.getClaimedActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theActivities, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using Activity: " + formatActivityData(theSelectedInstance));			
			if (!areYouSure(true))
				return;
			
			try {
				// Show the input for the activity
				ClientObjectWrapper theInput = getProcessEJB().getInputMessage(theSelectedInstance.getID());
				println("Activity input:");
				Object theObject = theInput.getObject();
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process activity input message");
					return;
				}
				// Create the output
				println("Asking for the activity output: ");
				ClientObjectWrapper aOutput = WSDLHelper.createOutputMessage(getProcessEJB(), theSelectedInstance);
				Object outputObject = aOutput.getObject();
				if (outputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) outputObject);
				} else {
					println("Unable to process output message");
					return;
				}
				// Complete the instance
				getProcessEJB().complete(theSelectedInstance.getID(), aOutput);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No claimed instances found");
	}

	public String formatProcessInstanceData(Object theObject) {
		ProcessInstanceData theInstance = (ProcessInstanceData) theObject;
		String theAdministrators = "";
		try {
			if (theInstance.getProcessAdministrators() != null) {
				String[] admins;
				admins = theInstance.getProcessAdministrators().getUserIDs();
				for (int i = 0; i < admins.length; i++) {
					theAdministrators += admins[i] + " ";
				}
			} else
				theAdministrators += "-";
		} catch (WorkItemManagerException e) {
			e.printStackTrace();
		}
		String[][] theFields = { { "PIID", theInstance.getID().toString(), "33" }, {
				"Name", theInstance.getName(), "15" }, {
				"Template", theInstance.getProcessTemplateName(), "15" }, {
				"State", Utility.getProcessState(theInstance.getExecutionState()), "15" }, {
				"Starter", theInstance.getStarter(), "15" }, {
				"Administrators", theAdministrators, "15" }, };
		return formatFields(theFields);
	}

	public void deleteProcessEJB() {
		ProcessInstanceData[] theTerminatedInstances = getTerminatedProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theTerminatedInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));			
			if (!areYouSure(true))
				return;
			
			try {
				// terminate the instance
				getProcessEJB().delete(theSelectedInstance.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No terminated instances found");
	}
	public ProcessInstanceData[] getProcessInstances(String theSelect, String theClause) {
		return getProcessInstances(theSelect, theClause, null);
	}

	public ProcessInstanceData[] getProcessInstances(String theSelect, String theClause, String theOrderBy) {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theProcessInstances = null;
		QueryResultSet queryresultset = null;
		try {
			queryresultset = process.query(theSelect, theClause, theOrderBy, null, null);
			if (queryresultset.size() > 0) {
				theProcessInstances = new ProcessInstanceData[queryresultset.size()];
				// Position cursor on first row
				queryresultset.first();
				for (int i = 0; i < queryresultset.size(); i++) {
					String thePiid = queryresultset.getOID(1).toString();
					theProcessInstances[i] = process.getProcessInstance(thePiid);
					queryresultset.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theProcessInstances;
	}

	
	public void listProcessInstanceDetails() throws Exception {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			Utility.printProcessInstanceDetails(theProcessEJB, theSelectedInstance);
		}
	}			
	
	public void listActivityInstanceDetails() throws Exception{
		BusinessProcess process = getProcessEJB();
		ActivityInstanceData[] theInstances = getAllActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theInstances, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using activity instance: " + formatActivityData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			Utility.printActivityInstanceDetails(getProcessEJB(), theSelectedInstance);
		}
	}			 

	public void listProcessFaults() {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				List theFaultNames = getProcessEJB().getFaultTerminalNames(theSelectedInstance.getProcessTemplateID());
				if(theFaultNames.size()>0)
				{
					Iterator iter = theFaultNames.iterator();
					while (iter.hasNext()) {
						String theFaultName = (String) iter.next();
						ClientObjectWrapper theFaultMsg = getProcessEJB().getFaultMessage(theSelectedInstance.getID(), theFaultName);
						if (theFaultMsg==null || theFaultMsg.getObject()==null)
						{
							println("No fault on terminal: '"+theFaultName);
						}
						else
							WSDLHelper.printMessage((WSIFMessage)theFaultMsg.getObject());
					}
				}
				else 
					println("No faults defined for process");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process instances found");
	}


	
	
	public void listProcessOutput() {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// get the output
				ClientObjectWrapper theOutput = getProcessEJB().getOutputMessage(theSelectedInstance.getID());
				Object theObject = theOutput.getObject();
				if (theObject == null) {
					println("Output not set");
					return;
				}
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process process output message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process instances found");
	}
	public void listProcessVariable() {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			String theVariableName = prompt("Variable name:");
			try {
				// get the output
				ClientObjectWrapper theVariable = getProcessEJB().getVariable(theSelectedInstance.getID(), theVariableName);
				if (theVariable==null)
				{
					println("Variable not set");
					return;
				}
				Object theObject = theVariable.getObject();
				if (theObject == null) {
					println("Variable not set");
					return;
				}
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process process input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process instances found");
	}
	
	
	
	
	
	public void listProcessInput() {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// get the input
				ClientObjectWrapper theInput = getProcessEJB().getInputMessage(theSelectedInstance.getID());
				Object theObject = theInput.getObject();
				if (theObject == null) {
					println("input not set");
					return;
				}
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process process input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process instances found");
	}
	
	public void listActivityInput()  throws Exception{
		BusinessProcess process = getProcessEJB();
		ActivityInstanceData[] theInstances = getAllActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theInstances, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using activity instance: " + formatActivityData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// get the input
				ClientObjectWrapper theInput = getProcessEJB().getInputMessage(theSelectedInstance.getID());
				Object theObject = theInput.getObject();
				if (theObject == null) {
					println("Input not set");
					return;
				}
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process activity input message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No activity instances found");
	}

	public void forceRetry()  throws Exception{
		BusinessProcess process = getProcessEJB();
		ActivityInstanceData[] theInstances = getStoppedActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theInstances, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using activity instance: " + formatActivityData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
//				println("Current activity input");
//				ClientObjectWrapper theOrgInput = getProcessEJB().getInputMessage(theSelectedInstance.getID());
//				if (theOrgInput.getObject()!=null)
//					if (theOrgInput.getObject() instanceof WSIFMessage)
//						WSDLHelper.printMessage((WSIFMessage)theOrgInput.getObject());
//					else if (theOrgInput.getObject() instanceof ActivityMessageInstance)
//					{
//						ActivityMessageInstance am = (ActivityMessageInstance) theOrgInput.getObject();	
//						
//						WSDLHelper.printMessage(am);
//
//					}
//				else
//				println("   No input for activity");
				
				println("Create new input message");
				boolean newInput = areYouSure(true);
				println("Continue if error occurs");
				boolean continueOnError = areYouSure(true);
				if (newInput){
					// create the new input
					ClientObjectWrapper theInput = WSDLHelper.createMessage(getProcessEJB(), theSelectedInstance.getProcessInstanceID(), theSelectedInstance.getInputMessageTypeName());
					Object theObject = theInput.getObject();
					if (theObject == null) {
						println("Input not set");
					return;
					}
					if (theObject instanceof WSIFMessage) {
						WSDLHelper.promptForParts((WSIFMessage) theObject);
					} else {
						println("Unable to process activity Input message");
						return;
					}
					getProcessEJB().forceRetry(theSelectedInstance.getID(), theInput, continueOnError);
				}
				else
					getProcessEJB().forceRetry(theSelectedInstance.getID(), continueOnError);						
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No stopped activity instances found");
	}


	public void listActivityOutput()  throws Exception{
		BusinessProcess process = getProcessEJB();
		ActivityInstanceData[] theInstances = getAllActivityInstances();
		ActivityInstanceData theSelectedInstance = (ActivityInstanceData) this.getSelection(theInstances, "formatActivityData");
		if (theSelectedInstance != null) {
			println("Using activity instance: " + formatActivityData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			
			try {
				// get the output
				ClientObjectWrapper theOutput = getProcessEJB().getOutputMessage(theSelectedInstance.getID());
				Object theObject = theOutput.getObject();
				if (theObject == null) {
					println("Output not set");
					return;
				}
				if (theObject instanceof WSIFMessage) {
					WSDLHelper.printMessage((WSIFMessage) theObject);
				} else {
					println("Unable to process activity Output message");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No activity instances found");
	}

	
	
	public ProcessTemplateData[] getProcessTemplates(String theClause) {
		BusinessProcess process = getProcessEJB();
		ProcessTemplateData[] theProcessTemplates = null;
		QueryResultSet queryresultset = null;
		try {
			theProcessTemplates = process.queryProcessTemplates(theClause, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theProcessTemplates;
	}
	public ProcessTemplateData[] getAllTemplates() {
		return getProcessTemplates(null);
	}	

	public ProcessTemplateData[] getSyncTemplates() {
		
		String theClause = "CAN_RUN_SYNC=true";
		
		return getProcessTemplates(theClause);
	}	
	
	public ProcessTemplateData[] getAsyncTemplates() {
		
		String theClause = "CAN_RUN_INTERRUP=true";
		
		return getProcessTemplates(theClause);
	}	
	
	public void listProcessAudit() {
		BusinessProcess process = getProcessEJB();
		ProcessInstanceData[] theInstances = getAllProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using process instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			try {
				AuditData theAudit = null;
				Object result;
				try {
					Context initialContext = new InitialContext();
					result = initialContext.lookup("java:comp/env/ejb/BPEAuditDataHome");
				} catch (NamingException e) {
					e.printStackTrace();
					return;
				}
				// Convert the lookup result to the proper type
				AuditDataHome auditHome = (AuditDataHome) javax.rmi.PortableRemoteObject.narrow(result, AuditDataHome.class);
				try {
					theAudit = auditHome.create();
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
				AuditDataResult theResult = theAudit.queryActivityAudit(theSelectedInstance.getID());
				println(theResult.rowCount() + " audit rows found");
				Utility.printAuditData(theResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No audit records found");	
	}

	public EventInstanceData[] getEventInstances() {
		String theSelect = "DISTINCT EVENT.EIID, EVENT.AIID, EVENT.PIID, EVENT.NAME";
//		String theClause = "WORK_ITEM.REASON = "+WorkItemData.REASON_POTENTIAL_OWNER;
		String theClause = "ACTIVITY.KIND = "+ActivityInstanceData.KIND_EVENT;		
		theClause += " and ACTIVITY.AIID = EVENT.AIID";				
		
		BusinessProcess process = getProcessEJB();
		EventInstanceData[] theEventInstances = null;
		QueryResultSet queryresultset = null;
		try {
			queryresultset = process.query(theSelect, theClause, null, null, null);
			if (queryresultset.size() > 0) {
				theEventInstances = new EventInstanceData[queryresultset.size()];
				// Position cursor on first row
				queryresultset.first();
				EIID theEIID=null;AIID theAIID=null;PIID thePIID=null;
				String theName=null;
				for (int i = 0; i < queryresultset.size(); i++) {
					
					theEIID = (EIID) queryresultset.getOID(1);
					theAIID = (AIID) queryresultset.getOID(2);
					thePIID = (PIID) queryresultset.getOID(3);										
					theName = queryresultset.getString(4);
					theEventInstances[i] = new EventInstanceData(theEIID, theAIID, thePIID, theName);
					queryresultset.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theEventInstances;
	}
	
	public ActivityInstanceData[] getActivityInstances(String theSelect, String theClause, String theOrderBy) {
		BusinessProcess process = getProcessEJB();
		ActivityInstanceData[] theActivityInstances = null;
		QueryResultSet queryresultset = null;
		try {
			queryresultset = process.query(theSelect, theClause, theOrderBy, null, null);
			if (queryresultset.size() > 0) {
				theActivityInstances = new ActivityInstanceData[queryresultset.size()];
				// Position cursor on first row
				queryresultset.first();
				for (int i = 0; i < queryresultset.size(); i++) {
					String theAiid = queryresultset.getOID(1).toString();
					theActivityInstances[i] = process.getActivityInstance(theAiid);
					queryresultset.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return theActivityInstances;
		
	}

	
	public ActivityInstanceData[] getActivityInstances(String theSelect, String theClause) {
		return getActivityInstances(theSelect, theClause, null);
	}
	public ProcessInstanceData[] getAllProcessInstances() {
		String theSelect = "distinct PROCESS_INSTANCE.PIID";
		String theClause = "process_instance.piid=work_item.object_id";
		theClause += " and process_instance.parent_name is null"; // only top level instances
		theClause += " and work_item.OBJECT_TYPE=" + WorkItemData.OBJECT_TYPE_PROCESS_INSTANCE;
		String theOrderBy = "process_instance.created";
		return getProcessInstances(theSelect, theClause, theOrderBy);
	}
	public ActivityInstanceData[] getAllActivityInstances() {
		String theSelect = "distinct ACTIVITY.AIID";
		String theClause = "KIND <> "+ActivityInstanceData.KIND_SOURCE; // Temp workaround
		theClause += " and KIND <> "+ActivityInstanceData.KIND_FAULT; // Temp workaround
//		String theOrderby = "ACTIVITY.PIID, ACTIVITY.STATE";
		String theOrderby = "ACTIVITY.PIID";		
//		String theClause = "WORK_ITEM.OBJECT_TYPE=" + WorkItemData.OBJECT_TYPE_ACTIVITY;
//		theClause += " OR WORK_ITEM.OBJECT_TYPE=" + WorkItemData.OBJECT_TYPE_EVENT;
		return getActivityInstances(theSelect, theClause, theOrderby);
	}
	public ActivityInstanceData[] getClaimableActivityInstances() {
		String theSelect = "distinct ACTIVITY.AIID";
		String theClause = "KIND=" + ActivityInstanceData.KIND_PERSON;
		theClause+= " and STATE="+ActivityInstanceData.STATE_READY;
		return getActivityInstances(theSelect, theClause);
	}
	
	public ActivityInstanceData[] getClaimedActivityInstances() {
		String theSelect = "distinct ACTIVITY.AIID";
		String theClause = "KIND=" + ActivityInstanceData.KIND_PERSON;
		theClause+= " and STATE="+ActivityInstanceData.STATE_CLAIMED;
		return getActivityInstances(theSelect, theClause);
	}

	public ActivityInstanceData[] getStoppedActivityInstances() {
		String theSelect = "distinct ACTIVITY.AIID";
		String theClause = " STATE="+ActivityInstanceData.STATE_STOPPED;
		return getActivityInstances(theSelect, theClause);
	}
	
	
	public ProcessInstanceData[] getRunningProcessInstances() {
		String theSelect = "distinct PROCESS_INSTANCE.PIID";
		String theClause = "STATE =" + ProcessInstanceData.STATE_RUNNING;
		theClause += " and process_instance.parent_name is null"; // only top level instances		
		return getProcessInstances(theSelect, theClause);
	}
	public ProcessInstanceData[] getTerminatedProcessInstances() {
		String theSelect = "distinct PROCESS_INSTANCE.PIID";
		String theClause = "(STATE =" + ProcessInstanceData.STATE_FINISHED;
		theClause += " or STATE=" + ProcessInstanceData.STATE_TERMINATED;
		theClause += " or STATE=" + ProcessInstanceData.STATE_COMPENSATED;
		theClause += " or STATE=" + ProcessInstanceData.STATE_FAILED;
		theClause += ") and process_instance.parent_name is null"; // only top level instances		
		return getProcessInstances(theSelect, theClause);
	}
	public void initiateProcessEJB() {
		ProcessTemplateData[] theTemplates = this.getAsyncTemplates();
		ProcessTemplateData theSelectedTemplate = (ProcessTemplateData) this.getSelection(theTemplates, "formatTemplateData");
		if (theSelectedTemplate != null) {
			String theTemplate = theSelectedTemplate.getName();
			println("Using template: " + formatTemplateData(theSelectedTemplate));
			if (!areYouSure(true))
				return;
			
			String theProcessName = this.prompt("Enter process name (optional)");
			try {
				// Create Input message
				ClientObjectWrapper aInput = WSDLHelper.createInputMessage(getProcessEJB(), theTemplate);

				Object inputObject = aInput.getObject();
				if (inputObject instanceof WSIFMessage) {
					WSDLHelper.promptForParts((WSIFMessage) inputObject);
				} else {
					println("Unable to process input message");
					return;
				}
 
				PIID thePiid;
				// Call the process
				if (theProcessName.equals(""))
					thePiid = getProcessEJB().initiate(theTemplate, aInput);
				else
					thePiid = getProcessEJB().initiate(theTemplate, theProcessName, aInput);
				println("Process started with piid: " + thePiid.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No process templates found");
	}
	public void listActivityInstances()  throws Exception{
		ActivityInstanceData[] theActivityInstances = this.getAllActivityInstances();
		if (theActivityInstances != null) {
			println(theActivityInstances.length + " activity(es) found");
			for (int i = 0; i < theActivityInstances.length; i++) {
				println(formatActivityData(theActivityInstances[i]));
			}
		} else
			println("No activity instances found");
		return;
	}
	public void listProcessInstances() {
		ProcessInstanceData[] theProcessInstances = this.getAllProcessInstances();
		if (theProcessInstances != null) {
			println(theProcessInstances.length + " process(es) found");
			for (int i = 0; i < theProcessInstances.length; i++) {
				println(formatProcessInstanceData(theProcessInstances[i]));
			}
		} else
			println("No processes instances found");
		return;
	}
	public void listTemplates() {
		ProcessTemplateData[] theTemplates = this.getAllTemplates();
		println(theTemplates.length + " Template(s) found");
		for (int i = 0; i < theTemplates.length; i++) {
			println(formatTemplateData(theTemplates[i]));
		}
	}
	public void listWorkListNames() {
		BusinessProcess process = getProcessEJB();
		if (process != null) {
			String[] theWorklistNames = null;
			try {
				theWorklistNames = process.getWorkListNames();
				if (theWorklistNames.length != 0) {
					for (int i = 0; i < theWorklistNames.length; i++) {
						println(theWorklistNames[i]);
					}
				} else
					println("No worklist names found");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void terminateProcessEJB() {
		ProcessInstanceData[] theRunningInstances = getRunningProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theRunningInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			try {
				// terminate the instance
				getProcessEJB().forceTerminate(theSelectedInstance.getID());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			println("No running instances found");
	}
	
		public void terminateProcessJMS() {
		ProcessInstanceData[] theRunningInstances = getRunningProcessInstances();
		ProcessInstanceData theSelectedInstance = (ProcessInstanceData) this.getSelection(theRunningInstances, "formatProcessInstanceData");
		if (theSelectedInstance != null) {
			println("Using instance: " + formatProcessInstanceData(theSelectedInstance));
			if (!areYouSure(true))
				return;
				// Call the process
				try {
					BusinessProcessJMS theProcessJMS = this.getProcessJMS();
					QueueSession queueSession = theProcessJMS.getTheConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
					QueueSender queueSender = queueSession.createSender(theProcessJMS.getTheQueue());
						// Start sending and receiving messages
					theProcessJMS.getTheConnection().start();
					// Create message
					ObjectMessage message = queueSession.createObjectMessage();
					message.setJMSReplyTo(theProcessJMS.getTheReplyQueue());
					message.setStringProperty("wf$verb", "forceTerminate");
					message.setStringProperty("wf$piid", theSelectedInstance.getID().toString());
					// Send message			
					queueSender.send(message);
					// Receive message and analyze reply
					QueueReceiver queueReceiver = queueSession.createReceiver(theProcessJMS.getTheReplyQueue(), "JMSCorrelationID='"+message.getJMSMessageID()+"'");													
					println("Waiting for reply within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");				
					javax.jms.Message reply = queueReceiver.receive(JMS_RECEIVE_WAIT_TIME);
					if (reply==null)
					{
						println("No reply received within "+JMS_RECEIVE_WAIT_TIME/1000+" seconds");
						return;
					}
					
					println(" ---------------------- Result from process");
	 				println("Process terminated");
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally
			{
				//Stop the connection to the free resources. 
				try {
					theProcessJMS.getTheConnection().stop();
				} catch (JMSException e) {
					e.printStackTrace();
				}
								
			}
		} else
			println("No running instances found");
	}

	public void sendEventJMS() {
		EventInstanceData[] theEvents = this.getEventInstances();
		EventInstanceData theSelectedInstance = (EventInstanceData) this.getSelection(theEvents, "formatEventData");
		if (theSelectedInstance != null) {
			println("Using Event: " + formatEventData(theSelectedInstance));
			if (!areYouSure(true))
				return;
			// Send the event 
			try {
				// Create event input message
				ProcessInstanceData processInstance = getProcessEJB().getProcessInstance(theSelectedInstance.getThePIID());
				String theEventType = processInstance.getEventMessageTypeName(theSelectedInstance.getTheName());
				ClientObjectWrapper theInput = WSDLHelper.createMessage(getProcessEJB(), processInstance.getID(), theEventType);

				WSDLHelper.promptForParts((WSIFMessage)theInput.getObject());
				
				BusinessProcessJMS theProcessJMS = this.getProcessJMS();
				QueueSession queueSession = theProcessJMS.getTheConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
				QueueSender queueSender = queueSession.createSender(theProcessJMS.getTheQueue());
				// Start sending and receiving messages
				theProcessJMS.getTheConnection().start();
				// Create message
				ObjectMessage message = queueSession.createObjectMessage();
				message.setObject(theInput);
				message.setJMSReplyTo(theProcessJMS.getTheReplyQueue());
				message.setStringProperty("wf$verb", "sendEvent");
				message.setStringProperty("wf$piid", theSelectedInstance.getThePIID().toString());
				// Send message			
				queueSender.send(message);
				// Receive message and analyze reply
				QueueReceiver queueReceiver =
					queueSession.createReceiver(theProcessJMS.getTheReplyQueue(), "JMSCorrelationID='" + message.getJMSMessageID() + "'");
				println("Waiting for reply within " + JMS_RECEIVE_WAIT_TIME / 1000 + " seconds");
				javax.jms.Message reply = queueReceiver.receive(JMS_RECEIVE_WAIT_TIME);
				if (reply == null) {
					println("No reply received within " + JMS_RECEIVE_WAIT_TIME / 1000 + " seconds");
					return;
				}
				println(" ---------------------- Result from process");
				println("Event sent");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//Stop the connection to the free resources. 
				try {
					theProcessJMS.getTheConnection().stop();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		} else
			println("No event instances found");
	}
	
	
	
	
	public BusinessProcess getProcessEJB() {
		if (theProcessEJB == null)
			if (disableAutomaticLogin)
				theProcessEJB = new RetrieveProcessEJB().getProcess();			
			else
				theProcessEJB = new RetrieveProcessEJB().getProcess(theEJBUser, theEJBPassword);

		return theProcessEJB; 
	}
	public BusinessProcessJMS getProcessJMS() {
		if (theProcessJMS == null) {
			MQEnvironment.userID = theJMSUser;
			MQEnvironment.password = theJMSPassword;

			theProcessJMS = new RetrieveProcessJMS().getProcess();
		}
		return theProcessJMS;
	}
	
	public boolean isUserAuthenticated() {
		return (theProcessEJB == null);
	}
	
	public static String getSelection() {
		char[] theInputBuffer = new char[255];
		InputStreamReader inputReader = new InputStreamReader(System.in);
		try {
			inputReader.read(theInputBuffer);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
		return new String(theInputBuffer).trim();
	}
	public String prompt(String thePrompt) {
		System.out.print(INDENTATION + thePrompt + " ===> ");
		return getSelection();
	}
	public static String prompt_s(String thePrompt) {
		System.out.print(INDENTATION + thePrompt + " ===> ");
		return getSelection();
	}
	
	public static boolean areYouSure(boolean theDefault) {
		String theReply;
		if (theDefault)
			theReply="[Y]/N";
		else
			theReply="Y/[N]";
			
		System.out.print(INDENTATION + "Are you sure "+theReply+" ===> ");
		String theSelection = getSelection();
		if (theSelection.equals(""))
			return theDefault;
			
		if (theSelection.equalsIgnoreCase("y"))
			return true;
		else
			return false;
	}

	public static void println_s(String theText) {
		System.out.println(INDENTATION + theText);
	}
	
	public void print(String theText, boolean useIndentation) {
		System.out.print((useIndentation ? INDENTATION : "") + theText);
	}
	public void println(String theText) {
		System.out.println(INDENTATION + theText);
	}
	public void println() {
		System.out.println();
	}
	public void setUser() {
		theEJBUser = this.prompt("Enter Userid");
	}
	
	public String[] getHeader(){
		String[] theHeader = {
			" ---------------- BPE Api Exerciser --------------",
			" EJB User: " + (disableAutomaticLogin ? " - " : theEJBUser)+" JMS User: " + theJMSUser	
			};
		return theHeader;
	}
	
	public static void main(String[] args) {
//		try {
//			Context initialContext = new InitialContext();
//			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup("jms/BPECF");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
		
		
//		String theInput;
		APIExerciser theExerciser=null;
		try
		{
			theExerciser = new APIExerciser();
		
			Menu theMenu = new Menu(theExerciser);
			theMenu.showMenu(MAINMENU);
		
			theExerciser.println(" ---------------- BPE Api Exerciser Finished --------------");
		} catch (NumberFormatException exp)
		{}
		catch (Exception exp)
		{
			exp.printStackTrace();
		} finally
		{
			if ((theExerciser!=null) && (theExerciser.theProcessJMS != null))
			{
				try {
					theExerciser.theProcessJMS.getTheConnection().close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

}
