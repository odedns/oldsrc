package onjlib.ejb.dispatcher;

import com.ibm.ejs.container.*;
import java.rmi.RemoteException;

/**
 * EJSLocalStatelessDispatcher_f34506e1
 */
public class EJSLocalStatelessDispatcher_f34506e1 extends EJSLocalWrapper implements onjlib.ejb.dispatcher.DispatcherLocal {
	/**
	 * EJSLocalStatelessDispatcher_f34506e1
	 */
	public EJSLocalStatelessDispatcher_f34506e1() {
		super();	}
	/**
	 * executeCommand
	 */
	public onjlib.command.CommandParams executeCommand(onjlib.command.CommandParams params) {
		EJSDeployedSupport _EJS_s = container.getEJSDeployedSupport(this);
		Object[] _jacc_parms = null;
		onjlib.command.CommandParams _EJS_result = null;
		try {
			if ( container.doesJaccNeedsEJBArguments(this) )
			{
				_jacc_parms = new Object[1];
				_jacc_parms[0] = params;
			}
	onjlib.ejb.dispatcher.DispatcherBean beanRef = (onjlib.ejb.dispatcher.DispatcherBean)container.preInvoke(this, 0, _EJS_s, _jacc_parms);
			_EJS_result = beanRef.executeCommand(params);
		}
		catch (java.rmi.RemoteException ex) {
		 	_EJS_s.setUncheckedLocalException(ex);
		}
		catch (Throwable ex) {
			_EJS_s.setUncheckedLocalException(ex);
		}

		finally {
			try {
				container.postInvoke(this, 0, _EJS_s);
			} catch ( RemoteException ex ) {
				_EJS_s.setUncheckedLocalException(ex);
			} finally {
				container.putEJSDeployedSupport(_EJS_s);
			}
		}
		return _EJS_result;
	}
}
