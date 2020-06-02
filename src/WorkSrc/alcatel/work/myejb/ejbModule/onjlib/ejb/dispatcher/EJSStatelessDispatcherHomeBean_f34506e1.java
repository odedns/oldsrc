package onjlib.ejb.dispatcher;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessDispatcherHomeBean_f34506e1
 */
public class EJSStatelessDispatcherHomeBean_f34506e1 extends EJSHome {
	/**
	 * EJSStatelessDispatcherHomeBean_f34506e1
	 */
	public EJSStatelessDispatcherHomeBean_f34506e1() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public onjlib.ejb.dispatcher.Dispatcher create() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
onjlib.ejb.dispatcher.Dispatcher result = null;
boolean createFailed = false;
try {
	result = (onjlib.ejb.dispatcher.Dispatcher) super.createWrapper(null);
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
	public onjlib.ejb.dispatcher.DispatcherLocal create_Local() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
onjlib.ejb.dispatcher.DispatcherLocal result = null;
boolean createFailed = false;
boolean preCreateFlag = false;
try {
	result = (onjlib.ejb.dispatcher.DispatcherLocal) super.createWrapper_Local(null);
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
