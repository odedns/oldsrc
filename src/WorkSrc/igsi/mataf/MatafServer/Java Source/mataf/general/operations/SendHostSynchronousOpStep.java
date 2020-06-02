package mataf.general.operations;

import mataf.logger.GLogger;
import mataf.utils.MatafUtilities;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEHandlerNotFoundException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Semaphore;
import com.ibm.dse.services.comms.CCMessage;
import com.ibm.dse.services.comms.CommonCommunicationsService;
import com.ibm.dse.services.comms.CommunicationsPoolService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SendHostSynchronousOpStep extends MatafOperationStep implements Handler {

	public static final String HOST_SEND_FORMAT_NAME = "hostSendFormat";
	public static final String HOST_RECIVE_FORMAT_NAME = "hostReceiveFormat";
	
	private Semaphore hostSemaphore;
	private CommunicationsPoolService pool;
	private CommonCommunicationsService service;
	
	/**
	 * Constructor for HostAccountBalanceRequestOpStep.
	 */
	public SendHostSynchronousOpStep() {
		super();
	}

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		try {
			
			// get reference to communication service from context 
			pool = (CommunicationsPoolService) getService("pool");
			service = (CommonCommunicationsService) pool.getPoolService();
			
			// register interest in events data and error received
			service.addHandler(this, service.getCcDataReceivedEventName());
			service.addHandler(this, service.getCcErrorReceivedEventName());
			
			// Prepare the message to be sent
			FormatElement hostSendFormat = (FormatElement) getFormat(HOST_SEND_FORMAT_NAME);
			String formatedCtx = hostSendFormat.format(getContext());
			setValueAt("HostBuff", formatedCtx);
			
			// Send the Message to Host
//			GLogger.debug("kColl before sending:");
//			GLogger.debug(getKeyedCollection());
			service.ccSendData(((String) getValueAt("HostBuff")));

			// Wait up to 10 seconds for reply notification and get the reply
			getHostSemaphore().waitOn(10000);
			CCMessage hostReply = service.ccReceiveData(10000);

			// analyze message received 
//			GLogger.debug("data received : " + hostReply.getDataReceived());
//			GLogger.debug("return code received : " + hostReply.getReceiveRC());
//			GLogger.debug("data sent : " + hostReply.getDataSent());
//			GLogger.debug("timeout : " + hostReply.getTimeout());

			if (hostReply.getReceiveRC() != 0) {
				GLogger.debug("*** Error in CICSOpStep ***");
				String errMsg = getMessagesHandlerService().getMsgFromTable("TL625", getRefTablesService());
				MatafUtilities.addBusinessMessage(getContext(), errMsg);
				return RC_ERROR;
			}

			// format the data reply from host
			FormatElement fromHostFormat = (FormatElement) getFormat(HOST_RECIVE_FORMAT_NAME);
			String dataFromHost = hostReply.getDataReceived();
			fromHostFormat.unformat(dataFromHost, getContext());
						
//			GLogger.debug("kColl after sending:");
//			GLogger.debug(getKeyedCollection());

		} catch (Exception e) {
			MatafUtilities.setErrorMessageInGLogger(this.getClass(), getContext(), e, null);
			String errMsg = getMessagesHandlerService().getMsgFromTable("TL624", getRefTablesService());
			MatafUtilities.addBusinessMessage(getContext(), errMsg);
			return RC_ERROR;
		} finally {
			service.removeHandler(this, service.getCcDataReceivedEventName());
			service.removeHandler(this, service.getCcErrorReceivedEventName());
			pool.releasePoolService(service);
		}

		return RC_OK;
	}
	
	/**
	 * @see com.ibm.dse.base.Handler#stopHandlingEvent(String, String, Context, String)
	 */
	public void stopHandlingEvent(String arg0, String arg1, Context arg2, String arg3)
		throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	}
	
	/**
	 * @see com.ibm.dse.base.Handler#handleEvent(String, String, Context)
	 */
	public void handleEvent(String arg0, String arg1, Context arg2) throws DSEInvalidArgumentException {
	}

	/**
	 * @see com.ibm.dse.base.Handler#handleEvent(String, String, Context, String)
	 */
	public void handleEvent(String arg0, String arg1, Context arg2, String arg3) throws DSEInvalidArgumentException {
	}
	
	/**
	 * @see com.ibm.dse.base.Handler#stopHandlingEvent(String, String, Context)
	 */
	public void stopHandlingEvent(String arg0, String arg1, Context arg2) throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	}
		
	/**
	 * @see com.ibm.dse.base.Handler#dispatchEvent(DSEEventObject)
	 */
	public Handler dispatchEvent(DSEEventObject anEvent) {
		if (anEvent.getName().equals(((CommonCommunicationsService) anEvent.getSource()).getCcDataReceivedEventName())) {
			getHostSemaphore().signalOn();
			GLogger.debug("Received Data...");
			return null;
		}
		if (anEvent.getName().equals(((CommonCommunicationsService) anEvent.getSource()).getCcErrorReceivedEventName())) {
			getHostSemaphore().signalOn();
			GLogger.debug("Received Error...");
			return null;
		}
		return this;

	}
	
	/**
	 * Returns the hostSemaphore.
	 * @return Semaphore
	 */
	public Semaphore getHostSemaphore() {
		if (hostSemaphore == null)
			hostSemaphore = new Semaphore();
		return hostSemaphore;
	}

	/**
	 * Sets the hostSemaphore.
	 * @param hostSemaphore The hostSemaphore to set
	 */
	public void setHostSemaphore(Semaphore hostSemaphore) {
		this.hostSemaphore = hostSemaphore;
	}

	
}
