package mataf.override;

import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.utils.ContextUtils;
import mataf.utils.MatafCoordinationEvent;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQMessage;
import mataf.dse.appl.OpenDesktop;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OverrideCancelClientOp extends BasicClientOp {

	/**
	 * send a cancel request to the manager.
	 * write the message type followed by the manager id.
	 */
	public void execute() throws Exception
	{
		try {
			GLogger.debug("in OverrideCancelOp.execute()");	
			Context ctx = ContextUtils.getNamedContext(getContext(),"overrideCtx");							
			
			/*
			 * now stop the listening thread.
			 * and close the view.
			 */
			SendOverrideView sView = (SendOverrideView)OpenDesktop.getActiveTransactionView();
			if(sView.m_th != null) {
				sView.m_th.stopThread();
			}

			
			
			Integer n = (Integer) ctx.getValueAt("status");
			int i = (n != null ? n.intValue() : 0);
		
			/*
			 * if status is open send a cancel message.
			 */
			if(i == OverrideConstants.OVERRIDE_REQUEST) {		
				String tmp = (String) ctx.tryGetValueAt("mgrUserId");		
				String trxId = (String) ctx.tryGetValueAt("trxUuid");
				MQConnection mqconn = (MQConnection) ctx.getService("MQConnection");
				mqconn.establishConnection();
				MQMessage msg = new MQMessage();
				msg.correlationId = tmp.getBytes();		
				msg.writeInt(OverrideConstants.OVERRIDE_CANCEL);
				msg.writeUTF(trxId);
				byte corrId[] = mqconn.send(msg);
				mqconn.closeConnection();
				GLogger.debug("sent cancel message : " + corrId);
				sendReceive(ctx);	
			} 
		
			MatafCoordinationEvent mevent = new MatafCoordinationEvent(this);
			mevent.setEventSourceType(MatafCoordinationEvent.EVENT_SOURCETYPE_CLOSE_CHILD_VIEW);
			sView.m_trxView.handleDSECoordinationEvent(mevent);
			GLogger.debug("sending parent child close view event, closing view ..");
			fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
			
		} catch(Exception e) {
			GLogger.error(this.getClass(), null, "Error in OverrideCancelClientOp ",e,false);
			setError(" שגיאה באישור מנהל מרוחק: " + e.getMessage());				
		}

	}
}
