package com.ness.fw.bl.proxy.ejb;
/**
 * Home interface for Enterprise Bean: BPOEJBFacade
 */
public interface BPOEJBFacadeHome extends javax.ejb.EJBHome
{
	/**
	 * Creates a default instance of Session Bean: BPOEJBFacade
	 */
	public com.ness.fw.bl.proxy.ejb.BPOEJBFacade create() throws javax.ejb.CreateException, java.rmi.RemoteException;
}
