package composer.services;

import java.io.IOException;
import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;

/**
 * The MessagingService will allow users to send and 
 * receive messages between them.
 * The service will use MQueue as its messaging
 * infrastructure.
 * @author Oded Nissan 10/7/2003
 */
public class MessagingService extends Service {
	byte m_userId[];
	MQConnectionAsynch m_mq;
	String m_mqServiceName;
	String m_connHandler;	
		
	/**
	 * initialize from externalizer.
	 */
	public java.lang.Object initializeFrom(Tag aTag)
                                throws java.io.IOException,
                                       DSEException
	{
		super.initializeFrom(aTag);
		System.out.println("Tag = " + aTag.toString());
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("MQServiceName")) {
				m_mqServiceName = value;
				continue;
			}
				
		}	//  for	
		init();
		return(this);
	}



	/**
	 * initialize the service.
	 * open the Mqueue service.
	 */
	public void init() throws DSEException
	{
		try {
						
			Context ctx = (Context) Context.readObject("messagingServiceCtx");
			m_mq = (MQConnectionAsynch) ctx.getService("mqsrv");				
			MQServiceHandler handler = new MQServiceHandler();
			m_mq.addHandler(handler,"message");
			handler.handleEvent("allEvents","mqsrv",ctx);
			
			m_mq.establishConnection();
			
		} catch(Exception e) {
			e.printStackTrace();
			throw new DSEException(DSEException.critical,"001",e.getMessage());
		}
	}


	/**
	 * set the name of the class to use
	 * as the connection handler.
	 * @param className the name of the connectionHandler
	 * class.
	 */
	public void setConnectionHandler(String className)
	{
		m_connHandler = className;		
	}
	
	
	/**
	 * get the connection handler name.
	 * @return String the name of the ConnectionHandler
	 * class used by the MQueue service.
	 */
	public String getConnectionHandlerName()
	{
		return(m_connHandler);	
	}
	
	
	/**
	 * get the user id correlation id.
	 * @return byte[] the correlation id used
	 * by this service.
	 */
	public byte[] getUserId()
	{
		return(m_userId);
	}
	
	/**
	 * set the name of the mq service to
	 * be used by the messaging service.
	 * @param name the name of the MQueue
	 * service to be used.
	 */
	public void setMQServiceName(String name)
	{
		m_mqServiceName = name;	
	}	
			
	/**
	 * returns the MQueue service name 
	 * used by the messaging service.
	 * @return String the name of the MQueue service.
	 */
	public String getMQServiceName()
	{
		return(m_mqServiceName);	
	}
	
	/**
	 * set the user that will be sending 
	 * messages using this service instance.
	 * @param userName the user using the
	 * service.
	 */
	public void setUser(String userName)
	{
		m_userId = userName.getBytes();
		m_mq.setCorrelationId(m_userId);


					
	}
	
	/**
	 * send a message to a specific user in the system.
	 * @param user the user to send the message to.
	 * @params msg the message to send.
	 * @throws Exception in case of error.
	 */
	public synchronized void sendMessage(String user, Object msg) throws Exception
	{
		MQMessage mqMsg = new MQMessage();
		mqMsg.correlationId = user.getBytes();
		//mqMsg.writeObject(msg);		
		mqMsg.writeString((String) msg);
		m_mq.send(mqMsg);
		System.out.println("send corrId =" + mqMsg.correlationId);		
	}
	
	
	/**
	 * terminate the service.
	 * terminate the MQueueConnectionService as well.
	 */
	public void terminate()
	{
		m_mq.terminate();		
	}
	
}
