package onjlib.ejb.mysession;
/**
 * Home interface for Enterprise Bean: Mysession
 */
public interface MysessionHome extends javax.ejb.EJBHome {
	/**
	 * Creates a default instance of Session Bean: Mysession
	 */
	public onjlib.ejb.mysession.Mysession create()
		throws javax.ejb.CreateException, java.rmi.RemoteException;
}
