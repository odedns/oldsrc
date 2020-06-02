
package onjlib.ejb.utils;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * This is a HomeFactory for EJB home interfaces.
 * It retrieves home interfaces using JNDI and then stores them
 * in a HashMap for caching.
 * Subsequent calls check the cache for the Home interface before
 * performing a JNDI call.
 * @author Oded Nissan
 * @version 1.00 15/1/2002
 */
public class HomeFactory {
	static Map m_map;
	static InitialContext m_ctx;
	static HomeFactory m_hf = null;

	/**
	 * get an Instance of the HomeFactory object.
	 * @return HomeFactory the HomeFactory object.
	 * @throws NamingException if there is a JNDI exception when
	 * connecting to the aplication server.
	 */
	public static HomeFactory getInstance() throws NamingException
	{
		if(null == m_hf) {
			init();
			m_hf = new HomeFactory();
		}
		return(m_hf);
	}

	static void init() throws NamingException
	{
		m_ctx = EJBUtils.getInitialContext();
		m_map = Collections.synchronizedMap(new HashMap());
	}

	/**
	 * Get the home interface for a specific EJB.
	 * @param jndiName the jndi name of the EJB.
	 * @param c a Class object representing the home interface for the EJB.
	 * @throws NamingException if there is a JNDI exception when
	 * connecting to the aplication server.
	 */
	public EJBHome getHomeIF(String jndiName, Class c) throws NamingException
	{
		EJBHome eh;
		if(null == (eh = (EJBHome) m_map.get(jndiName))) {

			eh = (EJBHome) PortableRemoteObject.narrow(m_ctx.lookup(jndiName),c);
			m_map.put(jndiName,eh);
		}
		return(eh);
	}

	/**
	 * Get the home interface for a specific EJB.
	 * @param c a Class object representing the home interface for the EJB.
	 * @throws NamingException if there is a JNDI exception when
	 * connecting to the aplication server.
	 */
	public EJBHome getHomeIF(Class c) throws NamingException
	{
		EJBHome eh;
		String name = c.getName();
		if(null == (eh = (EJBHome) m_map.get(name))) {
			eh = (EJBHome) PortableRemoteObject.narrow(m_ctx.lookup(name),c);
			m_map.put(name,eh);
		}
		return(eh);
	}




}
