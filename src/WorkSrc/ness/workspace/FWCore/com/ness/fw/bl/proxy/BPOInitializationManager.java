/*
 * Created on: 26/01/2005
 * @author: baruch hizkya
 * @version $Id: BPOInitializationManager.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */

package com.ness.fw.bl.proxy;

import java.rmi.RemoteException;

/**
 * @author bhizkya
 *
 */
public class BPOInitializationManager
{
	
	public static void reload(String serversConfigurationLocation) throws RemoteException, Throwable
	{
		BPOProxy.reloadServers(serversConfigurationLocation);
	}
}
