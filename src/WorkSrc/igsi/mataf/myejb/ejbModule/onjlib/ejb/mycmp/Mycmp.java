package onjlib.ejb.mycmp;
/**
 * Remote interface for Enterprise Bean: Mycmp
 */
public interface Mycmp extends javax.ejb.EJBObject {
	/**
	 * Get accessor for persistent attribute: name
	 */
	public java.lang.String getName() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: name
	 */
	public void setName(java.lang.String newName)
		throws java.rmi.RemoteException;
	/**
	 * Get accessor for persistent attribute: description
	 */
	public java.lang.String getDescription() throws java.rmi.RemoteException;
	/**
	 * Set accessor for persistent attribute: description
	 */
	public void setDescription(java.lang.String newDescription)
		throws java.rmi.RemoteException;
}
