package com.ibm.websphere.scheduler;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessReceiveNotification_3af8bc0f
 */
public class EJSRemoteStatelessReceiveNotification_3af8bc0f extends EJSWrapper implements NotificationSink {
	/**
	 * EJSRemoteStatelessReceiveNotification_3af8bc0f
	 */
	public EJSRemoteStatelessReceiveNotification_3af8bc0f() throws java.rmi.RemoteException {
		super();	}
	/**
	 * handleEvent
	 */
	public void handleEvent(com.ibm.websphere.scheduler.TaskNotificationInfo arg0) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = arg0;
			}
	hoshen.scheduler.ejb.ReceiveNotificationBean beanRef = (hoshen.scheduler.ejb.ReceiveNotificationBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			beanRef.handleEvent(arg0);
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
