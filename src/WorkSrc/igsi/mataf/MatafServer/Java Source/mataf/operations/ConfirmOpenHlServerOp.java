package mataf.operations;

import java.util.Date;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Semaphore;
import com.ibm.dse.services.comms.CCMessage;
import com.ibm.dse.services.comms.CommonCommunicationsService;
import com.ibm.dse.services.comms.DSECCException;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConfirmOpenHlServerOp extends DSEServerOperation implements Handler {

	private Semaphore sem;

	public void execute() throws Exception {

		setValueAt("taarichMatan", new Date());
		//		setValueAt("returnCode","0");

		// STEP ONE - Prepare The data
		setValueAt("HostBuff", ((FormatElement) getHostSendFormat()).format(this));

		System.out.println("Data to host:");
		System.out.println(getValueAt("HostBuff"));

		// STEP TWO - Send the data to the host
		handleEvent("allEvents", "host", getContext());
		CommonCommunicationsService ccs = (CommonCommunicationsService) getService("host");
		ccs.ccSendData((String) getValueAt("HostBuff"));
		getSem().waitOn(10000);
		CCMessage hostReply = null;
		try {
			hostReply = ccs.ccReceiveData(0);
		} catch (DSECCException ex) {
			ex.printStackTrace();
		}
		if (hostReply.getReceiveRC() != 0) {
			System.out.println("*** Error in ConfirmOpenHlServerOp ***");
			return;
		}
		FormatElement fromHostFormat = (FormatElement) getHostReceiveFormat();
		String dataFromHost = hostReply.getDataReceived();
		fromHostFormat.unformat(dataFromHost, this);
	}

	public Handler dispatchEvent(DSEEventObject anEvent) {
		System.out.println("ConfirmOpenHlServerOp Eevent:");
		System.out.println(anEvent);
		if (anEvent.getName().equals("allEvents")) {
			try {
				stopHandlingEvent("allEvents", "host", getContext());
				getSem().signalOn();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return this;
			}
		}
		return this;
	}

	public void handleEvent(String anEventName, String aNotifierName, Context aContext) throws com.ibm.dse.base.DSEInvalidArgumentException {
		com.ibm.dse.base.EventManager.addHandler(this, anEventName, aNotifierName, aContext);
	}
	public void handleEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext, String aTID)
		throws com.ibm.dse.base.DSEInvalidArgumentException {
		com.ibm.dse.base.EventManager.addHandler(this, anEventName, aNotifierName, aContext, aTID);
	}
	public void stopHandlingEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext)
		throws com.ibm.dse.base.DSEInvalidArgumentException, com.ibm.dse.base.DSEHandlerNotFoundException {
		com.ibm.dse.base.EventManager.removeHandler(this, anEventName, aNotifierName, aContext);
	}
	public void stopHandlingEvent(String anEventName, String aNotifierName, com.ibm.dse.base.Context aContext, String aTID)
		throws com.ibm.dse.base.DSEInvalidArgumentException, com.ibm.dse.base.DSEHandlerNotFoundException {
		com.ibm.dse.base.EventManager.removeHandler(this, anEventName, aNotifierName, aContext, aTID);
	}

	public Semaphore getSem() {
		if (sem == null)
			sem = new Semaphore();
		return sem;
	}
	/**
	 * Sets the sem
	 * @param sem The sem to set
	 */
	public void setSem(Semaphore sem) {
		this.sem = sem;
	}

}
