package mataf.common.operationsteps;

import mataf.logger.GLogger;

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
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class CICSOpStep extends AbstractOpStep implements Handler {

	private FormatElement hostSendFormat;
	private FormatElement hostReceiveFormat;
	private Semaphore hostSemaphore;
	private CommunicationsPoolService pool;
	private CommonCommunicationsService service;
	/**
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
		

		preExecute();
		try {
			// get reference to communication service from context 
			pool = (CommunicationsPoolService) getService("pool");
			service = (CommonCommunicationsService) pool.getPoolService();

			// register interest in events data and error received
			service.addHandler(this, service.getCcDataReceivedEventName());
			service.addHandler(this, service.getCcErrorReceivedEventName());

			// Prepare the message to be sent
			setValueAt("HostBuff", ((FormatElement) getHostSendFormat()).format(getContext()));

			// Send the Message to Host
			GLogger.debug("kColl before sending:");
			GLogger.debug(getKeyedCollection());
			service.ccSendData(((String) getValueAt("HostBuff")));

			// Wait up to 10 seconds for reply notification and get the reply
			getHostSemaphore().waitOn(10000);
			CCMessage hostReply = service.ccReceiveData(10000);

			// analyze message received 
			GLogger.debug("data received : " + hostReply.getDataReceived());
			GLogger.debug("return code received : " + hostReply.getReceiveRC());
			GLogger.debug("data sent : " + hostReply.getDataSent());
			GLogger.debug("timeout : " + hostReply.getTimeout());

			if (hostReply.getReceiveRC() != 0) {
				GLogger.debug("*** Error in CICSOpStep ***");
				return RC_ERROR;
			}

			// Format message received and store it in the context
			//			yossi test
			//			FormatElement fromHostFormat = (FormatElement)FormatElement.readObject("accountBalanceTrxReplyFmt");
			FormatElement fromHostFormat = (FormatElement) getHostReceiveFormat();
			String dataFromHost = hostReply.getDataReceived();
			fromHostFormat.unformat(dataFromHost, getContext());

			GLogger.debug("kColl before sending:");
			GLogger.debug(getKeyedCollection());

			//			Propper termination
			terminateTheStep();
		} catch (Exception e) {
			e.printStackTrace();
			GLogger.error(e.toString());
			return RC_ERROR;
		} finally {
			service.removeHandler(this, service.getCcDataReceivedEventName());
			service.removeHandler(this, service.getCcErrorReceivedEventName());
			pool.releasePoolService(service);
		}
		return RC_OK;
	}
	/**
	 * @see mataf.common.operationsteps.AbstractOpStep#terminateTheStep()
	 */
	public void terminateTheStep() throws Exception{
			service.removeHandler(this, service.getCcDataReceivedEventName());
			service.removeHandler(this, service.getCcErrorReceivedEventName());
			pool.releasePoolService(service);
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
	 * @see com.ibm.dse.base.Handler#stopHandlingEvent(String, String, Context, String)
	 */
	public void stopHandlingEvent(String arg0, String arg1, Context arg2, String arg3)
		throws DSEInvalidArgumentException, DSEHandlerNotFoundException {
	}

	/**
	 * Returns the hostReceiveFormat.
	 * @return FormatElement
	 */
	public FormatElement getHostReceiveFormat() {
		return hostReceiveFormat;
	}

	/**
	 * Returns the hostSendFormat.
	 * @return FormatElement
	 */
	public FormatElement getHostSendFormat() {
		return hostSendFormat;
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

	/**
	 * Sets the hostReceiveFormat.
	 * This method is used by the preExcute() method. Implement it in the way that your implementation will
	 * read the proper value from the xml format file.
	 * @param hostReceiveFormat The hostReceiveFormat to set
	 */
	public abstract void setHostReceiveFormat(FormatElement hostReceiveFormat);

	/**
	 * Sets the hostSendFormat.
	 * This method is used by the excute() method. Implement it in the way that your implementation will
	 * read the proper value from the xml format file.
	 * @param hostSendFormat The hostSendFormat to set
	 */
	public abstract void setHostSendFormat(FormatElement hostSendFormat);
}
