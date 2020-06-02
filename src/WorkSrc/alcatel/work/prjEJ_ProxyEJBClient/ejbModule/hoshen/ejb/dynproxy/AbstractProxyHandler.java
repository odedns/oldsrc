package hoshen.ejb.dynproxy;

import hoshen.common.utils.xstream.XStreamFacade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * @author Oded Nissan
 *  
 */
public abstract class AbstractProxyHandler implements InvocationHandler {

	private String realClassName;
	protected String url = null;


	private static Logger log = Logger.getLogger(AbstractProxyHandler.class);

	/**
	 * default empty constructor
	 *  
	 */
	public AbstractProxyHandler() {
	}

/**
	 * set the initial context url to use.
	 * @param url the initial context url.
	 */
	public void setInitialContext(String url)
	{
		this.url = url;
	}
	
	/**
	 * invoke.
	 */
	public Object invoke(Object proxy, Method m,Object[] args)
			throws Throwable {
		long before = Calendar.getInstance().getTimeInMillis();
		XStream xstream=XStreamFacade.getXStreamFacade();
		String xml=xstream.toXML(args);
		Object o = invokeServer(realClassName, m.getName(), m.getParameterTypes(),args);
		long after = Calendar.getInstance().getTimeInMillis();
		double time = (after - before) / 1000;
		log.debug(realClassName + "#" + m.getName() + "() took " + time + " seconds.");
		return (o);

	}

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
	protected abstract Object invokeServer(String className, String methodName,
			Class[] params,Object[] args) throws Throwable;

	/**
	 * @return Returns the realClassName.
	 */
	public String getRealClassName() {
		return realClassName;
	}

	/**
	 * @param realClassName
	 *            The realClassName to set.
	 */
	public void setRealClassName(String realClassName) {
		this.realClassName = realClassName;
	}
}
