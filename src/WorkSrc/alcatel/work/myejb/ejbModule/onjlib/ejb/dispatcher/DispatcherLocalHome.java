package onjlib.ejb.dispatcher;
/**
 * Local Home interface for Enterprise Bean: Dispatcher
 */
public interface DispatcherLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Creates a default instance of Session Bean: Dispatcher
	 */
	public onjlib.ejb.dispatcher.DispatcherLocal create()
		throws javax.ejb.CreateException;
}
