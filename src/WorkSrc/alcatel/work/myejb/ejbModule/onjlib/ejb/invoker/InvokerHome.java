package onjlib.ejb.invoker;
/**
 * Home interface for Enterprise Bean: Invoker
 */
public interface InvokerHome extends javax.ejb.EJBHome
{
	/**
	 * Creates a default instance of Session Bean: Invoker
	 */
	public onjlib.ejb.invoker.Invoker create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
