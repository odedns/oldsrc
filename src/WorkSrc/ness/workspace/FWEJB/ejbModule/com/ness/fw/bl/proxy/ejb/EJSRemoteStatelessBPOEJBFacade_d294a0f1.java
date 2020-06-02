package com.ness.fw.bl.proxy.ejb;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessBPOEJBFacade_d294a0f1
 */
public class EJSRemoteStatelessBPOEJBFacade_d294a0f1 extends EJSWrapper implements BPOEJBFacade {
	/**
	 * EJSRemoteStatelessBPOEJBFacade_d294a0f1
	 */
	public EJSRemoteStatelessBPOEJBFacade_d294a0f1() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getDeployedSupport
	 */
	public com.ibm.ejs.container.EJSDeployedSupport getDeployedSupport() {
		return container.getEJSDeployedSupport(this);
	}
	/**
	 * putDeployedSupport
	 */
	public void putDeployedSupport(com.ibm.ejs.container.EJSDeployedSupport support) {
		container.putEJSDeployedSupport(support);
		return;
	}
	/**
	 * execute
	 */
	public com.ness.fw.bl.proxy.EJBContainer execute(java.lang.String bpoCommand, com.ness.fw.bl.BusinessProcessContainer bpc) throws com.ness.fw.bl.proxy.BPOCommandNotFoundException, com.ness.fw.bl.proxy.BPOCommandException, com.ness.fw.common.exceptions.BusinessLogicException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = getDeployedSupport();
		com.ness.fw.bl.proxy.EJBContainer _EJS_result = null;
		try {
			com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean beanRef = (com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean)container.preInvoke(this, 0, _EJS_s);
			_EJS_result = beanRef.execute(bpoCommand, bpc);
		}
		catch (com.ness.fw.bl.proxy.BPOCommandNotFoundException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (com.ness.fw.bl.proxy.BPOCommandException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (com.ness.fw.common.exceptions.BusinessLogicException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 0, _EJS_s);
			} finally {
				putDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * execute
	 */
	public com.ness.fw.bl.proxy.EJBContainer execute(java.lang.String bpoCommand, com.ness.fw.bl.BusinessProcessContainer bpcIn, com.ness.fw.bl.BusinessProcessContainer bpcOut) throws com.ness.fw.bl.proxy.BPOCommandNotFoundException, com.ness.fw.bl.proxy.BPOCommandException, com.ness.fw.common.exceptions.BusinessLogicException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = getDeployedSupport();
		com.ness.fw.bl.proxy.EJBContainer _EJS_result = null;
		try {
			com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean beanRef = (com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean)container.preInvoke(this, 1, _EJS_s);
			_EJS_result = beanRef.execute(bpoCommand, bpcIn, bpcOut);
		}
		catch (com.ness.fw.bl.proxy.BPOCommandNotFoundException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (com.ness.fw.bl.proxy.BPOCommandException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (com.ness.fw.common.exceptions.BusinessLogicException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 1, _EJS_s);
			} finally {
				putDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * reloadSystemConfig
	 */
	public void reloadSystemConfig() throws com.ness.fw.common.lock.SynchronizationLockException, com.ness.fw.common.SystemInitializationException, java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = getDeployedSupport();
		
		try {
			com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean beanRef = (com.ness.fw.bl.proxy.ejb.BPOEJBFacadeBean)container.preInvoke(this, 2, _EJS_s);
			beanRef.reloadSystemConfig();
		}
		catch (com.ness.fw.common.lock.SynchronizationLockException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (com.ness.fw.common.SystemInitializationException ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 2, _EJS_s);
			} finally {
				putDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
