/*
 * Created on 14/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.command;

import onjlib.ejb.dispatcher.*;
import onjlib.j2ee.ServiceLocator;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommandDispatcher {

	
	public static CommandParams executeCommand(CommandParams params)
	{
		CommandParams outParams = null;
		try {
			DispatcherHome dispHome = (DispatcherHome) ServiceLocator.getInstance().findObject("ejb/onjlib/ejb/dispatcher/DispatcherHome", DispatcherHome.class);
			Dispatcher disp = dispHome.create();
			outParams =  disp.executeCommand(params);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return(outParams);
		
	}
}
