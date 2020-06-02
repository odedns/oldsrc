package com.ibm.websphere.startupservice;

import com.ibm.ejs.container.*;

/**
 * EJSStatefulHoshenStartupBeanHomeBean_e7b43101
 */
public class EJSStatefulHoshenStartupBeanHomeBean_e7b43101 extends EJSHome {
	static final long serialVersionUID = 61;
	/**
	 * EJSStatefulHoshenStartupBeanHomeBean_e7b43101
	 */
	public EJSStatefulHoshenStartupBeanHomeBean_e7b43101() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public com.ibm.websphere.startupservice.AppStartUp create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
com.ibm.websphere.startupservice.AppStartUp result = null;
boolean createFailed = false;
boolean preCreateFlag = false;
try {
	beanO = super.createBeanO();
	hoshen.startup.ejb.HoshenStartupBean bean = (hoshen.startup.ejb.HoshenStartupBean) beanO.getEnterpriseBean();
preCreateFlag = super.preEjbCreate(beanO);
	bean.ejbCreate();
	result = (com.ibm.websphere.startupservice.AppStartUp) super.postCreate(beanO);
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
	if(preCreateFlag && !createFailed)
		super.afterPostCreateCompletion(beanO);
	if (createFailed) {
		super.createFailure(beanO);
	}
}
return result;	}
}
