/*
 * Created on: 11/01/2005
 * @author: baruch hizkya
 * @version $Id: EJBLocator.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

import java.rmi.*;
import javax.ejb.CreateException;
import javax.naming.NamingException;

/**
 * Interface for find EJB
 */
public interface EJBLocator
{
	public javax.ejb.EJBObject getEJB() throws NamingException, RemoteException, CreateException;
	public javax.ejb.EJBObject getEJB(ServerDefinition serverDefinition) throws NamingException, RemoteException, CreateException;
	public void setAlwaysLookup(boolean alwaysLookup);
}
