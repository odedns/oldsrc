package mataf.general.operations;

import java.awt.Cursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import mataf.logger.GLogger;
import mataf.services.MessagesHandlerService;
import mataf.services.reftables.RefTablesService;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.clientserver.CSClientService;
import com.ibm.dse.desktop.Desktop;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MatafClientOp extends DSEClientOperation {
	
	public static final String MSG_TABLE_ID_PARAM_NAME = "msgTableId";
	public static final String ERR_NO_PARAM_NAME = "errorNo";
	
	public static final int SEND_AND_WAIT_TIMEOUT = 60000;
	
	private RefTablesService refTables;
	private MessagesHandlerService msgHandler;
	
	private KeyedCollection params;
	
	private boolean isInTestMode = false;
	
	protected RefTablesService getRefTablesService() throws DSEObjectNotFoundException {
		if(refTables == null) {
			refTables = (RefTablesService) getService("refTablesService");
		}
		return refTables;
	}
	
	protected MessagesHandlerService getMessagesHandlerService() throws DSEObjectNotFoundException {
		if(msgHandler == null) {
			msgHandler = (MessagesHandlerService) getService("msgsHandlerService");
		}
		return msgHandler;
	}
	
	/**
	 * @see com.ibm.dse.base.Operation#executeOp()
	 */
	public void executeOp() throws Exception {
		if(isInTestMode && (!isRequestFormatValid())) {
			addToErrorList("TL626");
		} else {
			CSClientService aCSClientService = (CSClientService)getService("CSClient");
			aCSClientService.sendAndWait(this, SEND_AND_WAIT_TIMEOUT);
		}
	}
	
	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {
		
		try {
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						
			if(preSend2hostValidation()) {
				executeOp();
				postExecute();
			}
		} catch(Exception ex) {
			String errMsg = "Exception has been thrown in client operation '"+this.getName()+"'";
			GLogger.error(this.getClass(), null, errMsg, ex, false);
			
			// TODO - set the next line as a remark (for debug only)
			MatafUtilities.addBusinessMessage(getContext(), ex.getMessage());
		} finally {
			close();
			Desktop.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));			
		}
	}
	
	/**
	 * Adds an error message from the messages handler service.
	 * 
	 * @param tableId - The table id.
	 * @param msgNumber - The message number.
	 */
	protected void addToErrorList(String msgNumber)
	{
		addToErrorList(MessagesHandlerService.DEFUALT_MSG_SERVICE_NAME, msgNumber);
	}
	protected void addToErrorList(String tableId, String msgNumber)
	{
		try
		{
			MatafUtilities.addBusinessMessage(getContext(), getErrorMsg(msgNumber));
		}
		catch(Exception e)
		{
			String msg = "Exception has been thrown while trying to add message to  Error List"+
							" in client operation '"+this.getName()+"'";
			GLogger.error(this.getClass(), null, msg, e, false);
		}
	}
	
	/**
	 * Returns:
	 * 		the error message by the message number taken from the XML operation definition.
	 */
	protected String getErrorMsgFromXml() {
		return getErrorMsg(new ArrayList(), null);
	}
	
	/**
	 * Returns:
	 * 		the error message by the message number sent as parameter.
	 */
	protected String getErrorMsg(String msgNumberAsStr) {
		return getErrorMsg(new ArrayList(), msgNumberAsStr);
	}
	
	protected String getErrorMsg(List msgParams, String msgNumberAsStr) {
		try {
			String tableId = (String) getParams().tryGetValueAt(MSG_TABLE_ID_PARAM_NAME);
			if(tableId==null)
				tableId = MessagesHandlerService.DEFUALT_MSG_SERVICE_NAME;
			
			String msgNumber = msgNumberAsStr;
			if(msgNumber==null) {
				msgNumber = (String) getParams().tryGetValueAt(ERR_NO_PARAM_NAME);
			}
			
			return getMessagesHandlerService().getMsgFromTable(tableId, msgNumber, getRefTablesService(), msgParams);
		} catch(Exception ex) {
			String msg = "Exception has been thrown while trying to get"+
							" error number "+msgNumberAsStr;
			GLogger.error(this.getClass(), null, msg, ex, false);
		}
		
		return "";
	}
	
	/**
	 * This method has been generated because it more simple to understand 
	 * then using the addToErrorList() (with no params) that get the message number
	 * from the XML operation definition.
	 */
	public void addToErrorListFromXML() {
		addToErrorList(getErrorMsgFromXml());
	}
	
	private boolean isRequestFormatValid() {
		try {
			FormatElement format = getCSRequestFormat();
			format.format(getContext());
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
	
	public boolean preSend2hostValidation() throws Exception {
		return true;
	}
	
	public void postExecute() throws Exception {
	}
	/**
	 * @see com.ibm.dse.base.Externalizable#initializeFrom(Tag)
	 */
	public Object initializeFrom(Tag aTag) throws IOException, DSEException {
		super.initializeFrom(aTag);
		
		params = new KeyedCollection();
		params.setDynamic(true);
		for (Enumeration e=aTag.getAttrList().elements();e.hasMoreElements();)
		{
			TagAttribute att = (TagAttribute) e.nextElement();
			params.setValueAt(att.getName(), att.getValue());
		}
	
		return this;
	}

	/**
	 * Returns the params.
	 * @return KeyedCollection
	 */
	public KeyedCollection getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 * @param params The params to set
	 */
	public void setParams(KeyedCollection params) {
		this.params = params;
	}

}
