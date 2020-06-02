package onjlib.ejb.invoker;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessInvokerHomeBean_56d8a526
 */
public class EJSStatelessInvokerHomeBean_56d8a526 extends EJSHome {
	/**
	 * EJSStatelessInvokerHomeBean_56d8a526
	 */
	public EJSStatelessInvokerHomeBean_56d8a526() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public onjlib.ejb.invoker.Invoker create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
onjlib.ejb.invoker.Invoker result = null;
boolean createFailed = false;
try {
	result = (onjlib.ejb.invoker.Invoker) super.createWrapper(null);
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
