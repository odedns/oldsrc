package composer.services;

import java.io.*;
import com.ibm.dse.base.*;
import com.ibm.dse.services.mq.*;
import com.ibm.mq.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQConnectionAsynch extends MQConnection {

	byte m_corrId[];
	
	
	/**
	 * set the correlation id to be used
	 * for receiving messages from the queue.
	 * @param corrId the correlation id.
	 */
	public void setCorrelationId(byte corrId[])
	{
		m_corrId = corrId;	
	}
	/**
	 * @see com.ibm.dse.services.mq.MQConnectionService#receive(byte[])
	 */
	
	  protected void receive()
        throws IOException
    {
        if(Trace.doTrace("#MQ", 512, 2))
            Trace.trace("#MQ", 512, 2, getName(), "Receive in listen thread");
        try
        {
            gmo.waitInterval = -1;

           
//            System.out.println("calling receive");
            tempMqMessage = getFromGetReplyQueue(m_corrId);
//            System.out.println("calling receive msg =" + tempMqMessage);
//            System.out.println("tempMesg: " + tempMqMessage.readLine());
            if(!isCloseMessage(tempMqMessage))
            {
                receiveReturnCode = 0;
                messageTable.put("message", tempMqMessage);
                messageTable.put("QUEUE", getReplyToQName());
                System.out.println("signaling message event");
                signalEvent("message", messageTable);
                if(Trace.doTrace("#MQ", 512, 2))
                    Trace.trace("#MQ", 512, 2, getName(), "Event DATA signaled");
            }
        }
        catch(DSEInvalidArgumentException dseinvalidargumentexception)
        {
            dseinvalidargumentexception.printStackTrace();
        }
        catch(MQException mqexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive MQException" + mqexception.toString());
            receiveReturnCode = mqexception.reasonCode;
            eventParameter.put(EXCEPTION, mqexception);
            if(ReplyToQName != null)
                eventParameter.put("QUEUE", ReplyToQName);
            else
            if(ReplyDynamicQName != null)
                eventParameter.put("QUEUE", ReplyDynamicQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception1)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in receive" + dseinvalidargumentexception1.toString());
            }
        }
        catch(NullPointerException nullpointerexception)
        {
            if(Trace.doTrace("#MQ", 512, 8))
                Trace.trace("#MQ", 512, 8, getName(), "Exception on receive NullPointerException" + nullpointerexception.toString());
            eventParameter.put(EXCEPTION, nullpointerexception);
            if(ReplyToQName != null)
                eventParameter.put("QUEUE", ReplyToQName);
            else
            if(ReplyDynamicQName != null)
                eventParameter.put("QUEUE", ReplyDynamicQName);
            try
            {
                signalEvent("errorReceived", eventParameter);
            }
            catch(DSEInvalidArgumentException dseinvalidargumentexception2)
            {
                if(Trace.doTrace("#MQ", 512, 8))
                    Trace.trace("#MQ", 512, 8, getName(), "Exception in receive" + dseinvalidargumentexception2.toString());
            }
        }
    }

    
 

}
