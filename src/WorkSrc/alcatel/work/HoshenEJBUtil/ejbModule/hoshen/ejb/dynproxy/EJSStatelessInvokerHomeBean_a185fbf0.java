package hoshen.ejb.dynproxy;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessInvokerHomeBean_a185fbf0
 */
public class EJSStatelessInvokerHomeBean_a185fbf0 extends EJSHome {
	/**
	 * EJSStatelessInvokerHomeBean_a185fbf0
	 */
	public EJSStatelessInvokerHomeBean_a185fbf0() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public hoshen.ejb.dynproxy.Invoker create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
hoshen.ejb.dynproxy.Invoker result = null;
boolean createFailed = false;
try {
	result = (hoshen.ejb.dynproxy.Invoker) super.createWrapper(null);
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
