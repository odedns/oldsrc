package onjlib.events;

import java.io.Serializable;
import java.util.*;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import onjlib.j2ee.ServiceLocator;
import onjlib.utils.PropertyReader;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class EventNotifier  {

	private static EventNotifier m_notifier = null;
	private final static String QCF_NAME = "jms.connectionfactory";
	private final static String EVENT_QUEUE = "jms.event.queue";
	private Properties m_props;
	private QueueConnectionFactory m_qcf=null;
	private QueueConnection m_qcon;
	private Queue m_queue;
	private QueueSender m_qsender;
	private QueueSession m_qsession;
	
	
	/**
	 * get the EventNotifier singleton object
	 * instance.
	 * @return EventNotifier the singleton object.
	 * @throws Exception in case of error.
	 */
	public static EventNotifier getInstance() throws Exception
	{
		if(null == m_notifier) {
			m_notifier = new EventNotifier();			
		}
		return(m_notifier);
	}
	
	/**
	 * Constructor for EventNotifier.
	 */
	private EventNotifier() throws Exception 
	{
		super();		
		m_props = PropertyReader.read("/events.properties");
		init();
		
		
	
	}

	public void init() throws Exception
	{
		String qcfName = m_props.getProperty(QCF_NAME);
		String queueName = m_props.getProperty(EVENT_QUEUE);
		ServiceLocator.setProperties(m_props);
		m_qcf = (QueueConnectionFactory) ServiceLocator.getInstance().findObject(qcfName, QueueConnectionFactory.class);
	    m_qcon = m_qcf.createQueueConnection();
	    m_qsession = m_qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    m_queue = (Queue) ServiceLocator.getInstance().findObject(queueName,Queue.class );
	    m_qsender = m_qsession.createSender(m_queue);
	    m_qcon.start();
		
	}
	
	/**
	 * send an event to all handlers in the notifier's
	 * list.
	 * @param e the EventObject to be send.
	 * @throws JMSException
	 * @see onjlib.patterns.EventNotifierIF#sendEvent(EventObject)
	 */
	public void sendEvent(String clientId, String systemId, EventObject e) throws JMSException {
		
		StringBuffer dest = new StringBuffer();
		if(clientId != null) {
			dest.append(clientId);
			dest.append('.');			
		}
		
		System.out.println("sending event");
		if(systemId == null) {
			throw new IllegalArgumentException("systemId parameter cannot be null");
		}
		dest.append(systemId);
		
		ObjectMessage msg = m_qsession.createObjectMessage();		
		msg.setStringProperty("destination", dest.toString());

		Object obj = e.getSource();
		
		if(! (obj instanceof Serializable)) {
			throw new IllegalArgumentException("Object must be serializable");
		}		
		msg.setObject((Serializable)obj);
		m_qsender.send(msg);
	}
	

	public void close() throws JMSException
	{
		
		m_qsender.close();
		m_qsession.close();
		m_qcon.close();
	}
}
