/* Created on 13/03/2006 */
package hoshen.utils.listener;

import hoshen.common.utils.PropertyReader;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This singleton class manages the message handler mapping.
 * It reads the hoshenlistener.property file and stores the
 * mapping in a HashMap.
 * Given a message type it returns the appropriate handler class.
 * @author Odedn
 */
public class MessageHandlerManager
{
	private static final String m_propertyFile = "/hoshenlistener.properties";
	private static final String DEFAULT_HANDLER = "default"; 
	private static MessageHandlerManager m_instance =  null;
	private Properties m_handlerMap = null;
	private String m_defaultHandler = null;
	private static Logger log = Logger.getLogger(MessageHandlerManager.class);
	
	/**
	 * private constructor.
	 * The class can only be initialize through the
	 * getIntance() method.
	 * read the property file and initialize the handlerMap.
	 *
	 */
	private MessageHandlerManager()
	{
		try {
			m_handlerMap = PropertyReader.read(m_propertyFile);
			if(m_handlerMap != null) {
				m_defaultHandler = m_handlerMap.getProperty(DEFAULT_HANDLER);
			}
		} catch(IOException ie) {
			log.error("Error reading property file:" + m_propertyFile);
		}		
		log.info("MessageHandlerManager initialized");
	}
	
	/**
	 * Get the MessageHandlerManager singleton 
	 * instance.
	 * @return MessageHandlerManager.
	 */
	public static MessageHandlerManager getInstance()
	{
		if(null == m_instance) {
			m_instance =  new MessageHandlerManager();
		} 
		return(m_instance);
	}
	
	/**
	 * Instantiate an appropriate MessageHandler according to 
	 * the given type.
	 * @param type String the message type.
	 * @return MessageHandler object. or null in case of error.
	 */
	public MessageHandler findMessageHandler(String type)
	{
		String handlerClass = null;
		MessageHandler handler = null;
		if(type == null) {
				handlerClass = m_defaultHandler;
		} else {
					
			handlerClass =  (String) m_handlerMap.get(type);
			if(handlerClass == null) {
				handlerClass = m_defaultHandler;
			} // if
		} // if
		
		
		try	{
			handler = (MessageHandler) Class.forName(handlerClass).newInstance();
		} catch (Exception e){
			e.printStackTrace();
			log.error("Error instantiating handler:" + e);
			handler = null;
		}						
		return(handler);
	}

}
