package onjlib.ejb.invoker;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessInvoker_56d8a526
 */
public class EJSRemoteStatelessInvoker_56d8a526 extends EJSWrapper implements Invoker {
	/**
	 * EJSRemoteStatelessInvoker_56d8a526
	 */
	public EJSRemoteStatelessInvoker_56d8a526() throws java.rmi.RemoteException {
		super();	}
	/**
	 * getDeployedSupport
	 */
	public com.ibm.ejs.container.EJSDeployedSupport getDeployedSupport() {
		return container.getEJSDeployedSupport(this);
	}
	/**
	 * putDeployedSupport
	 */
	public void putDeployedSupport(com.ibm.ejs.container.EJSDeployedSupport support) {
		container.putEJSDeployedSupport(support);
		return;
	}
	/**
	 * invoke
	 */
	public java.lang.Object invoke(java.lang.Class c, java.lang.String methodName, java.util.ArrayList args) throws java.lang.Exception {
		EJSDeployedSupport _EJS_s = getDeployedSupport();
		Object[] _jacc_parms = null;
		java.lang.Object _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[3];
				_jacc_parms[0] = c;
				_jacc_parms[1] = methodName;
				_jacc_parms[2] = args;
			}
	onjlib.ejb.invoker.InvokerBean beanRef = (onjlib.ejb.invoker.InvokerBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.invoke(c, methodName, args);
		}
		catch (java.lang.RuntimeException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
		}
		catch (java.lang.Exception ex) {
			_EJS_s.setCheckedException(ex);
			throw ex;
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedException(ex);
			throw new java.rmi.RemoteException("bean method raised unchecked exception", ex);
		}

		finally {
			try{
				container.postInvoke(this, 0, _EJS_s);
			} finally {
				putDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
}
