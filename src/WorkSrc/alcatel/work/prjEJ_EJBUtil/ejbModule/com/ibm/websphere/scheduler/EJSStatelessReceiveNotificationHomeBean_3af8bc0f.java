package com.ibm.websphere.scheduler;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessReceiveNotificationHomeBean_3af8bc0f
 */
public class EJSStatelessReceiveNotificationHomeBean_3af8bc0f extends EJSHome {
	static final long serialVersionUID = 61;
	/**
	 * EJSStatelessReceiveNotificationHomeBean_3af8bc0f
	 */
	public EJSStatelessReceiveNotificationHomeBean_3af8bc0f() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ibm.websphere.scheduler.NotificationSink create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ibm.websphere.scheduler.NotificationSink result = null;
boolean createFailed = false;
try {
	result = (com.ibm.websphere.scheduler.NotificationSink) super.createWrapper(null);
}
catch (javax.ejb.CreateException ex) {
	createFailed = true;
	throw ex;
} catch (java.rmi.RemoteException ex) {
	createFailed = true;
	throw ex;
} catch (Throwable ex) {
	createFailed = true;
	throw new CreateFailureException(ex);
} finally {
	if (createFailed) {
		super.createFailure(beanO);
	}
}
return result;	}
}
