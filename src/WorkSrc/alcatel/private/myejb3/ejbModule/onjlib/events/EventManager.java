package onjlib.events;

import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EventManager {

	private static HashMap m_map = new HashMap();
	/**
	 * Constructor for EventManager.
	 */
	private EventManager() {
		super();
	}
	
	
	
	/**
	 * Register a notfier to the EventManager.
	 * @param name the name that will identify
	 * the notifier.
	 * @param notifier the EventNotifier to register.
	 */
	public static synchronized void addHandler(String name, EventHandlerIF handler)
		throws Exception 
	{
		m_map.put(name,handler);
	}
	
	/**
	 * Remove a notfieir from the EventManager.
	 * @param name the name that will identify
	 * the notifier.
	 * @param notifier the EventNotifier to register.
	 */
	public static synchronized void removeHandler(String name, EventHandlerIF handler)
		throws Exception
	{
		m_map.remove(name);
	}
	
	public static synchronized EventHandlerIF findHandler(String key)
	{
		EventHandlerIF handler = (EventHandlerIF) m_map.get(key);
		return(handler);
	}
	

}
