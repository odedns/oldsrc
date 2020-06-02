package com.ibm;

import java.rmi.RemoteException;
import java.security.Identity;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Properties;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import sun.security.acl.PrincipalImpl;

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
public class RetrieveProcessEJB implements PrivilegedAction {
	public BusinessProcess getProcess(String theUserid, String thePassword) {
		BusinessProcess theProcess = null;
		Object result = null;
		LoginContext lc = null;
		try {
			// create a LoginContext and specify a CallbackHandler implementation
			// CallbackHandler implementation determin how authentication data is collected
			// in this case, the authentication data is "push" to the authentication mechanism
			//   implemented by the LoginModule.
			lc = new javax.security.auth.login.LoginContext("ClientContainer", new WSCallbackHandlerImpl(theUserid, thePassword));
		} catch (LoginException e) {
			e.printStackTrace();
			return null;
		}
		if (lc != null) {
			try {
				lc.login(); // perform login
				// get the authenticated subject			
				javax.security.auth.Subject s = lc.getSubject();
				// Invoke a J2EE resources using the authenticated subject
				result = com.ibm.websphere.security.auth.WSSubject.doAs(s, this);
			} catch (LoginException e) {
				e.printStackTrace();
				return null;
			}
		}
		if (result == null)
			System.out.println("Error retrieving process EJB");
		return (BusinessProcess) result;
	}
	public Object run() {
		Object result;
		BusinessProcess theProcess = null;
		try {
			Context initialContext = new InitialContext();
			result = initialContext.lookup("java:comp/env/ejb/BusinessProcessHome");
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		// Convert the lookup result to the proper type
		BusinessProcessHome processHome = (BusinessProcessHome) javax.rmi.PortableRemoteObject.narrow(result, BusinessProcessHome.class);
		try {
			theProcess = processHome.create();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return theProcess;
	}
	public BusinessProcess getProcess() {
		Object result;
		BusinessProcess theProcess;
		try {
			Context initialContext = new InitialContext();
			result = initialContext.lookup("java:comp/env/ejb/BusinessProcessHome");
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
		// Convert the lookup result to the proper type
		BusinessProcessHome processHome = (BusinessProcessHome) javax.rmi.PortableRemoteObject.narrow(result, BusinessProcessHome.class);
		try {
			theProcess = processHome.create();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return theProcess;
	}
}
