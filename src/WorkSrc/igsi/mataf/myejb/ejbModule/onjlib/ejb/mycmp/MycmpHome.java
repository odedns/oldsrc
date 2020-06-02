package onjlib.ejb.mycmp;
/**
 * Home interface for Enterprise Bean: Mycmp
 */
public interface MycmpHome extends javax.ejb.EJBHome {
	/**
	 * Creates an instance from a key for Entity Bean: Mycmp
	 */
	public onjlib.ejb.mycmp.Mycmp create(java.lang.Integer id)
		throws javax.ejb.CreateException, java.rmi.RemoteException;
	/**
	 * Finds an instance using a key for Entity Bean: Mycmp
	 */
	public onjlib.ejb.mycmp.Mycmp findByPrimaryKey(
		java.lang.Integer primaryKey)
		throws javax.ejb.FinderException, java.rmi.RemoteException;
}
