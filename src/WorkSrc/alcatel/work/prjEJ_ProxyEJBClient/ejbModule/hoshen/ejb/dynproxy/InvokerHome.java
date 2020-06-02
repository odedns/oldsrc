package hoshen.ejb.dynproxy;
/**
 * Home interface for Enterprise Bean: Invoker
 */
public interface InvokerHome extends javax.ejb.EJBHome
{
	/**
	 * Creates a default instance of Session Bean: Invoker
	 */
	public Invoker create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
