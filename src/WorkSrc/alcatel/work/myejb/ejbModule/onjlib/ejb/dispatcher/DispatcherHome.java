package onjlib.ejb.dispatcher;
/**
 * Home interface for Enterprise Bean: Dispatcher
 */
public interface DispatcherHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: Dispatcher
	 */
	public onjlib.ejb.dispatcher.Dispatcher create()
		throws javax.ejb.CreateException,
		java.rmi.RemoteException;
}
