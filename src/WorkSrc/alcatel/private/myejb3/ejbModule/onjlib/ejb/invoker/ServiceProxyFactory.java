/*
 * Created on 06/09/2005
 * 
 */
package onjlib.ejb.invoker;

import java.io.IOException;
import java.lang.reflect.Proxy;

import onjlib.utils.PropertyManager;

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
    private static final String ARGS_HANDLER = ".argsHandler";
	private static final String INVOKER_HANDLER = "invoker.handler";
	private static final String DEFAULT_PROXY_HANDLER_NAME = "hoshen.ejb.dynproxy.EjbProxyHandler";
	/**
	 * Get the service object implemntation.
	 * The object returned will be either the object implementation
	 * or an object proxy, depending on values in a property file.
	 * @param name the name of the service ( the key in the property file)
	 * @return Object the service implementation.
	 * @throws HoshenException in case of error.
	 */
	public static Object getService(String name) throws Exception
	{
		Object serviceImpl = null;
		PropertyManager pm = null;
		Class serviceInterface= null;
		AbstractProxyHandler proxyHandler = null;
		
		try {
			pm = PropertyManager.getInstance();
		} catch(IOException ie) {
			throw new Exception("Error initializing propertyManager", ie);
		}
		String className = getClassImpl(name,pm);
		boolean useProxy = useProxy(name,pm);
		System.out.println("className=" + className);
 	    String argsHandler = getArgsHandler(name, pm);
        System.out.println("argsHandler=" + argsHandler);
		/*
		 * either return the class implementation or 
		 * the proxy object.
		 */
		if(!useProxy) {
			
			try {
				serviceImpl = Class.forName(className).newInstance();
			} catch(Exception e) {
				throw new Exception("Error creating service implementation", e);
			}
			
		} else {
			
			try {						
				String interfaceName = getInterface(name,pm);
				serviceInterface = Class.forName(interfaceName);
			} catch(ClassNotFoundException ce) {
				throw new Exception("Error creating service implementation", ce);
			}	
			try {		
				String proxyHandlerName = pm.getProperty(INVOKER_HANDLER);
				System.out.println("proxyHandlerName= " + proxyHandlerName);
				if(null == proxyHandlerName) {
					proxyHandlerName = DEFAULT_PROXY_HANDLER_NAME; 					
				}
				proxyHandler = (AbstractProxyHandler) Class.forName(proxyHandlerName).newInstance();
			} catch(Exception cee){
				throw new Exception("Error creating invoker client implementation", cee);
			}
			serviceImpl = getProxyObject(serviceInterface,className,argsHandler,proxyHandler);
			
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
	

	private static String getArgsHandler(String name, PropertyManager pm)
    {
        String key = name + ".argsHandler";
        String argsHandler = pm.getProperty(key);
        return argsHandler;
    }

	
	private static Object getProxyObject(Class interfaceClass, String className,String argsHandler,AbstractProxyHandler handler)
	{
		ClassLoader cl = interfaceClass.getClassLoader();
		handler.setArgsHandler(argsHandler);
		handler.setRealClassName(className);
		
		Object proxyObj = Proxy.newProxyInstance(cl,
				  new Class[] { interfaceClass },
				  handler);
		                        
		return(proxyObj);
	}
}
