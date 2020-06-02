package onjlib.ejb.dispatcher;

import java.rmi.RemoteException;

import onjlib.command.CommandParams;

/**
 * Remote interface for Enterprise Bean: Dispatcher
 */
public interface Dispatcher  {
	
	public CommandParams executeCommand(CommandParams params ) throws RemoteException;
}
