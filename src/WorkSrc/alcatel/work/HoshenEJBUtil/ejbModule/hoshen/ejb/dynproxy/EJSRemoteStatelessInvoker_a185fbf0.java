package hoshen.ejb.dynproxy;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessInvoker_a185fbf0
 */
public class EJSRemoteStatelessInvoker_a185fbf0 extends EJSWrapper implements Invoker {
	/**
	 * EJSRemoteStatelessInvoker_a185fbf0
	 */
	public EJSRemoteStatelessInvoker_a185fbf0() throws java.rmi.RemoteException {
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
		java.lang.Object _EJS_result = null;
		try {
			hoshen.ejb.dynproxy.InvokerBean beanRef = (hoshen.ejb.dynproxy.InvokerBean)container.preInvoke(this, 0, _EJS_s);
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
