package com.ibm.websphere.scheduler;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessHoshenProcessHomeBean_04b79240
 */
public class EJSStatelessHoshenProcessHomeBean_04b79240 extends EJSHome {
	static final long serialVersionUID = 61;
	/**
	 * EJSStatelessHoshenProcessHomeBean_04b79240
	 */
	public EJSStatelessHoshenProcessHomeBean_04b79240() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ibm.websphere.scheduler.TaskHandler create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ibm.websphere.scheduler.TaskHandler result = null;
boolean createFailed = false;
try {
	result = (com.ibm.websphere.scheduler.TaskHandler) super.createWrapper(null);
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
