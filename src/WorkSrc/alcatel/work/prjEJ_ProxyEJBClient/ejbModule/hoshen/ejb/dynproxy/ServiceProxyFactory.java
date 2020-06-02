/*
 * Created on 06/09/2005
 * 
 */
package hoshen.ejb.dynproxy;

import hoshen.common.utils.PropertyManager;
import hoshen.common.utils.exception.HoshenException;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;




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
	private static final String USE_PROXY = ".useProxy";
	private static final String INVOKER_HANDLER = "invoker.handler";
	private static final String DEFAULT_PROXY_HANDLER_NAME = "hoshen.ejb.dynproxy.EjbProxyHandler";
	private static Logger log = Logger.getLogger(ServiceProxyFactory.class);
	private final static Map proxyCache=new HashMap();
	
	
	
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
		return(getService(name,null));
	}
	/**
	 * Get the service object implemntation.
	 * The object returned will be either the object implementation
	 * or an object proxy, depending on values in a property file.
	 * @param name the name of the service ( the key in the property file)
	 * @param url the initial context url of the service.
	 * @return Object the service implementation.
	 * @throws HoshenException in case of error.
	 */
	public static Object getService(String name,String url) throws HoshenException
	{
		Object serviceImpl = null;
		PropertyManager pm = null;
		Class serviceInterface= null;
		AbstractProxyHandler proxyHandler = null;
		
		try {
			pm = PropertyManager.getInstance();
		} catch(IOException ie) {
			throw new HoshenException("Error initializing propertyManager", ie);
		}
		String className = getClassImpl(name,pm);
		boolean useProxy = useProxy(name,pm);
 	    
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
				String interfaceName = getInterface(name,pm);
				serviceInterface = Class.forName(interfaceName);
			} catch(ClassNotFoundException ce) {
				throw new HoshenException("Error creating service implementation", ce);
			}	
			try {		
				String proxyHandlerName = pm.getProperty(INVOKER_HANDLER);
				log.debug("proxyHandlerName= " + proxyHandlerName);
				if(null == proxyHandlerName) {
					proxyHandlerName = DEFAULT_PROXY_HANDLER_NAME; 					
				}
				proxyHandler = (AbstractProxyHandler) Class.forName(proxyHandlerName).newInstance();
				if(url != null) {
					proxyHandler.setInitialContext(url);
				}
			} catch(Exception cee){
				throw new HoshenException("Error creating invoker client implementation", cee);
			}
			serviceImpl = getProxyObject(serviceInterface,className,proxyHandler);
			
		}
		return(serviceImpl);
		
	}
	/**
	 * This method should be used by the .NET infrastructure only.
	 * It only supports returning a proxy object not the real object.
	 * @param serviceInterface the service interface class.
	 * @param implClassName the service implementation class.
	 * @param proxyHandlerClass the proxyHandler class.
	 * @return Object the returned service proxy class.
	 * @throws HoshenException in case of error.
	 */
	public static Object getService(Class serviceInterface, String implClassName,
				Class proxyHandlerClass) throws HoshenException
	{
		Object serviceImpl = null;
		AbstractProxyHandler handler;
		try {
			handler = (AbstractProxyHandler)proxyHandlerClass.newInstance();
		} catch (InstantiationException e) {
			throw new HoshenException(e);
		} catch (IllegalAccessException e) {
			throw new HoshenException(e);
		}

		serviceImpl = getProxyObject(serviceInterface,implClassName,handler);
			
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
	

	private  static Object getProxyObject(Class interfaceClass, String className,AbstractProxyHandler handler)
	{
		if(getProxyCache().containsKey(className))
		{
			return getProxyCache().get(className);
		}
		ClassLoader cl = interfaceClass.getClassLoader();		
		handler.setRealClassName(className);
		Object proxyObj = Proxy.newProxyInstance(cl,
				  new Class[] { interfaceClass },
				  handler);
		                        
		getProxyCache().put(className,proxyObj);
		return(proxyObj);
	}
	
	/**
	 * @return Returns the proxyCache.
	 */
	private static Map getProxyCache() {
		return proxyCache;
	}
	
	
}
