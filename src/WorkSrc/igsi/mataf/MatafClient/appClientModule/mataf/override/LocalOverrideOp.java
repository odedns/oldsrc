package mataf.override;

import java.awt.Color;
import com.ibm.dse.base.Hashtable;

import javax.swing.JOptionPane;

import mataf.desktop.views.MatafClientView;
import mataf.desktop.views.MatafTransactionView;
import mataf.logger.GLogger;
import mataf.operations.general.BasicClientOp;
import mataf.services.proxy.ProxyService;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafOptionPane;
import mataf.utils.IndexedColUtils;
import mataf.utils.MQUtils;
import mataf.utils.MatafCoordinationEvent;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationRepliedEvent;

import mataf.dse.appl.OpenDesktop;
/**
 * @author Ronen 
 *
 * Local override functionality.
 * Authenticate the user/password typed in the dialog box.
 */
public class LocalOverrideOp extends BasicClientOp {
	private OperationRepliedEvent event;
	
	/**
	 * execute the operation.
	 * Send the user/password for authentication to the proxy.
	 * @throws Exception in case of error.
	 */
	public void execute()
		throws Exception
	{
		GLogger.debug("in LocalOverrideOp.execute()");
		Hashtable params = new Hashtable();
		event = new OperationRepliedEvent(this);
		try {
			ProxyService proxy = (ProxyService) getService("proxyService");
			String managerId = (String) getValueAt("GLSE_GLBL.GL_ZIHUI_MENAHEL");
			GLogger.debug("managerId = " + managerId);
		
			Context ctx = getContext().getParent();		
			IndexedCollection ic = (IndexedCollection) getElementAt("managersList");
			GLogger.debug("got managersList size = " + ic.size());
			String chosenMgrId = (String) getValueAt("chosenManagerId");	
			String samchut = null;
			MatafTransactionView panel = OpenDesktop.getActiveTransactionView();		
			LocalOverrideView localView=(LocalOverrideView)panel;		
			String sPassword= new String( localView.m_localPanel.getSpPasswordField().getPassword());

			if(null == chosenMgrId || chosenMgrId.equals("")) {
				MatafOptionPane.showMessageDialog(panel,"יש לבחור מנהל","שגיאה", JOptionPane.CLOSED_OPTION);	
				return;			
			}
		
			MatafErrorLabel melErrors=OpenDesktop.getActiveTransactionView().getTheErrorLabel();		
			int res = AuthorizationConstants.AUTH_OK;
			  
			String strOverrideInAdvance=(String)ctx.getValueAt("trxORData.overrideInAdvance");
			
			if(!Boolean.valueOf(strOverrideInAdvance).booleanValue())
				res = proxy.authenticate(chosenMgrId,sPassword);
    	  	boolean m_authResult=false;
			   				
			switch(res)
			{
				case AuthorizationConstants.AUTH_OK:
						m_authResult = true;
						//iSelectedReason=getJComboBox1().getSelectedIndex();
						//if(iSelectedReason==0)
						//	iSelectedReason=-1;
						params.put("exit","true");
						event.setParameters(params);
						melErrors.setText("");
						//dispose();
						break;
				case AuthorizationConstants.AUTH_GERROR:
				case AuthorizationConstants.AUTH_UNKNOWNERR:
				case AuthorizationConstants.AUTH_OVEDAHER:
						melErrors.queueErrorMessage("שגיאה לא ידועה",Color.RED);
						return;
				default:			
				case AuthorizationConstants.AUTH_BADPASS:
				case AuthorizationConstants.AUTH_BADPASSATTEMPT1:
				case AuthorizationConstants.AUTH_BADPASSATTEMPT2:
						melErrors.queueErrorMessage("סיסמא שגויה!",Color.RED);
						return;
				case AuthorizationConstants.AUTH_EXPIRED:
							melErrors.queueErrorMessage("תוקף הססמה פג!",Color.RED);
						return;
				case AuthorizationConstants.AUTH_LOCKED:
							melErrors.queueErrorMessage("המשתמש נעול!",Color.RED);
						return;
				case AuthorizationConstants.AUTH_TEMPEXPIRED:
						melErrors.queueErrorMessage("משתמש זמני",Color.RED);
						return;
				case AuthorizationConstants.AUTH_INVALID:
						melErrors.queueErrorMessage("משתמש לא תקף לתחנה",Color.RED);
						return;
				case AuthorizationConstants.AUTH_PRIMARYPASS:
						melErrors.queueErrorMessage("סיסמא ראשונית",Color.RED);
						return;
				case AuthorizationConstants.AUTH_TOBEEXPIRED:
						melErrors.queueErrorMessage("סיסמא עומדת לפוג.",Color.RED);
						m_authResult = true;				
						//iSelectedReason=getJComboBox1().getSelectedIndex();
						//if(iSelectedReason==0)
						//	iSelectedReason=-1;
						//dispose();
						break;

			}
			/*
			 * locate the chosen manager's id from the
			 * managers list.
			 */		
			/*KeyedCollection kc = IndexedColUtils.findFirst(ic,"managerId",chosenMgrId);
			if(null != kc) {
				samchut = (String) kc.getValueAt("samchutMeasheret");	
			}*/
		
			//ic = (IndexedCollection) ctx.getElementAt("managersComboList");
		
			KeyedCollection kc = IndexedColUtils.findFirst(ic,"managerId",chosenMgrId);
			if(null != kc) 
			{
				try 
				{
					samchut = (String) kc.getValueAt("samchutMeasheret");
				} catch(DSEObjectNotFoundException dseo) {
					samchut = "";
					GLogger.error(this.getClass(), null, "Exception in OverrideReqClientOp " , dseo,false);								
				}
	
			}
			
			
			setValueAt("overrideType",OverrideConstants.OVERRIDE_TYPE_LOCAL);
			setValueAt("samchutMeasheret",samchut);
			setValueAt("trxMqHeader",getValueAt("trxORData.mqData"));								
			if(m_authResult) {
				setValueAt("status", new Integer(OverrideConstants.OVERRIDE_ACCEPT_REPLY));	
			} else {
				setValueAt("status", new Integer(OverrideConstants.OVERRIDE_REJECT_REPLY));	
			}
			setValueAt("trxORData.overrideResult", new Boolean(m_authResult).toString());
			String chosenMgr = chosenMgrId;
			setValueAt("trxORData.mgrUserId", chosenMgr);
			setValueAt("trxORData.samchutMeasheret",samchut);		
			setValueAt("trxORData.answerCode",(String) getValueAt("answerCode"));
			setValueAt("trxORData.answerTxt",(String) getValueAt("answerTxt"));
			
			/*
			 * send the ishur menahel message to
			 * MF.
			 */
			MQUtils.sendMqMsg(getContext());			
			LocalOverrideView sView = (LocalOverrideView) OpenDesktop.getActiveTransactionView();
			MatafCoordinationEvent mevent = new MatafCoordinationEvent(this);
			mevent.setEventSourceType(MatafCoordinationEvent.EVENT_SOURCETYPE_CLOSE_CHILD_VIEW);
			sView.m_trxView.handleDSECoordinationEvent(mevent);
			GLogger.debug("sending parent child close view event, closing view ..");
			fireHandleOperationRepliedEvent(event);
			
			} catch(Exception e) {
				GLogger.error(this.getClass(), null, "Exception in OverrideReqClientOp " , e,false);								
				setError(" שגיאה באישור מנהל מקומי  : " + e.getMessage());
			}
	}

	
}
