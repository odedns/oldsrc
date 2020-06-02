package composer;

import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSENotifier;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyNotifier extends DSENotifier {

	/**
	 * Constructor for MyNotifier.
	 */
	public MyNotifier() {
		super();
	}

	/**
	 * Constructor for MyNotifier.
	 * @param arg0
	 */
	public MyNotifier(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor for MyNotifier.
	 * @param arg0
	 * @param arg1
	 */
	public MyNotifier(String arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for MyNotifier.
	 * @param arg0
	 */
	public MyNotifier(boolean arg0) {
		super(arg0);
	}
	
	public void sendEvent(String msg) throws Exception
	{
		System.out.println("in MyNotifier.sendEvent()");
		DSEEventObject event = new DSEEventObject(msg,this);
		signalEvent(event);
	}

}
