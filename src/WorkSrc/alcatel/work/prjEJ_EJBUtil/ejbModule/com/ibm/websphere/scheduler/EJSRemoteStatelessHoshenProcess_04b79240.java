package com.ibm.websphere.scheduler;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessHoshenProcess_04b79240
 */
public class EJSRemoteStatelessHoshenProcess_04b79240 extends EJSWrapper implements TaskHandler {
	/**
	 * EJSRemoteStatelessHoshenProcess_04b79240
	 */
	public EJSRemoteStatelessHoshenProcess_04b79240() throws java.rmi.RemoteException {
		super();	}
	/**
	 * process
	 */
	public void process(com.ibm.websphere.scheduler.TaskStatus arg0) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	hoshen.scheduler.ejb.HoshenProcessBean beanRef = (hoshen.scheduler.ejb.HoshenProcessBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			beanRef.process(arg0);
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
		return ;
	}
}
