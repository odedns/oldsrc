package mataf.operations;

import mataf.logger.GLogger;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.EventManager;
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
public class TestTrxServerOp extends DSEServerOperation implements Handler {
	private Semaphore hostSemaphore;

	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		CommunicationsPoolService pool = null;
		CommonCommunicationsService service = null;
		try {

			// get reference to communication service from context 
			pool = (CommunicationsPoolService) getService("pool");
			service = (CommonCommunicationsService) pool.getPoolService();
			// register interest in status events, opened and closed
			//					service.addHandler(this, service.getCcOpenedEventName());
			//					service.addHandler(this, service.getCcClosedEventName());

			// register interest in events data and error received
			service.addHandler(this, service.getCcDataReceivedEventName());
			service.addHandler(this, service.getCcErrorReceivedEventName());

			// open communication service
			//					service.ccOpen();

			// Wait until service is opened
			//					serviceOpenedSem.waitOn();

			// Prepare the message to be sent
			setValueAt("HostBuff", ((FormatElement) getHostSendFormat()).format(this));
			
			GLogger.debug("kColl before sending:");
			GLogger.debug(getKeyedCollection());
			// Send the message
			service.ccSendData(((String) getValueAt("HostBuff")));
			
			// Test for yossi
//			FormatElement fe = (FormatElement)FormatElement.readObject("accountBalanceTrxRequestFmt");			
//			String sendStr = (String)fe.format(this);
//			service.ccSendData(sendStr);
			
			getHostSemaphore().waitOn(10000);
			CCMessage hostReply = service.ccReceiveData(10000);
			
			
			// analyze message received 
			GLogger.debug("data received : " + hostReply.getDataReceived());
			GLogger.debug("return code received : " + hostReply.getReceiveRC());
			GLogger.debug("data sent : " + hostReply.getDataSent());
			GLogger.debug("timeout : " + hostReply.getTimeout());

			if (hostReply.getReceiveRC() != 0) {
				GLogger.error("*** Error in CICSOpStep ***");
				return;
			}
			
			// yossi test
//			FormatElement fromHostFormat = (FormatElement)FormatElement.readObject("accountBalanceTrxReplyFmt");
			FormatElement fromHostFormat = (FormatElement) getHostReceiveFormat();
			String dataFromHost = hostReply.getDataReceived();
			fromHostFormat.unformat(dataFromHost, this);
			
			GLogger.debug("kColl after sending:");
			GLogger.debug(getKeyedCollection());
			
			// Propper termination
			service.removeHandler(this, service.getCcDataReceivedEventName());
			service.removeHandler(this, service.getCcErrorReceivedEventName());
			pool.releasePoolService(service);
			
			GLogger.debug(getCSReplyFormat().format(getContext()));
			GLogger.debug(getKeyedCollection());			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			service.removeHandler(this, service.getCcDataReceivedEventName());
			service.removeHandler(this, service.getCcErrorReceivedEventName());
			pool.releasePoolService(service);
		}
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

	public void handleEvent(String anEventName, String aNotifierName, Context aContext) throws com.ibm.dse.base.DSEInvalidArgumentException {
		EventManager.addHandler(this, anEventName, aNotifierName, aContext);
	}
	public void handleEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext, String aTID)
		throws com.ibm.dse.base.DSEInvalidArgumentException {
		EventManager.addHandler(this, anEventName, aNotifierName, aContext, aTID);
	}
	public void stopHandlingEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext)
		throws com.ibm.dse.base.DSEInvalidArgumentException, com.ibm.dse.base.DSEHandlerNotFoundException {
		EventManager.removeHandler(this, anEventName, aNotifierName, aContext);
	}
	public void stopHandlingEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext, String aTID)
		throws com.ibm.dse.base.DSEInvalidArgumentException, com.ibm.dse.base.DSEHandlerNotFoundException {
		EventManager.removeHandler(this, anEventName, aNotifierName, aContext, aTID);
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
