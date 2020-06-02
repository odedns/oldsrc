package composer;

import com.ibm.dse.base.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MyHandler extends DSEHandler {

	/**
	 * Constructor for MyHandler.
	 */
	public MyHandler() {
		super();
		System.out.println("MyHandler()");
	}
	
	
	public Handler dispatchEvent(DSEEventObject event)
	{
		System.out.println("got event: " + event.toString());
		return(null);	
	}
}
