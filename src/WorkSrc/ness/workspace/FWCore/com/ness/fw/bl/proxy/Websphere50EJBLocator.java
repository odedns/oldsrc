/*
 * Created on: 11/01/2005
 * @author: baruch hizkya
 * @version $Id: Websphere50EJBLocator.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import com.ness.fw.bl.proxy.ejb.BPOEJBFacade;
import com.ness.fw.bl.proxy.ejb.BPOEJBFacadeHome;


/**
 * 
 */
public class Websphere50EJBLocator implements EJBLocator
{

	boolean alwaysLookup = false;

	public void setAlwaysLookup(boolean alwaysLookup)
	{
		this.alwaysLookup = alwaysLookup;
	}
	
	public EJBObject getEJB() throws NamingException, RemoteException, CreateException
	{
		return getEJB(ServerExternalizer.getDefaultServerDefinition());			
	}

	public EJBObject getEJB(ServerDefinition serverDefinition) throws NamingException, RemoteException, CreateException
	{
		BPOEJBFacade facade = null;
		Context initial = new InitialContext(serverDefinition.getEJBProperties());
		// TODO: there is a problem with the ref, so 4 use without ref.
		//		 Context myEnv = (Context)initial.lookup("java:comp/env"); 	
		//		 Object objref = myEnv.lookup(serverDefinition.getRefName()); 	

		Object objref = initial.lookup(serverDefinition.getJndiName()); 			
		BPOEJBFacadeHome home = 
		(BPOEJBFacadeHome) PortableRemoteObject.narrow(objref,
		BPOEJBFacadeHome.class); 
		facade = home.create();		
		return facade;
	}
}
