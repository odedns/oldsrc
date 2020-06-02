package composer.services;

import java.io.IOException;

import com.ibm.dse.base.*;
import com.ibm.dse.services.comms.DSECCException;
import com.ibm.mq.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQService extends Service {
	MQQueue m_queue;
	MQQueue m_receiveQ;
	MQPutMessageOptions m_putOpts;
	MQGetMessageOptions m_getOpts;
	byte m_corrId[];
	boolean m_asynch = true;
	MQServiceReceiver m_receiver;	
	String m_host;
	int m_port;
	String m_channel;
	String m_qMgrName;
	String m_queueName;

	
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
			if (name.equals("channel")) {
				m_channel = value;
				continue;
			}
			if (name.equals("hostName")) {
				m_host = value;
				continue;
			}	
			if (name.equals("port")) {
				m_port = Integer.parseInt(value);
				continue;
			}
			if (name.equals("QMgrName")) {
				m_qMgrName = value;
				continue;
			}	
			if (name.equals("sendToQ")) {
				m_queueName = value;
				continue;
			}	
			if (name.equals("loadReceiver")) {			
				if(value.equalsIgnoreCase("false")) {
					m_asynch = false;	
				} else {
					m_asynch = true;	
				}							
			}
		}	//  for	
		init();
		return(this);
	}



	void init() throws DSEException
	{
		MQEnvironment.hostname = m_host;
		MQEnvironment.port = m_port;
		MQEnvironment.channel = m_channel;

	
		try {
			MQQueueManager qmng = new MQQueueManager("QMS");
			int openOptions = MQC.MQOO_OUTPUT | 
							  MQC.MQOO_INPUT_AS_Q_DEF |
							  MQC.MQOO_SET |
							  MQC.MQOO_INQUIRE; 
							  
		
		// Instantiate an MQMessage for the received message:
			m_queue = qmng.accessQueue(m_queueName,openOptions,
						null,null,null);		
			m_receiveQ = qmng.accessQueue(m_queueName,openOptions,
						null,null,null);								
			m_putOpts = new MQPutMessageOptions();						
			m_getOpts = new MQGetMessageOptions();
			m_getOpts.options = MQC.MQGMO_COMPLETE_MSG | MQC.MQGMO_FAIL_IF_QUIESCING 
							| MQC.MQGMO_WAIT;
			m_getOpts.waitInterval = -1;

			if(m_asynch) {			
				m_receiver = new MQServiceReceiver(this);
				m_receiver.start();
			}	
			
		} catch( Exception e) {
			e.printStackTrace();
			throw new DSECCException(DSEException.critical, String.valueOf(((MQException)e).reasonCode), e.toString());
	    }	
								
	}
	
	
	/**
	 * send a message.
	 * @param corrId the correlation id.
	 * @param o the message object to send.
	 * @throws Exception in case of error.
	 */
	public synchronized void send(byte corrId[], Object o)
		throws Exception
	{
		System.out.println("sending msg : " + o.toString());	
		MQMessage msg = new MQMessage();
		msg.correlationId = corrId;
		msg.writeObject(o);
		m_queue.put(msg,m_putOpts);
		System.out.println("msg sent ..");	
	}
	
	
	public synchronized MQMessage receive(byte[] corrId)
		throws MQException
	{
		MQMessage msg = new MQMessage();
		msg.correlationId = corrId;	
		m_receiveQ.get(msg, m_getOpts);
		return(msg);
	}
	
	public synchronized MQMessage receive()
		throws MQException
	{
		System.out.println("in receive ..");
		MQMessage msg = new MQMessage();
		msg.correlationId = m_corrId;	
		m_receiveQ.get(msg, m_getOpts);
		
		System.out.println("after receive ..");
		return(msg);
	}
	

	public void setUser(String user)
	{
		m_corrId = user.getBytes();	
	}	

	/**
	 * terminate the service.
	 * terminate the MQueueConnectionService as well.
	 */
	public void terminate()
	{		
		m_receiver.stopThread();		
		try {
			m_queue.close();		
		} catch(MQException me) {			
		}
	}	

}
