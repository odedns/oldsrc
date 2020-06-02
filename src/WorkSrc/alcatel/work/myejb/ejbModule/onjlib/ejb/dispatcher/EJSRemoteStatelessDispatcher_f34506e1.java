package onjlib.ejb.dispatcher;

import com.ibm.ejs.container.*;

/**
 * EJSRemoteStatelessDispatcher_f34506e1
 */
public class EJSRemoteStatelessDispatcher_f34506e1 extends EJSWrapper implements Dispatcher {
	/**
	 * EJSRemoteStatelessDispatcher_f34506e1
	 */
	public EJSRemoteStatelessDispatcher_f34506e1() throws java.rmi.RemoteException {
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
	 * executeCommand
	 */
	public onjlib.command.CommandParams executeCommand(onjlib.command.CommandParams params) throws java.rmi.RemoteException {
		EJSDeployedSupport _EJS_s = getDeployedSupport();
		Object[] _jacc_parms = null;
		onjlib.command.CommandParams _EJS_result = null;
		try {
			if (container.doesJaccNeedsEJBArguments( this ))
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = params;
			}
	onjlib.ejb.dispatcher.DispatcherBean beanRef = (onjlib.ejb.dispatcher.DispatcherBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.executeCommand(params);
		}
		catch (java.rmi.RemoteException ex) {
			_EJS_s.setUncheckedException(ex);
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
