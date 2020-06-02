package onjlib.ejb.dispatcher;

import javax.ejb.EJBLocalObject;


import onjlib.command.CommandParams;

/**
 * Local interface for Enterprise Bean: Dispatcher
 */

public interface DispatcherLocal extends EJBLocalObject {
	
	public CommandParams executeCommand(CommandParams params);
	
}