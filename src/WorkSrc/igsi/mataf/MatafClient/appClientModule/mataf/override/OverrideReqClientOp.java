package mataf.override;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.types.MatafErrorLabel;
import mataf.utils.ContextUtils;
import mataf.utils.IndexedColUtils;
import mataf.utils.MQUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.services.mq.MQConnection;
import com.ibm.mq.MQMessage;
import mataf.dse.appl.OpenDesktop;


/**
 * @author Oded Nissan. 29/9/2003
 * 
 * Pack the override request into an MQ message and send it.
 * call the server in order to update the override table.
 */
public class OverrideReqClientOp extends BasicClientOp {
	
	/**
	 * execute the operation.
	 * Pack the context and request data into an MQ 
	 * message and send it to the manager.
	 * Update the override table on the server.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception		
	{
		try {
			Context ctx = getContext().getParent();
			setContext(ctx);		
			GLogger.debug("in OverrideReqClientOp.execute()");	
			IndexedCollection ic = (IndexedCollection) getElementAt("managersList");
			GLogger.debug("got managersList size = " + ic.size());
			String chosenMgrId = (String) getValueAt("chosenManagerId");	
			String samchut = null;
			MatafTransactionView panel = OpenDesktop.getActiveTransactionView();
		
			if(null == chosenMgrId || chosenMgrId.equals("")) {
				setError("יש לבחור מנהל");
				return;			
			}
			/*
			 * locate the chosen manager's id from the
			 * managers list.
			 */		
			KeyedCollection kc = IndexedColUtils.findFirst(ic,"managerId",chosenMgrId);
			if(null != kc) {
				samchut = (String) kc.getValueAt("samchutMeasheret");	
			}
		
			/*
			 * remove the lists from the context so that 
			 * it doesn't go to the server and the MQ message.
			 */
			ic.removeAll();
			ic = (IndexedCollection) getElementAt("managersComboList");
			ic.removeAll();				
			setValueAt("status", new Integer(OverrideConstants.OVERRIDE_REQUEST));
			setValueAt("mgrUserId",chosenMgrId);
			setValueAt("samchutMeasheret",samchut);
			setValueAt("trxORData.mgrUserId",chosenMgrId);		
			setValueAt("trxORData.samchutMeasheret",samchut);
			setValueAt("trxORData.answerCode",getValueAt("answerCode"));				
			setValueAt("trxORData.answerTxt",getValueAt("answerTxt"));				
			setValueAt("tellerUserId",getValueAt("GLSE_GLBL.GKSE_KEY.GL_ZIHUI_PAKID"));
			setValueAt("trxId",getValueAt("trxORData.trxId"));
			setValueAt("trxUuid",getValueAt("trxORData.trxUuid"));
			setValueAt("trxName",getValueAt("trxORData.trxName"));		
			setValueAt("viewName",getValueAt("trxORData.viewName"));
			setValueAt("overrideType",OverrideConstants.OVERRIDE_TYPE_REMOTE);
			setValueAt("trxMqHeader",getValueAt("trxORData.mqData"));		
			setValueAt("requestTs",new Long(System.currentTimeMillis()).toString());
			String trxCtxName = (String) getValueAt("trxORData.ctxName");
			setValueAt("ctxName",trxCtxName);		
			Context trxCtx = ContextUtils.getNamedContext(ctx,trxCtxName);
				
			/*
			 * now send the context using MQ
			 * to the manager's workstation.
			 */		 
			GLogger.debug("chosenManager: " + chosenMgrId + " samchut : " + samchut);
 			String tmp = chosenMgrId;
			MQConnection mqconn = (MQConnection) getService("MQConnection");
			mqconn.establishConnection();
			MQMessage msg = new MQMessage();
			msg.correlationId = tmp.getBytes();		
			msg.writeInt(OverrideConstants.OVERRIDE_REQUEST);				
		
			/*
			 * remove the answer list beacause serialization has
			 * problems with it.
			 */		
			ctx.removeAt("answerList");
			MQUtils.packTrxContext(msg,ctx,trxCtx);
				
			byte corrId[] = mqconn.send(msg);
			mqconn.closeConnection();
			GLogger.debug("sent message : " + corrId);		
			/*
			 * remove the KCol object int ctxData
			 */		
			ctx.removeAt("ctxData");
			sendReceive(ctx);				
			GLogger.debug("now starting listener thread");
			SendOverrideView sView = (SendOverrideView) mataf.dse.appl.OpenDesktop.getActiveTransactionView();		
			sView.m_sendOr.disableSendButton();
			sView.m_th = new OverrideWaitThread(sView.m_sendOr,ctx);
			MatafErrorLabel errLabel = sView.getTheErrorLabel();
			sView.m_th.setErrorLabel(errLabel);
			sView.m_th.start();
		} catch(Exception e) {			
			GLogger.error(this.getClass(),null, "Exception in OverrideReqClientOp : ", e,false);								
			setError( " שגיאה בשליחת בקשת אישור מנהל מרוחק  : " + e.getMessage());		
		}
	}
}
