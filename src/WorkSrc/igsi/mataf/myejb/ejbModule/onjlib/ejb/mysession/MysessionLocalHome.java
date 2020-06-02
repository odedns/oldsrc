package onjlib.ejb.mysession;
/**
 * Local Home interface for Enterprise Bean: Mysession
 */
public interface MysessionLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Creates a default instance of Session Bean: Mysession
	 */
	public onjlib.ejb.mysession.MysessionLocal create()
		throws javax.ejb.CreateException;
}
