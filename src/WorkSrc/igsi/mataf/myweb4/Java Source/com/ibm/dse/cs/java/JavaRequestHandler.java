package com.ibm.dse.cs.java;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import java.util.Vector;
import com.ibm.dse.clientserver.*;
import com.ibm.dse.cs.servlet.*;
import com.ibm.dse.base.*;

/**
 * This class implements <I>RequestHandler</I> interface and extends <I>JavaRequestHandler</I>.
 * <P> This class provides the implementation specific for the java client types
 * @copyright(c) Copyright IBM Corporation 2000, 2003.
 */
public class JavaRequestHandler extends DSERequestHandler {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
/**
 * JavaRequestHandler constructor.
 */
public JavaRequestHandler() {
	super();
}
/**
 * Close the operation.
 * @param anOperation com.ibm.dse.base.ServerOperation
 */
protected void endRequest(ServerOperation anOperation) {

	try {
		anOperation.close();
	} catch (DSEException e) {
		
	}
		

}
/**
 * Perform the operation for the Java client.
 * It may generate the following error codes during the process: CSS02, CSS03, CSS04, CSS05, CSS06, CSS07.
 * @param channelContext com.ibm.dse.clientserver.ChannelContext
 * @return aServerOperation com.ibm.dse.base.ServerOperation
 * @exception java.lang.Exception 
 */
protected ServerOperation executeRequest(ChannelContext channelContext) throws Exception {

	Context aContext;
	String sessionId, serverOperationName, parentContextName, formattedData, requestFormatName,tid, applicationId;
	int operationId;

	try{
		sessionId = getSessionId(channelContext);
	
		ServerOperation aServerOperation;
		KeyedCollection requestData = channelContext.getRequestData();
		errorCode = CSConstants.CSS02;
		
		serverOperationName = (String)requestData.getValueAt("data.operationName");
		parentContextName = (String)requestData.getValueAt("data.parentContextName");
		formattedData = (String)requestData.getValueAt("data.formattedData");
		requestFormatName = (String)requestData.getValueAt("data.requestFormatName");
		operationId = ((Integer)requestData.getValueAt("data.operationId")).intValue();
		applicationId = (String)requestData.getValueAt(CSConstants.DATAAPPLICATIONIDKEY);
		errorCode = CSConstants.CSS03;
	
		try {
			tid=Context.getTIDForSession(sessionId);
		} catch (Exception e) {
			tid="????";
		}
	
			if (Trace.doTrace(CSConstants.COMPID, Trace.Medium, Trace.Debug)) {
				String aMessage, aTID;
				StringBuffer sb = new StringBuffer();
				sb.append(getSessionId(channelContext));
				sb.append(": Data from client: ");
				sb.append(serverOperationName);
				sb.append(";");
				sb.append(requestFormatName);
				sb.append(";");
				sb.append(parentContextName);
				sb.append(";");
				sb.append(formattedData);
				sb.append(";");
				sb.append(sessionId);
				sb.append(";");
				sb.append(operationId);
				sb.append(";");
				sb.append(applicationId);
				aMessage = new String(sb);
				aTID = Settings.getTID();
				Trace.trace(CSConstants.COMPID,Trace.Medium,Trace.Debug, aTID, aMessage);
		 	}	
	
		// Security Check
		checkExecutionPermission(sessionId, serverOperationName, channelContext);
	
		aServerOperation = (ServerOperation) DSEOperation.readObject(serverOperationName);
		
		// Chain server operation to its context
		if(aServerOperation.getParent()==null) {
			errorCode = CSConstants.CSS04;
			if (parentContextName == null) {
				if (applicationId.equals(CSConstants.IGNORE)) {
					throw new DSECSRemoteOperationException("Must specify parent context when running out of session");
				} else {
					if (applicationId.equals("-1")) {
						aContext = Context.getCurrentContextForSession(sessionId);
					} else {
						aContext = Context.getCurrentContextForSession(sessionId, applicationId);
						aServerOperation.setApplicationId(applicationId);
					}
				}
			} else {
				aContext = Context.getContextNamed(parentContextName);		
			}
	
			if (aContext == null) {
				throw new DSECSRemoteOperationException("Failed: The context does not exist for the parent context " + parentContextName);
			}
			aServerOperation.chainTo(aContext);
		}
	
		// Unformat received data
		if (requestFormatName.compareTo("null")!=0) {
			errorCode = CSConstants.CSS05;
			FormatElement aRequestFormat = null;
			aRequestFormat= (FormatElement) aServerOperation.getFormat("csRequestFormat");
			if (aRequestFormat==null) aRequestFormat= (FormatElement) aServerOperation.getFormat(requestFormatName);
			if(aRequestFormat==null) aRequestFormat=(FormatElement) FormatElement.readObject(requestFormatName);
				errorCode = CSConstants.CSS06;
				aServerOperation.getFormats().put("csRequestFormat", aRequestFormat);
				aRequestFormat.unformat(formattedData, aServerOperation);
	
		} else {
			if (Trace.doTrace(CSConstants.COMPID, Trace.Medium, Trace.Debug)) {
				String aMessage, aTID;
				aMessage = "No data to unformat ";
				aTID = Settings.getTID();
				Trace.trace(CSConstants.COMPID,Trace.Medium,Trace.Debug, aTID,getSessionId(channelContext) +": "+ aMessage);
	 		}	
		}
		errorCode = CSConstants.CSS07;
		 
		//run the operation
		aServerOperation.runInSession(sessionId);
	
			
		//Required for application session management only if running with a valid session id
		//take care to ensure that this is not updated when the session is removed
		try {
			if (!applicationId.equals(CSConstants.IGNORE)) {
				Context.setTypeForSession(sessionId, channelContext.getDeviceType());
			if (usingCookies(channelContext)) {
				Context.setTimeStampForSession(sessionId, null);
				} else {
					Context.setTimeStampForSession(sessionId, new Long(System.currentTimeMillis()));
				}
			}
		} catch (DSEObjectNotFoundException dsee) {
			//Ignore the exception. This is not the end of the world. The session may have been removed by the operation
			if (Trace.doTrace(CSConstants.COMPID, Trace.Medium, Trace.Debug)) {
				String aMessage, aTID;
				aMessage = "No session when updating timestamp...moving on ";
				aTID = Settings.getTID();
				Trace.trace(CSConstants.COMPID,Trace.Medium,Trace.Debug, aTID,getSessionId(channelContext) +": "+ aMessage);
		 	}
		}
					
		return aServerOperation;
		
	} catch (Exception e) {
		e.printStackTrace();
		throw new DSEException(DSEException.harmless, errorCode, e.toString());
	}
}
/**
 * To support multiple client types using the same session the Java client requires additional processing
 * to check the application session table for a valid id
 * The parameter "createSession" can exist in the data kColl only
 * @param channelContext com.ibm.dse.clientserver.ChannelContext
 * @exception java.lang.Exception.
 */
protected void executeSession(ChannelContext channelContext)  throws Exception {

	boolean newSession = true;
	try {
		newSession = new Boolean((String)channelContext.getRequestData().getValueAt(CSConstants.DATACREATESESSIONKEY)).booleanValue();
	} catch (DSEObjectNotFoundException e) {
		return;
	}

	if (newSession) {
		//no additional processing required
		return;
	} else {
		//else use an existing session from the application session table with the TID parameter
		String tid = (String)channelContext.getRequestData().getValueAt(CSConstants.DATATIDKEY);
		String sessionId = Context.getSessionForTID(tid);
		if (sessionId == null) {
			if (Trace.doTrace(CSConstants.COMPID, Trace.Medium, Trace.Debug)) {
				String aMessage, aTID;
				aMessage = "The session has not yet been established for this workstation id(TID)";
				aTID = tid;
				Trace.trace(CSConstants.COMPID,Trace.Medium,Trace.Debug, aTID, "????: " + aMessage);
			}
			throw new DSECSSessionNotEstablishedException();
		}

		
		if (usingCookies(channelContext)) {
			Context.setTimeStampForSession(sessionId, null);
		} else {
			Context.setTimeStampForSession(sessionId, new Long(System.currentTimeMillis()));
		}

		//add the session id to the kColl to render to the client		
		DataField element = new DataField();
		element.setName(CSConstants.SESSIONIDKEY);
		element.setValue(sessionId);
		KeyedCollection kc = (KeyedCollection)channelContext.getRequestData().getElementAt(CSConstants.DATAKEY);
		kc.addElement(element);

		if (Trace.doTrace(CSConstants.COMPID, Trace.Medium, Trace.Debug)) {
			String aMessage, aTID;
			aMessage = "Use the application session id for the TID: " + tid;
			aTID = Settings.getTID();
			Trace.trace(CSConstants.COMPID,Trace.Medium,Trace.Debug, aTID, sessionId +": "+ aMessage);
 		}	

	}
}
/**
 * Convenience method to dertermine the session id from the channel session
 * @return java.lang.String
 * @param aChannelContext com.ibm.dse.clientserver.ChannelContext
 */
protected String getSessionId(ChannelContext aChannelContext) {

	String sessionId = null;

	if (aChannelContext.getChannelSession() == null) {
		return sessionId;
	}
	
	if (aChannelContext.getChannelSession() instanceof HttpSession) {
		sessionId = ((HttpSession)aChannelContext.getChannelSession()).getId();
	} else if (aChannelContext.getChannelSession() instanceof ChannelSession) {
		sessionId = ((ChannelSession)aChannelContext.getChannelSession()).getId();
	}

	return sessionId;
}
/**
 * Extend this method to perform any initial processing required by the request handler
 * <P> Verify that the server is started and the environment is initialized
 * @param channelContext com.ibm.dse.clientserver.ChannelContext
 * @exception com.ibm.dse.base.DSEException
 */
protected void preProcessRequest(ChannelContext channelContext) throws DSEException {

	if (Context.getCSServer() == null || Context.getCSServer().getServerStatus()==CSServer.STOPPED) {
		throw new DSECSServerNotStartedException("The server is not initiated");
	}
	
	
}
}
