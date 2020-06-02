package com.ibm;

import java.rmi.RemoteException;
import java.security.PrivilegedAction;

import javax.ejb.CreateException;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import com.ibm.bpe.api.BusinessProcess;
import com.ibm.bpe.api.BusinessProcessHome;
import com.ibm.websphere.security.auth.callback.WSCallbackHandlerImpl;

/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RetrieveProcessJMS implements PrivilegedAction {
	public BusinessProcessJMS getProcess(String theUserid, String thePassword) {
		
		BusinessProcessJMS theProcess = null;
		Object result=null;

		LoginContext lc = null;


		try {
			// create a LoginContext and specify a CallbackHandler implementation
			// CallbackHandler implementation determin how authentication data is collected
			// in this case, the authentication data is "push" to the authentication mechanism
			//   implemented by the LoginModule.
						
			lc = new javax.security.auth.login.LoginContext("ClientContainer",
					new WSCallbackHandlerImpl (theUserid, thePassword));
		} catch (LoginException e) {
			e.printStackTrace();
			return null;
		}


		if (lc != null)
		{
			try {
				lc.login();  // perform login
			} catch (LoginException e) {
				e.printStackTrace();
				return null;
			}
			javax.security.auth.Subject s = lc.getSubject();
			// get the authenticated subject

			// Invoke a J2EE resources using the authenticated subject
			result = com.ibm.websphere.security.auth.WSSubject.doAs(s,this);
		}
		if (result==null)
			System.out.println("Error retrieving process JMS");
		return (BusinessProcessJMS) result;
	}

	public Object run()
	{
		Object result;
		BusinessProcessJMS theProcess=null;
		try {
			Context initialContext = new InitialContext();
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup("jms/BPECF");
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			Queue bpeQueue = (Queue) initialContext.lookup("java:comp/env/jms/BPEApiQueue");
			Queue replyToQueue = (Queue) initialContext.lookup("java:comp/env/jms/receiverQ");

			theProcess = new BusinessProcessJMS(queueConnection, bpeQueue, replyToQueue);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return theProcess;
		
		
	}
	
	public BusinessProcessJMS getProcess()
	{
		BusinessProcessJMS theProcess=null;
		try {
			Context initialContext = new InitialContext();
			QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) initialContext.lookup("jms/BPECF");
			QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
			Queue bpeQueue = (Queue) initialContext.lookup("java:comp/env/jms/BPEApiQueue");
			Queue replyToQueue = (Queue) initialContext.lookup("java:comp/env/jms/receiverQ");

			theProcess = new BusinessProcessJMS(queueConnection, bpeQueue, replyToQueue);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return theProcess;
		
		
		
	}
	
	

}
