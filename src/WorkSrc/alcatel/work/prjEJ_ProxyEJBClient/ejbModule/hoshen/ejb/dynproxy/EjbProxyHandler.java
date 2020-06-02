/*
 * Created on 27/09/2005
 *
 */
package hoshen.ejb.dynproxy;

import hoshen.common.utils.PropertyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author odedn
 *  
 */
public class EjbProxyHandler extends AbstractProxyHandler {

	private static final String INVOKER_EJB_HOME = "invoker.ejbhome";

	private static final String URL = "iiop://localhost:2809";

	private static final String FACTORY = "com.ibm.websphere.naming.WsnInitialContextFactory";

	private static Logger log = Logger.getLogger(EjbProxyHandler.class);

	/**
	 * Invoke the EJB on the server side.
	 * 
	 * @param className
	 *            the name of the implementation class
	 * @param methodName
	 *            the name of the method to execute/
	 * @param args
	 *            array of params.
	 * @return the retrun value.
	 * @throws Throwable
	 *             in case of error.
	 */
	protected Object invokeServer(String className, String methodName,
			Class[] params, Object[] args) throws Throwable {
		InitialContext ctx = getInitialContext();
		String ejbHome = PropertyManager.getInstance().getProperty(
				INVOKER_EJB_HOME);
		Object o = ctx.lookup(ejbHome);

		InvokerHome invokerHome = (InvokerHome) javax.rmi.PortableRemoteObject
				.narrow(o, InvokerHome.class);
		Invoker invoker = invokerHome.create();
		log.debug("got invoker invoking class=" + className + "\tmethod = "
				+ methodName);
		Object result;
		try {
			List paramsList= ClassUtilsHelper.convertClassesToClassNames(Arrays.asList(params));
			result = invoker.invoke(className, methodName, paramsList, args, StringUtils.EMPTY); 
		} catch (InvocationTargetException invocationTargetException) {
			throw invocationTargetException.getCause();
		}
		return (result);
	}

	/**
	 * getInitialContext
	 * 
	 * @return InitialContext
	 * @throws Throwable
	 */
	private InitialContext getInitialContext() throws Throwable {

		InitialContext ctx = null;
		if (this.url != null) {
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
			prop.put(Context.PROVIDER_URL, url);
			ctx = new InitialContext(prop);
		} else {
			ctx = new InitialContext();
		}
		return (ctx);
	}

}
