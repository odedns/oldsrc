package com.ness.fw.bl.proxy.ejb;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessBPOEJBFacadeHomeBean_d294a0f1
 */
public class EJSStatelessBPOEJBFacadeHomeBean_d294a0f1 extends EJSHome {
	/**
	 * EJSStatelessBPOEJBFacadeHomeBean_d294a0f1
	 */
	public EJSStatelessBPOEJBFacadeHomeBean_d294a0f1() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ness.fw.bl.proxy.ejb.BPOEJBFacade create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ness.fw.bl.proxy.ejb.BPOEJBFacade result = null;
boolean createFailed = false;
try {
	result = (com.ness.fw.bl.proxy.ejb.BPOEJBFacade) super.createWrapper(new BeanId(this, null));
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
	/**
	 * create_Local
	 */
	public com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocal create_Local() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocal result = null;
boolean createFailed = false;
boolean preCreateFlag = false;
try {
	result = (com.ness.fw.bl.proxy.ejb.BPOEJBFacadeLocal) super.createWrapper_Local(null);
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
