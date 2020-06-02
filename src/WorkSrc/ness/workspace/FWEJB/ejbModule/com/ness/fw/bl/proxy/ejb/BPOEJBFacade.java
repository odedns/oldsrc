package com.ness.fw.bl.proxy.ejb;
import com.ness.fw.bl.BusinessProcessContainer;
import com.ness.fw.bl.proxy.BPOCommandNotFoundException;
import com.ness.fw.bl.proxy.BPOCommandException;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.bl.proxy.EJBContainer;
import com.ness.fw.common.lock.SynchronizationLockException;
import com.ness.fw.common.SystemInitializationException;
/**
 * Remote interface for Enterprise Bean: BPOEJBFacade
 */
public interface BPOEJBFacade extends javax.ejb.EJBObject
{
	/**
	 * Execute the bpo command using the {@link BPOFacadeEJB} object.
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpc The {@link BusinessProcessContainer} to pass the method as an argument.
	 * @return Object The return value of the business process method.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public EJBContainer execute(String bpoCommand, BusinessProcessContainer bpc)
		throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException, java.rmi.RemoteException;
	/**
	 * Execute the bpo command using the {@link BPODispatcher}. 
	 * @param bpoCommandName The name of the command to execute.
	 * @param bpcIn The <code>BusinessProcessContainer</code> to pass the method as an input argument.
	 * @param bpcOut The <code>BusinessProcessContainer</code> to pass the method as an output argument.
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public EJBContainer execute(String bpoCommand, BusinessProcessContainer bpcIn, BusinessProcessContainer bpcOut)
		throws BPOCommandNotFoundException, BPOCommandException, BusinessLogicException, java.rmi.RemoteException;
	public void reloadSystemConfig() throws SynchronizationLockException, SystemInitializationException, java.rmi.RemoteException;
}
