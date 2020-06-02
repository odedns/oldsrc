package onjlib.ejb.mycmp;
/**
 * Local Home interface for Enterprise Bean: Mycmp
 */
public interface MycmpLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Creates an instance from a key for Entity Bean: Mycmp
	 */
	public onjlib.ejb.mycmp.MycmpLocal create(java.lang.Integer id)
		throws javax.ejb.CreateException;
	/**
	 * Finds an instance using a key for Entity Bean: Mycmp
	 */
	public onjlib.ejb.mycmp.MycmpLocal findByPrimaryKey(
		java.lang.Integer primaryKey)
		throws javax.ejb.FinderException;
}
