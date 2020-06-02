package mataf.override;

import mataf.globalmessages.handler.WindowMessagesHandler;
import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.utils.MQUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.base.Service;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQMessage;
import mataf.dse.appl.OpenDesktop;

/**
 * @author Oded Nissan
 * 
 * This operation sends an override reply message to the teller.
 * The reply is an MQ message with a status indicating accept/reject
 * and a reason. The operation also calls a server operation to 
 * update the override table and update the indication.
 */
public class OverrideReplyClientOp extends BasicClientOp {
	
	/**
	 * execute the operation.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception
		
	{
		GLogger.debug("in OverrideReplyClientOp.execute()");
		Context overrideCtx = getContext();
		
		MQConnection service = (MQConnection) Service.readObject("MQC");		
		service.establishConnection();
			
		MQMessage msg = new MQMessage();
		
		String sRequestingUser=(String)overrideCtx.getValueAt("tellerUserId")+(String)overrideCtx.getValueAt("trxUuid");
		GLogger.debug("writing reply to: " + sRequestingUser);
		msg.correlationId=sRequestingUser.getBytes();
		msg.writeInt(OverrideConstants.OVERRIDE_REPLY);
		msg.writeUTF((String)overrideCtx.getValueAt("trxUuid"));
		Integer n = (Integer)overrideCtx.getValueAt("status");
		int i = (n != null ? n.intValue() : 0);
		msg.writeInt(i);
		
		String strComment=(String)overrideCtx.getValueAt("managerComment");
		if(strComment==null)
			strComment="";
		
		msg.writeUTF(strComment);
		
		String strManagerCode=(String)overrideCtx.getValueAt("managerAnswerCode");
		if(strManagerCode==null)
			strManagerCode="0";
		int iMangerAnswerCode=Integer.parseInt(strManagerCode);
		msg.writeInt(iMangerAnswerCode);
		
		service.send(msg);
		service.closeConnection();
		
		// call the server operation.		
		sendReceive(4000,overrideCtx);
		MQUtils.sendMqMsg(overrideCtx);
			
		WindowMessagesHandler.delMessage((String)overrideCtx.getValueAt("trxUuid"));		
		//fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
	}
	
		
}
