package onjlib.ejb.mytest;

import com.ibm.ejs.container.*;

/**
 * EJSStatelessmytestHomeBean_c69cb50d
 */
public class EJSStatelessmytestHomeBean_c69cb50d extends EJSHome {
	/**
	 * EJSStatelessmytestHomeBean_c69cb50d
	 */
	public EJSStatelessmytestHomeBean_c69cb50d() throws java.rmi.RemoteException {
		super();	}
	/**
	 * create
	 */
	public onjlib.ejb.mytest.Mytest create() throws java.rmi.RemoteException, javax.ejb.CreateException {
BeanO beanO = null;
onjlib.ejb.mytest.Mytest result = null;
boolean createFailed = false;
try {
	result = (onjlib.ejb.mytest.Mytest) super.createWrapper(null);
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
	public onjlib.ejb.mytest.MytestLocal create_Local() throws javax.ejb.CreateException, java.rmi.RemoteException {
BeanO beanO = null;
onjlib.ejb.mytest.MytestLocal result = null;
boolean createFailed = false;
boolean preCreateFlag = false;
try {
	result = (onjlib.ejb.mytest.MytestLocal) super.createWrapper_Local(null);
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
