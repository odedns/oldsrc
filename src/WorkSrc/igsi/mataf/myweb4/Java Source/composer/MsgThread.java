package composer;

import java.io.InterruptedIOException;

import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MsgThread extends DSEHandler implements Runnable {

	
	/**
	 * Constructor for MsgThread.
	 */
	public MsgThread() {
		super();
	}
	
	public MsgThread(CSClientService cs , Context ctx) throws Exception
	{
		EventManager.registerInterestInRemoteEvent("allEvents","MyNotifier",cs);	
		handleEvent("allEvents","CSClient",ctx);
	}
	
	public Handler dispatchEvent(DSEEventObject event)
	{
		System.out.println("got event: " + event.toString());
		return(null);	
	}
	
	/**
	 * runs the thread.
	 */
	public void run()
	{
		System.out.println("MsgThread: running thread");
		while(true) {
			try {
				Thread.currentThread().sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();	
			}
			
			
		}		
	}
}
