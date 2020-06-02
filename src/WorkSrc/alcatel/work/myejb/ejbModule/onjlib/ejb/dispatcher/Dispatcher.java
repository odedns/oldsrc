package onjlib.ejb.dispatcher;

import java.rmi.RemoteException;

import onjlib.command.CommandParams;

/**
 * Remote interface for Enterprise Bean: Dispatcher
 */
public interface Dispatcher extends javax.ejb.EJBObject {
	
	public CommandParams executeCommand(CommandParams params ) throws RemoteException;
}
