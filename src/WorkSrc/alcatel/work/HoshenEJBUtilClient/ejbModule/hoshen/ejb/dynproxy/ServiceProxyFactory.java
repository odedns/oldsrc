/*
 * Created on 06/09/2005
 * 
 */
package hoshen.ejb.dynproxy;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import hoshen.common.utils.exception.HoshenException;
import hoshen.common.utils.PropertyManager;

/**
 * @author Oded Nissan
 *
 * This class is a factory used to create service object implementation.
 * The factory reads a property file serviceproxy.properties and determines which object
 * will be returned by the factory: the real object implementation or 
 * a proxy object implementation.
 */
public class ServiceProxyFactory {

	private static final String IMPL = ".impl";
	private static final String INTERFACE = ".interface";
	private static final String USE_PROXY = ".userProxy";
	
	/**
	 * Get the service object implemntation.
	 * The object returned will be either the object implementation
	 * or an object proxy, depending on values in a property file.
	 * @param name the name of the service ( the key in the property file)
	 * @return Object the service implementation.
	 * @throws HoshenException in case of error.
	 */
	public static Object getService(String name) throws HoshenException
	{
		Object serviceImpl = null;
		PropertyManager pm = null;
		Class c = null;
		Class serviceInterface= null;
		
		try {
			pm = PropertyManager.getInstance();
		} catch(IOException ie) {
			throw new HoshenException("Error initializing propertyManager", ie);
		}
		String className = getClassImpl(name,pm);
		boolean useProxy = useProxy(name,pm);
		System.out.println("className=" + className);
		/*
		 * either return the class implementation or 
		 * the proxy object.
		 */
		if(!useProxy) {
			try {
				serviceImpl = Class.forName(className).newInstance();
			} catch(Exception e) {
				throw new HoshenException("Error creating service implementation", e);
			}
		} else {
			try {
				c = Class.forName(className);		
				String interfaceName = getInterface(name,pm);
				serviceInterface = Class.forName(interfaceName);
			} catch(ClassNotFoundException ce) {
				throw new HoshenException("Error creating service implementation", ce);
			}			
			serviceImpl = getProxyObject(serviceInterface,c);
			
		}
		return(serviceImpl);
		
	}
	
	
	/**
	 * check if we should use a proxy for this object.
	 * @param name the name of the service object.
	 * @param pm the PropertyManager
	 * @return boolean true in case we use a proxy.
	 */
	private static boolean useProxy(String name, PropertyManager pm)
	{
		String key = name + USE_PROXY;
		
		boolean useProxy = pm.getBooleanProperty(key,false);
		return(useProxy);				
	}
	
	/**
	 * Return the name of the service implementation class.
	 * @param name the name of the service object.
	 * @param pm the PropertyManager
	 * @return String the class name.
	 */
	private static String getClassImpl(String name, PropertyManager pm)
	{
		String key = name + IMPL;
		String className = pm.getProperty(key);
		return(className);
	}
	/**
	 * Return the name of the service interface class.
	 * @param name the name of the service object.
	 * @param pm the PropertyManager
	 * @return String the class name.
	 */
	private static String getInterface(String name, PropertyManager pm)
	{
		String key = name + INTERFACE;
		String className = pm.getProperty(key);
		return(className);
	}
	

	private static Object getProxyObject(Class interfaceClass, Class implClass)
	{
		InvocationHandler handler = new ProxyHandler(implClass);
		Object proxyObj = Proxy.newProxyInstance(implClass.getClassLoader(),
												  new Class[] { interfaceClass },
												  handler);
                                          
		return(proxyObj);
	}
}
