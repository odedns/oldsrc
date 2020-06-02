package composer.services;
import com.ibm.mq.*;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQServiceReceiver extends Thread {
	MQService m_service;
	boolean m_flag = true;
	
	/**
	 * Constructor for MQServiceReceiver.
	 */
	public MQServiceReceiver(MQService service) {
		super();
		m_service = service;
	}
	
	
	
	/**
	 * the thread's run method.
	 * poll the MQ queue for incoming messages.
	 */
	public void run()
	{
		
		MQMessage msg = null;
		System.out.println("starting receiver ..");
		while(m_flag) {
			try {				
				msg =(MQMessage)m_service.receive();								
				Hashtable params = new Hashtable();
				params.put("message",msg);		
				m_service.signalEvent("message",params);				
				System.out.println("signaling event msg " + msg.readLine());
			} catch(Exception e) {
				if(e  instanceof MQException) {
					MQException mqe = (MQException) e;
					if(mqe.reasonCode == 2033) {
						System.out.println("empty message...");
					}
											
				}
				e.printStackTrace();	
			}
			
		} // while
							
		
	} // run
	
	/**
	 * stop the thread by setting the flag
	 * to false to exit the loop in the run
	 * method.
	 */
	public void stopThread()
	{
		m_flag = false;		
	}
	
	public MQMessage receive(MQQueue queue, MQGetMessageOptions getOpts, byte corrId[])
		throws Exception
	{
		System.out.println("in receive thread receive ..");
		MQMessage msg = new MQMessage();
		msg.correlationId = corrId;	
		queue.get(msg, getOpts);
		
		System.out.println("after receive ..");
		return(msg);
	}

}
