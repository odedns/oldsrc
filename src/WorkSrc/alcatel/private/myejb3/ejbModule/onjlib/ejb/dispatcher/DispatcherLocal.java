package onjlib.ejb.dispatcher;

import javax.ejb.Local;

import onjlib.command.CommandParams;

/**
 * Local interface for Enterprise Bean: Dispatcher
 */
@Local
public interface DispatcherLocal extends Dispatcher {
	
	public CommandParams executeCommand(CommandParams params);
	
}