package mataf.override;

import java.awt.Color;

import mataf.logger.GLogger;
import mataf.types.MatafErrorLabel;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEHandler;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;


/**
 * This thread runs and listens for MQ messages sent from the manager
 * to the teller. If a request_handled message arrives it updates the
 * OverrideWaitView with a proper message.
 * If an Override reply message arrives it updates the OverrideWaitView 
 * with the proper messages and enables a button that allows the teller to
 * complete the banking operation.
 * 
 * @author Oded Nissan 22/10/2003
 */
public class OverrideWaitThread extends Thread {

	SendOverridePanel m_panel = null;
	Context m_ctx = null;
	boolean m_flag = true;	
	boolean m_toFlag = false;
	MatafErrorLabel m_errLabel = null;


	/**
	 * event handler.
	 * Handle MQ events.
	 * We use the event handler in order to handle a timeout
	 * on the queue when there is no message available, since
	 * the composer service does not return a status.
	 */
	class MyHandler extends DSEHandler {

	
		public Handler dispatchEvent(DSEEventObject event)
		{
			Hashtable ht = event.getParameters();
			MQException mqe = (MQException) ht.get("exception");
			if(mqe != null) {
				m_toFlag = true;
			}
			return(null);	
		}
	}

	/**
	 * constructor for the OverrideWaitThread.
	 * @param view the OverrideWaitView so that we can update it.
	 * @param ctx the override Context so that we can access MQ etc.
	 */
	public OverrideWaitThread(SendOverridePanel panel, Context ctx)
	{
		m_ctx = ctx;
		m_panel = panel;		
		
	}

	/**
	 * set the error label to use
	 * for displaying messages.
	 */
	public void setErrorLabel(MatafErrorLabel errLabel) 
	{
		m_errLabel = errLabel;	
	}
	
	/**
	 * the thread's run method.
	 */
	public void run()
	{
		MQConnection mqconn = null;
		MQMessage msg = null;
		String tellerId = null;
		String trxUuid = null;
		try {
			mqconn = (MQConnection) m_ctx.getService("MQConnection");
			mqconn.establishConnection();
			MyHandler handler = new MyHandler();			
			mqconn.addHandler(handler,"errorReceived");
			tellerId = (String) m_ctx.getValueAt("tellerUserId");
			trxUuid = (String)  m_ctx.getValueAt("trxUuid");
		} catch(Exception dsee) {
			GLogger.error(this.getClass(),null,"Error in OverrideWaitThread",dsee,false);
			dsee.printStackTrace();
			return;		
		}
		
		
		String tmp = tellerId + trxUuid;
		GLogger.debug("listening on : " + tmp);
		byte corrId[] = tmp.getBytes();
		/*
		 * read messages from MQ queue.
		 * if the message if an override reply or 
		 * override in process - update the view.
		 */
		while(m_flag) {
			try {
				msg = (MQMessage) mqconn.receive(corrId,4000);
				if(m_toFlag) {
					GLogger.debug("got timeout");
					m_toFlag = false;
					continue;	
				}
				int msgType = msg.readInt();
				if(msgType == OverrideConstants.OVERRIDE_OPEN) {
					GLogger.debug("got open message ");
					String openMsg = formatOpenMsg(msg);
					m_errLabel.queueErrorMessage(openMsg,Color.BLACK);					
					
					continue;
				}
				if(msgType == OverrideConstants.OVERRIDE_REPLY) {
					GLogger.debug("got reply message");
					String repMsg = formatReplyMsg(msg);
					m_errLabel.queueErrorMessage(repMsg,Color.BLACK);										
					m_flag = false;
					continue;
				}
			} catch(Exception e) {
				e.printStackTrace();					
			}
			
			
		}	 // while
		try {
			mqconn.closeConnection();
		} catch(MQException mqe) {
			GLogger.error(this.getClass(), null, null, mqe,false);
		}
	}
	
	/**
	 * stop the thread.
	 */
	public void stopThread()
	{
		m_flag =false;	
		GLogger.info("stopping thread ...");
	}
	
	
	/**
	 * format the reply message.
	 */
	private String  formatReplyMsg(MQMessage msg)	
	{
		String repStatus = "לא ידועה";
		String comment = null;
		String managerName= null;
		String trxUuid = null;
		boolean res = false;
		try {
			trxUuid = msg.readUTF();
			managerName = (String) m_ctx.getValueAt("chosenManagerName");
			int status = msg.readInt();
			if(OverrideConstants.OVERRIDE_REJECT_REPLY == status) {
				repStatus = " נדחתה ";
				res = false;
			}		
			if(OverrideConstants.OVERRIDE_ACCEPT_REPLY == status) {
				repStatus = " אושרה  ";
				res = true;
			}					
			comment = msg.readUTF();
			m_ctx.setValueAt("trxORData.overrideResult", new Boolean(res).toString());
			int tshuvaCode = msg.readInt();
			m_ctx.setValueAt("trxORData.managerAnswerCode", new Integer(tshuvaCode));
		} catch(Exception e) {
			e.printStackTrace();
			GLogger.error(this.getClass(),null, null, e,false);			
			return(null);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(" בקשת אישור מנהל ");
		sb.append(trxUuid);
		sb.append(repStatus);
		sb.append(" :על ידי מנהל  ");
		sb.append(managerName);
		sb.append(" הערת מנהל:");
		sb.append(comment);
		return(sb.toString());		
	}
	
	/**
	 * format the open message.
	 */	
	private String  formatOpenMsg(MQMessage msg)
	{
		String trxUuid = null;
		String managerName = null;	
		
		try {	
			trxUuid = msg.readUTF();
			managerName = (String) m_ctx.getValueAt("chosenManagerName");
		} catch(Exception e) {
			GLogger.error(this.getClass(),null,"Error in OverrideWaitThread",e,false);
			e.printStackTrace();
			return(null);	
		}			
		StringBuffer sb = new StringBuffer();
		sb.append(" בקשת אישור מנהל ");
		sb.append(trxUuid);
		sb.append(" נפתחה על ידי מנהל ");
		sb.append(managerName);
		return(sb.toString());		
	}

}

