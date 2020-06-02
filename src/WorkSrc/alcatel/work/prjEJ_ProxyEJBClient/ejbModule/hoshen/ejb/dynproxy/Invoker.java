package hoshen.ejb.dynproxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.ejb.EJBObject;

import java.util.List;
/**
 * Remote interface for Enterprise Bean: Invoker
 */
public interface Invoker extends EJBObject {
	
	/**
	 * wraps the other <code>invoke()</code> method in this bean. meant to be
	 * used by a web service.
	 * 
	 * @param c
	 * @param methodName
	 * @param args
	 * @param identity Contains the identity of the user.
	 * @return
	 */
	public String invoke(
		String className,
		String methodName,
		String params,
		String args,
		String identity) throws java.rmi.RemoteException;

	/**
	 * @author yakovl
	 * invoke a method on the specific object.
	 * 
	 * @param className
	 * @param methodName
	 * @param paramsStrings
	 * @param identity Contains the identity of the user.
	 * @throws InvocationTargetException
	 *             invocation failure
	 * @return
	 */
	public Object invoke(
		String className,
		String methodName,
		List paramsStrings,
		Object[] args,
		String identity)
		throws InvocationTargetException,
		java.rmi.RemoteException;

	/*
	 * @author yakovl, nadavw
	 * Invoke a method on the specified class.
	 *
	 * @param className The implementation class.
	 * @param methodName The name of the method to be invoked.
	 * @param paramsStrings A list of parameter class-names (for the method signature).
	 * @param args An array of arguments.
	 * @param identity Contains the identity of the user.
	 * @throws InvocationTargetException In case the invocation threw an exception.
	 * @return The result of the invocation.
	 */

}
