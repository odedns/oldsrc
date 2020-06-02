package com.ibm.websphere.startupservice;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatefulHoshenStartupBean_e7b43101
 */
public class EJSRemoteStatefulHoshenStartupBean_e7b43101 extends EJSWrapper implements AppStartUp {
	/**
	 * EJSRemoteStatefulHoshenStartupBean_e7b43101
	 */
	public EJSRemoteStatefulHoshenStartupBean_e7b43101() throws java.rmi.RemoteException {
		super();	}
	/**
	 * start
	 */
	public boolean start() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		boolean _EJS_result = false;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	hoshen.startup.ejb.HoshenStartupBean beanRef = (hoshen.startup.ejb.HoshenStartupBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.start();
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
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
	/**
	 * stop
	 */
	public void stop() throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[0];
			}
	hoshen.startup.ejb.HoshenStartupBean beanRef = (hoshen.startup.ejb.HoshenStartupBean)container.preInvoke(this, 1, _EJS_s, _jacc_parms);
			beanRef.stop();
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
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return ;
	}
}
